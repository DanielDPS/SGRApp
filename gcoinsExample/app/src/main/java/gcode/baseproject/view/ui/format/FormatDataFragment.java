package gcode.baseproject.view.ui.format;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import gcode.baseproject.R;
import gcode.baseproject.databinding.FormatDataFragmentBinding;
import gcode.baseproject.interactors.adapters.FormatDataAdapter;
import gcode.baseproject.interactors.adapters.SwipeToDeleteCallback;
import gcode.baseproject.interactors.db.entities.FormatDataEntity;
import gcode.baseproject.interactors.db.entities.FormatEntity;
import gcode.baseproject.interactors.dialogs.CreateDialog;
import gcode.baseproject.view.ui.general.BaseFragment;
import gcode.baseproject.view.viewmodel.dataFile.FileDataViewModel;
import gcode.baseproject.view.viewmodel.format.FormatDataViewModel;
import gcode.baseproject.view.viewmodel.format.FormatViewModel;
import gcode.baseproject.view.widgets.toolbar.BaseToolbarBuilder;
import gcode.baseproject.view.widgets.toolbar.ToolbarBuilder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class FormatDataFragment extends BaseFragment implements FormatDataAdapter.OnClickFormatDataPresenter {

    private  static String FORMAT_ARGS ="format_args";
    private FormatDataFragmentBinding formatDataFragmentBinding;
    private FormatEntity format;
    private FormatDataViewModel formatDataViewModel;
    private FormatViewModel formatViewModel;
    private FileDataViewModel fileDataViewModel;

    private LayoutInflater layoutInflater;

    public static final FormatDataFragment newInstance(FormatEntity format){
        FormatDataFragment formatDataFragment = new FormatDataFragment();
        Bundle args= new Bundle();
        args.putSerializable(FORMAT_ARGS,format);
        formatDataFragment.setArguments(args);
        return formatDataFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null ){
            format = (FormatEntity)getArguments().getSerializable(FORMAT_ARGS);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        formatDataViewModel = ViewModelProviders.of(this).get(FormatDataViewModel.class);
        formatViewModel = ViewModelProviders.of(this).get(FormatViewModel.class);
        fileDataViewModel = ViewModelProviders.of(this).get(FileDataViewModel.class);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        formatDataFragmentBinding = FormatDataFragmentBinding.inflate(inflater,container,false);
        return formatDataFragmentBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Toast.makeText(getContext(),format.getCId(),Toast.LENGTH_SHORT).show();

    }
    @NonNull
    @Override
    public String getFragmentTag() {

        return FormatDataFragment.class.getName();
    }
    void RefreshAll(){
        final FormatDataFragment dataFragment = this;
        final FormatDataAdapter.OnClickFormatDataPresenter presenter = this;
        formatDataViewModel.ClearFormatData(this);
        formatDataViewModel.LoadFormatData();
        formatDataViewModel.getGetFormatData()
                .observe(this, new Observer<List<FormatDataEntity>>() {
                    @Override
                    public void onChanged(final List<FormatDataEntity> formatDataEntities) {

                        if (formatDataEntities.size() ==0){

                            //formatDataFragmentBinding.formatDataRecycler.setBackgroundColor(Color.parseColor("#ff7675"));
                            formatDataFragmentBinding.nodata.setVisibility(View.VISIBLE);

                        }else {
                            formatDataFragmentBinding.nodata.setVisibility(View.GONE);
                            formatDataFragmentBinding.formatDataRecycler.setHasFixedSize(true);
                            formatDataFragmentBinding.formatDataRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
                            final FormatDataAdapter formatDataAdapter = new FormatDataAdapter(formatDataEntities,dataFragment,presenter);
                            formatDataFragmentBinding.formatDataRecycler.setAdapter(formatDataAdapter);


                            SwipeToDeleteCallback swipeCallback = new SwipeToDeleteCallback(getContext()) {
                                @Override
                                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                                    final int index  = viewHolder.getAdapterPosition();
                                    final FormatDataEntity formatSelected = formatDataEntities.get(index);

                                    LayoutInflater inflater = LayoutInflater.from(getContext());
                                    View view = inflater.inflate(R.layout.dialog_confirm_delete, null);
                                    MaterialButton btnLeave = view.findViewById(R.id.btnDelete);
                                    MaterialButton btnCancel = view.findViewById(R.id.btnCancelDelete);
                                    CreateDialog myDialog = new CreateDialog();
                                    final android.app.AlertDialog.Builder builder = myDialog.openDialog2(getContext(), view);
                                    builder.setTitle("Confirmación");
                                    builder.setIcon(R.drawable.ic_info_outline);
                                    builder.setMessage(R.string.message_delete);
                                    final android.app.AlertDialog alert = builder.create();
                                    btnLeave.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (formatSelected.getEstado01()== 0 && formatSelected.getEstado02() ==0){

                                                final ProgressDialog dialog = new ProgressDialog(getContext(), AlertDialog.THEME_HOLO_DARK);
                                                dialog.setMessage("Eliminando inspección... por favor espere");
                                                dialog.setIndeterminate(false);
                                                dialog.setCancelable(false);
                                                dialog.show();

                                                formatDataViewModel.RemoveFormatData(formatSelected)
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(new DisposableCompletableObserver() {
                                                            @Override
                                                            public void onComplete() {
                                                                RefreshAll();
                                                            }
                                                            @Override
                                                            public void onError(Throwable e) {

                                                            }
                                                        });

                                                formatViewModel.UpdateFormats()
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(new DisposableCompletableObserver() {
                                                            @Override
                                                            public void onComplete() {

                                                                dialog.dismiss();
                                                                alert.dismiss();
                                                                RefreshAll();

                                                            }

                                                            @Override
                                                            public void onError(Throwable e) {

                                                                dialog.dismiss();
                                                                alert.dismiss();
                                                                RefreshAll();

                                                            }
                                                        });




                                            }else {
                                                alert.dismiss();
                                                formatDataAdapter.notifyItemChanged(index);
                                                Toast.makeText(getContext(),"Ya se ha creado un PDF de esta inspeccion",Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    });
                                    btnCancel.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            alert.dismiss();
                                        }
                                    });
                                    alert.show();



                                    formatDataAdapter.notifyItemChanged(index);

                                }
                            };

                            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
                            itemTouchHelper.attachToRecyclerView(formatDataFragmentBinding.formatDataRecycler);
                        }






                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        RefreshAll();


    }
    private  List<Integer> getMenuItems(){
        List<Integer> menuItems= new ArrayList<>();
        menuItems.add(R.id.Format_data_refresh);
        menuItems.add(R.id.Format_data_add);
        return menuItems;
    }

    private  List<MenuItem.OnMenuItemClickListener> getMenuClicks(){
        final FormatDataFragment contextFragment = this;

        List<MenuItem.OnMenuItemClickListener> listeners = new ArrayList<>();
        listeners.add(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                RefreshAll();
                return false;
            }
        });
        listeners.add(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                getNavigationManager().addFragment(FormatDataModuleFragment.newInstance(format));
                return false;
            }
        });
        return listeners;
    }



    @NonNull
    @Override
    public BaseToolbarBuilder getFragmentToolbarBuilder() {
        ToolbarBuilder builder = new ToolbarBuilder
                .Builder(formatDataFragmentBinding.toolbar)
                .withTitle("Datos")
                .withMenu(R.menu.refresh_formats_data)
                .withMenuItems(getMenuItems(),getMenuClicks())
                .withBackButton(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getNavigationManager().removeFragmentFromBackStack();
                    }
                }).build();

        return builder;
    }

    @Override
    public void OnClickFormatData(FormatDataEntity formatDataEntity) {
        if (formatDataEntity.getCreated().equals("f") || formatDataEntity.getCreated().equals("")){
            getNavigationManager().addFragment(FormatSectionsFragment.newInstance(formatDataEntity));
        }else {
            return;
        }
    }

}
