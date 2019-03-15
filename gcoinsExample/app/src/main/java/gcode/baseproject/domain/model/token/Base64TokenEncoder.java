package gcode.baseproject.domain.model.token;

import android.util.Base64;

public class Base64TokenEncoder extends TokenEncoder {
    @Override
    public String encode(String key) {
        byte[] data  = key.getBytes();
        String base64 = Base64.encodeToString(data,Base64.URL_SAFE );
        return base64;
    }
}
