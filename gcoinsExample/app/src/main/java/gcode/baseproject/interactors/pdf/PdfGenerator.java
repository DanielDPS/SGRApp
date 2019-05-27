package gcode.baseproject.interactors.pdf;

import android.graphics.drawable.Drawable;
import android.os.Environment;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.font.TextAttribute;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.AttributedString;
import java.util.List;
import gcode.baseproject.domain.model.pdf.Answer;
import gcode.baseproject.domain.model.pdf.AnswerMultiple;
import gcode.baseproject.domain.model.pdf.Format;
import gcode.baseproject.domain.model.pdf.Question;
import gcode.baseproject.domain.model.pdf.Section;
import gcode.baseproject.domain.model.pdf.SectionData;
import gcode.baseproject.interactors.date.CurrentDate;
import gcode.baseproject.interactors.db.ApplicationDatabase;
import gcode.baseproject.interactors.db.dao.data.EvidenceDataDao;
import gcode.baseproject.interactors.db.dao.data.QuestionDataDao;
import gcode.baseproject.interactors.db.entities.data.QuestionDataEntity;
import gcode.baseproject.interactors.session.SessionUser;
import gcode.baseproject.view.viewmodel.dataQuestion.QuestionDataViewModel;

public class PdfGenerator {

    private Format format;
    private File folder;
    private QuestionDataDao questionDataDao;
    private EvidenceDataDao evidenceData ;
    private QuestionDataViewModel questionDataViewModel;
    private TemplateBase template;
    private  PdfWriter pdfWriter;
    public PdfGenerator(Format format) {
        this.format = format;
        template = new TemplateBase();
        checkIfRootExists();
        questionDataDao = ApplicationDatabase.getDatabase().getQuestionDataDao();
        evidenceData  =ApplicationDatabase.getDatabase().getEvidenceDataDao();
    }
    private void checkIfRootExists() {
        folder =  new File(Environment.getExternalStorageDirectory().toString(),"PDF");
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    public String generateFirstPdf(Drawable logotipo,Drawable icon,Drawable iconBackground) throws FileNotFoundException, DocumentException {


        String fileName = "Acta de visita"+"_"+format.getIdFormatData()+".pdf";
        File file = new File(folder,fileName);

        Document document = new Document(PageSize.A4);
        document.setMargins(35,35,70,40);

        OutputStream outputStream = new FileOutputStream(file);
        pdfWriter = PdfWriter.getInstance(document, outputStream);
        pdfWriter.setPageEvent(new HeaderFooterPageEvent(logotipo,icon,iconBackground,format.getIdentifier(),""));

        document.open();
        String firstTitle = String.format("%s\n%s",format.getCustomer().getCRepresentative().toUpperCase(),format.getCustomer().getCBusinessName().toUpperCase());
        template.addTitle(document,firstTitle,5);
        template.addTitle(document, "VISITA DE INSPECCIÓN INTERNA DE OPERACIÓN Y MANTENIMIENTO NOM-005-ASEA-2016",1);

        template.addText(document,"En atención a lo establecido en las normas jurídicas aplicables y relativas al cumplimiento técnico, jurídico y administrativo ante la Agencia Nacional de Seguridad Industrial y Protección al Medio Ambiente del sector Hidrocarburos.",1);
        template.addTitle(document,"SOBRE LA ESTACIÓN DE SERVICIO",3);
        template.addText(document,"El personal designado por SAGANOR se constituyó en el predio ubicado en la dirección "+format.getCustomer().getCDirection()+", donde se encuentra la "+format.getCustomer().getCActivity()+" número "+format.getCustomer().getCCreationPermission()+" perteneciente a la empresa "+format.getCustomer().getCBusinessName()+" con el fin de verificar el cumplimiento de la norma oficial mexicana NOM-005-ASEA-2016 en materia Ambiental y de Seguridad Industrial, mediante la materialización del principio de autogestión, cuyo objeto principal es identificar los puntos que pudieran considerarse Hallazgos Críticos y Hallazgos No Críticos tal y como lo establece el modelo de Inspección Basado en la Gestión del Riesgo.",1);

        // Primera Seccion
        Section  section = format.getSections().get(0);
        template.addTitle(document,section.getTitle().toUpperCase(),5);
        List<Question> questions = section.getQuestions();
        for (Question question : questions) {

            template.addTitle(document,question.getTitle()+ ": "+question.getAnswer().getText(),4);
        }

        // Resto del documento
        List<Section> sections = format.getSections();
        for (int i = 1; i < sections.size(); i++) {
            Section sect = sections.get(i);
            if (i==1){
                template.addTitle(document,"INSPECCIÓN TÉCNICA",3);

            }
            template.addTitle(document,sect.getTitle(),3);


            if (sect.isMultiple()){
                for (SectionData sectionData : sect.getSectionDataList()){
                    String titleMultiple = String.format("%s %d %s",sect.getMultipleDescription(),sectionData.getFolio(),sectionData.getReference());
                    template.addTitle(document,titleMultiple,3);
                    template.createMultiple(document,sect);
                }
            }else {
                 template.createMultiple(document,sect);
            }
        }
        template.addTitle(document,"CIERRE DE LA VISITA DE INSPECCION INTERNA DE OPERACIÓN Y MANTENIMIENTO.",3);
        template.addText(document,"La Visita de Inspección Interna de Operación y Mantenimiento NOM-005-ASEA-2016, registrada bajo el oficio número "+format.getIdentifier()+" y realizada por el personal de SAGANOR en fecha "+CurrentDate.LongDate()+" en las instalaciones de la "+format.getCustomer().getCActivity()+" perteneciente a la empresa "+format.getCustomer().getCBusinessName()+" ubicada en "+format.getCustomer().getCDirection()+" se constituye como un acto de autogestión realizado por la empresa a través de SAGANOR, con el objetivo principal de identificar los puntos que pudieran considerarse Hallazgos Críticos y Hallazgos No Críticos de la "+format.getCustomer().getCActivity()+" número "+format.getCustomer().getCCreationPermission()+", tal y como lo establece el modelo de inspección basado en la gestión del riesgo.",1);
        template.addText(document,"Dicho acto no se constituye como un acto de autoridad, administrativo o de molestia, realizados única y exclusivamente para el sector hidrocarburos por la Agencia Nacional de Seguridad y de Protección al Medio Ambiente del Sector Hidrocarburos, ni se constituye como un acto de verificación, realizado única y exclusivamente para el sector hidrocarburos por Terceros Autorizados y/o Unidades de Verificación. Por lo que la visita de Inspección Interna de Operación y Mantenimiento NOM-005-ASEA-2016 realizada por SAGANOR no sustituye bajo ningún motivo y circunstancia a los actos arriba mencionados, facultad de otros organismos, instituciones y Autoridades.",1);
        template.addText(document,"En virtud de lo mencionado, la decisión de acatar y/o llevar a cabo las Recomendaciones realizadas por SAGANOR en el presente reporte derivado de la Visita de Inspección Interna de Operación y Mantenimiento, registrada bajo el oficio número "+format.getIdentifier()+" corresponde única y exclusivamente a la empresa "+format.getCustomer().getCBusinessName()+", por lo que el actuar consecuente relativo al presente reporte, así como el cumplimiento parcial y/o total de las recomendaciones del mismo es decisión y responsabilidad exclusiva de la empresa en comento.",1);
        template.addText(document,"El presente acto sirve como elemento documental referido como ESCRITO DE ADVERTENCIA en el contrato de prestación de servicios signado por los representantes legales de SAGANOR y la empresa "+format.getCustomer().getCBusinessName(),1);
        template.addText(document,"Se recuerda que el valor agregado de SAGANOR reside en el conocimiento real en relación a los requerimientos administrativos y técnicos que la ASEA solicita a los administrados, así como el conocimiento en relación a los criterios, interpretaciones y demás facultades discrecionales realizados por esta autoridad. El interés de SAGANOR es brindar un servicio profesional, de calidad, que garantice la certidumbre jurídica, técnica y procedimental sobre la esfera que engloba a la materia Ambiental y de Seguridad Industrial del sector hidrocarburos.",1);
        template.addText(document,"ATENTAMENTE",2);
        template.addText(document,""+ SessionUser.getInstance().getAccount().getName() +"",3);
        template.addText(document,"VERIFICADOR SAGANOR",4);






        document.close();

        return file.getAbsolutePath();
    }

    public String generateSecondPdf(Drawable logotipo,Drawable icon,Drawable firma) throws FileNotFoundException, DocumentException {





        String fileName = "Reporte de inspección"+"_"+format.getIdFormatData()+".pdf";
        File file = new File(folder,fileName);


        Document document = new Document(PageSize.A4);
        document.setMargins(35,35,70,40);


        OutputStream outputStream = new FileOutputStream(file);
        pdfWriter= PdfWriter.getInstance(document, outputStream);
        pdfWriter.setPageEvent(new HeaderFooterPageEvent(logotipo,icon,null,CurrentDate.LongDate(),""));
        document.open();

        String firstTitle = String.format("%s\n%s\n%s",format.getCustomer().getCRepresentative().toUpperCase(),format.getCustomer().getCBusinessName().toUpperCase(),"P R E S E N T E");

        template.addTitle(document,firstTitle,3);
        template.addTitle(document, "REPORTE DE VISITA DE INSPECCIÓN INTERNA DE OPERACIÓN Y MANTENIMIENTO NOM-005-ASEA-2016",1);


        template.addText(document,"Por medio de la presente, hago de su conocimiento el resultado de la Visita de Inspección Interna de Operación y Mantenimiento, registrada bajo el oficio número "+format.getIdentifier()+" y realizada por el personal de SAGANOR el día "+CurrentDate.LongDate()+" en las instalaciones de la "+format.getCustomer().getCActivity()+" número "+format.getCustomer().getCCreationPermission()+" perteneciente a la empresa "+format.getCustomer().getCBusinessName()+" ubicada en "+format.getCustomer().getCDirection()+".",1);
        template.addText(document,"En dicho acto se realizó la verificación del cumplimiento en materia Ambiental y de Seguridad Industrial de la norma oficial mexicana NOM-005-ASEA-2016, y; demás Documentos Vinculantes, cuyo objeto principal es identificar los puntos que pudieran considerarse Hallazgos Críticos y Hallazgos No Críticos tal y como lo establece el modelo de Inspección Basado en la Gestión del Riesgo.",1);
        template.addText(document,"Sobre el particular y con el fin de continuar con el ciclo permanente de mejora continua de la "+format.getCustomer().getCActivity()+" número "+format.getCustomer().getCCreationPermission()+", el presente reporte de Visita de Inspección Interna de Operación y Mantenimiento sólo hace referencia a los Hallazgos Críticos y Hallazgos No Críticos relativos a la Inspección Técnica e Inspección Documental de la misma, observados en el acto de autogestión registrado bajo el oficio número "+format.getIdentifier()+", en donde se identificó lo siguiente:",1);





        // Resto del documento
        List<Section> sections = format.getSections();
        for (int i = 1; i < sections.size(); i++) {
            Section sect = sections.get(i);
            //titulo seccion

            if (i==1){
                template.addTitle(document,"INSPECCIÓN TÉCNICA",3);

            }


            boolean show= false;
            for (int j =0 ; j < sect.getQuestions().size(); j++){
                if (sect.getQuestions().get(j).getAnswer().getText().equals("NO")){
                    show =true;
                }
            }

            if (show== true){
                template.addTitle(document,sect.getTitle(),3);


                if (sect.isMultiple()){

                    for (int countSectionData=0; countSectionData <sect.getSectionDataList().size(); countSectionData++){
                        SectionData sectionData = sect.getSectionDataList().get(countSectionData);
                        String titleMultiple = String.format("%s %d %s",sect.getMultipleDescription(),sectionData.getFolio(),sectionData.getReference());
                        template.addTitle(document,titleMultiple,3);
                        List<Question> questions2 = sect.getQuestions();
                        for (Question question : questions2) {
                            //Answer answer = question.getAnswer();

                            //answeMultiple
                            QuestionDataEntity answeMultiple = questionDataDao.getAnswerObjectMultiple(question.getId(),format.getIdFormatData(),sectionData.getIdSectionData());
                            //images multiples
                            AnswerMultiple multipleAnswer = new AnswerMultiple();
                            multipleAnswer.setIdAnswerMultiple(answeMultiple.getId());
                            multipleAnswer.setObservationMultiple(answeMultiple.getObservation());
                            multipleAnswer.setTextAnswer(answeMultiple.getTextQuestion());
                            List<String > images = evidenceData.getImagesByFkAnswer(multipleAnswer.getIdAnswerMultiple());

                            if (multipleAnswer.getTextAnswer()!=null){
                                if (multipleAnswer.getTextAnswer().equals("NO")){

                                    try {


                                        template.createTableForImages(document,images,null);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    template.createMultipleWithNo(document,question);
                                    template.createWithNoObservationsMultiple(document,multipleAnswer);
                                    template.createMultipleWithNoFundament(document,question);


                                }

                            }

                        }

                    }


                }
                else {



                    //NO SON MULTIPLE

                    List<Question> questionsNoMultiples= sect.getQuestions();
                    for (int countQuestion = 0; countQuestion< questionsNoMultiples.size(); countQuestion++){
                        if (countQuestion==0){
                            if (sect.getTitle().equals("INSPECCIÓN DOCUMENTAL")){
                                template.addTitle(document,"DOCUMENTOS EXHIBIDOS EN LA "+format.getCustomer().getCActivity().toUpperCase()+" NÚMERO "+format.getCustomer().getCCreationPermission()+" AL PERSONAL DE SAGANOR",2);
                            }
                        }

                        Question question = questionsNoMultiples.get(countQuestion);
                        Answer answer = question.getAnswer();
                        if (answer.getText()!=null){
                            if (answer.getText().equals("NO")){


                                if (question.getAnswer().getImagesUrls()!=null){
                                    try {
                                        template.createTableForImages(document,question.getAnswer().getImagesUrls(),null);

                                    }catch(IOException e){
                                        e.printStackTrace();
                                    }
                                }
                                template.createMultipleWithNo(document,question);
                                template.createMultipleWithNoObservations(document,answer);
                                template.createMultipleWithNoFundament(document,question);
                            }
                        }
                    }

                }

            }



        }
        boolean ifNoCount=false;
        int countQuestions = 0;
        List<Section> sections2 = format.getSections();
        for (int i = 0; i < sections.size(); i++) {
            Section sect = sections2.get(i);
            for (int j = 0; j < sect.getQuestions().size(); j++) {
                if (sect.getQuestions().get(j).getAnswer().getText().equals("NO")) {
                    countQuestions = countQuestions+1;
                    ifNoCount = true;
                }
            }
        }

        template.addTitle(document,"CONCLUSIÓN",3);
        if (ifNoCount == true) {
            template.addText(document,"Con base en lo anteriormente expuesto, y en concordancia con el objeto de la Visita de Inspección Interna de Operación y Mantenimiento, registrada bajo el oficio número "+format.getIdentifier()+", orientado principalmente en identificar los puntos que pudieran considerarse Hallazgos Críticos y Hallazgos No Críticos tal y como lo establece el modelo de Inspección Basado en la Gestión del Riesgo. SAGANOR identificó "+String.valueOf(countQuestions)+" puntos que pudieran considerarse Hallazgos Críticos y/o Hallazgos No Críticos",1);
        }
        template.addText(document,"La Visita de Inspección Interna de Operación y Mantenimiento, registrada bajo el oficio número "+format.getIdentifier()+" y realizada por el personal de SAGANOR el día "+CurrentDate.LongDate()+" en las instalaciones de la "+format.getCustomer().getCActivity()+" número "+format.getCustomer().getCCreationPermission()+" perteneciente a la empresa "+format.getCustomer().getCBusinessName()+" ubicada en "+format.getCustomer().getCDirection()+" se constituye como un acto de autogestión realizado por la empresa "+format.getCustomer().getCBusinessName()+" a través de SAGANOR, con el objeto principal de identificar los puntos que pudieran considerarse Hallazgos Críticos y Hallazgos No Críticos de la "+format.getCustomer().getCActivity()+" número "+format.getCustomer().getCCreationPermission()+", tal y como lo establece el modelo de Inspección Basado en la Gestión del Riesgo, teoría establecida, manejada y utilizada por los inspectores de la Agencia Nacional de Seguridad Industrial y Protección al Medio Ambiente del Sector Hidrocarburos, cumpliendo así con el menester y obligación jurídica de la "+format.getCustomer().getCActivity()+" número "+format.getCustomer().getCCreationPermission()+" de realizar inspecciones internas de operación y mantenimiento aplicando el principio de autogestión y realizando a una buena práctica de seguridad industrial, seguridad operativa y protección al medio ambiente mundialmente aceptada y realizada dentro del sector hidrocarburos.",1);
        template.addText(document,"Dicho acto no se constituye como un acto de autoridad, administrativo o de molestia, realizados única y exclusivamente para el sector hidrocarburos por la Agencia Nacional de Seguridad y de Protección al Medio Ambiente del Sector Hidrocarburos, ni se constituye como un acto de verificación, realizado única y exclusivamente para el sector hidrocarburos por Terceros Autorizados y/o Unidades de Verificación, por lo que la Visita de Inspección Interna de Operación y Mantenimiento realizada por SAGANOR no sustituye bajo ningún motivo y circunstancia a los actos arriba mencionados, facultad de otros organismos, instituciones y Autoridades; asimismo, se recalca que la visita realizada es únicamente de carácter preventivo, con un fin descriptivo y de aplicación del principio de autogestión y buenas prácticas de seguridad industrial, seguridad operativa y protección al medio ambiente mundialmente aceptada y realizada dentro del sector hidrocarburos.",1);
        template.addText(document,"En virtud de lo mencionado, la decisión de acatar y/o llevar a cabo las Recomendaciones realizadas por SAGANOR en el presente reporte derivado de la Visita de Inspección Interna de Operación y Mantenimiento, registrada bajo el oficio número "+format.getIdentifier()+", corresponde única y exclusivamente a la empresa "+format.getCustomer().getCBusinessName()+", por lo que el actuar consecuente relativo al presente reporte, así como el cumplimiento parcial y/o total de las recomendaciones del mismo es decisión y responsabilidad exclusiva de la empresa en comento.",1);
        template.addText(document,"Se recuerda que el valor agregado de SAGANOR reside en el conocimiento real en relación a los requerimientos administrativos y técnicos que la ASEA solicita a los administrados, así como el conocimiento en relación a los criterios, interpretaciones y demás facultades discrecionales realizados por esta autoridad. El interés de SAGANOR es brindar un servicio profesional, de calidad, que garantice la certidumbre jurídica, técnica y procedimental sobre la esfera que engloba a la materia Ambiental y de Seguridad Industrial del sector hidrocarburos.",1);
        template.addText(document,"ATENTAMENTE",2);
        template.AddImageDrawable(document,firma);
        template.addText(document,"M.C. Enrique Troyo Vega",3);
        template.addText(document,"Director General de SAGANOR",2);

        document.close();



        return file.getAbsolutePath();
    }

    public String generateThirdPdf(Drawable logotipo,Drawable icon) throws FileNotFoundException, DocumentException {

        String fileName = "Archivo fotográfico"+"_"+format.getIdFormatData()+".pdf";
        File file = new File(folder,fileName);
        Document document = new Document(PageSize.A4);
        document.setMargins(35,35,70,40);

        OutputStream outputStream = new FileOutputStream(file);
        pdfWriter= PdfWriter.getInstance(document, outputStream);
        pdfWriter.setPageEvent(new HeaderFooterPageEvent(logotipo,icon,null,"OFICIO NO. ",format.getIdentifier()));
        document.open();
        template.addTitle(document, "ARCHIVO FOTOGRÁFICO",1);

        Section firstSection = format.getSections().get(0);
        template.addTitle(document,firstSection.getTitle(),1);
        for (Question question : firstSection.getQuestions()) {
            if (question.getAnswer().getImagesUrls() != null) {
                try {
                    template.createTableForImages(document,question.getAnswer().getImagesUrls(),null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        for (int i =1; i<format.getSections().size(); i++ ){
            Section section = format.getSections().get(i);

            boolean isImageEmpty=false;
            for (int j =1; j <section.getQuestions().size(); j++){
                if (section.getQuestions().get(j).getAnswer().getImagesUrls() !=null){
                    isImageEmpty= true;
                }
            }
            if (isImageEmpty==true){
                template.addTitle(document,section.getTitle(),1);
            }
            for (Question question : section.getQuestions()){
                if (question.getAnswer() !=null){
                        if (question.getAnswer().getImagesUrls() != null){
                            try {
                                template.createTableForImages(document,question.getAnswer().getImagesUrls(),null);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                }
            }
        }
        document.close();
        return file.getAbsolutePath();
    }

}
