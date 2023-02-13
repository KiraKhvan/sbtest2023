package com.example.sbtest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    public NotFoundException(Class cls, UUID entityId) {
        super(buildErrorMessage(cls, entityId));
    }

    static String buildErrorMessage(Class cls, UUID entityId) {
        StringBuilder builder = new StringBuilder();
        if (cls != null) {
            builder.append(cls.getCanonicalName());
        } else {
            builder.append("Entity");
        }
        builder.append(" ");
        if (entityId != null) {
            builder.append("with ID =");
            builder.append(entityId);
            builder.append(" ");
        }
        builder.append("not found.");
        return builder.toString();
    }
}
