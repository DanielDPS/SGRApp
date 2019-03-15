package gcode.baseproject.view.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.GalleryFragmentBinding;
import gcode.baseproject.interactors.adapters.GalleryAdapter;
import gcode.baseproject.interactors.db.entities.data.EvidenceDataEntity;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.viewmodel.dataEvidence.EvidenceDataViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;

public class GalleryFragment  extends BaseFragment {


    private EvidenceDataViewModel evidenceDataViewModel;
    private GalleryFragmentBinding galleryFragmentBinding;
    private static String id;
    public static final GalleryFragment getInstance(String fk){
        GalleryFragment galleryFragment = new GalleryFragment();
        id =fk;
        return galleryFragment;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        galleryFragmentBinding = GalleryFragmentBinding.inflate(inflater,container,false);
        return galleryFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //viewmodel
        evidenceDataViewModel = ViewModelProviders.of(this).get(EvidenceDataViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        evidenceDataViewModel.LoadEvidenceList(id);
        evidenceDataViewModel.getEvidenceData()
                .observe(this, new Observer<List<EvidenceDataEntity>>() {
                    @Override
                    public void onChanged(List<EvidenceDataEntity> evidenceDataEntities) {
                        galleryFragmentBinding.galleryRecycler.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(),3);
                        galleryFragmentBinding.galleryRecycler.setLayoutManager(linearLayoutManager);
                        GalleryAdapter galleryAdapter = new GalleryAdapter(evidenceDataEntities);
                        galleryFragmentBinding.galleryRecycler.setAdapter(galleryAdapter);
                    }
                });
    }

    @NonNull
    @Override
    public String getFragmentTag() {
        return GalleryFragment.class.getName();
    }

    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        ToolbarBuilder builder = new ToolbarBuilder
                .Builder(galleryFragmentBinding.toolbar)
                .withTitle("Galeria")
                .withMenu(R.menu.main_menu)
                .withBackButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNavigationManager().removeFragmentFromBackStack();
                    }
                })
                .build();
        return builder;
    }
}
