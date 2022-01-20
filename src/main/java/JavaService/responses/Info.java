package JavaService.responses;

import JavaService.ResponseMessage;

public class Info extends ResponseMessage{
    private String message;

    public Info(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
