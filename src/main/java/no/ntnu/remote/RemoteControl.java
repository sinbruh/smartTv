package no.ntnu.remote;

import java.io.IOException;
import java.net.Socket;
import no.ntnu.server.TvServer;

/**
 * The main class for the remote control. Allows the user to send commands to the tv server.
 * Every action performed by the remote will receive a response from the server, to every single
 * remote control that is connected to the server. The remote control in its entirety consists of
 * the main class (this one), as well as a message sender and receiver objected that exists on
 * their separate threads.
 */
public class RemoteControl {
  /**
   * Creates a remote control and starts it. Can take in command line arguments to specify a
   * different hostname and port to connect to than the default one.
   * Format of args : 'hostname' 'port'
   *
   * @param args specify a different host and port than the default ones.
   */
  public static void main(String[] args) {
    RemoteControl remoteControl = new RemoteControl();
    if (args.length > 0) {
      remoteControl.run(args[0], Integer.parseInt(args[1]));
    } else {
      remoteControl.run("localhost", TvServer.PORT_NUMBER);
    }
  }

  /**
   * Starts the remote control, connects to a host and creates a message sender and receiver thread.
   *
   * @param hostname The address of the host.
   * @param port The port of the host to connect to.
   */
  private void run(String hostname, int port) {
    try {
      Socket socket = new Socket(hostname, port);

      MessageSender messageSender = new MessageSender(socket);
      messageSender.start();

      MessageReciever messageReciever = new MessageReciever(socket);
      messageReciever.start();

    } catch (IOException e) {
      System.err.println("Failed to connect to server: " + e.getMessage());
    }
  }
  }