package gcode.baseproject.view.ui.general;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import gcode.baseproject.R;

public class ExitDialogFragment extends DialogFragment {

    public static final String TAG = ExitDialogFragment.class.getName();

    private OnExitClickListener mListener;

    public static ExitDialogFragment newInstance() {
        ExitDialogFragment fragment = new ExitDialogFragment();
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.exit_dialog_title)
                .setMessage(R.string.exit_dialog_message)
                .setPositiveButton(R.string.exit_dialog_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onExitConfirm();
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton(R.string.logout_dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onExitCancel();
                        dialogInterface.dismiss();
                    }
                });
        return alertDialog.create();
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
