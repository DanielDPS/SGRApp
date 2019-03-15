package gcode.baseproject.interactors.adapters;

import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormCardItemBinding;
import gcode.baseproject.interactors.db.entities.QuestionEntity;
import gcode.baseproject.view.ui.format.FormFragment;
import gcode.baseproject.view.viewmodel.dataQuestion.QuestionDataViewModel;

public class FormAdapter extends  RecyclerView.Adapter<FormAdapter.FormViewHolder> {

    private List<QuestionEntity> questions;
    private LayoutInflater layoutInflater;
    private FormClickPresenter presenter;

    private FormFragment fragment;

    //Viewmodels
    private QuestionDataViewModel questionDataViewModel;
    public FormAdapter(List<QuestionEntity> questions,FormClickPresenter presenter,FormFragment formFragment){
        this.questions = questions;
        this.presenter=presenter;
        this.fragment = formFragment;
    }

    @NonNull
    @Override
    public FormViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        FormCardItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.form_card_item,parent,false);
        return new FormViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull FormViewHolder holder, int position) {
        QuestionEntity questionEntity = questions.get(position);
        questionDataViewModel = ViewModelProviders.of(this.fragment).get(QuestionDataViewModel.class);

        holder.binding.setQuestion(questionEntity);
        holder.binding.setPresenter(presenter);


        if (!questionEntity.getFieldtype().equals("")){
            if (questionDataViewModel.getCountQuestionsByFkQuestion(questionEntity.getId()) > 0){

                holder.binding.checked.setImageResource(R.drawable.ic_checked_true);
                holder.binding.capaForm.setEnabled(false);
                holder.binding.capaForm.setBackgroundColor(Color.LTGRAY);

            }else {
                holder.binding.checked.setImageResource(R.drawable.ic_checked_falsee);
                holder.binding.capaForm.setEnabled(true);

            }
        }




    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class FormViewHolder extends RecyclerView.ViewHolder{

        private FormCardItemBinding binding;
        public  FormViewHolder(FormCardItemBinding binding)
        {
            super(binding.getRoot());
            this.binding= binding;
        }
    }
    public  interface  FormClickPresenter{
        void OnClickQuestionPresenter(QuestionEntity questionEntity);
    }

}
