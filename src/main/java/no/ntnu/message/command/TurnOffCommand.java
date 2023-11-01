package no.ntnu.message.command;

import no.ntnu.server.TvLogic;
import no.ntnu.message.Message;
import no.ntnu.message.OkMessage;

public class TurnOffCommand extends Command {

  @Override
  public Message execute(TvLogic logic){
    logic.turnTvOff();
    return new OkMessage();
  }

  @Override
  public String messageAsString() {
    return null;
  }
}
