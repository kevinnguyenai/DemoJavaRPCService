package JavaService.responses;

import JavaService.ResponseMessage;

public class Error extends ResponseMessage {
    private String              errorMessage;
    private String              errorType;
    private StackTraceElement[] stackTrace;

    public Error(Exception errorMessage) {
        this.errorMessage   = errorMessage.getMessage();
        this.errorType      = errorMessage.getClass().getName();
        this.stackTrace     = errorMessage.getStackTrace();
    }
}
