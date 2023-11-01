package no.ntnu.message.command;

import no.ntnu.server.TvLogic;
import no.ntnu.message.Message;
import no.ntnu.message.OkMessage;

public class TurnOnCommand extends Command{

  @Override
  public Message execute(TvLogic logic){
    logic.turnTvOn();
    return new OkMessage();
  }

  @Override
  public String messageAsString() {
    return null;
  }
}
