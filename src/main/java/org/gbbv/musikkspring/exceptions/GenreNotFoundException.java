package org.gbbv.musikkspring.exceptions;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(String message) {
        super(message);
    }
}