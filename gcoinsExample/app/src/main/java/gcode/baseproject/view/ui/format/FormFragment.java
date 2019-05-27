package gcode.baseproject.view.ui.format;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.material.button.MaterialButton;
import java.util.List;
import java.util.UUID;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormFragmentBinding;
import gcode.baseproject.interactors.adapters.ClickListener;
import gcode.baseproject.interactors.adapters.FormAdapter;
import gcode.baseproject.interactors.adapters.RecyclerTouchListener;
import gcode.baseproject.interactors.date.CurrentDate;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;
import gcode.baseproject.interactors.db.entities.OptionEntity;
import gcode.baseproject.interactors.db.entities.QuestionEntity;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;
import gcode.baseproject.interactors.dialogs.CreateDialog;
import gcode.baseproject.interactors.dialogs.ShowInfoSections;
import gcode.baseproject.interactors.md5.EncryptMD5;
import gcode.baseproject.view.ui.camera.CameraFragment;
import gcode.baseproject.view.ui.gallery.GalleryFragment;
import gcode.baseproject.view.ui.gallery.GalleryMultipleFragment;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.viewmodel.MultiplePositionViewModel;
import gcode.baseproject.view.viewmodel.data.SectionDataViewModel;
import gcode.baseproject.view.viewmodel.dataEvidence.EvidenceDataViewModel;
import gcode.baseproject.view.viewmodel.dataQuestion.QuestionDataViewModel;
import gcode.baseproject.view.viewmodel.format.FormatViewModel;
import gcode.baseproject.view.viewmodel.option.OptionViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;


public class FormFragment extends BaseFragment implements FormAdapter.FormClickPresenter {

    public static final String MY_POSITION_SECTION = "MyPositionSectionFile";
    public static final String MY_POSITION = "MyPositionFile";
    private  static String FORM_FRAGMENT_ARGS ="form_fragment_args";
    private  static String FORMAT_DATA_ARGS = "format_data_args";
    private  static  String SECTION_DATA_ARGS="section_data_args";
    private FormFragmentBinding formFragmentBinding;
    private FormatSectionEntity formatSectionEntity;
    private  FormatViewModel formatViewModel;
    private QuestionDataEntity questionDbByIdQuestion;
    private Integer CountAnswersQuestions;
    private OptionViewModel optionViewModel;
    private QuestionDataViewModel questionDataViewModel;
    private EvidenceDataViewModel evidenceDataViewModel;
    private SectionDataViewModel sectionDataViewModel;
    private MultiplePositionViewModel multiplePositionViewModel;
    private  SectionDataEntity sectionDataEntity;
    private FormatDataEntity formatDataEntity;
    private  int countPhoto=0;
    private CreateDialog myDialog;
    private  int questionPosition;
    private  android.app.AlertDialog alert;
      String reference;
    int folio =1;
    private  SectionDataEntity objectSectionData;
    public static String str_SaveFolderName;
    private  int multiplePosition;
    private   FormAdapter formAdapter;
    private   int positionMultipleSelect;
    private  SectionDataEntity sectionDataObject;
    private  AdapterSections adapterSectionMultiple;
    private  static int sectionPosition;

