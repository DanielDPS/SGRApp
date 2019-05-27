package gcode.baseproject.interactors.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.itextpdf.text.Section;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormCardItemBinding;
import gcode.baseproject.domain.model.data.EvidenceData;
import gcode.baseproject.interactors.db.entities.FormatEntity;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;
import gcode.baseproject.interactors.db.entities.QuestionEntity;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;
import gcode.baseproject.view.ui.format.FormFragment;
import gcode.baseproject.view.utils.CircleTransformation;
import gcode.baseproject.view.viewmodel.data.SectionDataViewModel;
import gcode.baseproject.view.viewmodel.dataEvidence.EvidenceDataViewModel;
import gcode.baseproject.view.viewmodel.dataQuestion.QuestionDataViewModel;
import gcode.baseproject.view.viewmodel.format.FormatViewModel;

public class FormAdapter extends  RecyclerView.Adapter<FormAdapter.FormViewHolder> {

    private List<QuestionEntity> questions;
    private LayoutInflater layoutInflater;
    private FormClickPresenter presenter;

    private FormFragment fragment;

    //Viewmodels
    private QuestionDataViewModel questionDataViewModel;
    private EvidenceDataViewModel evidenceDataViewModel;
    private SectionDataViewModel sectionDataViewModel;
    private FormatViewModel formatViewModel;
    private  String idSectionData;
    private  int positionGlobal;
    public FormAdapter(List<QuestionEntity> questions,FormClickPresenter presenter,FormFragment formFragment,String id,int positionGlobal){
        this.questions = questions;
        this.presenter=presenter;
        this.fragment = formFragment;
        this.idSectionData = id;
        this.positionGlobal = positionGlobal;
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
    public  QuestionEntity getItemByPosition(int position){
        return  questions.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull FormViewHolder holder, int position) {
        positionGlobal = position;
        QuestionEntity questionEntity = questions.get(positionGlobal);
        questionDataViewModel = ViewModelProviders.of(this.fragment).get(QuestionDataViewModel.class);
        evidenceDataViewModel = ViewModelProviders.of(this.fragment).get(EvidenceDataViewModel.class);
        //sectionDataViewModel = ViewModelProviders.of(this.fragment).get(SectionDataViewModel.class);
        //formatViewModel = ViewModelProviders.of(this.fragment).get(FormatViewModel.class);
        // SectionDataEntity sectionDataEntity = sectionDataViewModel.getObjectSectionDataByFkSection(questionEntity.getFksection());
        holder.binding.setQuestion(questionEntity);
        holder.binding.setPresenter(presenter);






        QuestionDataEntity question =  questionDataViewModel.getCountQuestionsByFkQuestion(questionEntity.getId(),fragment.getFormatDataEntity().getFdid());


        if (question !=null){
            if (questionEntity.getFieldtype().equals("inputtext")){
                holder.binding.checked.setImageResource(R.drawable.ic_checked_true);
                holder.binding.capaForm.setBackgroundColor(Color.parseColor("#B2EBF2"));

                /*if (questionEntity.getFieldtype().equals("camera"))
                {
                    Integer countEvidence = evidenceDataViewModel.getCountEvidence(question.getId());
                    if (countEvidence>0){
                        holder.binding.checked.setImageResource(R.drawable.ic_checked_true);
                        holder.binding.capaForm.setBackgroundColor(Color.parseColor("#B2EBF2"));
                    }else{
                        holder.binding.checked.setImageResource(R.drawable.ic_checked_true);
                        holder.binding.capaForm.setBackgroundColor(Color.parseColor("#B2EBF2"));
                    }


                }*/

            }
            else if (questionEntity.getFieldtype().equals("radiobutton")){

                if (fragment.getFormatSectionEntity().getMultipledescription().equals("")){

                    holder.binding.checked.setImageResource(R.drawable.ic_checked_true);
                    holder.binding.capaForm.setBackgroundColor(Color.parseColor("#B2EBF2"));

                }else {

                    if (fragment.getFormatSectionEntity().getIsmultipleref().equals("t")){

                        if (questionDataViewModel.getCountQuestionsMultipleByIds(questionEntity.getId(),fragment.getFormatDataEntity().getFdid(),this.idSectionData)>0){
                            holder.binding.checked.setImageResource(R.drawable.ic_checked_true);
                            holder.binding.capaForm.setBackgroundColor(Color.parseColor("#B2EBF2"));
                        }else {

                            holder.binding.checked.setImageResource(R.drawable.ic_checked_falsee);
                            holder.binding.capaForm.setBackgroundColor(Color.WHITE);
                        }
                    }else {
                        if (questionDataViewModel.getCountQuestionsMultipleByIds(questionEntity.getId(),fragment.getFormatDataEntity().getFdid(),this.idSectionData)>0){

                            holder.binding.checked.setImageResource(R.drawable.ic_checked_true);
                            holder.binding.capaForm.setBackgroundColor(Color.parseColor("#B2EBF2"));
                        }else
                        {
                            holder.binding.checked.setImageResource(R.drawable.ic_checked_falsee);
                            holder.binding.capaForm.setBackgroundColor(Color.WHITE);
                        }
                    }
                }




            }

            if (questionEntity.getFieldtype().equals("camera"))
            {


                Integer countEvidence = evidenceDataViewModel.getCountEvidence(question.getId());
                if (countEvidence>0){
                    holder.binding.checked.setImageResource(R.drawable.ic_checked_true);
                    holder.binding.capaForm.setBackgroundColor(Color.parseColor("#B2EBF2"));
                }else{
                     holder.binding.checked.setImageResource(R.drawable.ic_checked_falsee);
                     holder.binding.capaForm.setBackgroundColor(Color.WHITE);
                }



            }



        }
        else {
            holder.binding.checked.setImageResource(R.drawable.ic_checked_falsee);
            holder.binding.capaForm.setBackgroundColor(Color.WHITE);


        }

        if (questionEntity.getImage().equals("")){
            holder.binding.formImg.setImageResource(R.drawable.ic_question_icon);
        }else {
            Picasso.get()
                    .load(questionEntity.getImage())
                    .resize(90,90)
                    .centerCrop()
                    .error(R.drawable.ic_error_message)
                    .into(holder.binding.formImg);
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
