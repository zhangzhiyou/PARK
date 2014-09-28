package com.example.zhiyou.huoqugps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Created by zhiyou on 14-9-27.
 */
public class SubActivite extends Activity {
    private EditText views;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       views = (EditText) this.findViewById(R.id.views);

        Intent intent = getIntent();
        String message = intent.getStringExtra(GPS.EXTRA_MESSAGE);
        views.setText(""+message.toString());
    }

}
