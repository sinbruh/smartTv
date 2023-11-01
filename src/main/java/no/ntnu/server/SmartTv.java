package no.ntnu.server;

/**
 * Run the whole Smart TV, including the TCP socket communication.
 */
public class SmartTv {

  /**
   * Runs the SmartTV, including the server.
   * @param args
   */
  public static void main(String[] args) {
    TvLogic tv = new TvLogic(10);
    TvServer server = new TvServer(tv);
    if (args.length > 0) {
      server.startServer(Integer.parseInt(args[0]));
    } else {
      server.startServer(TvServer.PORT_NUMBER);
    }
  }
}
