package gcode.baseproject.view.ui.general;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import gcode.baseproject.R;
import gcode.baseproject.interactors.dialogs.CreateDialog;

public class ExitDialogFragment extends DialogFragment {

    public static final String TAG = ExitDialogFragment.class.getName();

    private OnExitClickListener mListener;

    public static ExitDialogFragment newInstance() {
        ExitDialogFragment fragment = new ExitDialogFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_logout, null);
        MaterialButton btnLeave = view.findViewById(R.id.btnLeave);
        MaterialButton btnCancel = view.findViewById(R.id.btnCancelLogout);
        CreateDialog   myDialog = new CreateDialog();
        final android.app.AlertDialog.Builder builder = myDialog.openDialog2(getContext(), view);
        builder.setTitle("Salir");
        builder.setIcon(R.drawable.ic_exit);
        builder.setMessage(R.string.exit_dialog_message);
        final android.app.AlertDialog alert = builder.create();
        btnLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onExitConfirm();
                alert.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onExitCancel();
                alert.dismiss();
            }
        });
               /* .setPositiveButton(R.string.exit_dialog_ok, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton(R.string.logout_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onExitCancel();
                        dialogInterface.dismiss();
                    }
                });*/

        return alert;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnExitClickListener) {
            mListener = (OnExitClickListener)context;
        } else {
            throw new RuntimeException(context.toString() + " Must implement OnDialogClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnExitClickListener {
        void onExitConfirm();
        void onExitCancel();
    }
}
