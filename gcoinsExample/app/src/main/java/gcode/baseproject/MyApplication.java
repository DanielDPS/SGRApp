package gcode.baseproject;

import android.app.Application;


import net.danlew.android.joda.JodaTimeAndroid;

import gcode.baseproject.interactors.db.ApplicationDatabase;
import gcode.baseproject.domain.model.token.Base64TokenEncoder;
import gcode.baseproject.domain.model.token.TokenDecoder;
import gcode.baseproject.domain.repository.account.SessionManager;
import gcode.baseproject.domain.repository.token.ITokenRepository;
import gcode.baseproject.domain.repository.token.TokenRepository;
import gcode.baseproject.view.utils.API;

public class MyApplication extends Application {

    private ITokenRepository tokenRepository;
    private TokenDecoder tokenDecoder;
    private SessionManager mSessionManager;

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        ApplicationDatabase.init(getApplicationContext());
        tokenDecoder = new TokenDecoder(API.TOKEN_KEY, new Base64TokenEncoder());
        tokenRepository = new TokenRepository();
        mSessionManager = new SessionManager(this, tokenRepository, tokenDecoder);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        tokenRepository = null;
        tokenDecoder = null;
        mSessionManager = null;
    }

    public SessionManager getSessionManager() {
        return mSessionManager;
    }
}
