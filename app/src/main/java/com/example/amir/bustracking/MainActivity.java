package com.example.amir.bustracking;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn;
    private static final int R_CALL=2;
    String mPermission= Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker  gpsTracker;
    TextView location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            if (ActivityCompat.checkSelfPermission(this,mPermission)!= MockPackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[] {mPermission},R_CALL);

            }
        }catch (Exception e){e.printStackTrace();}
        btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gpsTracker=new GPSTracker(MainActivity.this);
                location=(TextView)findViewById(R.id.location_txt);
                if (gpsTracker.canGetLocation()){
                    double lalitude=gpsTracker.getLatitude();
                    double longittude=gpsTracker.getLongtitude();
                    location.setText(lalitude+""+longittude);
                }else {
                    gpsTracker.ShowSettingalert();
                }
            }
        });
    }
}
