package no.ntnu.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import no.ntnu.message.command.Command;
import no.ntnu.message.ErrorMessage;
import no.ntnu.message.Message;
import no.ntnu.message.MessageSerializer;

public class ClientHandler extends Thread {
  private BufferedReader socketReader;
  private PrintWriter socketWriter;
  private TvLogic tvLogic;
  private Socket socket;
  private TvServer tvServer;

  public ClientHandler(Socket socket, TvLogic logic, TvServer tvServer) {
    tvLogic = logic;
    this.socket = socket;
    this.tvServer = tvServer;
    initializeStreams();
  }

  public boolean initializeStreams() {
    boolean success = true;
    try {
      socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      socketWriter = new PrintWriter(socket.getOutputStream(), true);
    } catch (IOException e) {
      success = false;
      System.err.println("Could not initialize streams");
    }
    return success;
  }

  @Override
  public void run() {
    if (!initializeStreams()) {
      return;
    }

    System.out.println("Handling new client on " + Thread.currentThread().getName());

    Message response;
    do {
      Command clientCommand = readClientRequest();
      System.out.println("Received from client: " + clientCommand);
      try {
        response = clientCommand.execute(tvLogic);
      } catch (NullPointerException ep) {
        response = new ErrorMessage("Invalid command");
      } catch (Exception e) {
        response = new ErrorMessage(e.getMessage());
      }
      if (response != null) {
        sendResponseToClient(response);
      }
    } while (response != null);
  }

  /**
   * Read one message from the TCP socket - from the client.
   *
   * @return The received client message, or null on error
   */
  private Command readClientRequest() {
    Message clientCommand = null;
    try {
      String rawClientRequest = socketReader.readLine();
      clientCommand = MessageSerializer.fromString(rawClientRequest);
      if (!(clientCommand instanceof Command)) {
        System.err.println("Wrong message from the client: " + clientCommand);
        clientCommand = null;
      }
    } catch (IOException e) {
      System.err.println("Could not receive client request: " + e.getMessage());
    }
    return (Command) clientCommand;
  }

  /**
   * Send a response from the server to the client, over the TCP socket.
   *
   * @param response The response to send to the client, NOT including the newline
   */
  private void sendResponseToClient(Message response) {
    tvServer.notifyClients(response);
  }
}
