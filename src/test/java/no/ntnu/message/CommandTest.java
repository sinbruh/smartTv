package no.ntnu.message;

import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.TvLogic;
import org.junit.jupiter.api.Test;

class CommandTest {
  private static final int CHANNEL_COUNT = 5;

  @Test
  void testChannelCountWhenTvIsOff() {
    ChannelCountCommand c = new ChannelCountCommand();
    TvLogic tvLogic = new TvLogic(CHANNEL_COUNT);
    Message response = c.execute(tvLogic);
    assertTrue(response instanceof ErrorMessage);
  }
  
  @Test
  void testChannelCount() {
    TvLogic tvLogic = new TvLogic(CHANNEL_COUNT);
    tvLogic.turnTvOn();
    ChannelCountCommand c = new ChannelCountCommand();
    Message response = c.execute(tvLogic);
    assertTrue(response instanceof  ChannelCountMessage);
    ChannelCountMessage channelCountMessage = (ChannelCountMessage) response;
    assertEquals(CHANNEL_COUNT, channelCountMessage.channelCount);
  }

  @Test
  void testTurnOn() {
    TvLogic logic = new TvLogic(CHANNEL_COUNT);
    TurnOnCommand c = new TurnOnCommand();
    Message response = c.execute(logic);
    assertTrue(response instanceof OkMessage);
  }
}