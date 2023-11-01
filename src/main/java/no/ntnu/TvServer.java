package no.ntnu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import no.ntnu.message.Command;
import no.ntnu.message.ErrorMessage;
import no.ntnu.message.Message;
import no.ntnu.message.MessageSerializer;

/**
 * no.ntnu.TvServer - Handles TCP communication.
 */
public class TvServer {
  public static final int PORT_NUMBER = 1025;
  private static final char CHANNEL_COUNT_COMMAND = 'c';
  private static final char TURN_ON_COMMAND = '1';
  private static final char TURN_OFF_COMMAND = '0';
  private static final char SET_CHANNEL_COMMAND = 's';
  private static final char GET_CURRENT_CHANNEL_COMMAND = 'g';
  boolean isTcpServerRunning;
  private BufferedReader socketReader;
  private PrintWriter socketWriter;
  private TvLogic tvLogic;

  public TvServer(TvLogic tvLogic) {
    this.tvLogic = tvLogic;
  }

  /**
   * Start TCP server for this TV.
   */
  public void startServer(int port) {
    ServerSocket listeningSocket = openListeningSocket(port);
    System.out.println("Server listening on port " + port);
    if (listeningSocket != null) {
      isTcpServerRunning = true;
      while (isTcpServerRunning) {
        Socket clientSocket = acceptNextClientConnection(listeningSocket);
        if (clientSocket != null) {
          System.out.println("New client connected from " + clientSocket.getRemoteSocketAddress());
          handleClient();
        }
      }
    }
  }

  private ServerSocket openListeningSocket(int port) {
    ServerSocket listeningSocket = null;
    try {
      listeningSocket = new ServerSocket(port);
    } catch (IOException e) {
      System.err.println("Failed to open listening socket: " + e.getMessage());
    }
    return listeningSocket;
  }

  private Socket acceptNextClientConnection(ServerSocket listeningSocket) {
    Socket clientSocket = null;
    try {
      clientSocket = listeningSocket.accept();
      socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
    } catch (IOException e) {
      System.err.println("Failed to open client socket: " + e.getMessage());
    }
    return clientSocket;
  }

  private void handleClient() {
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
    socketWriter.println(MessageSerializer.toString(response));
  }
}
