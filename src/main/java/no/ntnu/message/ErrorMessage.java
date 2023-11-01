package no.ntnu.message;

public class ErrorMessage extends Message {
  private String errorMessage;
  public ErrorMessage(String message) {
    errorMessage = message;
  }

  @Override
  public String messageAsString() {
    return "e" + errorMessage;
  }
}
