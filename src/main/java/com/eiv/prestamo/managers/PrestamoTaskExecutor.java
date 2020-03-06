package com.eiv.prestamo.managers;

public class PrestamoTaskExecutor {

    public <T> T run(PrestamoTask<T> operation) {
        return operation.execute();
    }
}
