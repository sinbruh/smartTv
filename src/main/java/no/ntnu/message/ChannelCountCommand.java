package no.ntnu.message;

import no.ntnu.TvLogic;

public class ChannelCountCommand extends Command{
  @Override
  public Message execute(TvLogic logic){
    Message response;
    try {
      int channelCount = logic.getNumberOfChannels();
      response = new ChannelCountMessage(channelCount);
    } catch (IllegalStateException e) {
      response = new ErrorMessage(e.getMessage());
    }
    return response;
  }

  @Override
  public String messageAsString() {
    return null;
  }
}
