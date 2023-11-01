package no.ntnu.message;

import no.ntnu.message.command.ChannelCountCommand;
import no.ntnu.message.command.CurrentChannelCommand;
import no.ntnu.message.command.SetCurrentChannelCommand;
import no.ntnu.message.command.TurnOffCommand;
import no.ntnu.message.command.TurnOnCommand;

public class MessageSerializer {
  /**
   * Create message from a string, according to the communication protocol.
   * @param s The string sent over the communication channel
   * @return The logical message, as interpreted according to the protocol.
   */
  public static Message fromString(String s) {
    return switch (s.substring(0,1)) {
      case "c" -> new ChannelCountCommand();
      case "1" -> new TurnOnCommand();
      case "0" -> new TurnOffCommand();
      case "g" -> new CurrentChannelCommand();
      case "s" -> new SetCurrentChannelCommand(s.substring(1));
      default -> new ErrorMessage("Unknown command");
    };
  }

  public static String toString(Message message) {
    return message.messageAsString();
    }
}

