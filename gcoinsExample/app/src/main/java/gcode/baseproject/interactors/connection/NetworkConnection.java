package gcode.baseproject.interactors.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class NetworkConnection  {



    public  static  boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= cm.getActiveNetworkInfo();
        boolean connected = networkInfo !=null && networkInfo.isConnected() && networkInfo.isAvailable();
        if (connected ){
            return  true;
        }else {
            return false;
        }
    }
}
