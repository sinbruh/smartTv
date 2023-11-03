package no.ntnu.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import no.ntnu.message.Message;
import no.ntnu.message.MessageSerializer;


/**
 * no.ntnu.server.TvServer - Handles TCP communication.
 */
public class TvServer {
  public static final int PORT_NUMBER = 1025;
  private boolean isTcpServerRunning;
  private ArrayList<Socket> clients;
  private TvLogic tvLogic;

  public TvServer(TvLogic tvLogic) {
    this.tvLogic = tvLogic;
    this.clients = new ArrayList<>();
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
          clients.add(clientSocket);
          ClientHandler clientHandler = new ClientHandler(clientSocket, tvLogic, this);
          clientHandler.start();
        }
      }
    }
  }

  public void notifyClients(Message response) {
    for (Socket client : clients) {
      try {
        new PrintWriter(client.getOutputStream(), true)
            .println(MessageSerializer.toString(response));
        System.out.println("Sent response to " + client.getRemoteSocketAddress());
      } catch (IOException e) {
        System.err.println("IOException" + e.getMessage());
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
    } catch (IOException e) {
      System.err.println("Failed to open client socket: " + e.getMessage());
    }
    return clientSocket;
  }
}
