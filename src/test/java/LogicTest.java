import static org.junit.jupiter.api.Assertions.*;

import no.ntnu.TvLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LogicTest {
  TvLogic logic;
  final int CHANNEL_COUNT = 10;

  @BeforeEach
  void createLogic() {
    logic = new TvLogic(10);
  }

  @Test
  void testOffByDefault() {
    assertFalse(logic.isTvOn());
  }

  @Test
  void testTurnOn() {
    logic.turnTvOn();
    assertTrue(logic.isTvOn());
  }

  @Test
  void testTurnOff() {
    logic.turnTvOn();
    logic.turnTvOff();
    assertFalse(logic.isTvOn());
  }

  @Test
  void testGetNumberOfChannels() {
    logic.turnTvOn();
    assertEquals(CHANNEL_COUNT, logic.getNumberOfChannels());
  }

  @Test
  void testChannelCountFailsWhenTvIsOff() {
    assertThrows(IllegalStateException.class, () -> logic.getNumberOfChannels());
  }

  @Test
  void testSetChannelFailsWHenTvIsOff() {
    assertThrows(IllegalStateException.class, () -> logic.setCurrentChannel(4));
  }

  @Test
  void testTrySetInvalidChannel() {
    logic.turnTvOn();
    assertThrows(IllegalArgumentException.class, () -> logic.setCurrentChannel(-1));
    assertThrows(IllegalArgumentException.class, () -> logic.setCurrentChannel(CHANNEL_COUNT + 1));
  }
}