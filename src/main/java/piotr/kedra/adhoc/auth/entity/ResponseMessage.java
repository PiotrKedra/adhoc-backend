package piotr.kedra.adhoc.auth.entity;

public class ResponseMessage {

    private String message;
    private int status;

    public ResponseMessage(String message, int status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
