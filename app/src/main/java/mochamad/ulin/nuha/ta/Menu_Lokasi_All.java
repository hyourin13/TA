package mochamad.ulin.nuha.ta;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;

public class Menu_Lokasi_All extends AppCompatActivity {

    // Google Map
    private GoogleMap googleMap;


    // Latitude & Longitude
    private Double Latitude = 0.00;
    private Double Longitude = 0.00;
    ArrayList<HashMap<String, String>> location = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_all);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        //*** Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        String url = "http://commit.paiton.biz/nurja/tampil_lokasi_per_alumni.php";
        try {

            JSONArray data = new JSONArray(getHttpGet(url));

            location = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> map;

            for(int i = 0; i < data.length(); i++){
                JSONObject c = data.getJSONObject(i);

                map = new HashMap<String, String>();
                map.put("LocationID", c.getString("nis"));
                map.put("Latitude", c.getString("lat"));
                map.put("Longitude", c.getString("lng"));
                map.put("LocationName", c.getString("nama"));
                location.add(map);

            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        // *** Display Google Map

       // mapFragment.getMapAsync(this);

        Latitude = Double.parseDouble(location.get(0).get("Latitude").toString());
        Longitude = Double.parseDouble(location.get(0).get("Longitude").toString());
        LatLng coordinate = new LatLng(Latitude, Longitude);
        googleMap.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinate, 17));

        // *** Marker (Loop)
        for (int i = 0; i < location.size(); i++) {
            Latitude = Double.parseDouble(location.get(i).get("Latitude").toString());
            Longitude = Double.parseDouble(location.get(i).get("Longitude").toString());
            String name = location.get(i).get("LocationName").toString();
            MarkerOptions marker = new MarkerOptions().position(new LatLng(Latitude, Longitude)).title(name);
            googleMap.addMarker(marker);
        }

    }

    public static String getHttpGet(String url) {
        StringBuilder str = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) { // Download OK
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    str.append(line);
                }
            } else {
                Log.e("Log", "Failed to download result..");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }


}