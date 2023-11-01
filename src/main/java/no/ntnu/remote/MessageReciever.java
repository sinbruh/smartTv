package no.ntnu.remote;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageReciever extends Thread {
  BufferedReader socketReader;
  public MessageReciever(Socket socket) {
    try {
      socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      System.out.println("Handling server response on " + Thread.currentThread().getName());
    } catch (IOException e) {
      System.err.println("Could not create socket");
    }
  }

  @Override
  public void run() {
    try {
      String serverResponse;
      while ((serverResponse = socketReader.readLine()) != null) {
        System.out.println("Server: " + convertServerResponse(serverResponse));
      }
    } catch (IOException e) {
      System.err.println("Error while receiving server response: " + e.getMessage());
    } finally {
      try {
        socketReader.close();
      } catch (IOException e) {
        System.err.println("Error while closing the socket reader: " + e.getMessage());
      }
    }
  }

  /**
   * Converts the response from the server to something that is more readable for the user.
   *
   * @param response The server's response. Possible responses are defined in Protocol.md
   * @return Returns a converted response that is more readable for the end user.
   */
  private String convertServerResponse(String response) {
    String convertedResponse;

    switch (response.charAt(0)) {
      case 'o' -> convertedResponse = "Action was successfully performed";

      case 'c' -> convertedResponse = "Number of channels is " + response.substring(1);
      case 'e' -> convertedResponse = "Error! " + response.substring(1);
      case 'a' -> convertedResponse = "Current channel is " + response.substring(1);
      default -> convertedResponse = "Error: invalid response from server";
    }

    return convertedResponse;
  }
}
