package gcode.baseproject.view.ui.format;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.adapters.LinearLayoutBindingAdapter;
import androidx.databinding.adapters.ToolbarBindingAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormFragmentBinding;
import gcode.baseproject.domain.repository.dataEvidence.IEvidenceDataRepository;
import gcode.baseproject.domain.repository.dataSection.ISectionDataRepository;
import gcode.baseproject.interactors.adapters.DecodeImage;
import gcode.baseproject.interactors.adapters.FormAdapter;
import gcode.baseproject.interactors.date.CurrentDate;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;
import gcode.baseproject.interactors.db.entities.OptionEntity;
import gcode.baseproject.interactors.db.entities.QuestionEntity;
import gcode.baseproject.interactors.db.entities.data.EvidenceDataEntity;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;
import gcode.baseproject.interactors.dialogs.CreateDialog;
import gcode.baseproject.interactors.session.SessionUser;
import gcode.baseproject.view.ui.Customer.CustomerFragment;
import gcode.baseproject.view.ui.account.MainActivity;
import gcode.baseproject.view.ui.gallery.GalleryFragment;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.ui.image.ImageActivity;
import gcode.baseproject.view.viewmodel.data.SectionDataViewModel;
import gcode.baseproject.view.viewmodel.dataEvidence.EvidenceDataViewModel;
import gcode.baseproject.view.viewmodel.dataQuestion.QuestionDataViewModel;
import gcode.baseproject.view.viewmodel.format.FormatViewModel;
import gcode.baseproject.view.viewmodel.option.OptionViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import rx.Single;
import rx.functions.Func1;

import static android.app.Activity.RESULT_OK;


public class FormFragment extends BaseFragment implements FormAdapter.FormClickPresenter {


    private  static String FORM_FRAGMENT_ARGS ="form_fragment_args";
    private  static String FORMAT_DATA_ARGS = "format_data_args";
    private FormFragmentBinding formFragmentBinding;
    private FormatSectionEntity formatSectionEntity;
    private  FormatViewModel formatViewModel;
    private OptionViewModel optionViewModel;
    private QuestionDataViewModel questionDataViewModel;
    private EvidenceDataViewModel evidenceDataViewModel;
    private SectionDataViewModel sectionDataViewModel;
    private  SectionDataEntity sectionDataEntity;
    private FormatDataEntity formatDataEntity;
    private CreateDialog myDialog;
    Intent intent;
    private  final int cons = 0;
    private Bitmap bitmap;
      String reference;
    int folio =1;
    long numero = 0;
    int intento = 0;

