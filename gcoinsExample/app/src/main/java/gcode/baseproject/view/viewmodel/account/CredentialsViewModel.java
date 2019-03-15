package gcode.baseproject.view.viewmodel.account;

import androidx.databinding.ObservableField;
import gcode.baseproject.view.viewmodel.general.ObservableViewModel;

public class CredentialsViewModel extends ObservableViewModel {

    public ObservableField<String> password = new ObservableField<>();

    public ObservableField<String> username = new ObservableField<>();
}
