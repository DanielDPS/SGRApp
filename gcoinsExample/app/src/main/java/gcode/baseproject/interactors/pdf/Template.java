package gcode.baseproject.interactors.pdf;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEventAfterSplit;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gcode.baseproject.domain.model.pdf.Question;
import gcode.baseproject.domain.model.pdf.Section;
import gcode.baseproject.domain.model.pdf.SectionData;
import gcode.baseproject.interactors.db.entities.OptionEntity;
import gcode.baseproject.interactors.db.entities.QuestionEntity;

public class Template {
    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font answer= new Font(Font.FontFamily.TIMES_ROMAN,12, Font.NORMAL);
    private Font question= new Font(Font.FontFamily.TIMES_ROMAN,12, Font.NORMAL,BaseColor.BLACK);
    private Font fTitle= new Font(Font.FontFamily.TIMES_ROMAN,14, Font.BOLD);
    private Font fSubTitle= new Font(Font.FontFamily.TIMES_ROMAN,13, Font.BOLD);
    private Font fText= new Font(Font.FontFamily.TIMES_ROMAN,12, Font.NORMAL);
    private Font fHighText= new Font(Font.FontFamily.TIMES_ROMAN,12, Font.NORMAL);
    private File folder;
    public Template(Context context){
        this.context = context;
     }
    public   void openDocument()
    {



        try{
            document = new Document(PageSize.A4);

            pdfWriter = PdfWriter.getInstance(document,new FileOutputStream(pdfFile));

            document.open();

            //manipulatePdf(folder.getAbsolutePath(),folder.getAbsolutePath()+"forms");




        }catch(Exception e){
            Log.e("OpenDocument",e.getLocalizedMessage());
        }
    }


    public  void createFile(String fileName){
        folder =  new File(Environment.getExternalStorageDirectory().toString(),"PDF");
        if (!folder.exists())
            folder.mkdirs();
        pdfFile= new File(folder,fileName);

    }

    public void closeDocument(){

        document.close();
    }
    public void addMetaData(String title,String subject,String author){
        document.addTitle(title);
        document.addSubject(subject);
        document.addAuthor(author);
    }


    public void  addTitle1(Document document, String title) {


    }



