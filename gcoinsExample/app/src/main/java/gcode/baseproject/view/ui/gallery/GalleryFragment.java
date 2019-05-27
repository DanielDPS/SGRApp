package gcode.baseproject.view.ui.gallery;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.GalleryFragmentBinding;
import gcode.baseproject.domain.model.data.QuestionData;
import gcode.baseproject.domain.model.pdf.Answer;
import gcode.baseproject.interactors.adapters.ClickListener;
import gcode.baseproject.interactors.adapters.DialogProgressDark;
import gcode.baseproject.interactors.adapters.GalleryAdapter;
import gcode.baseproject.interactors.adapters.RecyclerTouchListener;
import gcode.baseproject.interactors.db.entities.data.EvidenceDataEntity;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;
import gcode.baseproject.interactors.dialogs.CreateDialog;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.viewmodel.dataEvidence.EvidenceDataViewModel;
import gcode.baseproject.view.viewmodel.dataQuestion.QuestionDataViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class GalleryFragment  extends BaseFragment {


    public static final String MY_POSITION = "MyPositionFile";
    private EvidenceDataViewModel evidenceDataViewModel;
    private GalleryFragmentBinding galleryFragmentBinding;
    private  static QuestionDataEntity answerDb;
    private  GalleryAdapter galleryAdapter;
    private  static int positionQuestion;
    private QuestionDataViewModel questionDataViewModel;
    public static final GalleryFragment getInstance(QuestionDataEntity answer,int position){
        GalleryFragment galleryFragment = new GalleryFragment();
        answerDb = answer;
        positionQuestion= position;
        return galleryFragment;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        galleryFragmentBinding = GalleryFragmentBinding.inflate(inflater,container,false);
        return galleryFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //viewmodel
        evidenceDataViewModel = ViewModelProviders.of(this).get(EvidenceDataViewModel.class);
        questionDataViewModel = ViewModelProviders.of(this).get(QuestionDataViewModel.class);
    }


    private void SyncImages(String id){
        evidenceDataViewModel.ClearEvidenceList(this);
        evidenceDataViewModel.LoadEvidenceList(id);
        evidenceDataViewModel.getEvidenceData()
                .observe(this, new Observer<List<EvidenceDataEntity>>() {
                    @Override
                    public void onChanged(final List<EvidenceDataEntity> evidenceDataEntities) {

                        if (evidenceDataEntities.size() ==0){
                            galleryFragmentBinding.nodata.setVisibility(View.VISIBLE);
                        }else {
                            galleryFragmentBinding.nodata.setVisibility(View.GONE);

                            galleryFragmentBinding.galleryRecycler.setHasFixedSize(true);
                            galleryFragmentBinding.galleryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                            galleryAdapter = new GalleryAdapter(evidenceDataEntities);
                            galleryFragmentBinding.galleryRecycler.setAdapter(galleryAdapter);

                        }



                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getActivity().getSharedPreferences(MY_POSITION, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("positionQ", positionQuestion);
        // Commit the edits!
        editor.commit();
        SyncImages(answerDb.getId());


        //respuestas normales
       /* if (answerDb != null ){
            SyncImages(answerDb.getId());

if (questionDataViewModel.getCountQuestionsByFkQuestion(idQuestion,idFormatData) !=null){
                 SyncImages(questionDataViewModel.getCountQuestionsByFkQuestion(idQuestion, idFormatData).getId());
             }
        }*/
        //respuestas multiples

            galleryFragmentBinding.galleryRecycler.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                    galleryFragmentBinding.galleryRecycler, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    EvidenceDataEntity evidenceDataEntity = galleryAdapter.getItemByPosition(position);
                }

                @Override
                public void onLongClick(View view, int position) {
                    final EvidenceDataEntity evidenceDataEntity = galleryAdapter.getItemByPosition(position);

                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    View viewGallery = inflater.inflate(R.layout.dialog_confirm_delete, null);
                    MaterialButton btnLeave = viewGallery.findViewById(R.id.btnDelete);
                    MaterialButton btnCancel = viewGallery.findViewById(R.id.btnCancelDelete);
                    CreateDialog myDialog = new CreateDialog();
                    final android.app.AlertDialog.Builder builder = myDialog.openDialog2(getContext(), viewGallery);
                    builder.setTitle("Confirmación");
                    builder.setIcon(R.drawable.ic_info_outline);
                    builder.setMessage(R.string.message_delete_evidence);
                    final android.app.AlertDialog alert = builder.create();
                    btnLeave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            final ProgressDialog dialog = new ProgressDialog(getContext(), AlertDialog.THEME_HOLO_DARK);
                            dialog.setMessage("Eliminando...");
                            dialog.setIndeterminate(false);
                            dialog.setCancelable(false);
                            dialog.show();
                            evidenceDataViewModel.DeleteEvidence(evidenceDataEntity)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DisposableCompletableObserver() {
                                        @Override
                                        public void onComplete() {
                                            alert.dismiss();
                                            Toast.makeText(getContext(), "Se ha eliminado correctamente..!!", Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                            //SyncImages();

                                        }

                                        @Override
                                        public void onError(Throwable e) {
                                            alert.dismiss();
                                            dialog.dismiss();

                                        }
                                    });


                        }
                    });
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                        }
                    });
                    alert.show();

                }


            }));



    }
    /*

     */

    @NonNull
    @Override
    public String getFragmentTag() {
        return GalleryFragment.class.getName();
    }

    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        ToolbarBuilder builder = new ToolbarBuilder
                .Builder(galleryFragmentBinding.toolbar)
                .withTitle("Galeria")
                .withMenu(R.menu.main_menu)
                .withBackButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNavigationManager().removeFragmentFromBackStack();
                    }
                })
                .build();
        return builder;
    }
}
