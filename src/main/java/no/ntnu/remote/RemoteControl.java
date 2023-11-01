package no.ntnu.remote;

import java.io.IOException;
import java.net.Socket;
import no.ntnu.server.TvServer;

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

      MessageSender messageSender = new MessageSender(socket);
      messageSender.start();

      MessageReciever messageReciever = new MessageReciever(socket);
      messageReciever.start();

    } catch (IOException e) {
      System.err.println("Failed to connect to server: " + e.getMessage());
    }
  }
  }