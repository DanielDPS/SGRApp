package gcode.baseproject.view.ui.camera;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.security.spec.EncodedKeySpec;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;
import gcode.baseproject.R;
import gcode.baseproject.databinding.CameraPhotosFragmentBinding;
import gcode.baseproject.interactors.adapters.DecodeImage;
import gcode.baseproject.interactors.date.CurrentDate;
import gcode.baseproject.interactors.db.entities.QuestionEntity;
import gcode.baseproject.interactors.db.entities.data.EvidenceDataEntity;
import gcode.baseproject.interactors.md5.EncryptMD5;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.viewmodel.dataEvidence.EvidenceDataViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.BIND_ABOVE_CLIENT;

public class CameraFragment extends BaseFragment {


    public static final String MY_POSITION = "MyPositionFile";
    private final int MY_PERMISSIONS = 100;
    private static final int PHOTO_CODE = 1;
     private  File wallpaperDirectory;
    private  static String idAnswer;
    private static  int countImage =0;
    private  String pathEvidence;
    private  File f;
    private static int positionQuestion;
    private EvidenceDataViewModel evidenceDataViewModel;
    private static QuestionEntity question;
    private static android.app.AlertDialog myAlert;
    private CameraPhotosFragmentBinding binding;
    public  static  CameraFragment newInstance(String id, int position, QuestionEntity questionEntity, AlertDialog alert){
        CameraFragment cameraFragment = new CameraFragment();
        idAnswer =id;
        positionQuestion =position;
        question= questionEntity;
        myAlert = alert;
        return  cameraFragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CameraPhotosFragmentBinding.inflate(inflater, container,false);
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        evidenceDataViewModel= ViewModelProviders.of(this).get(EvidenceDataViewModel.class);
    }
    private  void ValidateFile(String id,File file){
        if (file.exists()){
            OpenCamera(file);
            SaveEvidence(id,file.getAbsolutePath());
        }
        else {
            return;
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        myAlert.dismiss();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getActivity().getSharedPreferences(MY_POSITION, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("positionQ", positionQuestion);

        // Commit the edits!
        editor.commit();
        binding.btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 pathEvidence = Environment.getExternalStorageDirectory().getAbsolutePath() + "/questions/options";
                TakePhoto( pathEvidence, idAnswer);
                countImage = countImage+1;
                if(countImage==1){
                    OpenCamera(f);

                }else {
                    if (countImage ==2){
                        OpenCamera(f);
                    }else if (countImage==3){
                        OpenCamera(f);
                    }else if (countImage==4){
                        OpenCamera(f);

                    }else if (countImage==5){
                        OpenCamera(f);
                    }else if (countImage==6){
                        OpenCamera(f);
                        countImage=0;
                        return;

                    }
                }

            }
        });
    }

    @NonNull
    @Override
    public String getFragmentTag() {
        return CameraFragment.class.getName();
    }

    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        ToolbarBuilder builder = new ToolbarBuilder
                .Builder(binding.toolbar)
                .withTitle("Fotografias")
                .withBackButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNavigationManager().removeFragmentFromBackStack();
                    }
                }).build();

        return builder;
    }


    private  void TakePhoto(String path,String id) {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 110);
        }else {

            String str_SaveFolderName = path;
            String str_randomnumber = String.valueOf(EncryptMD5.convertToMD5(String.valueOf(System.currentTimeMillis()+Math.random())));

            wallpaperDirectory = new File(str_SaveFolderName);
            if (!wallpaperDirectory.exists())
                wallpaperDirectory.mkdirs();
                String str_Camera_Photo_ImagePath = str_SaveFolderName
                        + "/" + str_randomnumber + ".jpg";
                f = new File(str_Camera_Photo_ImagePath);
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());




        }

    }
    void OpenCamera(File f){


            startActivityForResult(new Intent(
                            MediaStore.ACTION_IMAGE_CAPTURE).putExtra(
                    MediaStore.EXTRA_OUTPUT, Uri.fromFile(f)),
                    PHOTO_CODE);

    }
    void SaveEvidence(String id,String ruta){

        final EvidenceDataEntity evidenceDataEntity = new EvidenceDataEntity();
        evidenceDataEntity.setFkquestionData(id);
        evidenceDataEntity.setImageUrl(ruta);
        evidenceDataEntity.setCreation(CurrentDate.showDate());
        evidenceDataViewModel.AddEvidence(evidenceDataEntity).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {


                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(),"Error en: "+e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_CODE  && resultCode == RESULT_OK) {
            if(f.exists()){
                Bitmap bitmapImage= DecodeImage.decode_image2(new File(f.getAbsolutePath()));
                binding.imgTaked.setImageBitmap(bitmapImage);
                SaveEvidence(idAnswer,f.getAbsolutePath());

            }else {
                return;
            }


        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS){
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext(), "No tienes permisos", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
