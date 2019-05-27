package gcode.baseproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class ViewPDFActivity extends AppCompatActivity {


    private PDFView pdfView;
    private File file;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);






        pdfView = findViewById(R.id.viewPDF);
        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            file = new File(bundle.getString("path",""));

        }
        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(true)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .load();
    }


    ToolbarBuilder toolbarBuilder = new ToolbarBuilder.Builder(toolbar)
            .withTitle("PDF")
            .withBackButton(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            })
            .build();

}
