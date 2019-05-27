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
    public static String showShortDate(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,0);
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        String date = format1.format(cal.getTime());
        return date;
    }

    public static String  Day (){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,0);
        SimpleDateFormat format= new SimpleDateFormat("d");
        String day = format.format(calendar.getTime());
        return day;
    }

    public static String Month (){
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.MONTH,0);
        SimpleDateFormat formatmonth = new SimpleDateFormat("MMMM");
        String month =formatmonth.format(cal.getTime());
        return month;
    }
    public  static  String Year(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR,0);
        SimpleDateFormat format = new SimpleDateFormat("YYYY");
        String year = format.format(calendar.getTime());
        return  year;
    }
    public  static  String LongDate(){
        return  Day() + " de "+Month()+" del "+Year();
    }
}
