package gcode.baseproject.interactors.pdf;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;

public class GeneralFonts {

    public  static Font fontTitleBold (){
        return  new Font(FontFactory.getFont("Arial Narrow", BaseFont.IDENTITY_H,false,12,Font.BOLD));
    }
    public static  Font fontTitleNormal(){
        return  new Font(FontFactory.getFont("Arial Narrow", BaseFont.IDENTITY_H,false,12,Font.NORMAL));
    }
    public  static Font fontTitleBold10 (){
        return  new Font(FontFactory.getFont("Arial Narrow", BaseFont.IDENTITY_H,false,10,Font.BOLD));
    }

}
