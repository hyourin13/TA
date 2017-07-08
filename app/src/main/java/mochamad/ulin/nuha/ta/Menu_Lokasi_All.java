package mochamad.ulin.nuha.ta;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.CameraUpdateFactory;

public class Menu_Lokasi_All extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Location location;
    Marker mCurrLocationMarker;
    LocationManager locationManager;
    ProgressDialog pDialog;
    // Google Map
    JSONArray _JSONarray = null;
    JSONArray string_json = null;
    JSONParser jsonParser = new JSONParser();
    private static String url_tampil = "http://commit.paiton.biz/nurja/tampil_lokasi_per_alumni.php";

    private static final String TAGnama = "nama";
    private static final String TAGnis = "nis";
    private static final String TAGlatitude = "lat";
    private static final String TAGlongitude = "lng";
    private static final String TAGalamat = "alamat";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_hasil = "Hasil";


    ArrayList<HashMap<String, String>> dataMap = new ArrayList<HashMap<String, String>>();
    SharedPreferences sharedPreferences;
    LinearLayout puskesmas, history, darurat, selesai, panduan, bloker;
    String username;
    TextView lat, lng;
    public static final String TAG = MapsActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_all);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.googleMap1);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        new tampil_maps().execute();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            handleNewLocation(location);
        }
    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());


        Drawable circleDrawable2 = getResources().getDrawable(R.drawable.icon_now);
        BitmapDescriptor markerIcon2 = getMarkerIconFromDrawable(circleDrawable2);

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng3 = new LatLng(currentLatitude, currentLongitude);
        mMap.addMarker(new MarkerOptions().position(latLng3).title("Anda Disini").icon(markerIcon2)).showInfoWindow();
        float zoomLevel = (float) 5.0;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng3, zoomLevel));
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
    }

    class tampil_maps extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            System.out.println(params);
            JSONObject json = jsonParser.makeHttpRequest(url_tampil, "GET",params);
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    _JSONarray = json.getJSONArray(TAG_hasil);
                    for (int i = 0; i < _JSONarray.length(); i++) {
                        JSONObject object = _JSONarray.getJSONObject(i);
                        String nis = object.getString(TAGnis);
                        String latitude = object.getString(TAGlatitude);
                        String longitude = object.getString(TAGlongitude);
                        String nama = object.getString(TAGnama);
                        String alama = object.getString(TAGalamat);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(TAGnis,nis);
                        map.put(TAGnama,nama);
                        map.put(TAGlatitude,latitude);
                        map.put(TAGlongitude,longitude);
                        map.put(TAGalamat,alama);
                        dataMap.add(map);
                    }
                } else if (success == 0){

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < dataMap.size(); i++) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map = dataMap.get(i);
                        Drawable circleDrawable = getResources().getDrawable(R.drawable.icon_now);
                        BitmapDescriptor markerIcon = getMarkerIconFromDrawable(circleDrawable);
                        LatLng POSISI = new LatLng(Double.parseDouble(map.get(TAGlatitude)),Double.parseDouble(map.get(TAGlongitude)));
                        mMap.addMarker(new MarkerOptions()
                                .position(POSISI)
                                .title(map.get(TAGnama)).snippet(map.get(TAGalamat)).icon(markerIcon));

                    }
                }
            });
        }
    }

    private BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }
}