package gcode.baseproject.view.utils;

import org.json.JSONObject;

import okhttp3.ResponseBody;

public class ErrorUtils {

    public static String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
