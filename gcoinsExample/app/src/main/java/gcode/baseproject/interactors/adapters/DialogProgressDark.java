package gcode.baseproject.interactors.adapters;

import android.app.ProgressDialog;
import android.content.Context;

public class DialogProgressDark {


    private ProgressDialog dialog;
    public  DialogProgressDark(Context context,String message){
        dialog = new ProgressDialog(context);
        dialog.setMessage(message);
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.show();

    }
    public  void CloseDialog(){
         dialog.dismiss();
    }


}
