package com.eiv.utiles;

import java.util.function.Supplier;

import com.eiv.exceptions.NotFoundServiceException;

public class ExceptionUtils {

    public static Supplier<? extends RuntimeException> notFoundExceptionSupplier(
            String message, Object... args) {
        return () -> new NotFoundServiceException(message, args);
    }
}
