package gcode.baseproject.interactors.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormatSectionsCardItemBinding;
import gcode.baseproject.domain.model.formatSection.FormatSection;
import gcode.baseproject.domain.model.question.Question;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;
import gcode.baseproject.interactors.db.entities.QuestionEntity;
import gcode.baseproject.view.ui.format.FormatFragment;
import gcode.baseproject.view.ui.format.FormatSectionsFragment;
import gcode.baseproject.view.viewmodel.data.SectionDataViewModel;
import gcode.baseproject.view.viewmodel.dataQuestion.QuestionDataViewModel;
import gcode.baseproject.view.viewmodel.format.FormatDataViewModel;
import gcode.baseproject.view.viewmodel.format.FormatViewModel;

public class FormatSectionsAdapter  extends  RecyclerView.Adapter<FormatSectionsAdapter.FormatSectionsViewHolder> {

    //List
    private List<FormatSectionEntity> sections;
    private LayoutInflater layoutInflater;
    //Click
    private FormatSectionPresenter presenter;
    //ViewModels
    private FormatViewModel formatViewModel;
    private SectionDataViewModel sectionDataViewModel;
    private QuestionDataViewModel questionDataViewModel;
    //Context
    private FormatSectionsFragment formatSectionsFragment;
    public FormatSectionsAdapter(List<FormatSectionEntity> sections,FormatSectionPresenter presenter,FormatSectionsFragment formatSectionsFragment){
        this.sections=sections;
        this.presenter = presenter;
        this.formatSectionsFragment = formatSectionsFragment;
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

    @Override
    public void onBindViewHolder(@NonNull final FormatSectionsViewHolder holder, final int position) {
        FormatSectionEntity formatSection = sections.get(position);
        formatViewModel = ViewModelProviders.of(this.formatSectionsFragment).get(FormatViewModel.class);
        sectionDataViewModel = ViewModelProviders.of(this.formatSectionsFragment).get(SectionDataViewModel.class);
        questionDataViewModel = ViewModelProviders.of(this.formatSectionsFragment).get(QuestionDataViewModel.class);
        Integer CountQuestions=formatViewModel.getQuestionsCountByIdSection(formatSection.getId());
        Integer CountAnswers= questionDataViewModel.getCountAnswersByFkSectionData(formatSection.getId());
        Integer CountSectionData =sectionDataViewModel.getCountSectionDataByFkSection(formatSection.getId());
        Integer result  =(CountSectionData) * (CountQuestions);

        String AnswersCount =String.valueOf(CountAnswers);
        String QuestionsCount= String.valueOf(CountQuestions);
        String MultipleCount= String.valueOf(result);

        holder.binding.setFormatSection(formatSection);
        holder.binding.setPresenter(presenter);
        if (formatSection.getMultipledescription().equals("")){
            //NO ES MULTIPLE
            holder.binding.formatSectionTotal.setText(AnswersCount+" / "+ QuestionsCount);
        }else {
            if (!formatSection.getIsmultipleref().equals("t")){

                holder.binding.formatSectionTotal.setText(AnswersCount+ "/"+MultipleCount);
            }else
            //ES MULTIPLE
            holder.binding.formatSectionTotal.setText(AnswersCount + "/"+MultipleCount);
        }
        holder.setIsRecyclable(false);
        if (AnswersCount.equals(QuestionsCount) || AnswersCount.equals(result) ){
            holder.binding.checked.setImageResource(R.drawable.ic_checked_true);
            holder.binding.capaSectionData.setEnabled(false);
            holder.binding.capaSectionData.setBackgroundColor(Color.LTGRAY);

        }else{
            holder.binding.checked.setImageResource(R.drawable.ic_checked_falsee);
            holder.binding.capaSectionData.setEnabled(true);
        }

        holder.binding.cardSection.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                holder.binding.capaSectionData.setBackgroundColor(Color.BLUE);
                return false;
            }
        });
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
