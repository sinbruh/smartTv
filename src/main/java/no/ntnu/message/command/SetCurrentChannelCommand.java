package no.ntnu.message.command;

import no.ntnu.server.TvLogic;
import no.ntnu.message.Message;
import no.ntnu.message.OkMessage;

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
