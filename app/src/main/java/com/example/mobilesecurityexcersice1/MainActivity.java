package com.example.mobilesecurityexcersice1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        password = findViewById(R.id.editText);
        Button button = findViewById(R.id.enter_btn);

        button.setOnClickListener(v -> buttonClicked());
    }

    private void buttonClicked() {
        if (!password.getText().toString().isEmpty()) {
            if (computeAll(password.getText().toString()))
                showAlertDialog("Succeeded");
            else showAlertDialog("Failed");
        }
    }

    private void showAlertDialog(String msg) {
        new AlertDialog.Builder(this).setMessage(msg).setPositiveButton("ok", null).create().show();
    }

    private boolean computeAll(String password) {
        if (!checkCondition1(password))
            return false;
        if (!checkCondition2(password))
            return false;
        if (!checkCondition3(password))
            return false;
        return true;
    }

    private boolean checkCondition3(String password) {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;

    }

    private boolean checkCondition2(String password) {
        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        return am.getRingerMode() == AudioManager.RINGER_MODE_SILENT;
    }

    private boolean checkCondition1(String password) {
        BatteryManager bm = (BatteryManager) this.getSystemService(BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        return password.contains(String.valueOf(batLevel));
    }

}