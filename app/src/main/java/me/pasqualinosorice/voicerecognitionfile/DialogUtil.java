package me.pasqualinosorice.voicerecognitionfile;

import android.app.Activity;
import android.app.ProgressDialog;

public class DialogUtil {

    public static ProgressDialog createProgressDialog(Activity activity, String message) {
        final ProgressDialog pDialog = new ProgressDialog(activity);
        pDialog.setMessage(message);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        return pDialog;
    }

    public static void removeProgressDialog(ProgressDialog pd) {
        pd.hide();
    }

}
