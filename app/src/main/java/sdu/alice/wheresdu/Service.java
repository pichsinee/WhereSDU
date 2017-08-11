package sdu.alice.wheresdu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.LocaleList;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

public class Service extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latADouble = 0, lngADouble = 0;
    private LocationManager locationManager;
    private Criteria criteria;
    private int secInAnt = 1000;    //==> 1 sec
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            String tag = "11AugV2";
            int intMarker = R.mipmap.ic_marker_friend;
            String idUser = getIntent().getStringExtra("ID");
            int intIDUser = Integer.parseInt(idUser);   //แปลง String เป็น int

            try {

                GetAllUser getAllUser = new GetAllUser(Service.this);
                getAllUser.execute();
                String strJSON = getAllUser.get();

                JSONArray jsonArray = new JSONArray(strJSON);
                String[] strID = new String[jsonArray.length()];
                String[] strName = new String[jsonArray.length()];
                String[] strLat = new String[jsonArray.length()];
                String[] strLng = new String[jsonArray.length()];

                for (int i=0; i<jsonArray.length(); i+=1) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    strID[i] = jsonObject.getString("id");
                    strName[i] = jsonObject.getString("Name");
                    strLat[i] = jsonObject.getString("Lat");
                    strLng[i] = jsonObject.getString("Lng");

                    if (Integer.parseInt(strID[i]) == intIDUser) {

                        intMarker = R.mipmap.ic_marker_user;
                    } else {
                        intMarker = R.mipmap.ic_marker_friend;
                    }


                }   //for


            } catch (Exception e) {
                e.printStackTrace();
            }   //try
            handler.postDelayed(runnable, secInAnt);   //ทำการหน่วงเวลา
        }   //run
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_service_layout);
        // Setup MapFragment
        setupMapFragment();

        //Show Title
        showTitle();

        //Off Controler
        offControler();

        //Setup For Get Location
        setupForGetLocation();

    }   //onCreate Main Method

    @Override
    protected void onResume() {
        super.onResume();

        myGetLocation();
    }

    private void myGetLocation() {
        locationManager.removeUpdates(locationListener);

        //For Network ค้นหา location ผ่านเน็ตเวิร์ก ซึ่งมี error ความคลาดเคลื่อนด่นพิกัดค่อนข้างมากห (กรณีมือถือไม่มีการ์ด GPS)
        Location networkLocation = myFindLocation(locationManager.NETWORK_PROVIDER);
        if (networkLocation != null) {  //ถ้าค้นหาพิกีดได้ คือ มี Network
            latADouble = networkLocation.getLatitude();
            lngADouble = networkLocation.getLongitude();
        }

        //For GPS
        Location gpsLocation = myFindLocation(locationManager.GPS_PROVIDER);
        if (gpsLocation != null) {  //ถ้าพบพิกัดจากสัญญาณ GPS
            latADouble = gpsLocation.getLatitude();
            lngADouble = gpsLocation.getLongitude();
        }

        Log.d("11AugV1", "Lat ==> " + latADouble);
        Log.d("11AugV1", "Lng ==> " + lngADouble);
    }

    @Override
    protected void onStop() {
        super.onStop();

        locationManager.removeUpdates(locationListener);
    }

    public Location myFindLocation(String strProvider) {

        Location location = null;

        if (locationManager.isProviderEnabled(strProvider)) {   //ถ้าสามารถหาตำแหน่ง location ได้

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return null;
            }
            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);    //จับการเปลี่ยนแปลงทุก 1 นาที (1000) และทุกๆ 10 เมตร
            location = locationManager.getLastKnownLocation(strProvider);

        } //if

        return location;
    }

    public LocationListener locationListener = new LocationListener() { //ตรวจจับการเปลี่ยนแปลงตำแหน่ง
        @Override
        public void onLocationChanged(Location location) {  //ทำงานหลังจากมีการเปลี่ยนแปลงตำแหน่ง โดยค้นหาพิกัดที่มีการเปลี่ยนแปลงตำแหน่ง
            latADouble = location.getLatitude();
            lngADouble = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    private void setupForGetLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
    }

    private void offControler() {
        ImageView imageView = findViewById(R.id.imvOff);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showTitle() {
        TextView textView = findViewById(R.id.TxtTitle);
        String strName = getIntent().getStringExtra("Name");
        textView.setText(strName);
    }

    private void setupMapFragment() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Check lat, lng != 0
        createMapAndEditLatLng();

    }   //  onMapReady

    private void createMapAndEditLatLng() {

        //CreateMap
        while (latADouble == 0) {   //ถ้า location lat,lng = 0 ให้วนหาพิกัดใหม่จนกว่าจะเจอ (จนกว่า lat,lng != 0)
            myGetLocation();
        }   //While

        LatLng latLng = new LatLng(latADouble, lngADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        //Edit LatLng
        String strID = getIntent().getStringExtra("ID");
        MyConstant myConstant = new MyConstant();
        String strURL = myConstant.getUrlEditLatLng();

        try {

            EditLatLng editLatLng = new EditLatLng(Service.this);
            editLatLng.execute(strID,
                    Double.toString(latADouble),
                    Double.toString(lngADouble),
                    strURL);
            String result = editLatLng.get();
            Log.d("11AugV1", "result ==> " + result);


        } catch (Exception e) {
            e.printStackTrace();
        }

    } //CreateMap

}   //Main Class
