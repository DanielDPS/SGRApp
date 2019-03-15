package gcode.baseproject.domain.model.welcome;

import com.google.gson.annotations.SerializedName;

public class Greeting {

    @SerializedName("saludo")
    private String mGreeting;

    @SerializedName("icono")
    private String mIcon;

    @SerializedName("actual")
    private String mActual;

    public void setMessage(String greeting) {
        mGreeting = greeting;
    }

    public String getMessage() {
        return mGreeting;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setActual(String actual) {
        mActual = actual;
    }

    public String getActual() {
        return mActual;
    }

}
