package gcode.baseproject.interactors.progress;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.domain.model.format.FormatData;
import gcode.baseproject.interactors.adapters.FormatDataAdapter;

public class ValidatingProgress {

    private  int progressStatus;
    private Handler handler;
    private ProgressDialog progressDialog;
     public  ValidatingProgress(Context context,String title,String name){
         handler= new Handler();
        progressDialog = new ProgressDialog(context);
         progressDialog.setTitle(title);
        progressDialog.setMessage(name);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(false);


    }
    public  void ShowProgressDialog(){
        progressDialog.show();
    }
    public void Progress(final RecyclerView recyclerView, final Context context, final RecyclerView.Adapter adapter){

        new Thread(new Runnable() {
            @Override
            public void run() {




                while (progressStatus <=70  ) {


                    try {
                        Thread.sleep(20);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.setProgress(progressStatus);
                            if (progressStatus ==70) {

                                    progressDialog.dismiss();
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                    recyclerView.setAdapter(adapter);

                            }
                        }
                    });


                }
            }
        }).start();

    }
    public  void CloseProgressDialog(){
        progressDialog.dismiss();
    }

    /*
     handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //progressDialog.setProgress(progressStatus);
                            if (recyclerView.getAdapter() != null){


                                if (recyclerView.getAdapter().getItemCount() !=0){
                                    progressDialog.dismiss();
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                                    recyclerView.setAdapter(adapter);
                                }


                            }
                        }
                    });
     */
    public int getProgressStatus(){
        return this.progressStatus;
    }





}
