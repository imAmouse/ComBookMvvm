package com.imamouse.combook.ui.error;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.imamouse.combook.R;

import me.goldze.mvvmhabit.crash.CaocConfig;
import me.goldze.mvvmhabit.crash.CustomActivityOnCrash;

public class ErrorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        //Close/restart button logic:
        //If a class if set, use restart.
        //Else, use close and just finish the app.
        //It is recommended that you follow this logic if implementing a custom error activity.
        final Button restartButton = findViewById(R.id.btn_error_restart);

        final CaocConfig config = CustomActivityOnCrash.getConfigFromIntent(getIntent());

        if (config.isShowRestartButton() && config.getRestartActivityClass() != null) {
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustomActivityOnCrash.restartApplication(ErrorActivity.this, config);
                }
            });
        } else {
            restartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    restartButton.setText("关闭程序");
                    CustomActivityOnCrash.closeApplication(ErrorActivity.this, config);
                }
            });
        }

        Button moreInfoButton = findViewById(R.id.btn_error_more_info);

        if (config.isShowErrorDetails()) {
            moreInfoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //We retrieve all the error data and show it

                    AlertDialog dialog = new AlertDialog.Builder(ErrorActivity.this)
                            .setTitle(me.goldze.mvvmhabit.R.string.customactivityoncrash_error_activity_error_details_title)
                            .setMessage(CustomActivityOnCrash.getAllErrorDetailsFromIntent(ErrorActivity.this, getIntent()))
                            .setPositiveButton(me.goldze.mvvmhabit.R.string.customactivityoncrash_error_activity_error_details_close, null)
                            .setNeutralButton(me.goldze.mvvmhabit.R.string.customactivityoncrash_error_activity_error_details_copy,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            copyErrorToClipboard();
                                            Toast.makeText(ErrorActivity.this, me.goldze.mvvmhabit.R.string.customactivityoncrash_error_activity_error_details_copied, Toast.LENGTH_SHORT).show();
                                        }
                                    })
                            .show();
                    TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(me.goldze.mvvmhabit.R.dimen.customactivityoncrash_error_activity_error_details_text_size));
                }
            });
        } else {
            moreInfoButton.setVisibility(View.GONE);
        }
    }

    private void copyErrorToClipboard() {
        String errorInformation = CustomActivityOnCrash.getAllErrorDetailsFromIntent(ErrorActivity.this, getIntent());

        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(getString(me.goldze.mvvmhabit.R.string.customactivityoncrash_error_activity_error_details_clipboard_label), errorInformation);
        clipboard.setPrimaryClip(clip);
    }
}
