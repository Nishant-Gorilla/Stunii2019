package obllivionsoft.djole.nis.rs.stusdeals.controller.Utills;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Thakur on 1/11/2018.
 */

public class ProgressBar {
    Context context;

    public static ProgressBar mInstanse;

    public ProgressBar() {
        mInstanse = this;
    }

    public static ProgressBar getInstanse() {
        return mInstanse;
    }

    private ProgressDialog dialog;

    public void showDialog(Context context) {
        try
        {
            this.context=context;
            dialog = new ProgressDialog(context);
           // dialog.setCancelable(false);
            dialog.setMessage("Loading...");
            /* dialog.setMessage("Please Wait...");*/
            dialog.show();
        }catch (Exception e)
        {

        }

    }

    public void hideDialog() {


            dialog.dismiss();

    }
}
