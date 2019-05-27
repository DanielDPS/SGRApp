package gcode.baseproject.interactors.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormatSectionsCardItemBinding;
import gcode.baseproject.domain.model.formatSection.FormatSection;
import gcode.baseproject.domain.model.question.Question;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;
import gcode.baseproject.interactors.db.entities.QuestionEntity;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;
import gcode.baseproject.interactors.mappers.Section;
import gcode.baseproject.interactors.mappers.SectionManager;
import gcode.baseproject.interactors.mappers.SectionMapper;
import gcode.baseproject.view.ui.format.FormatFragment;
import gcode.baseproject.view.ui.format.FormatSectionsFragment;
import gcode.baseproject.view.utils.CircleTransformation;
import gcode.baseproject.view.viewmodel.data.SectionDataViewModel;
import gcode.baseproject.view.viewmodel.dataQuestion.QuestionDataViewModel;
import gcode.baseproject.view.viewmodel.format.FormatDataViewModel;
import gcode.baseproject.view.viewmodel.format.FormatViewModel;

public class FormatSectionsAdapter  extends  RecyclerView.Adapter<FormatSectionsAdapter.FormatSectionsViewHolder> {

    //List
    private List<FormatSectionEntity> sections;
    private LayoutInflater layoutInflater;
    //Click
    private   Integer countSectionData;
    private FormatSectionPresenter presenter;
    //ViewModels
    private FormatViewModel formatViewModel;
    private SectionDataViewModel sectionDataViewModel;
    private QuestionDataViewModel questionDataViewModel;
    private  int positionGlobal;
    //Context

    private FormatSectionsFragment formatSectionsFragment;
    public FormatSectionsAdapter(List<FormatSectionEntity> sections,FormatSectionPresenter presenter,FormatSectionsFragment formatSectionsFragment,int position){
        this.sections=sections;
        this.presenter = presenter;
        this.formatSectionsFragment = formatSectionsFragment;
        this.positionGlobal = position;
     }

     public  FormatSectionEntity getSectionByPosition(int position){
        return  sections.get(position);
     }

