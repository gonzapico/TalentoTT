package xyz.gonzapico.talentott.exception;

/**
 * Created by gfernandez on 1/03/17.
 */

public class GeonamesNotFoundException extends Exception {
  public GeonamesNotFoundException() {
    super();
  }

  public GeonamesNotFoundException(final String message) {
    super(message);
  }

  public GeonamesNotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public GeonamesNotFoundException(final Throwable cause) {
    super(cause);
  }
}
