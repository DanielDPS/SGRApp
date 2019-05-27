package gcode.baseproject.interactors.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import gcode.baseproject.R;

public class CreateDialog {

    private static MaterialDialog.Builder alertDialog;
    private static AlertDialog.Builder alerDialog2;
    private static String Title;
    private static String Message;

    private static  CreateDialog instance=null;
    public  static  CreateDialog getInstanceDialog(){
        if (instance ==null)
            instance= new CreateDialog();
        return  instance;
    }
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public CreateDialog(){

    }

    public static MaterialDialog.Builder openDialog(Context context, View view, String title){
        alertDialog=  new MaterialDialog.Builder(context);
        alertDialog.title(title);
        alertDialog.customView(view,false);
        return alertDialog;
    }

    public static AlertDialog.Builder openDialog2(Context context, View view ){
        alerDialog2=  new AlertDialog.Builder(context);
        alerDialog2.setTitle(Title);
        alerDialog2.setMessage(Message);
        alerDialog2.setView(view);
        return alerDialog2;
    }
    public   MaterialDialog getDialog(){
        return alertDialog.show();
    }
}
