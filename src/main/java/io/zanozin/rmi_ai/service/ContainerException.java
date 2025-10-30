package io.zanozin.rmi_ai.service;

/**
 * Exception thrown when there is an error related to container operations.
 */
public class ContainerException extends RuntimeException {

    public ContainerException(String message) {
        super(message);
    }
}
