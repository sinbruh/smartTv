package no.ntnu.message.command;

import no.ntnu.server.TvLogic;
import no.ntnu.message.Message;

/**
 * A command sent from th client to the server (from remote to TV)
 */
public abstract class Command extends Message {
  /**
   * Execute the command.
   * @param logic The TV logic to be affected by this command.
   */
  public abstract Message execute(TvLogic logic);
}
