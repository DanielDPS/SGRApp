package gcode.baseproject.domain.repository.welcome;

import gcode.baseproject.domain.model.welcome.Greeting;
import io.reactivex.Single;

public interface IWelcomeRepository {
    Single<Greeting> getWelcomeGreeting(String token);
}
