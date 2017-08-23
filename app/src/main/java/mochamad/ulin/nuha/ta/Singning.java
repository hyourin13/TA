package mochamad.ulin.nuha.ta;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by madchen on 8/18/2017.
 */

public class Singning extends Activity implements View.OnClickListener {

    String[] PERMISSIONS = { android.Manifest.permission.READ_PHONE_STATE,android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.CALL_PHONE, android.Manifest.permission.READ_SMS, android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.WRITE_CONTACTS, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    int PERMISSION_ALL = 99;
    private static int MY_REQUEST_CODE = 1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    TextView cedafatar,ceklupa,ceklogin;
    EditText edthp,edtpass;
    Button nis;
    String hp,passs,final_hp,no_hp;

    Server con = new Server();
    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_hasil = "Hasil";
    int success;

    public static String TAGnis = "nis";
    public static String TAGnama = "nama";
    public static String TAGemail = "email";
    public static String TAGpendidikan = "pendidikan";
    public static String TAGpekerjaan = "pekerjaan";
    public static String TAGwilayah = "wilayah";
    public static String TAGno_hp = "no_hp";
    public static String TAGlat= "lat";
    public static String TAGlng= "lng";
    public static String TAGalamat= "alamat";
    public static String TAGjns_klmn= "jns_klmn";
    public static String TAGmasuk= "tgl_masuk";
    public static String TAGkeluar= "tgl_keluar";
    public static String TAGfoto= "foto";
    public static String TAGket= "ket";

    String nama,nis1,nama1,email1, kerja1, didik1, wilayah1, no_hp1, lat1, lng1,alamat1 ,jns_klmn1,masuk1, keluar1, foto1, ket1 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signing);
        edthp = (EditText) findViewById(R.id.sing_hp);
        edtpass = (EditText) findViewById(R.id.sing_pass);
        ceklogin = (TextView) findViewById(R.id.sing_login);

        cedafatar = (TextView) findViewById(R.id.sing_daftar);
        ceklupa = (TextView) findViewById(R.id.forgotpass);

        ceklogin.setOnClickListener(this);
        cedafatar.setOnClickListener(this);
        ceklupa.setOnClickListener(this);


        new TestInternet().execute();

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

        } else {
            showGPSDisabledAlertToUser();
        }

        askPermission();
        bacaPreferensi();
        if (no_hp.toString().equals("0")){

        }else {
            Intent i = new Intent(Singning.this, Menu_utama.class);
            startActivity(i);
            finish();
        }


    }

    @Override
    public void onBackPressed() {
        System.exit(0);
        // This above line close correctly
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(this, "Until you grant the permission, we cannot proceed further", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    public void askPermission() {
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(PERMISSIONS, PERMISSION_ALL);
                }
                return;
            }
        }
    }

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        no_hp = pref.getString("no_hp", "0");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sing_daftar:
                Intent pindah = new Intent(this, Login.class);
                startActivity(pindah);
                break;
            case R.id.sing_login:
                hp = edthp.getText().toString();
                String kata1 = "+62";
                final_hp = kata1.concat(hp);
                passs = edtpass.getText().toString();
                if (no_hp.equalsIgnoreCase("")){
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Singning.this);
                    alertDialog.setTitle("Gagal");
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage("Anda Belum Memasukkan Nomer");
                }else{
                    new semu().execute();
                }
                break;

        }
    }


    class TestInternet extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Singning.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                URL url = new URL("http://www.google.com");
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(1000);
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    return true;
                }
            } catch (MalformedURLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return false;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            pDialog.dismiss();
            if (!result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Singning.this);
                builder.setMessage("Tidak dapat menyambung ke internet. Silahkan cek koneksi internet anda. !!!");
                builder.setCancelable(false);

                builder.setPositiveButton(
                        "Coba Lagi",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                new TestInternet().execute();
                            }
                        });


                AlertDialog alert11 = builder.create();
                alert11.show();
            } else {
                pDialog.dismiss();

            }
        }
    }


    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setMessage("GPS pada perangkat anda sedang non-aktif. silakan mengaktifkan?")
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                                finish();
                            }
                        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    class semu extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Singning.this);
            pDialog.setMessage("Loading....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            // Berhubung Tidak ada proses Where dalam Query maka Parameternya
            // Kosong
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("hp", final_hp));
            params.add(new BasicNameValuePair("passs", passs));
            System.out.println(params);

            // Melakukan Proses Request HTTP Post dengan Parameter yang ada
            JSONObject json = jsonParser.makeHttpRequest(con.URL + "view_cari_alumni_new.php", "POST",
                    params);

            // menampilkan log JSON pada logcat
            Log.d("Create Response", json.toString());

            // check untuk proses penyimpanan
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    JSONArray Object_hasil = json.getJSONArray(TAG_hasil);
                    JSONObject hasil = Object_hasil.getJSONObject(0);
                    nis1 = hasil.getString(TAGnis);
                    nama1 = hasil.getString(TAGnama);
                    email1 = hasil.getString(TAGemail);
                    didik1 = hasil.getString(TAGpendidikan);
                    kerja1 = hasil.getString(TAGpekerjaan);
                    wilayah1 = hasil.getString(TAGwilayah);
                    no_hp1 = hasil.getString(TAGno_hp);
                    lat1 = hasil.getString(TAGlat);
                    lng1 = hasil.getString(TAGlng);
                    alamat1 = hasil.getString(TAGalamat);
                    jns_klmn1 = hasil.getString(TAGjns_klmn);
                    masuk1 = hasil.getString(TAGmasuk);
                    keluar1 = hasil.getString(TAGkeluar);
                    foto1 = hasil.getString(TAGfoto);
                    ket1 = hasil.getString(TAGket);

                } else {
                    // hasil Tidak ditemukan
                    System.out.println("Data Tidak Ditemukan");
                }

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

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Singning.this);
                alertDialog.setTitle("Sukses");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Data Ditemukan");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        startActivity(new Intent(Singning.this,Menu_utama.class));
                        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("no_hp", no_hp1);
                        editor.putString("nis", nis1);
                        editor.putString("nama", nama1);
                        editor.putString("wilayah", wilayah1);
                        editor.putString("didik", didik1);
                        editor.putString("email", email1);
                        editor.putString("kerja", kerja1);
                        editor.putString("lat", lat1);
                        editor.putString("lngg", lng1);
                        editor.putString("hpp", no_hp1);
                        editor.putString("foto", foto1);
                        editor.putString("alamat", alamat1);
                        editor.putString("jenis_kelamin", jns_klmn1);
                        editor.putString("tggl_keluar", keluar1);
                        editor.putString("tggl_masuk", masuk1);
                        editor.commit();
                        finish();

                    }
                });
                alertDialog.show();
            }else {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Singning.this);
                alertDialog.setTitle("Gagal");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Nomer Anda Belum Terdaftar");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        startActivity(new Intent(Singning.this,ErrorDaftar.class));
                        finish();

                    }
                });
                alertDialog.show();
            }
            pDialog.dismiss();
        }
    }


}
