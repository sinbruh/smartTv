package no.ntnu.message;

import no.ntnu.server.TvLogic;

public class SetCurrentChannelCommand extends Command {
  int channelToSet;
  public SetCurrentChannelCommand(String channelAsString) {
      channelToSet = Integer.parseInt(channelAsString);
  }

  @Override
  public Message execute(TvLogic logic) {
    logic.setCurrentChannel(channelToSet);
    return new OkMessage();
  }

  @Override
  public String messageAsString() {
    return null;
  }
}
