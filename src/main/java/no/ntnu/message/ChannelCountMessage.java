package no.ntnu.message;

public class ChannelCountMessage extends Message {
  int channelCount;
  public ChannelCountMessage(int channelCount) {
    this.channelCount = channelCount;
  }

  @Override
  public String messageAsString() {
    return "c" + channelCount;
  }
}
