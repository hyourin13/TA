package mochamad.ulin.nuha.ta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by madchen on 14/07/2017.
 */

public class Detail_Home extends AppCompatActivity {
    LinearLayout btn_profil,btn_foto,btn_lokasi,btn_data;
    String nis2;
    SwipeRefreshLayout swipe;
    static Server con = new Server();
    JSONParser jsonParser = new JSONParser();
    //Web api url

    NetworkImageView thumb_image;
    String tag_json_obj = "json_obj_req";

    private static final String TAG = Detail_Home.class.getSimpleName();

    public static final String DATA_URL = con.URL+"tampil_per_alumni.php";

    public static final String TAG_nis 		    = "nis";
    public static final String TAG_nama         = "nama";
    public static final String TAG_email = "email";
    public static final String TAG_pendidikan 		= "pendidikan";
    public static final String TAG_pekerjaan = "pekerjaan";
    public static final String TAG_wilayah = "wilayah";
    public static final String TAG_no_hp	= "no_hp";
    public static final String TAG_lat	= "lat";
    public static final String TAG_lng	= "lng";
    public static final String TAG_alamat	= "alamat";
    public static final String TAG_token	= "token";
    public static final String TAG_gambar	= "gambar";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_hasil = "Hasil";

    TextView nama,nis,wilayah,didik,kerja,email,alamat,hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_news);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nis2 = bundle.getString("NIS");
        Toast.makeText(this, nis2, Toast.LENGTH_SHORT).show();

        //thumb_image = (NetworkImageView) findViewById(R.id.gambar_news);
//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        nama 		= (TextView) findViewById(R.id.judul_nama);
        nis 		= (TextView) findViewById(R.id.det_txt_nis);
        wilayah 		= (TextView) findViewById(R.id.det_txt_wilayah);
        didik 		= (TextView) findViewById(R.id.det_txt_didik);
        kerja 		= (TextView) findViewById(R.id.det_txt_kerja);
        email 		= (TextView) findViewById(R.id.det_txt_email);
        alamat 		= (TextView) findViewById(R.id.det_txt_alamat);
        hp 		= (TextView) findViewById(R.id.det_txt_hp);

    }

/*

    class ambiltampil extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ProgressDialog progressDialog = new ProgressDialog(Detail_Home.this);
            progressDialog.setMessage("Mohon Tunggu...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("nis", nis2));
            System.out.println(params);

            // Melakukan Proses Request HTTP Post dengan Parameter yang ada
            JSONObject json = jsonParser.makeHttpRequest(con.URL + "view_cari_data_alumni.php", "POST",
                    params);

            // menampilkan log JSON pada logcat
            Log.d("Create Response", json.toString());

            // check untuk proses penyimpanan

            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray Object_hasil = json.getJSONArray(TAG_hasil);
                    JSONObject hasil = Object_hasil.getJSONObject(0);
                    nis = hasil.getString(TAGnis);
                    nama = hasil.getString(TAGnama);
                    lat = hasil.getString(TAGlat);
                    lng = hasil.getString(TAGlng);
                    email = hasil.getString(TAGemail);
                    didik = hasil.getString(TAGdidik);
                    kerja = hasil.getString(TAGkerja);
                    wilya = hasil.getString(TAGwil);
                    hp = hasil.getString(TAGhp);
                    foto = hasil.getString(TAGfoto);

                }else{
                    // hasil Tidak ditemukan
                    System.out.println("Data Tidak Ditemukan");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //Toast.makeText(getActivity(),Coba,Toast.LENGTH_LONG).show();
            Picasso.with(getActivity())
                    .load(foto)
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imagea);
            txtnis.setText(nis);
            txtnama.setText(nama);
            txtkerja.setText(kerja);
            txtdidik.setText(didik);
            txtemail.setText(email);
            txtwil.setText(wilya);
            progressDialog.dismiss();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();

                }
            });
        }

    }

*/

    private void callDetailNews(final String id){
        swipe.setRefreshing(true);

        StringRequest strReq = new StringRequest(Request.Method.POST, DATA_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response.toString());
                swipe.setRefreshing(false);

                try {
                    JSONObject obj = new JSONObject(response);

                    nama.setText(obj.getString(TAG_nama));
                    nis.setText(obj.getString(TAG_nis));
                    wilayah.setText(obj.getString(TAG_wilayah));
                    didik.setText(obj.getString(TAG_pendidikan));
                    kerja.setText(obj.getString(TAG_pekerjaan));
                    email.setText(obj.getString(TAG_email));
                    alamat.setText(obj.getString(TAG_alamat));
                    hp.setText(obj.getString(TAG_no_hp));

                   // catatan.loadData("<p style=\"color:black;font-size:12px;text-align: justify\">"+obj.getString(TAG_ISI)+"</p>","text/html","utf-8");

                   /* if (obj.getString(TAG_gambar)!=""){
                        thumb_image.setImageUrl(obj.getString(TAG_gambar), imageLoader);
                    }*/



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Detail News Error: " + error.getMessage());
                Toast.makeText(Detail_Home.this, error.getMessage(), Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("nis", nis2);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

}
