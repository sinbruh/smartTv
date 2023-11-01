package no.ntnu.server;

/**
 * Smart TV - the logic.
 */
public class TvLogic {

  private boolean isTvOn;
  private int numberOfChannels;
  private int currentChannel;


  /**
   * Create a new Smart TV.
   *
   * @param numberOfChannels The total number of channels the TV has.
   */
  public TvLogic(int numberOfChannels) {
    if (numberOfChannels < 1) {
      throw new IllegalArgumentException("Number of channels must be a positive number");
    }

    this.numberOfChannels = numberOfChannels;
    isTvOn = false;
    currentChannel = 1;
  }

  /**
   * Checks whether the tv is on or off.
   * @return True when the TV is on, false when the TV is off
   */
  public boolean isTvOn() {
    return isTvOn;
  }

  /**
   * Turns on the tv.
   */
  public void turnTvOn() {
    isTvOn = true;
  }

  /**
   * Turns the TV off.
   */
  public void turnTvOff() {
    isTvOn = false;
  }

  /**
   * Returns the current channel.
   * @return the current channel.
   */
  public int getCurrentChannel() throws IllegalStateException {
    if (!isTvOn) {
      throw new IllegalStateException("Tv is not on.");
    }
    return currentChannel;
  }

  /**
   * Sets the current channel.
   * @param channel The channel number to set the tv to.
   */
  public void setCurrentChannel(int channel) throws IllegalArgumentException, IllegalStateException {
    if (channel < 0 || channel > numberOfChannels) {
      throw new IllegalArgumentException();
    }
    if (!isTvOn) {
      throw new IllegalStateException("Tv is not on.");
    }

    this.currentChannel = channel;
  }

  /**
   * Returns the number of channels.
   * @return the number of channels.
   */
  public int getNumberOfChannels() throws IllegalStateException {
    if (!isTvOn) {
      throw new IllegalStateException("Tv is not on.");
    }
    return this.numberOfChannels;
  }
}
