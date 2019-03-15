package gcode.baseproject.interactors.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CurrentDate {
    private  CurrentDate(){

    }
    public static String showDate(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,0);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = format1.format(cal.getTime());
        return date;
    }
}
