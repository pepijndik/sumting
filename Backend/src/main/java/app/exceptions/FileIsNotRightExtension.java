package app.exceptions;

public class FileIsNotRightExtension extends RuntimeException {
    public FileIsNotRightExtension(String message) {
        super(message);
    }
    public FileIsNotRightExtension(String message, Exception e) {
        super(message,e);
    }
}

