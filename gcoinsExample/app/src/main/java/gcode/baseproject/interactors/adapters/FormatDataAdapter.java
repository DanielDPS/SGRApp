package gcode.baseproject.interactors.adapters;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.itextpdf.text.pdf.codec.Base64;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.File;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormatDataCardItemBinding;
import gcode.baseproject.domain.model.dataAPI.fileHeaders.FileHeader;
import gcode.baseproject.domain.model.format.FormatData;
import gcode.baseproject.interactors.connection.NetworkConnection;
import gcode.baseproject.interactors.date.CurrentDate;
import gcode.baseproject.interactors.db.entities.CustomerEntity;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.data.FileDataEntity;
import gcode.baseproject.interactors.dialogs.CreateDialog;
import gcode.baseproject.interactors.mappers.FileHeaderManager;
import gcode.baseproject.interactors.mappers.FileHeaderMapper;
import gcode.baseproject.interactors.mappers.FileHeaderObject;
import gcode.baseproject.view.ui.format.FormatDataFragment;
import gcode.baseproject.view.ui.pdf.PdfFragment;
import gcode.baseproject.view.utils.ErrorUtils;
import gcode.baseproject.view.viewmodel.Customer.CustomerViewModel;
import gcode.baseproject.view.viewmodel.dataAPI.addFile.AddFileViewModel;
import gcode.baseproject.view.viewmodel.dataAPI.addFormatData.AddFormatDataViewModel;
import gcode.baseproject.view.viewmodel.dataAPI.fileHeaders.FileHeaderViewModel;
import gcode.baseproject.view.viewmodel.dataFile.FileDataViewModel;
import gcode.baseproject.view.viewmodel.format.FormatDataViewModel;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class FormatDataAdapter  extends    RecyclerView.Adapter<FormatDataAdapter.FormatDataViewHolder>  {


    private LayoutInflater layoutInflater;
    private List<FormatDataEntity> formatDataList;
    private CustomerViewModel customerViewModel;
    private FileDataViewModel fileDataViewModel;
    private  FormatDataFragment formatDataFragment ;
    private FormatDataViewModel formatDataViewModel;
    private AddFileViewModel addFileViewModel;
    private FileHeaderViewModel fileHeaderViewModel;
    private AddFormatDataViewModel addFormatDataViewModel;
    private  OnClickFormatDataPresenter presenter;
    public  FormatDataAdapter (List<FormatDataEntity> dataList,FormatDataFragment fragment,OnClickFormatDataPresenter presenter)
    {
        this.formatDataList= dataList;
        this.formatDataFragment = fragment;
        this.presenter =presenter;
    }
    @NonNull
    @Override
    public FormatDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        FormatDataCardItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.format_data_card_item,parent,false);
        return new FormatDataViewHolder(binding);
    }

    public void setFormatDataList(List<FormatDataEntity> formatDataList) {
        this.formatDataList = formatDataList;
    }

    public List<FormatDataEntity> getFormatDataList() {
        return formatDataList;
    }

    @Override
    public void onBindViewHolder(final @NonNull FormatDataViewHolder holder, int position) {
        final FormatDataEntity format = formatDataList.get(position);
        customerViewModel= ViewModelProviders.of(this.formatDataFragment).get(CustomerViewModel.class);
        fileDataViewModel = ViewModelProviders.of(this.formatDataFragment).get(FileDataViewModel.class);
        addFileViewModel= ViewModelProviders.of(this.formatDataFragment).get(AddFileViewModel.class);
        formatDataViewModel =ViewModelProviders.of(this.formatDataFragment).get(FormatDataViewModel.class);
        fileHeaderViewModel = ViewModelProviders.of(this.formatDataFragment).get(FileHeaderViewModel.class);
        addFormatDataViewModel = ViewModelProviders.of(this.formatDataFragment).get(AddFormatDataViewModel.class);
        final List<FileDataEntity> listFiles = fileDataViewModel.getFilesByFkFormatData(format.getFdid());
        final CustomerEntity customerEntity = customerViewModel.getCustomerEntityById(format.getFkCustomer());
        holder.binding.setFormatData(format);
        holder.binding.setCustomer(customerEntity);
        holder.binding.setPresenter(presenter);
        format.setCustomerObject(customerEntity);
        holder.binding.syncPDF.setVisibility(View.VISIBLE);
        if (format.getEstado01()==0 && format.getEstado02() ==0){
            holder.binding.syncPDF.setVisibility(View.GONE);
        }
        else if ( format.getEstado02()==0 || format.getEstado02() ==1) {
            holder.binding.syncPDF.setVisibility(View.VISIBLE);
        }
        else {
            holder.binding.syncPDF.setVisibility(View.GONE);
        }
        //format.getEstado01() ==0 && format.getEstado02()==0  || format.getEstado01()==0 && format.getEstado02()==1

        holder.binding.viewPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    LayoutInflater inflater = LayoutInflater.from(formatDataFragment.getContext());
                    View view = inflater.inflate(R.layout.dialog_files, null);
                    final MaterialButton btnActa = view.findViewById(R.id.btnActa);
                    final MaterialButton btnReporte = view.findViewById(R.id.btnInspeccion);
                    final MaterialButton btnPhoto = view.findViewById(R.id.btnFilePhoto);
                    CreateDialog fileDialog = new CreateDialog();
                    final android.app.AlertDialog.Builder builder = fileDialog.openDialog2(formatDataFragment.getContext(), view);
                    fileDialog.setTitle("Listado de archivos");
                    builder.setIcon(R.drawable.ic_list_files);
                    btnActa.setText(listFiles.get(0).getType());
                    btnReporte.setText(listFiles.get(1).getType());
                    btnPhoto.setText(listFiles.get(2).getType());
                    final android.app.AlertDialog alert = builder.show();
                    final PdfFragment pdfFragment =new PdfFragment();

                    btnActa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{
                                pdfFragment.setPathFile(listFiles.get(0).getFile());
                                formatDataFragment.getNavigationManager().addFragment(pdfFragment);
                                alert.dismiss();
                            }catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(formatDataFragment.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                alert.dismiss();
                            }



                        }
                    });
                    btnReporte.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{
                                pdfFragment.setPathFile(listFiles.get(1).getFile());
                                formatDataFragment.getNavigationManager().addFragment(pdfFragment);
                                alert.dismiss();
                            }catch(Exception e){
                                e.printStackTrace();
                                alert.dismiss();

                            }

                        }
                    });
                    btnPhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try{
                                pdfFragment.setPathFile(listFiles.get(2).getFile());
                                formatDataFragment.getNavigationManager().addFragment(pdfFragment);
                                alert.dismiss();
                            }catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(formatDataFragment.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                alert.dismiss();
                            }

                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(formatDataFragment.getContext(), "No existen archivos PDF para mostrar", Toast.LENGTH_SHORT).show();
                }


            }
        });


        
        holder.binding.syncPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkConnection.isConnected(formatDataFragment.getContext())){
                    try{

                        final ProgressDialog progressDialog = new ProgressDialog(formatDataFragment.getContext(), AlertDialog.THEME_HOLO_DARK);
                        progressDialog.setMessage("Sincronizando archivos PDF...");
                        progressDialog.setCancelable(false);
                        progressDialog.setIndeterminate(false);
                        progressDialog.show();

                        addFormatDataViewModel.addHeaderOfButton(format,progressDialog);


                    }catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(formatDataFragment.getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(formatDataFragment.getContext(), "Sin conexión a internet, favor de verificar su conexión", Toast.LENGTH_LONG).show();
                }



            }
        });

    }

    @Override
    public int getItemCount() {
        return formatDataList.size();
    }
    public static class FormatDataViewHolder extends RecyclerView.ViewHolder{
        private FormatDataCardItemBinding binding;
        public FormatDataViewHolder(FormatDataCardItemBinding binding){
            super(binding.getRoot());
            this.binding= binding;
        }
    }
    public interface  OnClickFormatDataPresenter{
        void OnClickFormatData (FormatDataEntity formatDataEntity);
    }

}
