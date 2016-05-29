package org.mishpaha.project.exception;

public class DaoMistakeException extends Exception{

    private static final String BASE_MESSAGE = "DAO mistake happened. ";

    public DaoMistakeException() {
        super(BASE_MESSAGE);
    }

    public DaoMistakeException(String message) {
        super(BASE_MESSAGE + message);
    }
}
