package mochamad.ulin.nuha.ta;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;


/**
 * Created by madchen on 14/07/2017.
 */

public class Detail extends AppCompatActivity implements View.OnClickListener {
    LinearLayout btn_profil,btn_foto,btn_lokasi,btn_data;
    String nis2;
    SwipeRefreshLayout swipe;
    static Server con = new Server();
    JSONParser jsonParser = new JSONParser();
    //Web api url

    NetworkImageView thumb_image;
    String tag_json_obj = "json_obj_req";

    ProgressDialog progressDialog;
    JSONArray string_json = null;
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_hasil = "Hasil";
    private ImageView imagea;
    Intent intent;

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
    public static final String TAG_gambar	= "foto";

    TextView nama,nis,wilayah,didik,kerja,email,alamat,hp;
    String nis1,nama1,lat1,lng1,email1,didik1,kerja1,wilya1,hp1,foto1,alaamt1;
    Button hpp,lokasss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_news);
//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imagea = (ImageView) findViewById(R.id.gambar_news);
        nama 		= (TextView) findViewById(R.id.judul_nama);
        nis 		= (TextView) findViewById(R.id.det_txt_nis);
        wilayah 		= (TextView) findViewById(R.id.det_txt_wilayah);
        didik 		= (TextView) findViewById(R.id.det_txt_didik);
        kerja 		= (TextView) findViewById(R.id.det_txt_kerja);
        email 		= (TextView) findViewById(R.id.det_txt_email);
        alamat 		= (TextView) findViewById(R.id.det_txt_alamat);
        hp 		= (TextView) findViewById(R.id.det_txt_hp);
        hpp = (Button) findViewById(R.id.btn_telpon);
        lokasss = (Button) findViewById(R.id.btn_lokasiii);

        hpp.setOnClickListener(this);
        lokasss.setOnClickListener(this);


    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nis2 = bundle.getString("NIS");
        Toast.makeText(this, nis2, Toast.LENGTH_SHORT).show();
        new ambiltampil().execute();
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_telpon:
                Toast.makeText(this, hp1.toString(), Toast.LENGTH_LONG).show();
               dialContactPhone(hp1);
                break;
            case R.id.btn_lokasiii:
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+lat1+","+lng1);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                break;
        }
    }

    class ambiltampil extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Detail.this);
            progressDialog.setMessage("Mohon Tunggu...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
           // progressDialog.dismiss();
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
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray Object_hasil = json.getJSONArray(TAG_hasil);
                    JSONObject hasil = Object_hasil.getJSONObject(0);
                    nis1 = hasil.getString(TAG_nis);
                    nama1 = hasil.getString(TAG_nama);
                    lat1 = hasil.getString(TAG_lat);
                    lng1 = hasil.getString(TAG_lng);
                    email1 = hasil.getString(TAG_email);
                    didik1 = hasil.getString(TAG_pendidikan);
                    kerja1 = hasil.getString(TAG_pekerjaan);
                    wilya1 = hasil.getString(TAG_wilayah);
                    hp1 = hasil.getString(TAG_no_hp);
                    foto1 = hasil.getString(TAG_gambar);
                    alaamt1 = hasil.getString(TAG_alamat);

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
         /*   Picasso.with(Detail.this).load(foto1).centerCrop().fit()
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .into(imagea);*/

            Picasso.with(Detail.this)
                    .load(foto1)
                    .error(R.drawable.ic_launcher)
                    .placeholder(R.drawable.ic_launcher)
                    .resize(200, 200)
                    .transform(new CircleTransform())
                    .into(imagea);
           // Picasso.with(context).load(imageUri).into(ivBasicImage);
            nis.setText(nis1);
            nama.setText(nama1);
            kerja.setText(kerja1);
            didik.setText(didik1);
            wilayah.setText(wilya1);
            hp.setText(hp1);
            alamat.setText(alaamt1);
            email.setText(email1);
            progressDialog.dismiss();
            Detail.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();

                }
            });
        }

    }



}
