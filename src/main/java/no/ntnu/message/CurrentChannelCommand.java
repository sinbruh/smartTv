package no.ntnu.message;

import no.ntnu.TvLogic;

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
