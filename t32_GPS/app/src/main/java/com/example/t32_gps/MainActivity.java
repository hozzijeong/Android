package com.example.t32_gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LocationManager manager;
    TextView lat,lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lat  = findViewById(R.id.lat);
        lon = findViewById(R.id.lon);
        checkPers();
    }

    private void checkPers() {
        String[] pers = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        //GPS 와 기지국 위치의 허용을 받는 행위
        if (PackageManager.PERMISSION_DENIED == ContextCompat.checkSelfPermission(this, pers[0])) {
            ActivityCompat.requestPermissions(this, pers, 2000);
        } else {
            init();
        }
    }

    private void init() {
        manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
         /*
            위치 정보 업데이트 메서드들의 파라미터 의미
            provider -> 어디서 정보를 받을것인가 (기지국 or 로케이션)
            minTime(mill sec) -> 위치값을 받는 주기가 얼마나 되나?
            Distance (meters) -> n미터씩 이동할때 마다 위치 정보를 보냐줌
         */

        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, new GPSListener());
        Location location =manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if(location != null){
            lat.setText("Lat: "+location.getLatitude());
            lon.setText("Lon: "+ location.getLongitude());

            Log.d("mood","lat: " + location.getLatitude() +" lon: "+ location.getLongitude());
        }


    }

    class GPSListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