    //for camera
    Uri imageUri;
    private final int MY_PERMISSIONS = 100;
    private final int SELECT_PICTURE = 200;
    private final int SELECT_PICTURE2 = 50;
    static String str_Camera_Photo_ImagePath = "";
    private static File f;
    private static final int PHOTO_CODE = 2;
    private static String str_randomnumber = "";
    static String str_Camera_Photo_ImageName = "";
    public static String str_SaveFolderName;
    private static File wallpaperDirectory;
    private  static String idQuestion ="";
     private ISectionDataRepository isectionDataRepository;
    public static final FormFragment newInstance(FormatSectionEntity formatSectionEntity,FormatDataEntity entity){
        FormFragment  formFragment= new FormFragment();
        Bundle args= new Bundle();
        args.putSerializable(FORM_FRAGMENT_ARGS,formatSectionEntity);
        args.putSerializable(FORMAT_DATA_ARGS,entity);
         formFragment.setArguments(args);
        return formFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){

            formatSectionEntity = (FormatSectionEntity) getArguments().getSerializable(FORM_FRAGMENT_ARGS);
            formatDataEntity = (FormatDataEntity)getArguments().getSerializable(FORMAT_DATA_ARGS);
         }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //VIEWMODEL
        formatViewModel = ViewModelProviders.of(this).get(FormatViewModel.class);
        optionViewModel = ViewModelProviders.of(this).get(OptionViewModel.class);
        sectionDataViewModel= ViewModelProviders.of(this).get(SectionDataViewModel.class);
        evidenceDataViewModel = ViewModelProviders.of(this).get(EvidenceDataViewModel.class);
        questionDataViewModel = ViewModelProviders.of(this).get(QuestionDataViewModel.class);
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
    private Observer<List<QuestionEntity>> getObserverQuestions(){
         final FormAdapter.FormClickPresenter presenter= this;
         final FormFragment formFragment= this;
        return new Observer<List<QuestionEntity>>() {
            @Override
            public void onChanged(List<QuestionEntity> questionEntities) {

                //LISTADO DE PREGUNTAS
                formFragmentBinding.formRecycler.setHasFixedSize(true);
                formFragmentBinding.formRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                FormAdapter formAdapter =new FormAdapter(questionEntities,presenter,formFragment);
                formFragmentBinding.formRecycler.setAdapter(formAdapter);

                Integer CountAnswers= questionDataViewModel.getCountAnswersByFkSectionData(formatSectionEntity.getId());
                if (CountAnswers.equals(formAdapter.getItemCount())){

                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    View view = inflater.inflate(R.layout.dialog_success_message,null);
                    myDialog = new CreateDialog();
                    final android.app.AlertDialog.Builder builder= myDialog.openDialog(getContext(),view,"Respuestas respondidas");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getNavigationManager().removeFragmentFromBackStack();
                        }
                    });

                    final android.app.AlertDialog show =builder.show();
                    show.getButton(show.BUTTON_POSITIVE).setTextColor(Color.WHITE);
                    show.getButton(show.BUTTON_POSITIVE).setBackgroundColor(Color.LTGRAY);

                }




            }
        };
    }
    void AddSectionData(String reference){
        sectionDataEntity= new SectionDataEntity();
        sectionDataEntity.setId(numero);
        sectionDataEntity.setFksection(formatSectionEntity.getId());
        sectionDataEntity.setFolio(folio);
        sectionDataEntity.setReference(reference);
        //if (sectionDataViewModel.CheckIfExists(sectionDataEntity.getFksection(),sectionDataEntity.getFolio())){
           // Toast.makeText(getContext(), "Ya existe el folio: " + String.valueOf(folio) + " con esta seccion",Toast.LENGTH_LONG).show();
            //getNavigationManager().removeFragmentFromBackStack();
        //}else {
        sectionDataViewModel.AddSectionData(sectionDataEntity);
         //}

    }
    @SuppressLint("RestrictedApi")
    private  void VisibilityButtons(){
        formFragmentBinding.btnCreate.setVisibility(View.VISIBLE);
        formFragmentBinding.title.setVisibility(View.VISIBLE);
        formFragmentBinding.folio.setVisibility(View.VISIBLE);
        formFragmentBinding.cmbMultiple.setVisibility(View.VISIBLE);
    }

    void DialogWithInput(){
        final FormFragment fragmentcontext = this;

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
                        formFragmentBinding.title.setText(formatSectionEntity.getMultipledescription());
                        formFragmentBinding.folio.setText(String.valueOf(folio));
                        numero =(long)(Math.random()*100000000);

                        AddSectionData(reference);
                        /*
                        sectionDataViewModel.LoadSectoionsData(formatSectionEntity.getId());
                        sectionDataViewModel.getData()
                                .observe(fragmentcontext, new Observer<List<SectionDataEntity>>() {
                                    @Override
                                    public void onChanged(List<SectionDataEntity> sectionDataEntities) {

                                    }
                                });*/

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



    @Override
    public void onResume() {
        super.onResume();

        // Ok eso es lo que queria saber, para salir de la bronca se me ocurre que hagas una entidad
        // que tenga un camp booleano
        // y otra de tipo Question entitiy
        // que lo proceses despues de obtner los datos de la db
        formatViewModel.LoadQuestionsDB(formatSectionEntity.getId());
        formatViewModel.mgetQuestionsDB()
                .observe(this,getObserverQuestions());

        //Toast.makeText(getContext(), SessionUser.getInstance().getAccount().getId(),Toast.LENGTH_SHORT).show();


        if (formatSectionEntity.getMultipledescription().equals("")){



         }
        else {

            if (formatSectionEntity.getIsmultipleref().equals("t")){
                //si necesita dialogo con edittext
                DialogWithInput();


            }else {
                //Dispensario
                Toast.makeText(getContext(),formatSectionEntity.getMultipledescription(),Toast.LENGTH_SHORT).show();
                VisibilityButtons();
                formFragmentBinding.title.setText(formatSectionEntity.getMultipledescription());
                formFragmentBinding.folio.setText(String.valueOf(folio));
                numero =(long)(Math.random()*100000000);
                AddSectionData("");
            }
        }
        formFragmentBinding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    folio = folio +1;
                    numero = numero+1;
                    DialogWithInput();
                    formFragmentBinding.folio.setText(String.valueOf(folio));

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(v.getContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }

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
    ImageView imageOption;
    ImageView imageInput;

    void DialogWithButtons(final QuestionEntity questionEntity){




         optionViewModel.LoadQuestionsByIdQuestion(questionEntity.getId());
        optionViewModel.mgetOptionsEntity()
                .observe(this, new Observer<List<OptionEntity>>() {
                    @SuppressLint("ResourceType")
                    @Override
                    public void onChanged(final List<OptionEntity> optionEntities) {


                        if (questionEntity.getFieldtype().equals("radiobutton")){

                            LayoutInflater inflater = LayoutInflater.from(getContext());
                            View view = inflater.inflate(R.layout.dialog_radiobutton,null);
                            ImageButton take =  view.findViewById(R.id.photoOption);
                            ImageButton gallery =  view.findViewById(R.id.galleryOption);
                            final EditText txtObservaciones= view.findViewById(R.id.txtObservaciones);
                            ImageButton goGallery = view.findViewById(R.id.goGalleryButton);
                            final Button btnSave = view.findViewById(R.id.btnSaveRD);
                            Button btnCancel = view.findViewById(R.id.btnCancelRD);
                            RadioGroup group = view.findViewById(R.id.radioGroup);
                            myDialog = new CreateDialog();
                            final android.app.AlertDialog.Builder builder= myDialog.openDialog(getContext(),view,"Seleccione una opcion:");
                            builder.setIcon(R.drawable.ic_radio_button);

                            final android.app.AlertDialog alert =builder.show();

                            for(final OptionEntity optionEntity : optionEntities){
                                 final RadioButton radioButton = new RadioButton(getContext());
                                 radioButton.setText(optionEntity.getDescription());
                                 group.addView(radioButton);
                                 radioButton.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         final RadioButton radioButton1 = (RadioButton)v;

                                             btnSave.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {

                                                     numero =(long)(Math.random()*100000000);
                                                     AddSectionData("");

                                                     QuestionDataEntity questionDataEntity= new QuestionDataEntity();
                                                     questionDataEntity.setId(String.valueOf(System.currentTimeMillis()));
                                                     questionDataEntity.setEdited(CurrentDate.showDate());
                                                     questionDataEntity.setFkformatData(formatDataEntity.getFdid());
                                                     questionDataEntity.setFkquestion(questionEntity.getId());
                                                     questionDataEntity.setFksectionData(String.valueOf(sectionDataEntity.getId()));
                                                     questionDataEntity.setFkoption(optionEntity.getId());
                                                     questionDataEntity.setTextQuestion(radioButton1.getText().toString());
                                                     questionDataEntity.setObservation(txtObservaciones.getText().toString());
                                                     if (radioButton1.isChecked()){
                                                         questionDataViewModel.AddQuestionData(questionDataEntity);
                                                         TakePhoto(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+"/questions/options",questionDataEntity.getId());
                                                         alert.dismiss();

                                                     }else {
                                                         Toast.makeText(getContext(),"Favor selecciona una respuesta", Toast.LENGTH_LONG).show();
                                                     }





                                                 }
                                             });

                                         if (radioButton1.getText().toString().equals("NO")){
                                                txtObservaciones.setVisibility(View.VISIBLE);
                                                txtObservaciones.setHint("Escriba una observacion por favor");

                                         }else
                                             txtObservaciones.setVisibility(View.GONE);
                                     }
                                 });


                            }


                            take.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            gallery.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    intent.setType("image/*");
                                    startActivityForResult(Intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE2);
                                }
                            });

                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alert.dismiss();
                                }
                            });
                            goGallery.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alert.dismiss();
                                    getNavigationManager().addFragment(GalleryFragment.getInstance(questionEntity.getId()));
                                }
                            });
                        }



                        else if (questionEntity.getFieldtype().equals("camera")){
                            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()+"/questions/photos";
                            TakePhoto(path,null);
                            QuestionDataEntity questionDataEntity = new QuestionDataEntity();
                            questionDataEntity.setId(String.valueOf(System.currentTimeMillis()));
                            questionDataEntity.setFkquestion(questionEntity.getId());
                            questionDataEntity.setEdited(CurrentDate.showDate());
                            if (questionEntity.getId().equals("")){
                                Toast.makeText(getContext(),"Datos vacios",Toast.LENGTH_LONG).show();
                            }else
                            {
                                questionDataViewModel.AddQuestionData(questionDataEntity);
                            }
                        }



                        else

                            //RESPUESTA ABIERTA
                            {
                            try{

                                LayoutInflater inflater = LayoutInflater.from(getContext());
                                View view = inflater.inflate(R.layout.dialog_input,null);
                                  final Button btnSave = view.findViewById(R.id.btnSaveINPUT);
                                Button btnCancel = view.findViewById(R.id.btnCancelINPUT);
                                 final EditText answer  = view.findViewById(R.id.txtAnswer);
                                myDialog = new CreateDialog();
                                final android.app.AlertDialog.Builder builder= myDialog.openDialog(getContext(),view,"Respuesta directa");
                                builder.setIcon(R.drawable.ic_inputtext);
                                final android.app.AlertDialog show =builder.show();

                                btnSave.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        QuestionDataEntity questionDataEntity= new QuestionDataEntity();
                                        questionDataEntity.setId(String.valueOf(System.currentTimeMillis()));
                                        questionDataEntity.setEdited(CurrentDate.showDate());
                                        questionDataEntity.setFkformatData(formatDataEntity.getFdid());
                                        questionDataEntity.setFkquestion(questionEntity.getId());
                                        questionDataEntity.setFksectionData(null);
                                        questionDataEntity.setFkoption(null);
                                        questionDataEntity.setTextQuestion(answer.getText().toString());
                                        questionDataEntity.setObservation(null);
                                        if (answer.getText().toString().equals("")){
                                            Toast.makeText(getContext(),"Favor de escribir una respuesta", Toast.LENGTH_LONG).show();
                                        }else {
                                            questionDataViewModel.AddQuestionData(questionDataEntity);
                                            Toast.makeText(getContext(),"Se guardo la respuesta exitosamente",Toast.LENGTH_SHORT).show();
                                            show.dismiss();
                                        }
                                    }
                                });
                                btnCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        show.dismiss();
                                    }
                                });

                            }catch(Exception e){
                                e.printStackTrace();
                                Log.e("TAG21","ERRORR "+e.getLocalizedMessage());
                            }


                        }
                    }
                });

    }

    private  void TakePhoto(String path,String id) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
        } else {

            str_SaveFolderName = path;
            str_randomnumber = String.valueOf(String.valueOf(System.currentTimeMillis()));
            wallpaperDirectory = new File(str_SaveFolderName);
            if (!wallpaperDirectory.exists())
                wallpaperDirectory.mkdirs();
            str_Camera_Photo_ImageName = str_randomnumber
                    + ".jpg";
            str_Camera_Photo_ImagePath = str_SaveFolderName
                    + "/" + str_randomnumber + ".jpg";

            f = new File(str_Camera_Photo_ImagePath);
            startActivityForResult(new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
                    MediaStore.EXTRA_OUTPUT, Uri.fromFile(f)),
                    PHOTO_CODE);

            OpenCamera(id,str_Camera_Photo_ImagePath);
        }

    }
    void OpenCamera(String id,String ruta){

        EvidenceDataEntity evidenceDataEntity = new EvidenceDataEntity();
        evidenceDataEntity.setFkquestionData(id);
        evidenceDataEntity.setImageUrl(ruta);
        evidenceDataEntity.setCreation(CurrentDate.showDate());
        evidenceDataViewModel.AddEvidence(evidenceDataEntity);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_CODE:
                    String filePath = null;
                    filePath = str_Camera_Photo_ImagePath;
                     if (filePath != null) {
                         //Bitmap imageBitmap = ( DecodeImage.decode_image(new File(filePath)));
                         //imageOption.setImageBitmap(imageBitmap);
                         //Intent intent = new Intent(getActivity(),FormatSectionsFragment. );
                         //intent.putExtra("filepath", filePath);
                         //startActivity(intent);
                        //File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        //String [] archivos   =  file.list();
                        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,archivos);
                        //imageView.setAdapter(adapter);
                        //imageView.setImageBitmap(imageBitmap);
                    } else {
                        bitmap = null;
                    }
                    break;
                case SELECT_PICTURE:
                    Uri pathInput = data.getData();
                    imageInput.setImageURI(pathInput);
                    //image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                    break;
                case SELECT_PICTURE2:
                    Uri pathRadioButton=data.getData();
                    imageOption.setImageURI(pathRadioButton);

                    break;
            }
        }
    }

    @Override
    public void OnClickQuestionPresenter(QuestionEntity questionEntity) {
        DialogWithButtons(questionEntity);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==MY_PERMISSIONS){
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] ==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(),"Permisos Aceptados",Toast.LENGTH_SHORT).show();
            }
            else {
                ErrorPermissions();
            }
        }
    }
    private  void ErrorPermissions(){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setTitle("Permisos denegados");
        builder.setMessage("Necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=  new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri =Uri.fromParts("package",getActivity().getPackageName(),null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                getActivity().finish();
            }
        });
        builder.show();
    }
}
