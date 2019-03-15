package gcode.baseproject.view.viewmodel.general;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class LoadingViewModel extends ViewModel {

    public ObservableField<Boolean> isLoading = new ObservableField<>();
}