    @NonNull
    @Override
    public FormatSectionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        FormatSectionsCardItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.format_sections_card_item,parent,false);
        return new FormatSectionsViewHolder(binding);
    }
    public List<FormatSectionEntity> getSections(){
        return  this.sections;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull final FormatSectionsViewHolder holder, final int position) {
        positionGlobal = position;
        FormatSectionEntity formatSection = sections.get(positionGlobal);
        formatViewModel = ViewModelProviders.of(this.formatSectionsFragment).get(FormatViewModel.class);
        sectionDataViewModel = ViewModelProviders.of(this.formatSectionsFragment).get(SectionDataViewModel.class);
        questionDataViewModel = ViewModelProviders.of(this.formatSectionsFragment).get(QuestionDataViewModel.class);
        Integer CountQuestions=formatViewModel.getQuestionsCountByIdSection(formatSection.getId());



        Integer CountAnswers= questionDataViewModel.getCountAnswersByFkSectionData(formatSection.getId(),this.formatSectionsFragment.getFormat().getFdid());

        countSectionData = sectionDataViewModel.getCOUNTForSectionId(formatSection.getId(),this.formatSectionsFragment.getFormat().getFdid());
        Integer result  =(countSectionData)* (CountQuestions);

        String AnswersCount =String.valueOf(CountAnswers);
        String QuestionsCount= String.valueOf(CountQuestions);
        String MultipleCount= String.valueOf(result);

        holder.binding.setFormatSection(formatSection);
        holder.binding.setPresenter(presenter);


        /*
        if (formatSection.getMultipledescription().equals("")){
            //NO ES MULTIPLE
            holder.binding.formatSectionTotal.setText(AnswersCount+" / "+ QuestionsCount);

        }else {
            if (formatSection.getIsmultipleref().equals("t")){

                holder.binding.formatSectionTotal.setText(AnswersCount+ "/"+MultipleCount);
            }else{
                holder.binding.formatSectionTotal.setText(AnswersCount + "/"+MultipleCount);
            }
        }*/


        /*if (AnswersCount.equals(QuestionsCount)  ){
            holder.binding.checked.setImageResource(R.drawable.ic_checked_true);
            holder.binding.capaSectionData.setBackgroundColor(Color.parseColor("#B2EBF2"));

        }else{
            holder.binding.checked.setImageResource(R.drawable.ic_checked_falsee);
            holder.binding.capaSectionData.setBackgroundColor(Color.WHITE);
        }*/



        if (formatSection.getMultipledescription().equals("")){
            //NO ES MULTIPLE

            holder.binding.formatSectionTotal.setText(AnswersCount+" / "+ QuestionsCount);

            if (AnswersCount.equals(QuestionsCount)  ){
                holder.binding.checked.setImageResource(R.drawable.ic_checked_true);
                holder.binding.capaSectionData.setBackgroundColor(Color.parseColor("#B2EBF2"));

            }else{
                holder.binding.checked.setImageResource(R.drawable.ic_checked_falsee);
                holder.binding.capaSectionData.setBackgroundColor(Color.WHITE);
            }


        }else {


            if (formatSection.getIsmultipleref().equals("t")){
                //TAANQUE
                holder.binding.formatSectionTotal.setText(AnswersCount+ "/"+MultipleCount);


                if (AnswersCount.equals("0") && MultipleCount.equals("0")){
                    holder.binding.checked.setImageResource(R.drawable.ic_checked_falsee);
                    holder.binding.capaSectionData.setBackgroundColor(Color.WHITE);
                }
                else if (AnswersCount.equals(MultipleCount)  ){
                    holder.binding.checked.setImageResource(R.drawable.ic_checked_true);
                    holder.binding.capaSectionData.setBackgroundColor(Color.parseColor("#B2EBF2"));

                }else{
                    holder.binding.checked.setImageResource(R.drawable.ic_checked_falsee);
                    holder.binding.capaSectionData.setBackgroundColor(Color.WHITE);
                }

            }else{
                //DISPENSARIO
                holder.binding.formatSectionTotal.setText(AnswersCount+ "/"+MultipleCount);

                if (AnswersCount.equals("0") && MultipleCount.equals("0")){
                    holder.binding.checked.setImageResource(R.drawable.ic_checked_falsee);
                    holder.binding.capaSectionData.setBackgroundColor(Color.WHITE);
                }
                else if (AnswersCount.equals(MultipleCount)){
                    holder.binding.checked.setImageResource(R.drawable.ic_checked_true);
                    holder.binding.capaSectionData.setBackgroundColor(Color.parseColor("#B2EBF2"));

                }else{
                    holder.binding.checked.setImageResource(R.drawable.ic_checked_falsee);
                    holder.binding.capaSectionData.setBackgroundColor(Color.WHITE);
                }


            }
            //ES MULTIPLE

        }
        if (formatSection.getImage().equals("")){
            holder.binding.formatSectionImg.setImageResource(R.drawable.ic_format_section_icon);
        }else {
            Picasso.get()
                    .load("http://geniuscode.ddns.net/gcoins/assets/img/secciones/"+formatSection.getImage())
                    .resize(110,110)
                    .centerCrop()
                    .error(R.drawable.ic_error_message)
                    .transform(new CircleTransformation())
                    .into(holder.binding.formatSectionImg);
        }




    }
    @Override
    public int getItemCount() {
        return sections.size();
    }


    public static  class FormatSectionsViewHolder extends RecyclerView.ViewHolder{

        private FormatSectionsCardItemBinding binding;
        public FormatSectionsViewHolder(FormatSectionsCardItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public  interface  FormatSectionPresenter{
          void OnClickSectionPresenter(FormatSectionEntity formatSectionEntity);
    }
}
