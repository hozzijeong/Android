package com.example.t33_googlemap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{
    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.maps);

        mapFragment.getMapAsync( this);
    }

    Marker myMarker;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

         /*
            이 아래부터 맵을 사용할 수 있음.
            LatLng(위도/경도) 클래스
            moveCamera(내 시야)

            markerOptions.title -> 제목
            markerOptions.snippet-> 설명

            Marker는 add된 것 자체가 return 을 받기 때문에, add된 Marker 자체를 객체화 시켜서 사용이 가능함.
            즉, 따로 생성이 된 마커 자체를 조작이 가능하게됨.
         */
        LatLng capital = new LatLng(37.56, 126.97);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(capital);
        markerOptions.title("서울");
        markerOptions.snippet("수도");
        myMarker = map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(capital));
        map.animateCamera(CameraUpdateFactory.zoomTo(15));

    }
}
