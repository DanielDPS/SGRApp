package gcode.baseproject.interactors.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import gcode.baseproject.R;

public class CreateDialog {

    private  AlertDialog.Builder alertDialog;
    public CreateDialog(){

    }

    public AlertDialog.Builder openDialog(Context context, View view, String title){
        alertDialog=  new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setView(view);
        return alertDialog;
    }
    public   AlertDialog getDialog(){
        return alertDialog.show();
    }
}
