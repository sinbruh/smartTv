package no.ntnu.message;

import no.ntnu.server.TvLogic

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
