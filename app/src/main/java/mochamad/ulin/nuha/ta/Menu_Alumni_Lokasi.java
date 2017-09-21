package mochamad.ulin.nuha.ta;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;

/**
 * Created by madchen on 8/14/2017.
 */

public class Menu_Alumni_Lokasi extends SwipeBackActivity {
    static Server con = new Server();

    //Web api url
    String DATA_URL;
    //public static final String DATA_URL = "http://commit.paiton.biz/nurja/tampil_lokasi_alumni.php";
    //public static final String DATA_URL = "http://www.simplifiedcodingreaders.16mb.com/superheroes.php";

    //Tag values to read from json
    public static final String TAGfoto = "gambar";
    public static final String TAGnama= "nama";
    public static final String TAGalamat= "no_hp";
    public static final String TAGnis= "nis";


    //GridView Object
    private GridView gridView;

    //ArrayList for Storing image urls and titles
    private ArrayList<String> images;
    private ArrayList<String> names;
    private ArrayList<String> alamat;
    private ArrayList<String> nis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumni2);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        gridView = (GridView) findViewById(R.id.gridView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String Lokasi = bundle.getString("WILAYAH");
       // Toast.makeText(this, nama, Toast.LENGTH_SHORT).show();
        DATA_URL = con.URL+"tampil_lokasi_alumni_lokasi.php?wilayah="+Lokasi;
       // Toast.makeText(this, DATA_URL, Toast.LENGTH_SHORT).show();
        System.out.print(DATA_URL);


        images = new ArrayList<>();
        names = new ArrayList<>();
        alamat = new ArrayList<>();
        nis = new ArrayList<>();

        //Calling the getData method
        getData();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                // TODO Auto-generated method stub
                // Toast.makeText(Menu_Alumni.this, , Toast.LENGTH_SHORT).show();
                String coba = nis.get(position);
                Toast.makeText(Menu_Alumni_Lokasi.this, coba, Toast.LENGTH_SHORT).show();
                // gridView.getAdapter().getItem(position);
                Intent i = new Intent(Menu_Alumni_Lokasi.this, Detail.class);
                i.putExtra("NIS", coba);
                startActivity(i);


            }
        });


    }

    private void getData(){
        //Showing a progress dialog while our app fetches the data from url
        final AlertDialog dialog = new SpotsDialog(this, "Sedang Memuat Halaman", R.style.Custom);
        dialog.show();
        final Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                dialog.dismiss();
                t.cancel();
            }
        }, 10000);

        //Creating a json array request to get the json from our api
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(DATA_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing the progressdialog on response
                        dialog.dismiss();

                        //Displaying our grid
                        showGrid(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        Toast.makeText(Menu_Alumni_Lokasi.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding our request to the queue
        requestQueue.add(jsonArrayRequest);
    }


    private void showGrid(JSONArray jsonArray){
        //Looping through all the elements of json array
        for(int i = 0; i<jsonArray.length(); i++){
            //Creating a json object of the current index
            JSONObject obj = null;
            try {
                //getting json object from current index
                obj = jsonArray.getJSONObject(i);

                //getting image url and title from json object
                images.add(obj.getString(TAGfoto));
                names.add(obj.getString(TAGnama));
                alamat.add(obj.getString(TAGalamat));
                nis.add(obj.getString(TAGnis));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Creating GridViewAdapter Object
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this,images,names,alamat,nis);

        //Adding adapter to gridview
        gridView.setAdapter(gridViewAdapter);
    }


}
