package com.example.zhiyou.huoqugps;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDGeofence;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDLocationStatusCodes;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.LogRecord;

import javax.net.ssl.SSLSession;

public class GPS extends Activity {
    LocationManager manager;
    Location location;
    public final static String EXTRA_MESSAGE = "com.example.zhiyou.huoqugps";

    private static final int OVER = 1;//todo

    Handler handler = new Handler() {//todo
            @Override

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case OVER:
//                        views.setText(""+msgs.toString());
//                        System.out.println((msgs);

                        Intent intent = new Intent(GPS.this,Infoactivity.class);//内部类访问外部类，在this前面加上外部类的名字。
                        // EditText editText = (EditText) findViewById(R.id.views);
                        //  msgs = editText.getText().toString();
                        intent.putExtra(EXTRA_MESSAGE, msgs);
                        startActivity(intent);
                        break;
                }
                super.handleMessage(msg);

            }

    };



    Socket socket = null;
    MapView mMapView = null;

   // private EditText wd;


  //  private EditText jd;
    private EditText views;
    private EditText address;
    private EditText phone;
    private EditText number;
    private   String msgs;//todo



    public GeofenceClient mGeofenceClient =null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

      //  jd = (EditText) this.findViewById(R.id.jd);
     //   wd = (EditText) this.findViewById(R.id.wd);
        address = (EditText) this.findViewById(R.id.address);
        phone = (EditText) this.findViewById(R.id.phone);
        views = (EditText) this.findViewById(R.id.views);
        number = (EditText) this.findViewById(R.id.number);


        Button button = (Button) this.findViewById(R.id.button);

        button.setOnClickListener(new ButtonClickListener());//button.setOnClicklistener是一个监听器

    }
//加百度地图


    private final class ButtonClickListener implements View.OnClickListener {


        public void onClick(View v) {

            new Thread(updateThread).start();
/*
            Intent intent = new Intent(GPS.this,SubActivite.class);//内部类访问外部类，在this前面加上外部类的名字。
            EditText editText = (EditText) findViewById(R.id.views);
             msgs = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, msgs);
            startActivity(intent);
            */

        }
    }

    Runnable updateThread = new Runnable() {
        public void run() {
            show();
        }
    };

    public void show() {
        try {
            socket = new Socket("10.6.66.234", 8080);
            //向服务器发送消息
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            String params = address.getText().toString() + "," + phone.getText().toString() + "," + number.getText().toString() + ",";
            out.println(params);
            System.out.println(params);


            //接收来自服务器的消息
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            msgs = br.readLine();//todo
            System.out.println(msgs);

            if ( msgs != null ) {

                handler.sendEmptyMessage(OVER);//todo


                //views.setText(msg);

            }
            else
            {
               views.setText("数据错误!");
            }

            out.close();

            br.close();
            socket.close();
        }

        catch (IOException e) {
            e.printStackTrace();

        }


    }



    private void post(String wd,String jd) throws Exception {

        DefaultHttpClient client = new DefaultHttpClient();
        StringBuilder buf = new StringBuilder("10.6.66.234");

        HttpURLConnection conn = null;
        conn.setRequestMethod("POST");
//设置post请求必要的请头
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
// 请求头, 必须设置


        HttpPost post = new HttpPost(buf.toString());
        HttpResponse response = client.execute(post);
        if(response.getStatusLine().getStatusCode()==200) {
            InputStream in = response.getEntity().getContent();
            String str = readString(in);


        }
    }



     protected String readString(InputStream in) throws Exception {
         byte[] data = new byte[1024];
         int length = 0;
         ByteArrayOutputStream bout = new ByteArrayOutputStream();
         while ((length = in.read(data)) != -1) {
             bout.write(data, 0, length);
         }
         return new String(bout.toByteArray(), "UTF-8");
     }
/*
    private void getLocation() {
        // 获取位置管理服务
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) this.getSystemService(serviceName);

         Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER); // 通过GPS获取位置

      //  updateToNewLocation(location);//当点击按钮时调用该函数获得经纬度

    }

    private void updateToNewLocation(Location location) {


        double latitude = location.getLatitude();
        double longitude= location.getLongitude();
     //   wd.setText("" + latitude + ",");//显示纬度
     //   jd.setText(""+longitude+",");//显示经度




    }
*/
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.g, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}



