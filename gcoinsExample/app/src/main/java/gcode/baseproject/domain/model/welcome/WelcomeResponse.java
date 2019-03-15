package gcode.baseproject.domain.model.welcome;
import com.google.gson.annotations.SerializedName;
public class WelcomeResponse {

    private boolean mSuccess;
    private String mMessage;
    @SerializedName("result")
    private Greeting mGreeting;

    public void setSuccess(boolean success) {
        mSuccess = success;
    }

    public boolean isSuccessful() {
        return mSuccess;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }

    public Greeting getGreeting() {
        return mGreeting;
    }

    public void setGreeting(Greeting greeting) {
        mGreeting = greeting;
    }
}
