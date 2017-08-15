package mochamad.ulin.nuha.ta;

import android.*;
import android.Manifest;
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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ryu on 23/05/2017.
 */

public class Signin extends AppCompatActivity implements View.OnClickListener {
    String[] PERMISSIONS = { android.Manifest.permission.READ_PHONE_STATE,android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.CALL_PHONE, android.Manifest.permission.READ_SMS, android.Manifest.permission.READ_CONTACTS, android.Manifest.permission.WRITE_CONTACTS, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    int PERMISSION_ALL = 99;
    private static int MY_REQUEST_CODE = 1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    TextView daftar,masuk;
    EditText hp;
    Server con = new Server();
    private ProgressDialog pDialog;
    String no_hp;
    HashMap<String, String> map;
    ProgressDialog progressDialog;
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> tampil = new ArrayList<HashMap<String, String>>();
    public static String TAGnis = "nis";
    public static String TAGnama = "nama";
    public static String TAGlat = "lat";
    public static String TAGlng = "lng";
    public static String TAGemail = "email";
    public static String TAGdidik = "pendidikan";
    public static String TAGkerja = "pekerjaan";
    public static String TAGwil = "wilayah";
    public static String TAGhp = "no_hp";
    public static String TAGfoto = "foto";
    public static String TAGdevice = "device";
    public static String TAGtoken = "token";
    public static String TAGalamat = "alamat";
    public static String TAGjns_klmn = "jns_klmn";
    public static String TAGtgl_keluar = "tgl_keluar";
    public static String TAGtgl_masuk = "tgl_masuk";
    int success;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_hasil = "Hasil";
    JSONArray string_json = null;

    String nis, nama, lat,lngg,email,didik,kerja, wilayah, hpp, foto,device, token,alamat,jenis_kelamin, tggl_keluar, tggl_masuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.awal);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),"font/Lato-Medium.ttf");
        daftar = (TextView) findViewById(R.id.Create2);
        masuk = (TextView) findViewById(R.id.signin);
        hp = (EditText) findViewById(R.id.nomorr);
        daftar.setTypeface(custom_font);
        masuk.setTypeface(custom_font);
        hp.setTypeface(custom_font);
        daftar.setOnClickListener(this);
        masuk.setOnClickListener(this);
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connMgr.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
        } else {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Signin.this);
            alertDialog.setTitle("Koneksi");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Maaf ada masalah dengan koneksi, coba periksa koneksi internet anda.");
            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent callGPSSettingIntent = new Intent(
                            Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(callGPSSettingIntent);
                    finish();
                }
            });
            alertDialog.show();
        }
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
         if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

        } else {
            showGPSDisabledAlertToUser();
        }

        bacaPreferensi();
        askPermission();
        if (no_hp.toString().equals("0")){

        }else {
            Intent i = new Intent(Signin.this, Menu_utama.class);
            startActivity(i);
            finish();
        }
       // Toast.makeText(this, no_hp, Toast.LENGTH_SHORT).show();
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

    public void cekkameraMashmellow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("MyApp", "SDK >= 23");
            if (this.checkSelfPermission(android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d("MyApp", "Request permission");
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.CAMERA},
                        MY_REQUEST_CODE);
                ActivityCompat.requestPermissions(Signin.this, new String[]{android.Manifest.permission.CAMERA},
                        MY_REQUEST_CODE);
            }
        } else {
            Log.d("MyApp", "Android < 6.0");
        }
    }

    public void ceklokasiMashmellow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.d("MyApp", "SDK >= 23");
            if (this.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d("MyApp", "Request permission");
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_REQUEST_CODE);
                ActivityCompat.requestPermissions(Signin.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_REQUEST_CODE);
            }
        } else {
            Log.d("MyApp", "Android < 6.0");
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission. ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission. ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Permission Location")
                        .setMessage("Give Acces ")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Signin.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission. ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Create2:
                Intent pindah = new Intent(this, Login.class);
                startActivity(pindah);
                break;
            case R.id.signin:
                no_hp = hp.getText().toString();
                if (no_hp.equalsIgnoreCase("")){
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Signin.this);
                    alertDialog.setTitle("Gagal");
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage("Anda Belum Memasukkan Nomer");
                    hp.setText("");
                }else{
                    new semu().execute();
                }
                break;
        }
    }

    class semu extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Signin.this);
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
            params.add(new BasicNameValuePair("no_hp", no_hp));
            System.out.println(params);

            /*// Melakukan Proses Request HTTP Post dengan Parameter yang ada
            JSONObject json = jsonParser.makeHttpRequest(con.URL + "view_cari_alumni.php", "POST",
                    params);

            // menampilkan log JSON pada logcat
            Log.d("Create Response", json.toString());

            // check untuk proses penyimpanan
            try {

                success = json.getInt(TAG_SUCCESS);


            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            JSONObject json = jsonParser.makeHttpRequest(con.URL + "view_cari_alumni.php", "POST",
                    params);

            // menampilkan log JSON pada logcat
            Log.d("Create Response", json.toString());

            // check untuk proses penyimpanan

            try {
                success = json.getInt(TAG_SUCCESS);
              //  System.out.println(success);
                if (success == 1) {
                    JSONArray Object_hasil = json.getJSONArray(TAG_hasil);
                    JSONObject hasil = Object_hasil.getJSONObject(0);
                    nis = hasil.getString(TAGnis);
                    nama = hasil.getString(TAGnama);
                    lat = hasil.getString(TAGlat);
                    lngg = hasil.getString(TAGlng);
                    email = hasil.getString(TAGemail);
                    didik = hasil.getString(TAGdidik);
                    kerja = hasil.getString(TAGkerja);
                    wilayah = hasil.getString(TAGwil);
                    hpp = hasil.getString(TAGhp);
                    foto = hasil.getString(TAGfoto);
                    device = hasil.getString(TAGdevice);
                    token = hasil.getString(TAGtoken);
                    alamat = hasil.getString(TAGalamat);
                    jenis_kelamin = hasil.getString(TAGjns_klmn);
                    tggl_keluar = hasil.getString(TAGtgl_keluar);
                    tggl_masuk = hasil.getString(TAGtgl_masuk);
                }else  if (success == 5){
                    JSONArray Object_hasil = json.getJSONArray(TAG_hasil);
                    JSONObject hasil = Object_hasil.getJSONObject(0);
                    nis = hasil.getString(TAGnis);
                    nama = hasil.getString(TAGnama);
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

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Signin.this);
                alertDialog.setTitle("Sukses");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Anda Berhasil Login");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        startActivity(new Intent(Signin.this,Menu_utama.class));
                        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("no_hp", no_hp);
                        editor.putString("nis", nis);
                        editor.putString("nama", nama);
                        editor.putString("wilayah", wilayah);
                        editor.putString("didik", didik);
                        editor.putString("email", email);
                        editor.putString("kerja", kerja);
                        editor.putString("lat", lat);
                        editor.putString("lngg", lngg);
                        editor.putString("hpp", hpp);
                        editor.putString("foto", foto);
                        editor.putString("device", device);
                        editor.putString("token", token);
                        editor.putString("alamat", alamat);
                        editor.putString("jenis_kelamin", jenis_kelamin);
                        editor.putString("tggl_keluar", tggl_keluar);
                        editor.putString("tggl_masuk", tggl_masuk);
                        editor.commit();
                        finish();

                    }
                });
                alertDialog.show();
            }else  if (success == 5) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Signin.this);
                alertDialog.setTitle("Sukses");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Data Tidak Lengkap, Mohon Dilengkapi");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(Signin.this, ScrollingActivity.class);
                        i.putExtra("NIS", nis);
                        startActivity(i);

                    }
                });
                alertDialog.show();
            } else {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Signin.this);
                alertDialog.setTitle("Gagal");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Nomer Anda Belum Terdaftar");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        startActivity(new Intent(Signin.this,ErrorDaftar.class));
                        finish();

                    }
                });
                alertDialog.show();
            }
            pDialog.dismiss();
        }
    }


    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        no_hp = pref.getString("no_hp", "0");
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


}
