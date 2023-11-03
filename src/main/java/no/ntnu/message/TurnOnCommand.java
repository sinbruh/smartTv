package no.ntnu.message;

import no.ntnu.server.TvLogic

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
