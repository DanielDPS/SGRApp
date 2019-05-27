package gcode.baseproject.interactors.pdf;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import gcode.baseproject.domain.model.pdf.Answer;
import gcode.baseproject.domain.model.pdf.AnswerMultiple;
import gcode.baseproject.domain.model.pdf.Option;
import gcode.baseproject.domain.model.pdf.Question;
import gcode.baseproject.domain.model.pdf.Section;
import gcode.baseproject.interactors.adapters.DecodeImage;

public class TemplateBase {

    private void addChildPRight(Paragraph paragraph, Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_RIGHT);
        paragraph.add(childParagraph);
    }
    private void addChildPCenter(Paragraph paragraph, Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
    }
    private void addChildLeftBoth(Paragraph paragraph, Paragraph childParagraph,Paragraph p2){
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
        paragraph.add(p2);
    }
    private void addChildPLeft(Paragraph paragraph, Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(childParagraph);
    }
    public  void addChildPJustifyAll(Paragraph paragraph, Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
        paragraph.add(childParagraph);
    }
    public  void addChildPJustify(Paragraph paragraph, Paragraph childParagraph){
        childParagraph.setAlignment(Element.ALIGN_JUSTIFIED);
        paragraph.add(childParagraph);
    }

    public  void addTitle(Document document, String title,int type){


        try {
            Paragraph paragraph = new Paragraph();
            if(type==1){
                Paragraph p1 = new Paragraph(title,GeneralFonts.fontTitleBold());
                addChildPCenter(paragraph, p1);
            }else if (type==2) {
                Paragraph pp = new Paragraph(title,GeneralFonts.fontTitleNormal());
                addChildPJustify(paragraph, pp);
            }else if (type ==3){
                Paragraph p3 =new Paragraph(title,GeneralFonts.fontTitleBold());
                p3.setSpacingAfter(4);
                addChildPLeft(paragraph, p3);

            }else if (type==4){
                addChildPLeft(paragraph,new Paragraph(title,GeneralFonts.fontTitleNormal()));
            }
            else if (type==5){
                Paragraph p5= new Paragraph(title,GeneralFonts.fontTitleBold());
                addChildPLeft(paragraph, p5);
            }else if (type==6){
                Paragraph p6= new Paragraph(title,GeneralFonts.fontTitleBold());
                addChildPRight(paragraph, p6);
            }
            paragraph.setSpacingBefore(1);
            paragraph.setSpacingAfter(1);
            document.add(paragraph);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    public  void addTitleBoth(Document document, String title,String title2){


        try {
            Paragraph paragraph = new Paragraph();
            Paragraph p1 = new Paragraph(title+": ",GeneralFonts.fontTitleBold());
            Paragraph p2 = new Paragraph(title2,GeneralFonts.fontTitleNormal());
            Chunk ck1= new Chunk(String.valueOf(p1));
            ck1.setFont(GeneralFonts.fontTitleBold());
            paragraph.add(ck1);
            Chunk ck2= new Chunk(String.valueOf(p2));
            ck2.setFont(GeneralFonts.fontTitleNormal());
            paragraph.add(ck2);
            paragraph.setSpacingBefore(1);
            paragraph.setSpacingAfter(1);
            paragraph.setAlignment(Element.ALIGN_LEFT);

            document.add(paragraph);

        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void AddImageDrawable (Document document,Drawable drawable){
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,10,stream);
        byte[]arrayBytes = stream.toByteArray();
        try {
            Image logo = Image.getInstance(arrayBytes);
            logo.setAlignment(Element.ALIGN_CENTER);
            logo.scalePercent(25);
            document.add(logo);

        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void addText(Document document, String text, int type){

        try {
            Paragraph paragraph = new Paragraph();
            if (type==1){
                Paragraph paragraph1 = new Paragraph(text,GeneralFonts.fontTitleNormal());
                paragraph1.setSpacingBefore(5);
                paragraph1.setSpacingAfter(5);
                addChildPJustify(paragraph, paragraph1);
            }else if (type==2){
                Paragraph p2 = new Paragraph(text,GeneralFonts.fontTitleNormal());
                p2.setSpacingAfter(5);
                addChildPCenter(paragraph,p2);
            }else if (type==3){
                Paragraph p3=new Paragraph(text,GeneralFonts.fontTitleBold());
                p3.setSpacingAfter(5);
                addChildPCenter(paragraph,p3);
            }
            else {
                addChildPCenter(paragraph, new Paragraph(text,GeneralFonts.fontTitleNormal()));
            }
            document.add(paragraph);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    public  void  AddImageToDB3(Document document, String path){
        Image image = null;
        try{

            if (path==null){
                return;
            }else {

                File file = new File(path);
                if (file.exists()){
                    Bitmap bitmap = DecodeImage.decode_image(file,300);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
                    byte[] imageInByte = stream.toByteArray();
                    image = Image.getInstance(imageInByte);
                    image.setAlignment(Element.ALIGN_CENTER);
                }else {
                    return;

                }




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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private List<PdfPCell> createCells(Section section, PdfPTable table)throws DocumentException {
        List<PdfPCell> cells  = new ArrayList<>();
        int optionSize = section.getQuestions().get(0).getOptions().size();
        PdfPCell answerCell = new PdfPCell(new Phrase("PREGUNTA",GeneralFonts.fontTitleBold()));
        answerCell.setBackgroundColor(new BaseColor(215 ,227,189));
        answerCell.setVerticalAlignment(Element.ALIGN_CENTER);
        answerCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cells.add(answerCell);
        for (Option option : section.getQuestions().get(0).getOptions()){
            PdfPCell cellOption = new PdfPCell(new Phrase(option.getTitle(),GeneralFonts.fontTitleBold()));
            cellOption.setBackgroundColor(new BaseColor(215 ,227,189));
            cellOption.setVerticalAlignment(Element.ALIGN_CENTER);
            cellOption.setHorizontalAlignment(Element.ALIGN_LEFT);
            cells.add(cellOption);
        }

        for(Question question : section.getQuestions()){
            PdfPCell cellQuestion = new PdfPCell(new Phrase(question.getTitle(),GeneralFonts.fontTitleNormal()));
            Answer answer = question.getAnswer();


            PdfPCell cellYes = null;
            PdfPCell cellNon = null;
            PdfPCell cellNa=null;
            if (optionSize ==2){
                table.setWidths(new int[]{82,9,9});
                if (answer.getText().equals("SI")) {
                    cellYes = new PdfPCell(new Phrase("X",GeneralFonts.fontTitleBold()));
                    cellYes.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellNon = new PdfPCell(new Phrase(" " ));
                }
                else if (answer.getText().equals("NO")){
                    cellYes = new PdfPCell(new Phrase( " "));
                    cellNon = new PdfPCell(new Phrase("X" ,GeneralFonts.fontTitleBold()));
                    cellNon.setHorizontalAlignment(Element.ALIGN_CENTER);
                }

            }
            else if (optionSize==3){
                table.setWidths(new int[]{80,6,6,6});
                if (answer.getText().equals("SI")) {
                    cellYes = new PdfPCell(new Phrase("X",GeneralFonts.fontTitleBold()));
                    cellYes.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellNon = new PdfPCell(new Phrase(" " ));
                    cellNa = new PdfPCell(new Phrase(""));
                }
                else if (answer.getText().equals("NO")){
                    cellYes = new PdfPCell(new Phrase( " "));
                    cellNon = new PdfPCell(new Phrase("X" ,GeneralFonts.fontTitleBold()));
                    cellNon.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellNa = new PdfPCell(new Phrase(""));

                }else if (answer.getText().equals("N.A.")){
                    cellYes = new PdfPCell(new Phrase( " "));
                    cellNon = new PdfPCell(new Phrase(" " ));
                    cellNa = new PdfPCell(new Phrase("X",GeneralFonts.fontTitleBold()));
                    cellNa.setHorizontalAlignment(Element.ALIGN_CENTER);

                }

            }


            cells.add(cellQuestion);
            cells.add(cellYes);
            cells.add(cellNon);
            if (cellNa != null){
                cells.add(cellNa);
            }
        }

        return  cells;
    }
    // Generate Table
    public void createMultiple(Document document, Section section) throws DocumentException {
        int optionsSize = section.getQuestions().get(0).getOptions().size();
        int width = optionsSize + 1;

        PdfPTable table = new PdfPTable(width);
        table.setWidthPercentage(100);



        List<PdfPCell> cells = createCells(section,table);

        for (PdfPCell cell : cells) {
            table.addCell(cell);
        }

        document.add(table);
    }
    /**INICIO TABLAS MULTIPLES **/
    /**INICIO CELDAS**/
    public  List<PdfPCell> createCellsMultipleWithNoObservations( AnswerMultiple answerMultiple){
        List<PdfPCell> cells  = new ArrayList<>();
        PdfPCell cellObservationTitle= new PdfPCell(new Phrase("OBSERVACIONES "));
        cellObservationTitle.setBackgroundColor(new BaseColor(215 ,227,189));
        cellObservationTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        cells.add(cellObservationTitle);

        PdfPCell cellObservation = new PdfPCell(new Phrase(answerMultiple.getObservationMultiple()));
        cellObservation.setHorizontalAlignment(Element.ALIGN_LEFT);
        cells.add(cellObservation);

        return  cells;
    }
    /**FIN CELDAS **/
    public void createWithNoObservationsMultiple(Document document, AnswerMultiple answer) throws DocumentException {


        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new int[] {25,75});
        List<PdfPCell> cells = createCellsMultipleWithNoObservations(answer);

        for (PdfPCell cell : cells) {
            table.addCell(cell);
        }

        document.add(table);
    }

    /**FIN TABLAS MULTIPLES**/
    /**INICIO TABLAS NORMALES**/

    /** INICIO CELDAS  **/
    private  List<PdfPCell> createCellsWithNo(Question question){
        List<PdfPCell> cells  = new ArrayList<>();



        PdfPCell cellHallazgo = new PdfPCell(new Phrase("Hallazgo ",GeneralFonts.fontTitleBold()));
        cellHallazgo.setHorizontalAlignment(Element.ALIGN_CENTER);
        cells.add(cellHallazgo);


        PdfPCell celltextHallazgo = new PdfPCell(new Phrase(question.getHallazgo(),GeneralFonts.fontTitleNormal()));
        celltextHallazgo.setBackgroundColor(new BaseColor(215 ,227,189));
        celltextHallazgo.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cells.add(celltextHallazgo);

        PdfPCell cellType = new PdfPCell(new Phrase("Tipo de Hallazgo",GeneralFonts.fontTitleBold()));
        cellType.setHorizontalAlignment(Element.ALIGN_CENTER);
        cells.add(cellType);

        PdfPCell cellCritical = new PdfPCell(new Phrase(question.isCritical()==true?"CRÍTICO":"NO CRÍTICO",GeneralFonts.fontTitleNormal()));
        cellCritical.setBackgroundColor(new BaseColor(215 ,227,189));
        cellCritical.setHorizontalAlignment(Element.ALIGN_CENTER);
        cells.add(cellCritical);


        return  cells;

    }
    public  List<PdfPCell> createCellsWithNoObservations( Answer answer){
        List<PdfPCell> cells  = new ArrayList<>();
        PdfPCell cellObservationTitle= new PdfPCell(new Phrase("OBSERVACIONES ",GeneralFonts.fontTitleBold()));
        cellObservationTitle.setBackgroundColor(new BaseColor(215 ,227,189));
        cellObservationTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        cells.add(cellObservationTitle);

        PdfPCell cellObservation = new PdfPCell(new Phrase(answer.getObservation(),GeneralFonts.fontTitleNormal()));
        cellObservation.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cells.add(cellObservation);

        return  cells;
    }
    public  List<PdfPCell> createCellsWithNoFundament(Question question){
        List<PdfPCell> cells  = new ArrayList<>();

        PdfPCell cellFundamentTitle = new PdfPCell(new Phrase("FUNDAMENTO JURÍDICO",GeneralFonts.fontTitleBold()));
        cellFundamentTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        cells.add(cellFundamentTitle);

        PdfPCell cellFundament = new PdfPCell(new Phrase(question.getFound(),GeneralFonts.fontTitleNormal()));
        cellFundament.setBackgroundColor(new BaseColor(215 ,227,189));
        cellFundament.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cells.add(cellFundament);
        return  cells;
    }
    /** FIN CELDAS **/
    public void createMultipleWithNo(Document document, Question question) throws DocumentException {


        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        table.setWidths(new int[] {25,45,15,15});
        List<PdfPCell> cells = createCellsWithNo(question);

        for (PdfPCell cell : cells) {
            table.addCell(cell);
        }
        table.setSpacingBefore(15);
        document.add(table);
    }
    public void createMultipleWithNoObservations(Document document,Answer answer) throws DocumentException {


        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new int[] {25,75});
        List<PdfPCell> cells = createCellsWithNoObservations(answer);

        for (PdfPCell cell : cells) {
            table.addCell(cell);
        }

        document.add(table);
    }
    public void createMultipleWithNoFundament(Document document, Question question) throws DocumentException {


        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new int[] {25,75});
        List<PdfPCell> cells = createCellsWithNoFundament(question);

        for (PdfPCell cell : cells) {
            table.addCell(cell);
        }
        table.setSpacingAfter(10);
        document.add(table);
    }
    /** INICIO TABLAS NORMALES **/
    public  void createTableForImages(Document document,List<String> paths,String title) throws DocumentException,IOException{


        document.add(new Paragraph(title,GeneralFonts.fontTitleBold()));
        if (paths.size() ==1){
            PdfPTable tableOpnePhoto = new PdfPTable(1);
            tableOpnePhoto.setWidths(new int[] {50});
            tableOpnePhoto.setWidthPercentage(100);
            Bitmap bitmap = DecodeImage.decode_image(new File(paths.get(0)),300);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            byte[] imageInByte = stream.toByteArray();
            Image image = Image.getInstance(imageInByte);
            image.scalePercent(75);
            image.setRotation(90);
            PdfPCell cell = new PdfPCell(image);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(5);
            tableOpnePhoto.addCell(cell);
            document.add(tableOpnePhoto);

        }else {

        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int [] {50,50});
        table.setWidthPercentage(100);
        for (String path : paths)
        {

            Bitmap bitmap = DecodeImage.decode_image(new File(path),300);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            byte[] imageInByte = stream.toByteArray();
            Image image = Image.getInstance(imageInByte);
            image.scalePercent(75);
            image.setRotation(90);
            PdfPCell cell = new PdfPCell(image);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(5);
            table.addCell(cell);
        }
        document.add(table);

        }


    }


}