    public void addTitles(String title,String subTitle,String date,String identifier){

        try {



            paragraph= new Paragraph();
            addChildPRight(new Paragraph(subTitle,fSubTitle));
            paragraph.setSpacingBefore(5);
             document.add(paragraph);


            paragraph= new Paragraph();
            addChildPRight(new Paragraph("ACTA NO. : "+ identifier +" "+date,fHighText));
             document.add(paragraph);



            paragraph= new Paragraph();
            addChildPRight(new Paragraph("S A G A N O R  H I D R O C A R B U R O S"));
            paragraph.setSpacingAfter(30);
             document.add(paragraph);


        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }
    private void addChildPCenter(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }
    private void addChildPRight(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_RIGHT);
        paragraph.add(childParagraph);
    }
    private void addChildPLeft(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(childParagraph);
    }
    public  void addChildPJustifyAll(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
        paragraph.add(childParagraph);
    }
    public  void addChildPJustify(Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_JUSTIFIED);
        paragraph.add(childParagraph);
    }

    public  void addTitle(String title,int type){

        try {
            paragraph = new Paragraph();
            if(type==1){
                addChildPCenter(new Paragraph(title,fTitle));
            }else if (type==2) {
                addChildPJustify(new Paragraph(title,fTitle));
            }else if (type ==3){
                addChildPLeft(new Paragraph(title,question));
            }else{
                addChildPLeft(new Paragraph(title,answer));
            }
            paragraph.setSpacingBefore(5);
            paragraph.setSpacingAfter(5);
            document.add(paragraph);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void addText(Document document, String text, int type){

        try {
            paragraph = new Paragraph();
            if (type==1){
                addChildPJustifyAll(new Paragraph(text,fText));
            }else {
                addChildPLeft(new Paragraph(text,fText));
            }
             document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public PdfPTable createTABLE (int size){

        PdfPTable table = new PdfPTable(size);
        return  table;
    }


    public void  createHeader(String [] header){
        paragraph = new Paragraph();
        paragraph.setFont(fText);

        PdfPTable pdfPTable = new PdfPTable(header.length);
        pdfPTable.setWidthPercentage(100);
        pdfPTable.setSpacingBefore(20);
        PdfPCell pdfPCell;
        int indexC = 0;
        while (indexC < header.length) {
            pdfPCell = new PdfPCell(new Phrase(header[indexC++], fSubTitle));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setVerticalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            pdfPTable.addCell(pdfPCell);
        }

        paragraph.add(pdfPTable);
        try {
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }


    }
    public void CreateBodyTable(ArrayList<QuestionEntity[]> questions, List<OptionEntity>options , PdfPTable table,String iddb){
        //rows

        PdfPCell pdfPCellQuestions;
        PdfPCell pdfPCellQuestionFirst;
        PdfPCell pdfPCellOptions1;
        PdfPCell pdfPCellOptions2;
        PdfPCell pdfPCellOption11;
        PdfPCell pdfPCellOption22;
        PdfPCell pdfPCellOptions3;
        for (int iQuestion = 0; iQuestion < questions.size(); iQuestion++) {

                QuestionEntity[] questionName= questions.get(iQuestion);
                pdfPCellQuestions = new PdfPCell(new Phrase(questionName[iQuestion].getDescription()));
                pdfPCellQuestions.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCellQuestions.setVerticalAlignment(Element.ALIGN_CENTER);
                pdfPCellQuestions.setFixedHeight(50);
                table.addCell(pdfPCellQuestions);



            for (int indexOption = 0; indexOption< options.size(); indexOption++){


                        if (options.size()==2){

                                pdfPCellOptions1= new PdfPCell(new Phrase(iddb.equals(options.get(0).getId())?"X":"",new Font(Font.FontFamily.TIMES_ROMAN,13,Font.BOLD,BaseColor.BLACK)));
                                pdfPCellOptions1.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCellOptions1.setVerticalAlignment(Element.ALIGN_CENTER);
                                table.addCell(pdfPCellOptions1);
                                pdfPCellOptions2= new PdfPCell(new Phrase(iddb.equals(options.get(1).getId())?"X":"",new Font(Font.FontFamily.TIMES_ROMAN,13,Font.BOLD,BaseColor.BLACK)));
                                pdfPCellOptions2.setHorizontalAlignment(Element.ALIGN_CENTER);
                                pdfPCellOptions2.setVerticalAlignment(Element.ALIGN_CENTER);
                                table.addCell(pdfPCellOptions2);


                        }else if (options.size() ==3) {
                            pdfPCellOption11= new PdfPCell(new Phrase(iddb.equals(options.get(0).getId())?"X":"",new Font(Font.FontFamily.TIMES_ROMAN,13,Font.BOLD,BaseColor.BLACK)));
                            pdfPCellOption11.setHorizontalAlignment(Element.ALIGN_CENTER);
                            pdfPCellOption11.setVerticalAlignment(Element.ALIGN_CENTER);
                            table.addCell(pdfPCellOption11);
                            pdfPCellOption22= new PdfPCell(new Phrase(iddb.equals(options.get(1).getId())?"X":"",new Font(Font.FontFamily.TIMES_ROMAN,13,Font.BOLD,BaseColor.BLACK)));
                            pdfPCellOption22.setHorizontalAlignment(Element.ALIGN_CENTER);
                            pdfPCellOption22.setVerticalAlignment(Element.ALIGN_CENTER);
                            table.addCell(pdfPCellOption22);
                            pdfPCellOptions3 = new PdfPCell(new Phrase(iddb.equals(options.get(2).getId())?"X":"", new Font(Font.FontFamily.TIMES_ROMAN,13,Font.BOLD,BaseColor.BLACK)));
                            pdfPCellOptions3.setHorizontalAlignment(Element.ALIGN_CENTER);
                            pdfPCellOptions3.setVerticalAlignment(Element.ALIGN_CENTER);
                            table.addCell(pdfPCellOptions3);
                        }
            }

        }
    }
    //agregar celdas
    public PdfPTable CreateHeaderTable( int countHeaders){

            paragraph = new Paragraph();
            paragraph.setFont(fText);
            PdfPTable pdfPTable = new PdfPTable(countHeaders);
            pdfPTable.setWidthPercentage(100);
            pdfPTable.setSpacingBefore(2);
            pdfPTable.setSpacingAfter(2);
            paragraph.add(pdfPTable);
            return  pdfPTable;

    }
    public void getTableForOptions(List<String[]> content){
        paragraph = new Paragraph();
        paragraph.setFont(fText);
        PdfPCell cell;
        PdfPTable pdfPTable = new PdfPTable(content.size());

        for (int i =0 ; i <content.size(); i++){
            //Phrase phrase = new Phrase();
           // phrase.addAll(content.get(i))
            //cell = new PdfPCell(new Phrase(content.get(0),content.get(1)));
        }


        paragraph.add(pdfPTable);
    }

    public  void CreateFooterTable(PdfPTable table){

        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    public  PdfWriter getPdfWriter(){
        return this.pdfWriter;
    }
    public  Document getDocument(){
        return  this.document;
    }
    public  File getPdfFile(){
        return this.pdfFile;
    }
    public  void  AddImageName(String imageName){
        Image image = null;
        try {
            image = Image.getInstance(folder.getAbsolutePath() + File.separator + imageName + ".png");
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        image.setSpacingBefore(5);
        image.setSpacingAfter(5);
        image.scaleToFit(100,100);
        image.setAlignment(Element.ALIGN_LEFT);
        try {
            document.add(image);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    public  void  AddImageToDB(String path){
        Image image = null;
        try {
            if (path==null){
                image= null;
            }else {
                image = Image.getInstance(path);
                image.setSpacingBefore(5);
                image.setSpacingAfter(5);
                image.scaleToFit(100,100);
                image.setAlignment(Element.ALIGN_LEFT);
            }

        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            document.add(image);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    public void viewPDF(){




        /*
        Intent intent = new Intent(context, ViewPDFActivity.class);
        intent.putExtra("path",pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        */
    }


    public   PdfPTable createTABLEMultipleRef( int size){
        PdfPTable table = new PdfPTable( size);
        return  table;
    }
    // Crear TAblas para multireferencia
    public PdfPTable createTableForMultireference(Section section) {
        int optionsSize = section.getQuestions().get(0).getOptions().size();
        int tankCounts = section.getSectionDataList().size();
        int width = optionsSize * tankCounts + 1;


        Log.d("TAG4", String.format("My Section has %d Tanks",section.getSectionDataList().size()));
        /// Cabezera
        PdfPTable table = new PdfPTable(3);

        List<SectionData> dataList = section.getSectionDataList();


        for (int i = 0; i < dataList.size(); i++) {
            SectionData data  = dataList.get(0);
            //String message = String.format("%s %s %d", data.getIdSectionData(), data.getReference(), data.getFolio());
            table.addCell(data.getIdSectionData());
            //Log.d("TAG4", message);
        }

        /*
        PdfPCell cell = new PdfPCell(new Phrase("PREGUNTA"));
        cell.setRowspan(3);
        table.addCell(cell);*/


        return  table;
    }

    public void createTableForMulti(Section section) {
        try {
            int optionsSize = section.getQuestions().get(0).getOptions().size();
            int questionsCounts = section.getQuestions().size();
            int width = optionsSize * questionsCounts + 1;

            /// Cabezera
            PdfPTable table = new PdfPTable(width);
            PdfPCell cell = new PdfPCell(new Phrase("PREGUNTA"));
            cell.setRowspan(2);
            table.addCell(cell);


            int tanksCount = (width - 1) / optionsSize;
            for (int i = 1; i <= tanksCount; i++) {

            }
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    public  void tableExample(){

        try {
            PdfPTable table = new PdfPTable(5);

            table.setWidths(new int[]{ 1, 2, 2, 2, 1});

            PdfPCell cell;

            cell = new PdfPCell(new Phrase("S/N"));

            cell.setRowspan(2);

            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Name"));

            cell.setColspan(3);

            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Age"));

            cell.setRowspan(2);

            table.addCell(cell);

            table.addCell("SURNAME");

            table.addCell("FIRST NAME");

            table.addCell("MIDDLE NAME");

            table.addCell("1");

            table.addCell("James");

            table.addCell("Fish");

            table.addCell("Stone");

            table.addCell("17");
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

}
