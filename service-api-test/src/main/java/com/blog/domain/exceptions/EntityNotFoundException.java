package com.blog.domain.exceptions;

import java.util.UUID;

public class EntityNotFoundException extends DomainException {
    public EntityNotFoundException(String message) {
        super(message);
    }

    public <T> EntityNotFoundException(Class<T> entityClass, UUID id) {
        super("cannot find the " + entityClass.getSimpleName().toLowerCase() + " with id " + id);
    }
}
