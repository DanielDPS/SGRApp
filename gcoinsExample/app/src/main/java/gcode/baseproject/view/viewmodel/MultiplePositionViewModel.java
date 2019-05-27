package gcode.baseproject.view.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import gcode.baseproject.view.viewmodel.general.BaseNetworkViewModel;

public class MultiplePositionViewModel extends BaseNetworkViewModel {


    private int Position;
    private  int PositionQuestion;

    public int getPositionQuestion() {
        return PositionQuestion;
    }

    public void setPositionQuestion(int positionQuestion) {
        PositionQuestion = positionQuestion;
    }

    public int getPosition() {
        return Position;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public MultiplePositionViewModel(@NonNull Application application) {
        super(application);
    }
}
