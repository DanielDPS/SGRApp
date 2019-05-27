package gcode.baseproject.interactors.pdf;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;

public class HeaderFooterPageEvent extends PdfPageEventHelper {
    private PdfTemplate t;
    private com.itextpdf.text.Image total;
    private  Drawable drawable;
    private  Drawable icon;
    private  String identifier;
    private  String textInfo;
    private  Drawable backgroundIcon;
    public  HeaderFooterPageEvent(Drawable drawable,Drawable icon,Drawable iconBackground,String text , String identifier){
        this.drawable= drawable;
        this.icon = icon;
        this.identifier= identifier;
        this.textInfo = text;
        this.backgroundIcon = iconBackground;
    }
    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = com.itextpdf.text.Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        addHeader(writer);
        AddBackgroundImage(writer);

    }
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        addFooter(writer);
    }
    private void addHeader(PdfWriter writer){
        PdfPTable header = new PdfPTable(2);
        try {
            // set defaults
            header.setWidths(new int[]{24,30});
            header.setTotalWidth(529);
            header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(40);
            header.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            // add image
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,10,stream);
            byte[]arrayBytes = stream.toByteArray();
            Image logo = com.itextpdf.text.Image.getInstance(arrayBytes);
            header.addCell(logo);


            PdfPCell cellIdentifier = new PdfPCell();
            Paragraph p1= new Paragraph(textInfo+identifier,GeneralFonts.fontTitleBold10());
            p1.setAlignment(Element.ALIGN_RIGHT);
            cellIdentifier.addElement(p1);
            cellIdentifier.setBorder(Rectangle.NO_BORDER);
            header.addCell(cellIdentifier);



            header.writeSelectedRows(0, -1, 30, 820, writer.getDirectContent());
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            throw new ExceptionConverter(e);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }
    private  void AddBackgroundImage(PdfWriter writer){




        if (backgroundIcon==null){
            return;
        }else {
            PdfContentByte canvas = writer.getDirectContentUnder();
            // add image
            Bitmap bitmap = ((BitmapDrawable)backgroundIcon ).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,10,stream);
            byte[]arrayBytes = stream.toByteArray();
            Image logo = null;
            try {
                logo = Image.getInstance(arrayBytes);
            } catch (BadElementException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            logo.scaleAbsolute(400,460);
            logo.setAbsolutePosition(100,210);
            try {
                canvas.addImage(logo);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }



    }
    private void addFooter(PdfWriter writer){
        PdfPTable footer = new PdfPTable(4);
        try {
            // set defaults
            footer.setWidths(new int[]{27,2,1,1});
            footer.setTotalWidth(527);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(40);
            footer.getDefaultCell().setBorder(Rectangle.TOP);
            footer.getDefaultCell().setBorderColor(new BaseColor(215 ,227,189));

            // add copyright
            footer.addCell(new Phrase("Francisco Monteverde 178, Col. San Benito, C.P. 83180, Hermosillo Sonora, 662 222 5918, saganorsc@gmail.com", new Font(FontFactory.getFont("Arial", BaseFont.IDENTITY_H,false,8,Font.BOLD,BaseColor.LIGHT_GRAY))));

            // add current page count
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(new Phrase(String.format("%d / ", writer.getPageNumber()), new Font(FontFactory.getFont("Arial Narrow", BaseFont.IDENTITY_H,false,8,Font.NORMAL))));

            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(total);
            totalPageCount.setBorder(Rectangle.TOP);
            totalPageCount.setBorderColor(new BaseColor(215 ,227,189));
            footer.addCell(totalPageCount);

            //add image icon
            Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,10,stream);
            byte[]arrayBytes = stream.toByteArray();

            Image logoImg = null;
            try {
                logoImg = Image.getInstance(arrayBytes);
                logoImg.scalePercent(17);
            } catch (IOException e) {
                e.printStackTrace();
            }
            PdfPCell cellIcon = new PdfPCell();
            cellIcon.setBorder(Rectangle.TOP);
            cellIcon.setBorderColor(new BaseColor(215 ,227,189));
            cellIcon.addElement(logoImg);
            footer.addCell(cellIcon);

            // write page
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            footer.writeSelectedRows(0, -1, 34, 30, canvas);
            canvas.endMarkedContentSequence();
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }
    public void onCloseDocument(PdfWriter writer, Document document) {
        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;
        ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
                new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
                totalWidth, 6, 0);
    }
}
