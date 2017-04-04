package xyz.gonzapico.exception;

/**
 * Created by gfernandez on 8/08/16.
 *
 * Interface to represent a wrapper around an {@link Exception} to manage errors.
 */
public interface ErrorBundle {
  Exception getException();

  String getErrorMessage();
}
