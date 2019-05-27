package gcode.baseproject.interactors.adapters;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.GalleryItemBinding;
import gcode.baseproject.interactors.db.entities.data.EvidenceDataEntity;

public class GalleryAdapter  extends  RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {


    private LayoutInflater inflater;
    private List<EvidenceDataEntity> listEvidence;
     public GalleryAdapter(List<EvidenceDataEntity> evidenceDataEntities){
        this.listEvidence = evidenceDataEntities;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null){
            inflater= LayoutInflater.from(parent.getContext());
        }
        GalleryItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.gallery_item,parent,false);
        return new GalleryViewHolder(binding);
    }

     public EvidenceDataEntity getItemByPosition(int position) {
         return   listEvidence.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        EvidenceDataEntity evidenceDataEntity = listEvidence.get(position);
        Bitmap bitmap = DecodeImage.decode_image(new File(evidenceDataEntity.getImageUrl()),500);
        holder.binding.imgGallery.setImageBitmap(bitmap);
        holder.binding.imgDate.setText("Fecha de captura: "+evidenceDataEntity.getCreation());

    }

    @Override
    public int getItemCount() {
        return listEvidence.size();
    }

    public  static class GalleryViewHolder extends RecyclerView.ViewHolder{

        private GalleryItemBinding binding;
        public GalleryViewHolder(GalleryItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
