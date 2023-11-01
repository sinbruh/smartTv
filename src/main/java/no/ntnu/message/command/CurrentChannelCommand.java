package no.ntnu.message.command;

import no.ntnu.server.TvLogic;
import no.ntnu.message.CurrentChannelMessage;
import no.ntnu.message.Message;

public class CurrentChannelCommand extends Command {
  @Override
  public String messageAsString() {
    return null;
  }

  @Override
  public Message execute(TvLogic logic) {

    return new CurrentChannelMessage(logic.getCurrentChannel());
  }
}
