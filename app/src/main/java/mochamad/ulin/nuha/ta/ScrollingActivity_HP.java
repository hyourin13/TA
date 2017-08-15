package mochamad.ulin.nuha.ta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ScrollingActivity_HP extends AppCompatActivity {

    private ProgressDialog pDialog;
    int success;
    private static final String TAG_SUCCESS = "success";
    JSONParser jsonParser = new JSONParser();
    String nis, nama, email, pendidikan, pekerjaan, wilayah, device, no_hp,phonee,fnltoken;
    EditText ednama, edemail, eddidik, edkerja, edwilayah, edmasukz, edkeluarz;
    String refreshedToken,device_id;
    Server con = new Server();
    Button btok;
    String terimanis, terimajns, terimanama, terimamsukz, terimakeluarz;
    RadioButton rdn_laki,rdn_perempuan;
    String jns_kelamin, tgl_keluar, tgl_masuk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling__hp);

        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        // Toast.makeText(this, refreshedToken, Toast.LENGTH_SHORT).show();
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        device_id = tm.getDeviceId();
      //  Toast.makeText(this, device_id, Toast.LENGTH_SHORT).show();
        ednama = (EditText) findViewById(R.id.input_nama);
        edmasukz = (EditText) findViewById(R.id.input_masukz);
        edkeluarz = (EditText) findViewById(R.id.input_keluarz);
        edemail = (EditText) findViewById(R.id.input_email);
        eddidik = (EditText) findViewById(R.id.input_didik);
        edkerja = (EditText) findViewById(R.id.input_kerja);
        edwilayah = (EditText) findViewById(R.id.input_wilayah);
        rdn_laki = (RadioButton) findViewById(R.id.rb_laki);
        rdn_perempuan = (RadioButton) findViewById(R.id.rb_perempuan);
        btok = (Button) findViewById(R.id.btn_simpan);
        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = ednama.getText().toString();
                email = edemail.getText().toString();
                pendidikan = eddidik.getText().toString();
                pekerjaan = edkerja.getText().toString();
                wilayah = edwilayah.getText().toString();
                device = device_id;
                no_hp = phonee;
                fnltoken = refreshedToken;
                if (rdn_laki.isChecked()){
                    jns_kelamin = "1";
                }else if (rdn_perempuan.isChecked()){
                    jns_kelamin = "p";
                }else{
                    jns_kelamin = "z";
                }
                tgl_keluar=  edmasukz.getText().toString();
                tgl_masuk =  edkeluarz.getText().toString();

                new  semu().execute();
            }
        });


    }


    class semu extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ScrollingActivity_HP.this);
            pDialog.setMessage("Loading....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("nama", nama));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("pendidikan", pendidikan));
            params.add(new BasicNameValuePair("pekerjaan", pekerjaan));
            params.add(new BasicNameValuePair("wilayah", wilayah));
            params.add(new BasicNameValuePair("device", device));
            params.add(new BasicNameValuePair("no_hp", no_hp));
            params.add(new BasicNameValuePair("jns_kelamin", jns_kelamin));
            params.add(new BasicNameValuePair("tgl_keluar", tgl_keluar));
            params.add(new BasicNameValuePair("tgl_masuk", tgl_masuk));
            System.out.println(params);

            // Melakukan Proses Request HTTP Post dengan Parameter yang ada
            JSONObject json = jsonParser.makeHttpRequest(con.URL + "input_alumni_hp.php", "POST",
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
                Intent i = new Intent(ScrollingActivity_HP.this, Daftar.class);
                i.putExtra("NIS", nis);
                startActivity(i);
                SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("statusZ", device);
                editor.commit();
            }else {
                Toast.makeText(ScrollingActivity_HP.this, "Data Belum Lengkap", Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(R.string.app_name);
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                final PhoneNumber number = account.getPhoneNumber();
                phonee = number == null ? null : number.toString();
                //  Toast.makeText(ScrollingActivity.this, phonee, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(final AccountKitError error) {
            }
        });
    }


}