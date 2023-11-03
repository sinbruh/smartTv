package no.ntnu.remote;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * The message sender logic for the remote control. Will be instanced in a new thread whenever a
 * remote control is created. This class has methods for user input (in the command line) as well
 * as converting and sending the message to the server.
 */
public class MessageSender extends Thread {
  private Socket socket;
  public MessageSender(Socket socket) {
    this.socket = socket;
  }

  @Override
  public synchronized void run() {
    try {
      commandLineLoop(new PrintWriter(socket.getOutputStream(), true));
    } catch (IOException e) {
      System.err.println("could not initialize stream");
    }
  }

  private void commandLineLoop(PrintWriter socketWriter)
      throws IOException {
    System.out.println("Command line has started. Type \"help\" for a list of commands");

    boolean running = true;
    Scanner scanner = new Scanner(System.in);
    while (running) {
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
          socketWriter.println(command);
        }
      }
    }
  }
}
