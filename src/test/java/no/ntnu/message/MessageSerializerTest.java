package no.ntnu.message;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MessageSerializerTest {
  @Test
  void testChannelCountCommand() {
    Message m = MessageSerializer.fromString("c");
    assertNotNull(m);
    assertTrue(m instanceof ChannelCountCommand);
  }

}