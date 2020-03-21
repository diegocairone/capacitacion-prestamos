package com.eiv.manager.task;

public class PrestamoTaskExecutor {

    public <T> T run(PrestamoTask<T> operation) {
        return operation.execute();
    }
}
