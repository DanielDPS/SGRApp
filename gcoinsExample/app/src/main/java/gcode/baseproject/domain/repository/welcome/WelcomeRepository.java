package gcode.baseproject.domain.repository.welcome;

import gcode.baseproject.interactors.api.NetworkManager;
import gcode.baseproject.interactors.api.welcome.WelcomeService;
import gcode.baseproject.domain.model.welcome.Greeting;
import gcode.baseproject.domain.model.welcome.WelcomeResponse;

import io.reactivex.Single;
import io.reactivex.functions.Function;

public class WelcomeRepository implements IWelcomeRepository {


    private WelcomeService mWelcomeService;

    public WelcomeRepository() {

        mWelcomeService = NetworkManager
                .getInstance()
                .create(WelcomeService.class);
    }

    @Override
    public Single<Greeting> getWelcomeGreeting(String token) {
        Single<Greeting> greetingRequest = mWelcomeService.getWelcomeGreeting(token)
                .map(new Function<WelcomeResponse, Greeting>() {
                    @Override
                    public Greeting apply(WelcomeResponse welcomeResponse) throws Exception {
                        return welcomeResponse.getGreeting();
                    }
                });
        return greetingRequest;
    }

}
