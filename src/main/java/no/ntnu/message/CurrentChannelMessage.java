package no.ntnu.message;

public class CurrentChannelMessage extends Message {
  int currentChannel;
  public CurrentChannelMessage(int channel) {
    currentChannel = channel;
  }
  @Override
  public String messageAsString() {
    return "a" + currentChannel;
  }
}
