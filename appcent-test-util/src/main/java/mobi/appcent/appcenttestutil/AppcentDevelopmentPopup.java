package mobi.appcent.appcenttestutil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by MohammadARAFA on 31/10/2017.
 */

public class AppcentDevelopmentPopup implements View.OnClickListener {

    private Activity activity;
    AlertDialog alertDialog;


    public void showAppcentDevelopmentPopup(Activity activity){
        this.activity = activity;
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.appcent_development_popup, null);

        TextView tvVersion = (TextView) dialogView.findViewById(R.id.tvVersion);
        LinearLayout llTestURL = (LinearLayout) dialogView.findViewById(R.id.llTestURL);
        LinearLayout llProdURL = (LinearLayout) dialogView.findViewById(R.id.llProdURL);
        LinearLayout llDoneTasks = (LinearLayout) dialogView.findViewById(R.id.llDoneTasks);
        TextView tvBaseUrl = (TextView) dialogView.findViewById(R.id.tvBaseUrl);

        tvVersion.setText(Constants.VERSION_NUMBER);
        tvBaseUrl.setText("Base URL : " + Constants.BASE_URL);
        llTestURL.setOnClickListener(this);
        llProdURL.setOnClickListener(this);
        llDoneTasks.setOnClickListener(this);

        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.llTestURL) {
            Constants.appcentCallback.initBaseUrl(Constants.TEST_URL);
        }
        else if(view.getId() == R.id.llProdURL){
            Constants.appcentCallback.initBaseUrl(Constants.PROD_URL);
        }
        else if(view.getId() == R.id.llDoneTasks){
            Intent intent = new Intent(activity, AppcentDoneTasksActivity.class);
            activity.startActivity(intent);
        }
        alertDialog.cancel();
    }
}