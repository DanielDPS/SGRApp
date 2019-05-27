package gcode.baseproject.interactors;
import android.content.Context;

import gcode.baseproject.view.utils.NavigationManager;

public   class MyContext {


    private  static MyContext instance;
    private  Context navigationManager;
    public  static MyContext Instance (){
        if (instance==null)
            instance= new MyContext();
        return instance;
    }
    public Context getContextFragment(){
        return this.navigationManager;
    }
    public  void setContextFragment(Context navigationManager){
        this.navigationManager= navigationManager;
    }
}
