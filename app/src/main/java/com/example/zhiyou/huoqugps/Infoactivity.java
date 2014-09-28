package com.example.zhiyou.huoqugps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zhiyou.huoqugps.R;

public class Infoactivity extends Activity {
    private EditText views;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
     //   views = (EditText) this.findViewById(R.id.views);

        Intent intent = getIntent();
        String message = intent.getStringExtra(GPS.EXTRA_MESSAGE);
       // views.setText(""+message.toString());
      //  TextView view = new TextView(this);
//        TextView view = new TextView(this);
//        view.setText(message.toString());
        TextView textView = new TextView(this);
        textView.setTextSize(20);
        textView.setText(message.toString());

        // 设置textview为activity的布局
        setContentView(textView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.infoactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
