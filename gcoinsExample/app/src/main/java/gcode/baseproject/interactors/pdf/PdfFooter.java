package gcode.baseproject.interactors.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfFooter extends PdfPageEventHelper {

    Font font= new Font(Font.FontFamily.UNDEFINED,5,Font.ITALIC);
    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        super.onEndPage(writer, document);
        PdfContentByte cb = writer.getDirectContent();
        Phrase header =new Phrase("this is header",font);
        Phrase footer = new Phrase("this is footer",font);

        ColumnText.showTextAligned(cb,Element.ALIGN_CENTER,
                header,
                (document.right() - document.left()) /2 +document.leftMargin(),
                document.top()+10,0);

        ColumnText.showTextAligned(cb,Element.ALIGN_CENTER,
                footer,
                (document.right() - document.left()) / 2 + document.leftMargin(),
                document.bottom()-10,0);

    }

}
