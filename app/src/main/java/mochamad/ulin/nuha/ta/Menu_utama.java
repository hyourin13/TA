package mochamad.ulin.nuha.ta;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

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

import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



/**
 * Created by king on 27/04/2017.
 */

public class Menu_utama extends AppCompatActivity implements View.OnClickListener {
    private ViewFlipper mViewFlipper;

    LinearLayout btn_akun, btn_maps,btn_nj1,btn_alumni,btn_dunia;
    String no_hp,lat,lngg,accountKitId,phoneNumberString;

    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    JSONParser jsonParser = new JSONParser();
    int success;
    Server con = new Server();

    private static final String TAG_hasil = "Hasil";
    String refreshedToken,device_id,HP;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_utama);
        initViews();
        bacaPreferensi();
        new TestInternet().execute();
    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed()
    {
/*
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);*/


        // code here to show dialog
        //super.onBackPressed();  // optional depending on your needs
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Peringatan");
        builder.setMessage("Apakah Anda Yakin akan Keluar");

        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog
               /* dialog.dismiss();
                finish();*/
                Intent ii = new Intent(Menu_utama.this, DrowRanger.class);
                startActivity(ii);
                finish();
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    class TestInternet extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Menu_utama.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.dismiss();
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
           // pDialog.dismiss();
            if (!result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Menu_utama.this);
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
                AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                    @Override
                    public void onSuccess(final Account account) {

                        // Get Account Kit ID
                        accountKitId = account.getId();

                        // Get phone number
                        PhoneNumber phoneNumber = account.getPhoneNumber();
                        phoneNumberString = phoneNumber.toString();
                        HP = phoneNumberString;
                        //Toast.makeText(Menu_utama.this, HP, Toast.LENGTH_LONG).show();


                        refreshedToken = FirebaseInstanceId.getInstance().getToken();
                        //Toast.makeText(Menu_utama.this, refreshedToken, Toast.LENGTH_LONG).show();
                        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                        device_id = tm.getDeviceId();
                        futar();
                        if (no_hp.toString().equals("0")){
                            Intent i = new Intent(Menu_utama.this, DrowRanger.class);
                            startActivity(i);
                            finish();
                        }else {
                            Toast.makeText(Menu_utama.this, " Aktif", Toast.LENGTH_SHORT).show();
                            new semu().execute();
                        }


                    }

                    @Override
                    public void onError(final AccountKitError error) {
                        // Handle Error
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Menu_utama.this);
                        alertDialog.setTitle("Gagal Login");
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage("Sesi Anda Telah Habis");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                startActivity(new Intent(Menu_utama.this,DrowRanger.class));
                                finish();

                            }
                        });
                        alertDialog.show();
                    }
                });
            }
        }
    }

    class semu extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Menu_utama.this);
            pDialog.setMessage("Loading....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.dismiss();
        }

        @Override
        protected String doInBackground(String... arg0) {
            // Berhubung Tidak ada proses Where dalam Query maka Parameternya
            // Kosong
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("token", refreshedToken));
            params.add(new BasicNameValuePair("device", device_id));
            params.add(new BasicNameValuePair("phone", HP));
            System.out.println(params);

            // Melakukan Proses Request HTTP Post dengan Parameter yang ada
            JSONObject json = jsonParser.makeHttpRequest(con.URL + "update_token.php", "POST",
                    params);

            // menampilkan log JSON pada logcat
            Log.d("Create Response", json.toString());

            // check untuk proses penyimpanan
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    System.out.println("Data Ditemukan");
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
                Toast.makeText(Menu_utama.this, "Sukses", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(Menu_utama.this, "Gagal", Toast.LENGTH_SHORT).show();
            }
            //pDialog.dismiss();
        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

    }


    public void futar(){
        //puter atas
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.image_view_flipper);
        mViewFlipper.setAutoStart(false);
        mViewFlipper.setFlipInterval(5000);
        mViewFlipper.startFlipping();
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
        mViewFlipper.showNext();

    }

    private void initViews() {
        btn_akun = (LinearLayout) findViewById(R.id.btn_akun);
        btn_akun.setOnClickListener(this);

        btn_maps = (LinearLayout) findViewById(R.id.btn_maps);
        btn_maps.setOnClickListener(this);

        btn_alumni = (LinearLayout) findViewById(R.id.btn_alumni);
        btn_alumni.setOnClickListener(this);

        btn_dunia = (LinearLayout) findViewById(R.id.btn_dunia);
        btn_dunia.setOnClickListener(this);

        btn_nj1 = (LinearLayout) findViewById(R.id.btn_nj);
        btn_nj1.setOnClickListener(this);
    }

    private void skipActivity(Class<?> classOf) {
        Intent intent = new Intent(getApplicationContext(), classOf);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_akun:
                skipActivity(Pilihan.class);
                break;
            case R.id.btn_maps:
                skipActivity(Menu_Lokasi.class);
                break;
            case R.id.btn_dunia:
                 skipActivity(Menu_Lokasi_All.class);
                break;
            case R.id.btn_alumni:
                skipActivity(Pilihan_Alumni.class);
                break;
            case R.id.btn_nj:
                skipActivity(Activity_NJ.class);
                break;

        }
    }


    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);

        no_hp = pref.getString("no_hp", "0");
        lat = pref.getString("lat", "0");
        lngg = pref.getString("lngg", "0");

    }
}
