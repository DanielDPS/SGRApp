package gcode.baseproject.view.ui.pdf;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.listener.OnPageScrollListener;

import java.io.File;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import gcode.baseproject.databinding.PdfFragmentBinding;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.view.ui.format.FormatSectionsFragment;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;

public class PdfFragment extends BaseFragment {


    private  PdfFragmentBinding binding;
    private File file;
    private String pathFile;


    public  void setPathFile(String path){
        this.pathFile = path;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = PdfFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

         if(pathFile !=null){
            file = new File(pathFile);

        }
        binding.pdfVIEW.fromFile(file)
                .enableSwipe(true)
                .defaultPage(0)
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        Toast.makeText(getContext(), "Pagina "+String.valueOf( page),Toast.LENGTH_LONG).show();

                    }
                }).load();
    }


    @NonNull
    @Override
    public String getFragmentTag() {
        return PdfFragment.class.getName();
    }

    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
       ToolbarBuilder builder = new ToolbarBuilder
               .Builder(binding.toolbar)
               .withTitle("PDF")
               .withBackButton(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       getNavigationManager().removeFragmentFromBackStack();
                   }
               }).build();
       return builder;
    }
}
