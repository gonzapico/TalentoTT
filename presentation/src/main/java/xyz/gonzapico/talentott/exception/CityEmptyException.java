package xyz.gonzapico.talentott.exception;

/**
 * Created by gfernandez on 1/03/17.
 */

public class CityEmptyException extends Exception {
  public CityEmptyException() {
    super();
  }

  public CityEmptyException(final String message) {
    super(message);
  }

  public CityEmptyException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public CityEmptyException(final Throwable cause) {
    super(cause);
  }
}
