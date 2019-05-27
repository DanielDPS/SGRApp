package gcode.baseproject.domain.repository.option;

import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import gcode.baseproject.interactors.db.ApplicationDatabase;
import gcode.baseproject.interactors.db.dao.OptionDao;
import gcode.baseproject.interactors.db.entities.OptionEntity;
import io.reactivex.Single;

public class OptionRepository implements   IOptionRepository {

    private OptionDao optionDao;
    public OptionRepository(){
        optionDao = ApplicationDatabase.getDatabase().getOptionDao();

    }
    @Override
    public Single<List<OptionEntity>> getOptionByIdQuestion(String id) {
        return optionDao.getOptionsByQuestion(id);
    }

    @Override
    public OptionEntity getOptionEntity(String id) {
        GetOptionByIdAsyncTask task = new GetOptionByIdAsyncTask(optionDao,id);
        try {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  task.getOption();
    }
}
