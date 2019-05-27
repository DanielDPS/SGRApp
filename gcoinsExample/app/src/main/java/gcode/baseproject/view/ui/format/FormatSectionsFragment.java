package gcode.baseproject.view.ui.format;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormatSectionsFragmentBinding;
import gcode.baseproject.domain.model.general.NetworkResponse;
import gcode.baseproject.domain.model.pdf.Format;
import gcode.baseproject.interactors.adapters.ClickListener;
import gcode.baseproject.interactors.adapters.FormatSectionsAdapter;
import gcode.baseproject.interactors.adapters.RecyclerTouchListener;
import gcode.baseproject.interactors.connection.NetworkConnection;
import gcode.baseproject.interactors.date.CurrentDate;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.FormatSectionEntity;
import gcode.baseproject.interactors.db.entities.data.FileDataEntity;
import gcode.baseproject.interactors.db.entities.data.SectionDataEntity;
import gcode.baseproject.interactors.dialogs.ShowInfoSections;
import gcode.baseproject.interactors.mappers.Section;
import gcode.baseproject.interactors.mappers.SectionManager;
import gcode.baseproject.interactors.mappers.SectionMapper;
import gcode.baseproject.interactors.md5.EncryptMD5;
import gcode.baseproject.interactors.pdf.PdfGenerator;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.utils.ErrorUtils;
import gcode.baseproject.view.viewmodel.Customer.CustomerViewModel;
import gcode.baseproject.view.viewmodel.data.SectionDataViewModel;
import gcode.baseproject.view.viewmodel.dataAPI.addFile.AddFileViewModel;
import gcode.baseproject.view.viewmodel.dataAPI.addFormatData.AddFormatDataViewModel;
import gcode.baseproject.view.viewmodel.dataEvidence.EvidenceDataViewModel;
import gcode.baseproject.view.viewmodel.dataFile.FileDataViewModel;
import gcode.baseproject.view.viewmodel.dataQuestion.QuestionDataViewModel;
import gcode.baseproject.view.viewmodel.format.FormatDataViewModel;
import gcode.baseproject.view.viewmodel.format.FormatViewModel;
import gcode.baseproject.view.viewmodel.pdf.GetPdfContentViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class FormatSectionsFragment extends BaseFragment implements  FormatSectionsAdapter.FormatSectionPresenter {

    private  static String FORMAT_DATA_ARGS ="format_data_args";
    private FormatSectionsFragmentBinding formatSectionsFragmentBinding;
    private FormatDataEntity format;
    private  FormatSectionsAdapter formatSectionsAdapter;
    //VIDEMODELS
    private  FormatViewModel formatViewModel;
    private SectionDataViewModel sectionDataViewModel;
    private QuestionDataViewModel questionDataViewModel;
    private CustomerViewModel customerViewModel;
    private EvidenceDataViewModel evidenceDataViewModel;
    private FileDataViewModel fileDataViewModel;
    private AddFormatDataViewModel addFormatDataViewModel;
    private   ProgressDialog showDialogPdf;
    private AddFileViewModel addFileViewModel;
    private FormatDataViewModel formatDataViewModel;
    private GetPdfContentViewModel getPdfContentViewModel;
    public static final String MY_POSITION_SECTION = "MyPositionSectionFile";
    private  FileDataEntity fileDB;
     public static final FormatSectionsFragment newInstance(FormatDataEntity format){
        FormatSectionsFragment formatSectionsFragment = new FormatSectionsFragment();
        Bundle args= new Bundle();
        args.putSerializable(FORMAT_DATA_ARGS,format);
        formatSectionsFragment.setArguments(args);
        return formatSectionsFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            format = (FormatDataEntity)getArguments().getSerializable(FORMAT_DATA_ARGS);
        }
    }
    public  FormatDataEntity getFormat(){
         return this.format;
    }

    //INITIALIZING VIEWMODELS
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        formatViewModel = ViewModelProviders.of(this).get(FormatViewModel.class);
        sectionDataViewModel = ViewModelProviders.of(this).get(SectionDataViewModel.class);
        questionDataViewModel = ViewModelProviders.of(this).get(QuestionDataViewModel.class);
        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        evidenceDataViewModel = ViewModelProviders.of(this).get(EvidenceDataViewModel.class);
        fileDataViewModel = ViewModelProviders.of(this).get(FileDataViewModel.class);
        addFormatDataViewModel = ViewModelProviders.of(this).get(AddFormatDataViewModel.class);
        FormatSectionsFragment context = this;
        addFormatDataViewModel.setContext(context);
        addFileViewModel = ViewModelProviders.of(this).get(AddFileViewModel.class);
        formatDataViewModel = ViewModelProviders.of(this).get(FormatDataViewModel.class);
        getPdfContentViewModel = ViewModelProviders.of(this).get(GetPdfContentViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        formatSectionsFragmentBinding = FormatSectionsFragmentBinding.inflate(inflater,container,false);
        return formatSectionsFragmentBinding.getRoot();
    }

    void LoadFormatSections(final int position){
        final FormatSectionsAdapter.FormatSectionPresenter presenter= this;
        final FormatSectionsFragment context = this;
        formatViewModel.ClearSectionsByFkFormat(this);
        formatViewModel.LoadSections(format.getFkformat());
        formatViewModel.mgetFormatSections()
                .observe(this, new Observer<List<FormatSectionEntity>>() {
                    @Override
                    public void onChanged(List<FormatSectionEntity> sectionEntities) {
                        //total = formatViewModel.getQuestionsCountByIdSection(formatSectionEntity.getId()).;
                        if (sectionEntities.size() ==0){
                            formatSectionsFragmentBinding.nodata.setVisibility(View.VISIBLE);
                        }else {
                            formatSectionsFragmentBinding.nodata.setVisibility(View.GONE);



                            formatSectionsFragmentBinding.formatsSectionsRecycler.setHasFixedSize(true);
                            formatSectionsFragmentBinding.formatsSectionsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                            formatSectionsAdapter= new FormatSectionsAdapter(sectionEntities, presenter,context,position);
                            formatSectionsFragmentBinding.formatsSectionsRecycler.setAdapter(formatSectionsAdapter);
                            SectionManager.setSectionsList(SectionMapper.ConvertToSectionDBToSection(sectionEntities));
                            formatSectionsFragmentBinding.formatsSectionsRecycler.getLayoutManager().scrollToPosition(position);


                        }
                    }
                });
     }


    @Override
    public void onResume() {
        super.onResume();

        SharedPreferences settins = getActivity().getSharedPreferences(MY_POSITION_SECTION,0);
        int positionSection = settins.getInt("positionSection",0);
        LoadFormatSections(positionSection);

        formatSectionsFragmentBinding.formatsSectionsRecycler.addOnItemTouchListener(new RecyclerTouchListener(getContext(), formatSectionsFragmentBinding.formatsSectionsRecycler,
                new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        FormatSectionEntity section = formatSectionsAdapter.getSectionByPosition(position);
                        SectionDataEntity sectionDataEntity = sectionDataViewModel.getObjectSectionDataByFkSection(section.getId());
                        getNavigationManager().addFragment(FormFragment.newInstance(section,format,sectionDataEntity,position));
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @NonNull
    @Override
    public String getFragmentTag() {
         return FormatSectionsFragment.class.getName();
    }
    private void generatePdf(final Format formatData) {
        final PdfGenerator generator = new PdfGenerator(formatData);
        final FormatSectionsFragment context = this;

         Callable<List<String>> callable = new Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                List<String> paths = new ArrayList<>();
                paths.add(generator.generateFirstPdf(getResources().getDrawable(R.drawable.saganor_logo),getResources().getDrawable(R.drawable.icono_02),getResources().getDrawable(R.drawable.icono_background)));
                paths.add(generator.generateSecondPdf(getResources().getDrawable(R.drawable.saganor_logo),getResources().getDrawable(R.drawable.icono_02),getResources().getDrawable(R.drawable.firma_enrique)));
                paths.add(generator.generateThirdPdf(getResources().getDrawable(R.drawable.saganor_logo),getResources().getDrawable(R.drawable.icono_02)));
                return paths;
            }
        };

        Single<List<String>> mySingle = Single.fromCallable(callable);
        mySingle
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<String>>() {
                    @Override
                    public void onSuccess(List<String> paths) {
                        try {


                            fileDB = new FileDataEntity();
                            fileDB.setId(EncryptMD5.convertToMD5(String.valueOf(System.currentTimeMillis())));
                            fileDB.setFile(paths.get(0));
                            fileDB.setFkFormatData(formatData.getIdFormatData());
                            fileDB.setType("Acta de Visita");
                            if (fileDataViewModel.getCountFilesByIDS(fileDB.getId(),fileDB.getFkFormatData())>0){

                                fileDataViewModel.UpdateFileData(fileDB)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new DisposableCompletableObserver() {
                                            @Override
                                            public void onComplete() {

                                            }
                                            @Override
                                            public void onError(Throwable e) {
                                                e.printStackTrace();
                                            }
                                        });
                            }else {
                                fileDataViewModel.AddFile(fileDB)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new DisposableCompletableObserver() {
                                            @Override
                                            public void onComplete() {

                                            }
                                            @Override
                                            public void onError(Throwable e) {
                                                e.printStackTrace();
                                            }
                                        });

                            }





                            //crear pdf2

                            fileDB = new FileDataEntity();
                            fileDB.setId(EncryptMD5.convertToMD5(String.valueOf(System.currentTimeMillis())));
                            fileDB.setFile(paths.get(1));
                            fileDB.setFkFormatData(formatData.getIdFormatData());
                            fileDB.setType("Reporte de Inspección");
                            if (fileDataViewModel.getCountFilesByIDS(fileDB.getId(),fileDB.getFkFormatData())>0){
                                fileDataViewModel.UpdateFileData(fileDB)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new DisposableCompletableObserver() {
                                            @Override
                                            public void onComplete() {
                                            }
                                            @Override
                                            public void onError(Throwable e) {
                                                e.printStackTrace();
                                                showDialogPdf.dismiss();
                                                getNavigationManager().removeFragmentFromBackStack();
                                            }
                                        });
                            }else {

                                fileDataViewModel.AddFile(fileDB)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new DisposableCompletableObserver() {
                                            @Override
                                            public void onComplete() {

                                            }
                                            @Override
                                            public void onError(Throwable e) {
                                                e.printStackTrace();
                                                showDialogPdf.dismiss();
                                                getNavigationManager().removeFragmentFromBackStack();
                                            }
                                        });


                            }


                            //crear pdf3

                            fileDB = new FileDataEntity();
                            fileDB.setId(EncryptMD5.convertToMD5(String.valueOf(System.currentTimeMillis())));
                            fileDB.setFile(paths.get(2));
                            fileDB.setFkFormatData(formatData.getIdFormatData());
                            fileDB.setType("Archivo Fotográfico");
                            if (fileDataViewModel.getCountFilesByIDS(fileDB.getId(),fileDB.getFkFormatData())>0){
                                fileDataViewModel.UpdateFileData(fileDB)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new DisposableCompletableObserver() {
                                            @Override
                                            public void onComplete() {

                                            }
                                            @Override
                                            public void onError(Throwable e) {
                                                e.printStackTrace();
                                                showDialogPdf.dismiss();
                                                getNavigationManager().removeFragmentFromBackStack();
                                            }
                                        });
                            }else {
                                fileDataViewModel.AddFile(fileDB)
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new DisposableCompletableObserver() {
                                            @Override
                                            public void onComplete() {

                                            }
                                            @Override
                                            public void onError(Throwable e) {
                                                e.printStackTrace();
                                                showDialogPdf.dismiss();
                                                getNavigationManager().removeFragmentFromBackStack();
                                            }
                                        });
                            }










                        //Eliminar todas las secctionData (multiple)
                         Completable deleteListSectionData=  sectionDataViewModel.DeleteListSectionData(sectionDataViewModel.getListSectionsByFkFormatData(formatData.getIdFormatData()));
                         deleteListSectionData.subscribeOn(Schedulers.io())
                                 .subscribeOn(AndroidSchedulers.mainThread())
                                 .subscribe(new DisposableCompletableObserver() {
                                     @Override
                                     public void onComplete() {
                                     }

                                     @Override
                                     public void onError(Throwable e) {
                                         e.printStackTrace();
                                         showDialogPdf.dismiss();
                                         getNavigationManager().removeFragmentFromBackStack();

                                     }
                                 });
                         //Eliminar QuestionData y EvidenceData
                         Completable deleteListAnswers = questionDataViewModel.DeleteAllAnswerList(questionDataViewModel.getAnswersListByFkFormatData(formatData.getIdFormatData()));
                         deleteListAnswers.subscribeOn(Schedulers.io())
                                 .observeOn(AndroidSchedulers.mainThread())
                                 .subscribe(new DisposableCompletableObserver() {
                                     @Override
                                     public void onComplete() {

                                     }

                                     @Override
                                     public void onError(Throwable e) {
                                         e.printStackTrace();
                                         showDialogPdf.dismiss();
                                         getNavigationManager().removeFragmentFromBackStack();

                                     }
                                 });

                        //Actualizar estado01 a 1 QUE INDICA QUE QUE LA INSPECCION HA SIDO FINALIZADA
                         FormatDataEntity formatDataUpdate01 = new FormatDataEntity();
                        formatDataUpdate01.setFdid(formatData.getIdFormatData());
                        formatDataUpdate01.setEdited(formatData.getEdited());
                        formatDataUpdate01.setFkuser(formatData.getFkUser());
                        formatDataUpdate01.setFkCustomer(formatData.getCustomer().getCId());
                        formatDataUpdate01.setFkformat(formatData.getFkFormat());
                        formatDataUpdate01.setEstado01(1);
                        formatDataUpdate01.setEstado02(0);
                        formatDataUpdate01.setInitialDate(formatData.getInitialDate());
                        formatDataUpdate01.setEndDate(CurrentDate.showDate());
                        formatDataUpdate01.setIdentifier(formatData.getIdentifier());
                        formatDataUpdate01.setCreated("t");
                         Completable updateFormatData = formatDataViewModel.Update(formatDataUpdate01);
                         updateFormatData.subscribeOn(Schedulers.io())
                                 .observeOn(AndroidSchedulers.mainThread())
                                 .subscribe(new DisposableCompletableObserver() {
                                     @Override
                                     public void onComplete() {

                                     }

                                     @Override
                                     public void onError(Throwable e) {
                                         e.printStackTrace();
                                         showDialogPdf.dismiss();
                                         getNavigationManager().removeFragmentFromBackStack();

                                     }
                                 });


                            addFormatDataViewModel.addHeaderFormatData(formatData,showDialogPdf)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new DisposableCompletableObserver() {

                                    @Override
                                    public void onComplete() {
                                    }
                                    @Override
                                    public void onError(Throwable e) {
                                        e.printStackTrace();
                                        Log.e("TAG4",e.getLocalizedMessage());
                                        HttpException httpException = (HttpException)e;
                                        String message = ErrorUtils.getErrorMessage(httpException.response().errorBody());
                                        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
                                        showDialogPdf.dismiss();
                                        getNavigationManager().removeFragmentFromBackStack();
                                     }
                                });



                        }
                        catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(),e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            showDialogPdf.dismiss();
                            getNavigationManager().removeFragmentFromBackStack();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        showDialogPdf.dismiss();
                        getNavigationManager().removeFragmentFromBackStack();
                    }
                });



    }
    private void loadPdfContent() {


         getPdfContentViewModel.clear(this);
         getPdfContentViewModel.loadPdfData(format.getFdid());
         getPdfContentViewModel.getNetworkResponse().observe(this, new Observer<NetworkResponse<Format>>() {
             @Override
             public void onChanged(NetworkResponse<Format> response) {
                 switch (response.status()) {
                     case LOADING:
                         showDialogPdf = new ProgressDialog(getContext(),AlertDialog.THEME_HOLO_DARK);
                         showDialogPdf.setMessage("Creando y sincronizando archivos PDF...");
                         showDialogPdf.setCancelable(false);
                         showDialogPdf.setIndeterminate(false);
                         showDialogPdf.show();
                          break;
                     case ERROR:
                         showDialogPdf.dismiss();
                         break;
                     case SUCCESS:
                         generatePdf(response.getResult());
                         break;
                 }
             }
         });
    }

    private  List<Integer> getMenuItems(){
        List<Integer> menuItems = new ArrayList<>();
        menuItems.add(R.id.Section_info);
        menuItems.add(R.id.Section_save);
        return  menuItems;
    }
    private  List<MenuItem.OnMenuItemClickListener>getMenuClicks(){
         final List<MenuItem.OnMenuItemClickListener>listeners= new ArrayList<>();
         final FormatSectionsFragment contextFragment = this;


         listeners.add(new MenuItem.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem item) {
                 ShowDialogInfo();
                 return false;
             }
         });
         listeners.add(new MenuItem.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(MenuItem item) {
                 //GENERACION DE PDFS




                 //Si el identificador es vacio
                 if (format.getIdentifier().equals("")){
                     if (NetworkConnection.isConnected(getContext())){

                         //Obtenemos el identificador del API por el FK del cliente
                         //customerViewModel.ClearIdentifier(contextFragment);
                         customerViewModel.LoadIdentifier(format.getFkCustomer());
                         customerViewModel.getGetIdentifier()
                                 .observe(contextFragment, new Observer<String>() {
                                     @Override
                                     public void onChanged(String identifier) {
                                         //Actualiza formatData con el identificador
                                         Toast.makeText(getContext(),identifier, Toast.LENGTH_SHORT).show();

                                         FormatDataEntity formatFirstUpdate = new FormatDataEntity();
                                         formatFirstUpdate.setFdid(format.getFdid());
                                         formatFirstUpdate.setEdited(format.getEdited());
                                         formatFirstUpdate.setFkuser(format.getFkuser());
                                         formatFirstUpdate.setFkCustomer(format.getFkCustomer());
                                         formatFirstUpdate.setFkformat(format.getFkformat());
                                         formatFirstUpdate.setEstado01(0);
                                         formatFirstUpdate.setEstado02(0);
                                         formatFirstUpdate.setInitialDate(format.getInitialDate());
                                         formatFirstUpdate.setEndDate(format.getEndDate());
                                         formatFirstUpdate.setIdentifier(identifier);
                                         formatFirstUpdate.setCreated("f");
                                         Completable updateFormatData = formatDataViewModel.Update(formatFirstUpdate);
                                         updateFormatData.subscribeOn(Schedulers.io())
                                                 .observeOn(AndroidSchedulers.mainThread())
                                                 .subscribe(new DisposableCompletableObserver() {
                                                     @Override
                                                     public void onComplete() {
                                                         Toast.makeText(getContext(), "Se ha actualizado el identificador", Toast.LENGTH_SHORT).show();
                                                     }

                                                     @Override
                                                     public void onError(Throwable e) {
                                                         e.printStackTrace();
                                                     }
                                                 });
                                     }
                                 });

                     }else {
                         Toast.makeText(getContext(), "Sin conexión a internet,favor de verificar tu conexión", Toast.LENGTH_SHORT).show();
                     }




                 }else {


                     boolean ifReady =true;
                     for(Section section : SectionManager.getSectionsList()){


                         //Total Respuestas
                         Integer countAnswers= questionDataViewModel.getCountAnswersByFkSectionData(section.getIdSection(),format.getFdid());
                         //Total preguntas
                         Integer countQuestions=formatViewModel.getQuestionsCountByIdSection(section.getIdSection());

                         //count Multiples
                         Integer countSectionData = sectionDataViewModel.getCOUNTForSectionId(section.getIdSection(),format.getFdid());
                         Integer countMultiple  =(countSectionData)* (countQuestions);

                         //String message= String.format("%s (%d/%d)",section.getName(),countAnswers,countMultiple);
                         //Log.d("TAG4",message);

                          //si aun no responde todas las preguntas de todas las secciones
                         if (!countAnswers.equals(countQuestions) && !countAnswers.equals(countMultiple) || countAnswers.equals(0)){
                             ifReady = false;
                         }
                     }
                     //Si respondio todo
                     if (ifReady ==true){
                         //loadPdfContent();
                         if (NetworkConnection.isConnected(getContext())){
                             loadPdfContent();
                         }else{
                             Toast.makeText(getContext(), "No tienes conexión a internet", Toast.LENGTH_SHORT).show();
                         }

                     }
                     else {
                         Toast.makeText(getContext(), "Aun restan preguntas por responder", Toast.LENGTH_SHORT).show();
                     }
                 }

                 return false;
             }
         });
         return listeners;
    }

    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        ToolbarBuilder builder = new ToolbarBuilder
                .Builder(formatSectionsFragmentBinding.toolbar)
                .withTitle("Secciones")
                .withMenu(R.menu.sections_menu)
                .withMenuItems(getMenuItems(),getMenuClicks())
                .withBackButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNavigationManager().removeFragmentFromBackStack();
                        getNavigationManager().addFragment(FormatDataFragment.newInstance(formatViewModel.getObjectFormatByIdFormat(format.getFkformat())));
                    }
                })
                .build();
        return builder;
    }


    @Override
    public void OnClickSectionPresenter(FormatSectionEntity formatSectionEntity) {
        //SectionDataEntity sectionDataEntity = sectionDataViewModel.getObjectSectionDataByFkSection(formatSectionEntity.getId());
       //  getNavigationManager().addFragment(FormFragment.newInstance(formatSectionEntity,format,sectionDataEntity));
    }
    private void   ShowDialogInfo(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ShowInfoSections showInfoSections= new ShowInfoSections();
        showInfoSections.setFormatObject(format);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.add(android.R.id.content,showInfoSections).addToBackStack(null).commit();

    }


}
