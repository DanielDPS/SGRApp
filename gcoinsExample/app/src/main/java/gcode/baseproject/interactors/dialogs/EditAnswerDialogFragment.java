package gcode.baseproject.interactors.dialogs;


import android.app.Dialog;
import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import gcode.baseproject.R;
import gcode.baseproject.domain.model.question.Question;
import gcode.baseproject.interactors.date.CurrentDate;
import gcode.baseproject.interactors.db.entities.OptionEntity;
import gcode.baseproject.interactors.db.entities.QuestionEntity;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;
import gcode.baseproject.view.ui.account.MainActivity;
import gcode.baseproject.view.ui.gallery.GalleryFragment;
import gcode.baseproject.view.viewmodel.dataQuestion.QuestionDataViewModel;
import gcode.baseproject.view.viewmodel.option.OptionViewModel;

public class EditAnswerDialogFragment extends DialogFragment {



    private  QuestionDataEntity Answer;
    private QuestionEntity Question;
    private  EditText txtAnswer,txtObservation;
    private RadioButton rbYes ;
    private RadioButton rbNo ;
    private RadioButton rbNA;
    private  TextInputLayout lObservation;
    private   OptionEntity option;
    private RadioGroup radioGroup;
    private QuestionDataViewModel questionDataViewModel;
    private  OptionViewModel optionViewModel;
    private  String idOptionSelected;
    private  TextInputLayout lOptions;
    private  TextInputLayout lInput;
    private  TextInputLayout lCamera;
    public void setAnswer(QuestionDataEntity answer){
        this.Answer = answer;
    }
    public void setQuestion(QuestionEntity question){
        this.Question= question;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questionDataViewModel= ViewModelProviders.of(this).get(QuestionDataViewModel.class);
        optionViewModel = ViewModelProviders.of(this).get(OptionViewModel.class);

        if (Question.getFieldtype().equals("radiobutton")){
            lOptions.setVisibility(View.VISIBLE);
            RefreshOptions(Question.getId());
        }
        else if (Question.getFieldtype().equals("camera")){
            lCamera.setVisibility(View.VISIBLE);
        }else {
            lInput.setVisibility(View.VISIBLE);
            txtAnswer.setText(Answer.getTextQuestion());
        }


    }

    public  void RefreshOptions (String fkquestion){
        optionViewModel.ClearOptions(this);
        optionViewModel.LoadOptionsByIdQuestion(fkquestion);
        optionViewModel.mgetOptionsEntity().observe(this, new Observer<List<OptionEntity>>() {
            @Override
            public void onChanged(List<OptionEntity> optionEntities) {

                for (OptionEntity option :optionEntities){
                    RadioButton radioButton = new RadioButton(getContext());
                    radioButton.setText(option.getDescription());
                    radioGroup.addView(radioButton);

                        switch (radioButton.getText().toString()){
                            case "SI":
                                idOptionSelected = option.getId();
                                if (Answer.getFkoption().equals(idOptionSelected))
                                    radioButton.setChecked(true);

                                break;
                            case "NO":
                                idOptionSelected=option.getId();
                                if (Answer.getFkoption().equals(idOptionSelected))
                                    radioButton.setChecked(true);
                                break;
                            case "N.A.":
                                idOptionSelected= option.getId();
                                if (Answer.getFkoption().equals(idOptionSelected))
                                    radioButton.setChecked(true);
                                break;
                        }


                }


            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.dialog_new_header,container,false);

        Toolbar toolbar =view.findViewById(R.id.toolbar);
        toolbar.setTitle("Editar pregunta");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        final ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }
        setHasOptionsMenu(true);

         txtAnswer= view.findViewById(R.id.txtAnswer);
         txtObservation = view.findViewById(R.id.txtObservation);
         radioGroup = view.findViewById(R.id.radioGroup);

        lInput = view.findViewById(R.id.capaInput);
        lOptions = view.findViewById(R.id.capaRadioButtons);
        lCamera = view.findViewById(R.id.capaCamera);
        lObservation = view.findViewById(R.id.capaObservacion);



        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.edit_question){
            QuestionDataEntity questionDataEntity= new QuestionDataEntity();
            questionDataEntity.setId(Answer.getId());
            questionDataEntity.setEdited(CurrentDate.showDate());
            questionDataEntity.setFkformatData(Answer.getFkformatData());
            questionDataEntity.setFkquestion(Answer.getFkquestion());
            questionDataEntity.setFksectionData(Answer.getFksectionData());
            questionDataEntity.setFkoption(idOptionSelected);
            questionDataEntity.setTextQuestion(txtAnswer.getText().toString());
            questionDataEntity.setObservation(txtObservation.getText().toString());
            questionDataViewModel.UpdateQuestionData(questionDataEntity);
            Toast.makeText(getContext(),"Se actualizo correctamente",Toast.LENGTH_SHORT).show();
        return true;
       }else if (id ==android.R.id.home){
                dismiss();
                return true;
        }else if (id== R.id.view_dialog){

            new MaterialDialog.Builder(getContext())
                    .title("Titulo")
                    .content("BODY")
                    .positiveText("Aceptar")
                    .negativeText("Cancelar")
                    .iconRes(R.drawable.ic_delete_product)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }
}
