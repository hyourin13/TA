package mochamad.ulin.nuha.ta;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by king on 27/04/2017.
 */

public class Daftar extends AppCompatActivity {

    String  id,nis2,device2;
    private ProgressDialog pDialog;
    int success;
    JSONArray _JSONarray = null;
    private static final String TAG_SUCCESS = "success";
    JSONParser jsonParser = new JSONParser();
    Server con = new Server();
    Button btlanjut;
    private static final int OJEK_JEMPUT = 0;
    String lat_jemput, long_jemput, alamat;
    EditText edlokasi,lokas_ket;
    String lat,loog,nisss,ket;
    String nis_pref,no_hp1,status_kirim,z_pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar22);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bacaPreferensi();
        if (no_hp1.toString().equals("0")){
            if (z_pref.toString().equals("0")){
                TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                device2 = tm.getDeviceId();
                status_kirim = "masuk";
            }else{
               // Toast.makeText(this, z_pref, Toast.LENGTH_SHORT).show();
                device2 = z_pref;
                status_kirim = "z";
            }
        }else {
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            device2 = tm.getDeviceId();
            status_kirim = "ubah";
        }
        Toast.makeText(this, status_kirim, Toast.LENGTH_LONG).show();
        Toast.makeText(this, device2, Toast.LENGTH_LONG).show();

        setTitle("Pilih Lokasi Rumah Anda");
        edlokasi = (EditText) findViewById(R.id.ed_lokasi);
        btlanjut = (Button) findViewById(R.id.btlanjut);
        lokas_ket = (EditText) findViewById(R.id.edt_lokkasi);
        btlanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alamat = edlokasi.getText().toString();
                 if(alamat.toString().equals("")){
                    Toast.makeText(Daftar.this, "Lokasi belum diisi", Toast.LENGTH_SHORT).show();
                }else {
                    lat = lat_jemput;
                    loog = long_jemput;
                     nisss =nis2;
                     ket = lokas_ket.getText().toString();
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Daftar.this);
                    alertDialog.setTitle("Info");
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage("Apakah data yang anda masukkan sudah sesuai dengan lokasi Anda?\n\nALamat  = " + alamat+ "\n\nLatitude = " + lat + "\n\nLongitude = " + loog );
                    alertDialog.setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // TODO Auto-generated method stub
                            new pendaftaran().execute();

                        }
                    }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }


            }
        });
        edlokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Daftar.this, MapsActivity.class);
                startActivityForResult(intent, OJEK_JEMPUT);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (status_kirim.equalsIgnoreCase("ubah")){
            skipActivity(Pilihan.class);
            finish();

        }else {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Daftar.this);
            alertDialog.setTitle("Informasi");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Harap Menyelesaikan Proses Registrasi");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    private void skipActivity(Class<?> classOf) {
        Intent intent = new Intent(getApplicationContext(), classOf);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OJEK_JEMPUT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");
                lat_jemput = data.getStringExtra("lat");
                long_jemput = data.getStringExtra("long");
                edlokasi.setText(result);
            }
        }
    }

    //pendaftaran
    class pendaftaran extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Daftar.this);
            pDialog.setMessage("Mohon tunggu...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            // Berhubung Tidak ada proses Where dalam Query maka Parameternya
            // Kosong
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("alamat", alamat));
            params.add(new BasicNameValuePair("lat", lat));
            params.add(new BasicNameValuePair("devicee", device2));
            params.add(new BasicNameValuePair("loog", loog));
            params.add(new BasicNameValuePair("ket", ket));
            params.add(new BasicNameValuePair("status_kirim", status_kirim));
            System.out.println(params);

            // Melakukan Proses Request HTTP Post dengan Parameter yang ada
            JSONObject json = jsonParser.makeHttpRequest(con.URL + "input_lokasi_alumni.php", "POST",
                    params);
            // menampilkan log JSON pada logcat
            Log.d("Create Response", json.toString());

            // check untuk proses penyimpanan
            try {
                success = json.getInt(TAG_SUCCESS);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Setalah selesai dialog menghilang
         **/
        protected void onPostExecute(String file_url) {
            if (success == 1) {
                if (status_kirim.equalsIgnoreCase("masuk")){
                    Intent i = new Intent(Daftar.this, Upload.class);
                    startActivity(i);
                    SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("lat", lat);
                    editor.putString("lngg", loog);
                    editor.putString("alamat", alamat);
                    editor.commit();
                    finish();
                }else if (status_kirim.equalsIgnoreCase("z")){
                    Intent i = new Intent(Daftar.this, Upload.class);
                    startActivity(i);
                    SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("lat", lat);
                    editor.putString("lngg", loog);
                    editor.putString("alamat", alamat);
                    editor.commit();
                    finish();
                }else{
                    startActivity(new Intent(Daftar.this,Pilihan.class));
                    finish();
                }


            } else if (success == 0) {
                Toast.makeText(Daftar.this, "Gagal Menyimpan", Toast.LENGTH_SHORT).show();
            } else {

            }
            pDialog.dismiss();
        }
    }

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        no_hp1 = pref.getString("no_hp", "0");
        nis_pref = pref.getString("nis", "0");
        z_pref = pref.getString("statusZ", "0");

    }




}
