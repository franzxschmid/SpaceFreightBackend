package de.hbt.planetexpressbackend.exception;

public class PartNotFoundException extends RuntimeException {

    public PartNotFoundException(Long id) {
        super("Could not find Part " + id);
    }
}
