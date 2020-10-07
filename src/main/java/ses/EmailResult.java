package ses;

import java.util.Date;

public class EmailResult {
    public final String message;
    public final String timestamp;

    public EmailResult(String message) {
        this.message = message;
        this.timestamp = new Date().toString();
    }
}
