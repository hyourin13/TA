package mochamad.ulin.nuha.ta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madchen on 9/20/2017.
 */

public class Detail_Tangggap  extends AppCompatActivity implements View.OnClickListener {
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

    public static final String TAG_nis 		    = "nis";
    public static final String TAG_nama         = "nama";
    public static final String TAG_email        = "klasifikasi";
    public static final String TAG_pendidikan 		= "masukan";
    public static final String TAG_pekerjaan = "tanggapan";
    public static final String TAG_wilayah = "user";
    public static final String TAG_no_hp	= "id_tanggapan";
    public static final String TAG_lat	= "waktu_masuk";
    public static final String TAG_lng	= "waktu_tanggap";
    public static final String TAG_alamat	= "id_klas";
    public static final String TAG_token	= "token";
    public static final String TAG_gambar	= "id_masukan";

    TextView nama,nis,wilayah,didik,kerja,email,alamat,hp;
    String nis1,nama1,lat1,lng1,email1,didik1,kerja1,wilya1,hp1,foto1,alaamt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_tanggap);
//        ImageLoader imageLoader = AppController.getInstance().getImageLoader();
        imagea = (ImageView) findViewById(R.id.gambar_n);
        nama 		= (TextView) findViewById(R.id.dtt_Klasifikasi);
        nis 		= (TextView) findViewById(R.id.dtt_nis);
        wilayah 		= (TextView) findViewById(R.id.dtt_masuk);
        didik 		= (TextView) findViewById(R.id.dtt_tanggap);
        kerja 		= (TextView) findViewById(R.id.dtt_petugas);
        email 		= (TextView) findViewById(R.id.dtt_w_masuk);
        alamat 		= (TextView) findViewById(R.id.dtt_w_tanggap);
        hp 		= (TextView) findViewById(R.id.dtt_nama);

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


    @Override
    public void onClick(View v) {

    }

    class ambiltampil extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Detail_Tangggap.this);
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
            JSONObject json = jsonParser.makeHttpRequest(con.URL + "tampil_per_tanggap.php", "POST",
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
            Picasso.with(Detail_Tangggap.this)
                    .load(foto1)
                    .error(R.drawable.logonj)
                    .placeholder(R.drawable.logonj)
                    .resize(200, 200)
                    .transform(new CircleTransform())
                    .into(imagea);
            nis.setText(nis1);
            nama.setText(email1);           //klas
            kerja.setText(wilya1);      //petugas
            didik.setText(kerja1);         //tanggap
            wilayah.setText(didik1);    //masuk
            hp.setText(nama1);      //nama
            alamat.setText(lng1);
            email.setText(lat1);
            progressDialog.dismiss();
            Detail_Tangggap.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();

                }
            });
        }

    }



}