    public static final FormFragment newInstance(FormatSectionEntity formatSectionEntity,FormatDataEntity entity,SectionDataEntity sectionDataObject,int positionSection){
        FormFragment  formFragment= new FormFragment();
        Bundle args= new Bundle();
        args.putSerializable(FORM_FRAGMENT_ARGS,formatSectionEntity);
        args.putSerializable(FORMAT_DATA_ARGS,entity);
        args.putSerializable(SECTION_DATA_ARGS,sectionDataObject);
        sectionPosition = positionSection;
         formFragment.setArguments(args);
        return formFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){

            formatSectionEntity = (FormatSectionEntity) getArguments().getSerializable(FORM_FRAGMENT_ARGS);
            formatDataEntity = (FormatDataEntity)getArguments().getSerializable(FORMAT_DATA_ARGS);
            sectionDataObject = (SectionDataEntity)getArguments().getSerializable(SECTION_DATA_ARGS);


         }
    }

    public  FormatSectionEntity getFormatSectionEntity(){
        return this.formatSectionEntity;
    }
    //INICIALIZAR VIEWMODELS
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //VIEWMODEL
        formatViewModel = ViewModelProviders.of(this).get(FormatViewModel.class);
        optionViewModel = ViewModelProviders.of(this).get(OptionViewModel.class);
        sectionDataViewModel= ViewModelProviders.of(this).get(SectionDataViewModel.class);
        evidenceDataViewModel = ViewModelProviders.of(this).get(EvidenceDataViewModel.class);
        questionDataViewModel = ViewModelProviders.of(this).get(QuestionDataViewModel.class);
        multiplePositionViewModel = ViewModelProviders.of(this).get(MultiplePositionViewModel.class);
    }
    public  FormatDataEntity getFormatDataEntity(){
        return this.formatDataEntity;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        formFragmentBinding = FormFragmentBinding.inflate(inflater,container,false);
        return formFragmentBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private  void RefreshSections(final int position){
        sectionDataViewModel.ClearSectionData(this);
        sectionDataViewModel.LoadSectoionsData(formatSectionEntity.getId(),formatDataEntity.getFdid());
        sectionDataViewModel.getData()
                .observe(this, new Observer<List<SectionDataEntity>>() {
                    @Override
                    public void onChanged(List<SectionDataEntity> sectionDataEntities) {


                        adapterSectionMultiple = new AdapterSections(getContext(),R.layout.sections_item_view,sectionDataEntities);
                        formFragmentBinding.cmbMultiple.setAdapter(adapterSectionMultiple);
                    }


                    });



    }

    //ADAPTER SPINNER
    public class AdapterSections extends ArrayAdapter<SectionDataEntity> {

        private  Context mcontext ;
        private  List<SectionDataEntity> dataEntities;
        private  LayoutInflater inflater;
        private  int Resource;
        private  int positionGlobalMultiple;
        private  int folio ;
        public AdapterSections(@NonNull Context context, int resource, @NonNull List<SectionDataEntity> objects) {
            super(context, resource, objects);

            this.mcontext = context;
            this.inflater = LayoutInflater.from(context);
            this.Resource = resource;
            this.dataEntities = objects;
         }

        public int getPositionGlobalMultiple() {
            return positionGlobalMultiple;
        }

        public void setPositionGlobalMultiple(int positionGlobalMultiple) {
            this.positionGlobalMultiple = positionGlobalMultiple;
        }
        public  int getFolio (){
            return folio;
        }

        @Override
        public int getPosition(@Nullable SectionDataEntity item) {
            return super.getPosition(item);
        }

        @Nullable
        @Override
        public SectionDataEntity getItem(int position) {
            return super.getItem(position);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return CreateItemView(position,convertView,parent);
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return CreateItemView(position,convertView,parent);
        }

        private  View CreateItemView (int position, View convertView, ViewGroup parent){
            final  View view =this.inflater.inflate(this.Resource,parent,false);
            TextView customer = view.findViewById(R.id.txtSectionCustomer);
            SectionDataEntity sectionDataObject =dataEntities.get(position);
            FormatSectionEntity sectionObject = formatViewModel.getObjectSectionById(sectionDataObject.getFksection());
            folio = sectionDataObject.getFolio();
            if (sectionObject.getIsmultipleref().equals("t")){
                customer.setText(sectionObject.getMultipledescription()+ " "+String.valueOf(folio+" - "+sectionDataObject.getReference()));
            }else {
                customer.setText(sectionObject.getMultipledescription()+ " "+String.valueOf(folio));

            }
            setPositionGlobalMultiple(position);

            return  view;
        }
    }

    void AddSectionData(String reference){

        sectionDataEntity= new SectionDataEntity();
        sectionDataEntity.setId(UUID.randomUUID().toString());
        sectionDataEntity.setFksection(formatSectionEntity.getId());
        sectionDataEntity.setFkFormatData(formatDataEntity.getFdid());
        sectionDataEntity.setFolio(folio);
        sectionDataEntity.setReference(reference);

        if (sectionDataViewModel.getCountSectionDataByID(sectionDataEntity.getId()) >0){
            sectionDataViewModel.UpdateSecitonData(sectionDataEntity);
        }
        else {
            sectionDataViewModel.AddSectionData(sectionDataEntity);
            RefreshSections(multiplePosition);
        }
    }

    @SuppressLint("RestrictedApi")
    private  void VisibilityButtons(){
        formFragmentBinding.btnCreate.setVisibility(View.VISIBLE);
        formFragmentBinding.cmbMultiple.setVisibility(View.VISIBLE);

    }
    @SuppressLint("RestrictedApi")
    private  void HideButton(){
        formFragmentBinding.btnCreate.setVisibility(View.GONE);
        //formFragmentBinding.cmbMultiple.setVisibility(View.GONE);

    }
    @SuppressLint("RestrictedApi")
    private  void ShowButton(){
        formFragmentBinding.btnCreate.setVisibility(View.VISIBLE);
        //formFragmentBinding.cmbMultiple.setVisibility(View.GONE);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }
    //MOSTRAR DIALOGO DE REF EN EVENTO LOAD
    void DialogWithInput(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Referencia");
        alertDialog.setMessage("Escribe una referencia");

        final EditText input  = new EditText(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        input.setLayoutParams(layoutParams);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.ic_question);
        alertDialog.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reference = input.getText().toString();
                        VisibilityButtons();
                        AddSectionData(reference);
                        RefreshSections(multiplePosition);
                    }
                }
        );
        alertDialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        getNavigationManager().removeFragmentFromBackStack();
                    }
                }
        );
        alertDialog.show();
    }
    //MOSTRAR DIALOGO DE REF EN EVENTO CLICK
    void DialogWithInput2(){

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Referencia");
        alertDialog.setMessage("Escribe una referencia");

        final EditText input  = new EditText(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        input.setLayoutParams(layoutParams);
        alertDialog.setView(input);
        alertDialog.setIcon(R.drawable.ic_question);
        alertDialog.setPositiveButton("Aceptar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        reference = input.getText().toString();
                        folio = sectionDataViewModel.getLastFolioMultiple(formatSectionEntity.getId(),formatDataEntity.getFdid())+1;
                        VisibilityButtons();
                        AddSectionData(reference);
                        RefreshSections(multiplePosition);
                        LoadQuestion(objectSectionData.getId(),0);
                        Toast.makeText(getContext(),"Sección creada con éxito",Toast.LENGTH_SHORT).show();

                    }
                }
        );
        alertDialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        getNavigationManager().removeFragmentFromBackStack();
                    }
                }
        );
        alertDialog.show();
    }
    //RECARGAR PREGUNTAS
    private  void LoadQuestion(final String idSectionData, final int position){


        final FormAdapter.FormClickPresenter presenter= this;
        final FormFragment formFragment= this;
        formatViewModel.ClearQuestions(this);
        formatViewModel.LoadQuestionsDB(formatSectionEntity.getId());
        formatViewModel.mgetQuestionsDB()
                .observe(this, new Observer<List<QuestionEntity>>() {
                    @Override
                    public void onChanged(final List<QuestionEntity> questionEntities) {

                        formFragmentBinding.formRecycler.setHasFixedSize(true);
                        formFragmentBinding.formRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                        formAdapter =new FormAdapter(questionEntities,presenter,formFragment,idSectionData,position);
                        formFragmentBinding.formRecycler.setAdapter(formAdapter);
                        formFragmentBinding.formRecycler.getLayoutManager().scrollToPosition(position);



                        /*
                        SwipeToViewGalleryCallback swipeCallback = new SwipeToViewGalleryCallback(getContext()) {
                            @Override
                            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                                final int index  = viewHolder.getAdapterPosition();
                                QuestionEntity question = questionEntities.get(index);

                                try {
                                    if (questionDataViewModel.getCountQuestionsByFkQuestion(question.getId(), formatDataEntity.getFdid()) ==null){
                                        formAdapter.notifyItemChanged(index);
                                        return;
                                    }
                                    if (questionDataViewModel.getObjectAnswerMultiple(question.getId(),formatDataEntity.getFdid(),objectSectionData.getId()) == null  ){
                                        formAdapter.notifyItemChanged(index);
                                        return;
                                    }
                                    else if (questionDataViewModel.getObjectAnswerMultiple(question.getId(),formatDataEntity.getFdid(),objectSectionData.getId()) != null)   {
                                        getNavigationManager().addFragment(GalleryFragment.getInstance(questionDataViewModel.getObjectAnswerMultiple(question.getId(),formatDataEntity.getFdid(),objectSectionData.getId())));
                                        formAdapter.notifyItemChanged(index);
                                    }


                                }catch (Exception e) {
                                    e.printStackTrace();
                                    formAdapter.notifyItemChanged(index);
                                    return;
                                }

                            }
                        };
                        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
                        itemTouchHelper.attachToRecyclerView(formFragmentBinding.formRecycler);*/
                    }
                });
    }
    //EVENTO LOAD
    @Override
    public void onResume() {
        super.onResume();

        //REFERENCIAS COMPARTIDAS
        SharedPreferences settinsSection = getActivity().getSharedPreferences(MY_POSITION_SECTION,0);
        SharedPreferences.Editor editor = settinsSection.edit();
        editor.putInt("positionSection",sectionPosition);
        editor.commit();

        SharedPreferences settings = getActivity().getSharedPreferences(MY_POSITION, 0);
        questionPosition = settings.getInt("positionQ",0);


        if (formatSectionEntity.getMultipledescription().equals("")){
        }
        else {


            if (formatSectionEntity.getIsmultipleref().equals("t")){

                //si necesita dialogo con edittext
                sectionDataViewModel.ClearCountSections(this);
                sectionDataViewModel.LoadCountSections(formatSectionEntity.getId(),formatDataEntity.getFdid());
                sectionDataViewModel.getCountSections().observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer >0){
                            VisibilityButtons();
                            RefreshSections(multiplePosition);

                        }else {

                            DialogWithInput();
                            VisibilityButtons();
                            RefreshSections(multiplePosition);

                        }
                    }
                });


            }else {
                //Dispensario
                sectionDataViewModel.ClearCountSections(this);
                sectionDataViewModel.LoadCountSections(formatSectionEntity.getId(),formatDataEntity.getFdid());
                sectionDataViewModel.getCountSections().observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (integer >0){

                            VisibilityButtons();
                            RefreshSections(multiplePosition);

                        }else {
                            VisibilityButtons();
                            AddSectionData("");
                            RefreshSections(multiplePosition);
                        }

                    }
                });

            }

        }


        LoadQuestion(null,questionPosition);
        RefreshMultipleSpinner(questionPosition);

        formFragmentBinding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountAnswersQuestions=questionDataViewModel.getCOUNTMultipleQuestionsByFks(formatDataEntity.getFdid(),objectSectionData.getId());

                //TANQUES
                if (formatSectionEntity.getIsmultipleref().equals("t")){

                    try{

                        if (formAdapter.getItemCount()==CountAnswersQuestions){
                            DialogWithInput2();

                        }else {
                            Toast.makeText(getContext()," Aun te faltan preguntas por responder",Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(v.getContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }

                }else {

                    //DISPENSARIOS
                    try{


                        if (formAdapter.getItemCount()==CountAnswersQuestions){
                            folio = sectionDataViewModel.getLastFolioMultiple(formatSectionEntity.getId(),formatDataEntity.getFdid())+1;
                            VisibilityButtons();
                            AddSectionData("");
                            Toast.makeText(getContext(),"Sección creada con éxito",Toast.LENGTH_SHORT).show();
                            RefreshSections(multiplePosition);
                        }else {
                            Toast.makeText(getContext(),"Aun te faltan preguntas por responder",Toast.LENGTH_SHORT).show();
                        }


                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(v.getContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        formFragmentBinding.formRecycler.addOnItemTouchListener(new RecyclerTouchListener(getContext(), formFragmentBinding.formRecycler,
                new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {

                        QuestionEntity quesiton = formAdapter.getItemByPosition(position);
                        DialogWithButtons(quesiton,position);


                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        QuestionEntity quesiton = formAdapter.getItemByPosition(position);
                        if (quesiton.getFieldtype().equals("inputext")){

                            if(questionDataViewModel.getCountQuestionsByFkQuestion(quesiton.getId(),formatDataEntity.getFdid()) ==null){
                                return;
                            }else {
                                getNavigationManager().addFragment(GalleryMultipleFragment.getInstance(questionDataViewModel.getCountQuestionsByFkQuestion(quesiton.getId(), formatDataEntity.getFdid()).getId(),position));

                            }


                        }else if (quesiton.getFieldtype().equals("radiobutton")){
                            if (formatSectionEntity.getMultipledescription().equals("")){


                                    if(questionDataViewModel.getCountQuestionsByFkQuestion(quesiton.getId(),formatDataEntity.getFdid()) ==null){
                                        return;
                                    }else {
                                        if (formatSectionEntity.getDescription().equals("INSPECCIÓN DOCUMENTAL"))
                                        {
                                            return;
                                        }else {
                                            getNavigationManager().addFragment(GalleryMultipleFragment.getInstance(questionDataViewModel.getCountQuestionsByFkQuestion(quesiton.getId(), formatDataEntity.getFdid()).getId(),position ));
                                        }

                                    } 


                                


                            }else {
                                if (formatSectionEntity.getIsmultipleref().equals("t")){
                                    if(questionDataViewModel.getObjectAnswerMultiple(quesiton.getId(),formatDataEntity.getFdid(),objectSectionData.getId()) ==null){
                                        return;
                                    }else {
                                        getNavigationManager().addFragment(GalleryFragment.getInstance(questionDataViewModel.getObjectAnswerMultiple(quesiton.getId(),formatDataEntity.getFdid(),objectSectionData.getId()),position));
                                    }
                                }else {
                                    if(questionDataViewModel.getObjectAnswerMultiple(quesiton.getId(),formatDataEntity.getFdid(),objectSectionData.getId()) ==null){
                                        return;
                                    }else {
                                        getNavigationManager().addFragment(GalleryFragment.getInstance(questionDataViewModel.getObjectAnswerMultiple(quesiton.getId(),formatDataEntity.getFdid(),objectSectionData.getId()),position));

                                    }

                                }
                            }
                                
                        }else if (quesiton.getFieldtype().equals("camera")){
                            if(questionDataViewModel.getCountQuestionsByFkQuestion(quesiton.getId(),formatDataEntity.getFdid()) ==null){
                                return;
                            }else {
                                getNavigationManager().addFragment(GalleryMultipleFragment.getInstance(questionDataViewModel.getCountQuestionsByFkQuestion(quesiton.getId(), formatDataEntity.getFdid()).getId(),position ));
                            }
                        }

                    }
                }));




    }

    public  void RefreshMultipleSpinner(final int positionQuestion ){
          formFragmentBinding.cmbMultiple.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  objectSectionData = adapterSectionMultiple.getItem(adapterSectionMultiple.getPositionGlobalMultiple());
                  folio = sectionDataViewModel.getLastFolioMultiple(formatSectionEntity.getId(),formatDataEntity.getFdid());
                  if (objectSectionData.getFolio() != folio){
                      HideButton();
                  }
                  else{
                      ShowButton();
                  }
                  LoadQuestion(objectSectionData.getId()==null?null:objectSectionData.getId(),positionQuestion);
              }

              @Override
              public void onNothingSelected(AdapterView<?> parent) {

              }
          });
      }

    @NonNull
    @Override
    public String getFragmentTag() {
        return FormFragment.class.getName();
    }

    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        ToolbarBuilder builder = new ToolbarBuilder
                .Builder(formFragmentBinding.toolbar)
                .withTitle("Preguntas")
                .withBackButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNavigationManager().removeFragmentFromBackStack();
                    }
                })
                .build();
        return builder;
    }
    String idQuestionData;
    String pathEvidence;

    private  void EditQuestionRadiobuttons(QuestionDataEntity questionDataEntity,final int position){
        questionDataViewModel.AddQuestionData(questionDataEntity,formatSectionEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        LoadQuestion(objectSectionData.getId(),position );
                        Toast.makeText(getContext(), "Pregunta editada con éxito", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                });
        //RefreshSections(multiplePositionViewModel.getPosition());

    }

    void DialogWithButtons(final QuestionEntity questionEntity, final int positionGlobal){
        //Respuesta db
        final FormFragment contextFragment = this;
        //questionDbByIdQuestion =questionDataViewModel.getObjectQuestionByFkQuestion(questionEntity.getId(),formatDataEntity.getFdid(),objectSectionData.getId().equals(null)?null:objectSectionData.getId());
        questionDbByIdQuestion  =  questionDataViewModel.getCountQuestionsByFkQuestion(questionEntity.getId(),formatDataEntity.getFdid());


        optionViewModel.ClearOptions(this);
        optionViewModel.LoadOptionsByIdQuestion(questionEntity.getId());
        optionViewModel.mgetOptionsEntity()
                .observe(this, new Observer<List<OptionEntity>>() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onChanged(final List<OptionEntity> optionEntities) {



                        if (questionEntity.getFieldtype().equals("radiobutton")) {

                                if (formatSectionEntity.getMultipledescription().equals("")){

                                    //questionDataViewModel.getCountQuestionsByFkQuestion(questionEntity.getId(), formatDataEntity.getFdid())
                                    if (questionDbByIdQuestion != null){

                                        LayoutInflater inflater = LayoutInflater.from(getContext());
                                        View view = inflater.inflate(R.layout.dialog_radiobutton, null);
                                        final EditText txtObservaciones = view.findViewById(R.id.txtObservaciones);
                                        RadioGroup group = view.findViewById(R.id.radioGroup);
                                        final MaterialButton btnSave = view.findViewById(R.id.btnSave);
                                        final MaterialButton btnCancel = view.findViewById(R.id.btnCancel);
                                        btnSave.setText("Editar");
                                        myDialog = CreateDialog.getInstanceDialog();
                                        final android.app.AlertDialog.Builder builder = myDialog.openDialog2(getContext(), view);
                                        builder.setTitle("Editar opciones");
                                        builder.setMessage("Seleccione una opcion:");
                                        builder.setIcon(R.drawable.ic_radio_button);
                                        alert = builder.show();

                                        for (final OptionEntity optionEntity : optionEntities) {
                                            final RadioButton radioButton = new RadioButton(getContext());
                                            radioButton.setText(optionEntity.getDescription());

                                            group.addView(radioButton);
                                            ColorGenerator colorGenerator;
                                            colorGenerator = ColorGenerator.MATERIAL;
                                            int color = colorGenerator.getRandomColor();
                                            radioButton.setButtonTintList(ColorStateList.valueOf(color));
                                            switch (optionEntity.getDescription()) {
                                                case "SI":
                                                    if (questionDbByIdQuestion.getFkoption().equals(optionEntity.getId())) {
                                                        radioButton.setChecked(true);
                                                        txtObservaciones.setVisibility(View.GONE);
                                                        txtObservaciones.setText("");
                                                    }

                                                    break;
                                                case "NO":
                                                    if (questionDbByIdQuestion.getFkoption().equals(optionEntity.getId())) {
                                                        radioButton.setChecked(true);
                                                        txtObservaciones.setVisibility(View.VISIBLE);
                                                        txtObservaciones.setText(questionDbByIdQuestion.getObservation());
                                                    }
                                                    break;
                                                case "N.A.":
                                                    if (questionDbByIdQuestion.getFkoption().equals(optionEntity.getId())) {
                                                        radioButton.setChecked(true);
                                                    }

                                                    break;
                                            }

                                            radioButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    final RadioButton radioButton1 = (RadioButton) v;


                                                    btnSave.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                                QuestionDataEntity questionDataEntity = new QuestionDataEntity();
                                                                questionDataEntity.setId(questionDbByIdQuestion.getId());
                                                                questionDataEntity.setEdited(CurrentDate.showDate());
                                                                questionDataEntity.setFkformatData(questionDbByIdQuestion.getFkformatData());
                                                                questionDataEntity.setFkquestion(questionDbByIdQuestion.getFkquestion());
                                                                questionDataEntity.setFksectionData(null);
                                                                questionDataEntity.setFkoption(optionEntity.getId());
                                                                questionDataEntity.setTextQuestion(radioButton1.getText().toString());
                                                                questionDataEntity.setObservation(txtObservaciones.getText().toString());
                                                                idQuestionData = questionDataEntity.getId();
                                                                questionDataViewModel.AddQuestionData(questionDataEntity,formatSectionEntity)
                                                                        .subscribeOn(Schedulers.io())
                                                                        .observeOn(AndroidSchedulers.mainThread())
                                                                        .subscribe(new DisposableCompletableObserver() {
                                                                            @Override
                                                                            public void onComplete() {
                                                                                LoadQuestion(null,positionGlobal );
                                                                                Toast.makeText(getContext(), "Pregunta editada con éxito", Toast.LENGTH_LONG).show();
                                                                                alert.dismiss();
                                                                                if (formatSectionEntity.getDescription().equals("INSPECCIÓN DOCUMENTAL")){
                                                                                    return;
                                                                                }else {
                                                                                    getNavigationManager().addFragment(CameraFragment.newInstance(idQuestionData,positionGlobal,questionEntity,alert));
                                                                                }

                                                                            }

                                                                            @Override
                                                                            public void onError(Throwable e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        });


                                                        }
                                                    });
                                                    if (radioButton1.getText().toString().equals("NO")) {
                                                        txtObservaciones.setVisibility(View.VISIBLE);

                                                    } else
                                                        txtObservaciones.setVisibility(View.GONE);
                                                }

                                            });
                                        }
                                        btnCancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alert.dismiss();
                                            }
                                        });


                                    }else {




                                        LayoutInflater inflater = LayoutInflater.from(getContext());
                                        View view = inflater.inflate(R.layout.dialog_radiobutton, null);
                                        final EditText txtObservaciones = view.findViewById(R.id.txtObservaciones);
                                        RadioGroup group = view.findViewById(R.id.radioGroup);
                                        final MaterialButton btnSave = view.findViewById(R.id.btnSave);
                                        final MaterialButton btnCancel = view.findViewById(R.id.btnCancel);
                                        myDialog = CreateDialog.getInstanceDialog();
                                        final android.app.AlertDialog.Builder builder = myDialog.openDialog2(getContext(), view);
                                        builder.setTitle("Opciones");
                                        builder.setMessage("Seleccione una opcion:");
                                        builder.setIcon(R.drawable.ic_radio_button);

                                        final android.app.AlertDialog alertNormal;
                                        countPhoto = countPhoto+1;
                                        if (countPhoto==1){
                                            alertNormal = builder.show();
                                            countPhoto =0;

                                        }else {
                                            return;
                                        }


                                        for (final OptionEntity option : optionEntities) {

                                                final RadioButton radioButton = new RadioButton(getContext());

                                                radioButton.setText(option.getDescription());

                                                group.addView(radioButton);

                                                radioButton.setButtonTintList(ColorStateList.valueOf(R.color.colorPrimaryDark));

                                                radioButton.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {

                                                        final RadioButton radioButton1 = (RadioButton) v;


                                                        btnSave.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View v) {





                                                                final QuestionDataEntity questionDataEntity = new QuestionDataEntity();
                                                                questionDataEntity.setId( EncryptMD5.convertToMD5(String.valueOf(System.currentTimeMillis())));
                                                                questionDataEntity.setEdited(CurrentDate.showDate());
                                                                questionDataEntity.setFkformatData(formatDataEntity.getFdid());
                                                                questionDataEntity.setFkquestion(questionEntity.getId());
                                                                questionDataEntity.setFksectionData(null);
                                                                questionDataEntity.setFkoption(option.getId());
                                                                questionDataEntity.setTextQuestion(radioButton.getText().toString());
                                                                questionDataEntity.setObservation(txtObservaciones.getText().toString());
                                                                idQuestionData = questionDataEntity.getId();
                                                                questionDataViewModel.AddQuestionData(questionDataEntity,formatSectionEntity)
                                                                        .subscribeOn(Schedulers.io())
                                                                        .observeOn(AndroidSchedulers.mainThread())
                                                                        .subscribe(new DisposableCompletableObserver() {
                                                                            @Override
                                                                            public void onComplete() {
                                                                                alertNormal.dismiss();
                                                                                 LoadQuestion(questionDataEntity.getFksectionData(),positionGlobal);
                                                                                if (formatSectionEntity.getDescription().equals("INSPECCIÓN DOCUMENTAL")){
                                                                                    return;
                                                                                }else{
                                                                                     getNavigationManager().addFragment(CameraFragment.newInstance(idQuestionData,positionGlobal,questionEntity,alertNormal));
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onError(Throwable e) {
                                                                                e.printStackTrace();
                                                                            }
                                                                        });




                                                            }
                                                        });


                                                        if (radioButton1.getText().toString().equals("NO")) {
                                                            txtObservaciones.setVisibility(View.VISIBLE);
                                                            txtObservaciones.setHint("Escriba una observación");

                                                        } else
                                                            txtObservaciones.setVisibility(View.GONE);

                                                    }

                                                });



                                        }
                                        btnCancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alertNormal.dismiss();
                                            }
                                        });



                                    }

                                }else {



                                    //Integer evidenceCount = evidenceDataViewModel.getCountEvidence(questionEntity.getId());
                                    //questionDataViewModel.getCountQuestionsByFkQuestion(questionEntity.getId(), formatDataEntity.getFdid()) != null && questionDataViewModel.getCountQuestionsMultipleByIds(questionEntity.getId(),formatDataEntity.getFdid(),objectSectionData.getId())>0
                                    if ( questionDataViewModel.getCountQuestionsMultipleByIds(questionEntity.getId(),formatDataEntity.getFdid(),objectSectionData.getId())>0 ) {



                                                LayoutInflater inflater = LayoutInflater.from(getContext());
                                                View view = inflater.inflate(R.layout.dialog_radiobutton, null);
                                                final EditText txtObservaciones = view.findViewById(R.id.txtObservaciones);
                                                RadioGroup group = view.findViewById(R.id.radioGroup);
                                                final MaterialButton btnSave = view.findViewById(R.id.btnSave);
                                                final MaterialButton btnCancel = view.findViewById(R.id.btnCancel);
                                                btnSave.setText("Editar");
                                                myDialog = CreateDialog.getInstanceDialog();
                                                final android.app.AlertDialog.Builder builder = myDialog.openDialog2(getContext(), view);
                                                builder.setTitle("Editar opciones");
                                                builder.setMessage("Seleccione una opcion:");
                                                builder.setIcon(R.drawable.ic_radio_button);
                                                alert = builder.create();


                                                for (final OptionEntity optionEntity : optionEntities) {
                                                    final RadioButton radioButton = new RadioButton(getContext());
                                                    radioButton.setText(optionEntity.getDescription());

                                                    group.addView(radioButton);
                                                    ColorGenerator colorGenerator;
                                                    colorGenerator = ColorGenerator.MATERIAL;
                                                    int color = colorGenerator.getRandomColor();
                                                    radioButton.setButtonTintList(ColorStateList.valueOf(color));
                                                    switch (optionEntity.getDescription()) {
                                                        case "SI":
                                                            questionDataViewModel.LoadQuestionMultiple(questionEntity.getId(),formatDataEntity.getFdid(),objectSectionData.getId());
                                                            questionDataViewModel.getGetQuestionMuliple().observe(contextFragment, new Observer<QuestionDataEntity>() {
                                                                @Override
                                                                public void onChanged(QuestionDataEntity questionMultiple) {
                                                                    if (questionMultiple.getFkoption().equals(optionEntity.getId())) {
                                                                        radioButton.setChecked(true);
                                                                        txtObservaciones.setVisibility(View.GONE);
                                                                        txtObservaciones.setText("");
                                                                    }

                                                                }
                                                            });



                                                            break;
                                                        case "NO":
                                                            questionDataViewModel.LoadQuestionMultiple(questionEntity.getId(),formatDataEntity.getFdid(),objectSectionData.getId());
                                                            questionDataViewModel.getGetQuestionMuliple().observe(contextFragment, new Observer<QuestionDataEntity>() {
                                                                @Override
                                                                public void onChanged(QuestionDataEntity questionMultiple) {
                                                                         if (questionMultiple.getFkoption().equals(optionEntity.getId())) {
                                                                            radioButton.setChecked(true);
                                                                            txtObservaciones.setVisibility(View.VISIBLE);
                                                                            txtObservaciones.setText(questionMultiple.getObservation());
                                                                        }

                                                                }
                                                            });


                                                            break;
                                                        case "N.A.":
                                                            questionDataViewModel.LoadQuestionMultiple(questionEntity.getId(),formatDataEntity.getFdid(),objectSectionData.getId());
                                                            questionDataViewModel.getGetQuestionMuliple().observe(contextFragment, new Observer<QuestionDataEntity>() {
                                                                @Override
                                                                public void onChanged(QuestionDataEntity questionMultiple) {
                                                                    if (questionMultiple.getFkoption().equals(optionEntity.getId())) {
                                                                        radioButton.setChecked(true);
                                                                    }


                                                                }
                                                            });


                                                            break;
                                                    }

                                                    radioButton.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            final RadioButton radioButton1 = (RadioButton) v;


                                                            btnSave.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {


                                                                    final QuestionDataEntity questionDataEntity = new QuestionDataEntity();

                                                                    questionDataViewModel.LoadQuestionMultiple(questionEntity.getId(),formatDataEntity.getFdid(),objectSectionData.getId());
                                                                    questionDataViewModel.getGetQuestionMuliple().observe(contextFragment, new Observer<QuestionDataEntity>() {
                                                                        @Override
                                                                        public void onChanged(QuestionDataEntity questionMultiple) {
                                                                            //answermultiple =String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s",questionMultiple.getId(),questionMultiple.getTextQuestion(),questionMultiple.getObservation(),questionMultiple.getEdited(),questionMultiple.getFkformatData(),questionMultiple.getFksectionData(),questionMultiple.getFkquestion(),questionMultiple.getFkoption());
                                                                            questionDataEntity.setId(questionMultiple.getId());
                                                                            questionDataEntity.setFkformatData(questionMultiple.getFkformatData());
                                                                            questionDataEntity.setFkquestion(questionMultiple.getFkquestion());

                                                                        }
                                                                    });

                                                                    questionDataEntity.setFksectionData(objectSectionData.getId());
                                                                    questionDataEntity.setFkoption(optionEntity.getId());
                                                                    questionDataEntity.setEdited(CurrentDate.showDate());
                                                                    questionDataEntity.setTextQuestion(radioButton1.getText().toString());
                                                                    questionDataEntity.setObservation(txtObservaciones.getText().toString());
                                                                    EditQuestionRadiobuttons(questionDataEntity,positionGlobal);
                                                                    idQuestionData = questionDataEntity.getId();
                                                                    getNavigationManager().addFragment(CameraFragment.newInstance(idQuestionData,positionGlobal,questionEntity,alert));






                                                                }
                                                            });
                                                            if (radioButton1.getText().toString().equals("NO")) {
                                                                txtObservaciones.setVisibility(View.VISIBLE);

                                                            } else{
                                                                txtObservaciones.setVisibility(View.GONE);
                                                                txtObservaciones.setText("");


                                                            }
                                                        }

                                                    });
                                                }
                                                btnCancel.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        alert.dismiss();
                                                    }
                                                });
                                                alert.show();




                                    } else {

                                        final LayoutInflater inflater = LayoutInflater.from(getContext());
                                        View view = inflater.inflate(R.layout.dialog_radiobutton, null);
                                        final EditText txtObservaciones = view.findViewById(R.id.txtObservaciones);
                                        RadioGroup group = view.findViewById(R.id.radioGroup);
                                        final MaterialButton btnSave = view.findViewById(R.id.btnSave);
                                        final MaterialButton btnCancel = view.findViewById(R.id.btnCancel);
                                        myDialog = CreateDialog.getInstanceDialog();
                                        final android.app.AlertDialog.Builder builder = myDialog.openDialog2(getContext(), view);
                                        builder.setTitle("Opciones");
                                        builder.setMessage("Seleccione una opcion:");
                                        builder.setIcon(R.drawable.ic_radio_button);
                                        final  android.app.AlertDialog  alertMultiple;
                                        countPhoto = countPhoto+1;
                                        if (countPhoto==1){
                                        alertMultiple = builder.show();
                                            countPhoto =0;

                                        }else {
                                            return;
                                        }

                                        for (final OptionEntity optionEntity : optionEntities) {
                                            final RadioButton radioButton = new RadioButton(getContext());
                                            radioButton.setText(optionEntity.getDescription());
                                            group.addView(radioButton);
                                            radioButton.setButtonTintList(ColorStateList.valueOf(R.color.colorPrimaryDark));
                                            radioButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    final RadioButton radioButton1 = (RadioButton) v;
                                                    btnSave.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {

                                                            final QuestionDataEntity questionDataEntity = new QuestionDataEntity();
                                                            questionDataEntity.setId(EncryptMD5.convertToMD5(String.valueOf(System.currentTimeMillis())));
                                                            questionDataEntity.setFkformatData(formatDataEntity.getFdid());
                                                            questionDataEntity.setFksectionData(objectSectionData.getId());
                                                            questionDataEntity.setFkquestion(questionEntity.getId());
                                                            questionDataEntity.setFkoption(optionEntity.getId());
                                                            questionDataEntity.setTextQuestion(radioButton.getText().toString());
                                                            questionDataEntity.setObservation(txtObservaciones.getText().toString());
                                                            questionDataEntity.setEdited(CurrentDate.showDate());
                                                            idQuestionData = questionDataEntity.getId();
                                                            questionDataViewModel.AddQuestionData(questionDataEntity,formatSectionEntity)
                                                                    .subscribeOn(Schedulers.io())
                                                                    .observeOn(AndroidSchedulers.mainThread())
                                                                    .subscribe(new DisposableCompletableObserver() {
                                                                        @Override
                                                                        public void onComplete() {
                                                                            LoadQuestion(questionDataEntity.getFksectionData(),positionGlobal);
                                                                            getNavigationManager().addFragment(CameraFragment.newInstance(idQuestionData,positionGlobal,questionEntity,alertMultiple));

                                                                        }

                                                                        @Override
                                                                        public void onError(Throwable e) {
                                                                            e.printStackTrace();
                                                                        }
                                                                    });


                                                        }
                                                    });


                                                    if (radioButton1.getText().toString().equals("NO")) {
                                                        txtObservaciones.setVisibility(View.VISIBLE);
                                                        txtObservaciones.setHint("Escriba una observacion por favor");

                                                    } else
                                                        txtObservaciones.setVisibility(View.GONE);

                                                }

                                            });

                                        }
                                        btnCancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                alertMultiple.dismiss();
                                            }
                                        });


                                    }


                                }







                        }
                        else if (questionEntity.getFieldtype().equals("camera")) {

                            if (questionDbByIdQuestion != null) {

                                QuestionDataEntity questionDataEntity = new QuestionDataEntity();
                                questionDataEntity.setId(questionDbByIdQuestion.getId());
                                questionDataEntity.setEdited(CurrentDate.showDate());
                                questionDataEntity.setFkformatData(questionDbByIdQuestion.getFkformatData());
                                questionDataEntity.setFkquestion(questionDbByIdQuestion.getFkquestion());
                                questionDataEntity.setFksectionData(null);
                                questionDataEntity.setFkoption(null);
                                questionDataEntity.setTextQuestion(null);
                                questionDataEntity.setObservation(null);
                                questionDataViewModel.UpdateQuestionData(questionDataEntity);
                                pathEvidence = Environment.getExternalStorageDirectory().getAbsolutePath() + "/questions/photos";
                                idQuestionData= questionDataEntity.getId();
                                getNavigationManager().addFragment(CameraFragment.newInstance(idQuestionData,positionGlobal,questionEntity,null));




                            }else {


                                QuestionDataEntity questionDataEntity = new QuestionDataEntity();
                                questionDataEntity.setId(EncryptMD5.convertToMD5(String.valueOf(System.currentTimeMillis())));
                                questionDataEntity.setFkformatData(formatDataEntity.getFdid());
                                questionDataEntity.setFksectionData(null);
                                questionDataEntity.setFkquestion(questionEntity.getId());
                                questionDataEntity.setFkoption(null);
                                questionDataEntity.setTextQuestion(null);
                                questionDataEntity.setObservation(null);
                                questionDataEntity.setEdited(CurrentDate.showDate());
                                pathEvidence = Environment.getExternalStorageDirectory().getAbsolutePath() + "/questions/photos";
                                idQuestionData = questionDataEntity.getId();
                                questionDataViewModel.AddQuestionData(questionDataEntity,formatSectionEntity)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new DisposableCompletableObserver() {
                                            @Override
                                            public void onComplete() {
                                                getNavigationManager().addFragment(CameraFragment.newInstance(idQuestionData,positionGlobal,questionEntity,null));

                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                e.printStackTrace();
                                            }
                                        });

                            }


                        } else


                        //RESPUESTA ABIERTA
                        {
                            //Editar pregunta
                            if (questionDbByIdQuestion != null) {

                                try {

                                    LayoutInflater inflater = LayoutInflater.from(getContext());
                                    final View view = inflater.inflate(R.layout.dialog_input, null);
                                    final EditText answer = view.findViewById(R.id.txtAnswer);
                                    answer.setText(questionDbByIdQuestion.getTextQuestion());
                                    myDialog = CreateDialog.getInstanceDialog();
                                    final MaterialDialog.Builder builder = myDialog.openDialog(getContext(), view, "Editar respuesta");
                                    builder.iconRes(R.drawable.ic_inputtext);
                                    builder.positiveText("Editar");
                                    builder.negativeText("Cancelar");
                                    builder.positiveColorRes(R.color.colorPrimaryDark);
                                    builder.negativeColorRes(R.color.colorPrimaryDark);

                                    final MaterialDialog show = builder.show();

                                    builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            QuestionDataEntity questionDataEntity = new QuestionDataEntity();
                                            questionDataEntity.setId(String.valueOf(questionDbByIdQuestion.getId()));
                                            questionDataEntity.setEdited(CurrentDate.showDate());
                                            questionDataEntity.setFkformatData(formatDataEntity.getFdid());
                                            questionDataEntity.setFkquestion(questionEntity.getId());
                                            questionDataEntity.setFksectionData(null);
                                            questionDataEntity.setFkoption(null);
                                            questionDataEntity.setTextQuestion(answer.getText().toString());
                                            questionDataEntity.setObservation(null);

                                            questionDataViewModel.UpdateQuestionData(questionDataEntity);
                                            Toast.makeText(getContext(), "Se editó la respuesta exitosamente", Toast.LENGTH_SHORT).show();
                                            show.dismiss();
                                            LoadQuestion(null,positionGlobal);
                                        }
                                    });
                                    builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            show.dismiss();
                                        }
                                    });

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e("TAG21", "ERROR: " + e.getLocalizedMessage());
                                }

                            }
                            //Guardar pregunta
                            else {

                                try {

                                    LayoutInflater inflater = LayoutInflater.from(getContext());
                                    final View view = inflater.inflate(R.layout.dialog_input, null);
                                    final EditText answer = view.findViewById(R.id.txtAnswer);


                                    myDialog = CreateDialog.getInstanceDialog();
                                    final MaterialDialog.Builder builder = myDialog.openDialog(getContext(), view, "Respuesta directa");
                                    builder.iconRes(R.drawable.ic_inputtext);
                                    builder.positiveText("Guardar");
                                    builder.negativeText("Cancelar");
                                    builder.positiveColorRes(R.color.colorPrimaryDark);
                                    builder.negativeColorRes(R.color.colorPrimaryDark);
                                    final MaterialDialog show = builder.show();

                                    builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            QuestionDataEntity questionDataEntity = new QuestionDataEntity();
                                            questionDataEntity.setId( EncryptMD5.convertToMD5(String.valueOf(System.currentTimeMillis())));
                                            questionDataEntity.setEdited(CurrentDate.showDate());
                                            questionDataEntity.setFkformatData(formatDataEntity.getFdid());
                                            questionDataEntity.setFkquestion(questionEntity.getId());
                                            questionDataEntity.setFksectionData(null);
                                            questionDataEntity.setFkoption(null);
                                            questionDataEntity.setTextQuestion(answer.getText().toString());
                                            questionDataEntity.setObservation(null);

                                            questionDataViewModel.AddQuestionData(questionDataEntity,formatSectionEntity)
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(new DisposableCompletableObserver() {
                                                        @Override
                                                        public void onComplete() {
                                                            show.dismiss();
                                                            LoadQuestion(null,positionGlobal);
                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {
                                                            e.printStackTrace();
                                                        }
                                                    });



                                        }
                                    });
                                    builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            show.dismiss();
                                        }
                                    });

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e("TAG21", "ERROR: " + e.getLocalizedMessage());
                                }

                            }
                        }
                    }
    });
    }

    @Override
    public void OnClickQuestionPresenter(QuestionEntity questionEntity) {

    }
    /*
    private void   ShowDialogEditQuestion(QuestionEntity question,QuestionDataEntity answer){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        EditAnswerDialogFragment editAnswerDialogFragment= new EditAnswerDialogFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.add(android.R.id.content,editAnswerDialogFragment).addToBackStack(null).commit();

    }*/

}
