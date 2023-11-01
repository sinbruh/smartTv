package no.ntnu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class RemoteControl {
  public static void main(String[] args) {
    RemoteControl remoteControl = new RemoteControl();
    if (args.length > 0) {
      remoteControl.run(args[0], Integer.parseInt(args[1]));
    } else {
      remoteControl.run("localhost", TvServer.PORT_NUMBER);
    }
  }

  private void run(String hostname, int port) {
    try {
      Socket socket = new Socket(hostname, port);
      PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader socketReader = new BufferedReader(
          new InputStreamReader(socket.getInputStream()));

      commandLineLoop(socketWriter, socketReader);

    } catch (IOException e) {
      System.err.println("Failed to connect to server: " + e.getMessage());
    }
  }

  private void commandLineLoop(PrintWriter socketWriter, BufferedReader socketReader)
      throws IOException {
    System.out.println("Command line has started. Type \"help\" for a list of commands");
    boolean running = true;
    while (running) {
      Scanner scanner = new Scanner(System.in);

      if (scanner.hasNext()) {
        String command = scanner.nextLine();

        if (command.equals("exit")) {
          running = false;
        } else if (command.equals("help")) {
          System.out.println("1 - turn on TV");
          System.out.println("0 - turn off TV");
          System.out.println("c - get the tv's number of channels");
          System.out.println("g - get the current active channel");
          System.out.println("s## - set the active channel to the number ##");
          System.out.println("help - list available commands");
          System.out.println("exit - quit the program");
        } else {
          sendCommandToServer(socketWriter, socketReader, command);
        }
      }
    }
  }

  private void sendCommandToServer(PrintWriter socketWriter, BufferedReader socketReader,
                                   String command) throws IOException {
    socketWriter.println(command);
    String serverResponse = socketReader.readLine();
    System.out.println("Server: " + convertServerResponse(serverResponse));
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