package gcode.baseproject.domain.repository.token;
import gcode.baseproject.interactors.db.ApplicationDatabase;
import gcode.baseproject.interactors.db.dao.TokenDao;
import gcode.baseproject.interactors.db.entities.TokenEntity;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

public class TokenRepository implements ITokenRepository {

    private TokenDao mTokenDao;

    public TokenRepository() {
        mTokenDao = ApplicationDatabase.getDatabase().getTokenDao();
    }

    @Override
    public Single<Boolean> accountIsLoggedIn() {
        return mTokenDao.getCount().map(new Function<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer rowCount) throws Exception {
                return rowCount > 0;
            }
        });
    }

    @Override
    public Completable add(final String token) {
        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                TokenEntity tokenEntity = new TokenEntity();
                tokenEntity.setToken(token);
                mTokenDao.add(tokenEntity);
            }
        });
    }

    @Override
    public Completable remove() {
        return Completable.fromRunnable(new Runnable() {
            @Override
            public void run() {
                mTokenDao.remove();
            }
        });
    }

    @Override
    public Single<String> get() {
        return mTokenDao.getToken();
    }

}
