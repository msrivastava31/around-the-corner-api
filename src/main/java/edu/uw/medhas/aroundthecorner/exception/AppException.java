package edu.uw.medhas.aroundthecorner.exception;

public class AppException extends RuntimeException {
    private static final long serialVersionUID = 17L;
    
    private int errorId;
    
    public AppException(String message) {
        this(400, message);
    }
    
    public AppException(String message, Throwable t) {
        super(message, t);
        this.errorId = 400;
    }
    
    public AppException(int errorId, String message) {
        super(message);
        this.errorId = errorId;
    }
    
    public int getErrorId() {
        return errorId;
    }
}
