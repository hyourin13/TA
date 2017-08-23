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

public class ScrollingActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    int success;
    private static final String TAG_SUCCESS = "success";
    JSONParser jsonParser = new JSONParser();
    String nis, nama, email, pendidikan, pekerjaan, wilayah, device, no_hp,phonee,fnltoken;
    EditText ednis, ednama, edemail, eddidik, edkerja, edwilayah, edjns, edmasukz, edkeluarz;
    String refreshedToken,device_id;
    Server con = new Server();
    Button btok;
    String nis_pref,no_hp1,status_kirim,tggl_masuk_pref, jenis_kelamin_pref,tggl_keluar_pref,nama_pref;
    String terimanis, terimajns, terimanama, terimamsukz, terimakeluarz;
    RadioButton rdn_laki,rdn_perempuan;
    String jns_kelamin, tgl_keluar, tgl_masuk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        refreshedToken = FirebaseInstanceId.getInstance().getToken();
       // Toast.makeText(this, refreshedToken, Toast.LENGTH_SHORT).show();
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        device_id = tm.getDeviceId();
       // Toast.makeText(this, device_id, Toast.LENGTH_SHORT).show();
        ednis = (EditText) findViewById(R.id.input_nis);
        ednama = (EditText) findViewById(R.id.input_nama);
       // edjns= (EditText) findViewById(R.id.input_jns);
        edmasukz = (EditText) findViewById(R.id.input_masukz);
        edkeluarz = (EditText) findViewById(R.id.input_keluarz);
        edemail = (EditText) findViewById(R.id.input_email);
        eddidik = (EditText) findViewById(R.id.input_didik);
        edkerja = (EditText) findViewById(R.id.input_kerja);
        edwilayah = (EditText) findViewById(R.id.input_wilayah);
        rdn_laki = (RadioButton) findViewById(R.id.rb_laki);
        rdn_perempuan = (RadioButton) findViewById(R.id.rb_perempuan);
        btok = (Button) findViewById(R.id.btn_simpan);
        bacaPreferensi();
        if (no_hp1.toString().equals("0")){
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            terimanis = bundle.getString("NIS");
            terimanama = bundle.getString("Nama");
            terimajns = bundle.getString("JNS");
            terimamsukz = bundle.getString("MASUKZ");
            terimakeluarz = bundle.getString("KELUARZ");
            ednis.setText(terimanis);
            ednama.setText(terimanama);
            //edjns.setText(terimajns);
            edmasukz.setText(terimamsukz);
            edkeluarz.setText(terimakeluarz);
            if (terimajns.equalsIgnoreCase("l")){
                rdn_laki.setChecked(true);
            }else if (terimajns.equalsIgnoreCase("p")) {
                rdn_perempuan.setChecked(true);
            }else{

            }
            status_kirim = "masuk";
        }else {
           /* Toast.makeText(this, nis_pref, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, nama_pref, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, jenis_kelamin_pref, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, tggl_keluar_pref, Toast.LENGTH_SHORT).show();
            Toast.makeText(this, tggl_masuk_pref, Toast.LENGTH_SHORT).show();*/
            ednis.setText(nis_pref);
            ednama.setText(nama_pref);
            if (jenis_kelamin_pref.equalsIgnoreCase("l")){
                rdn_laki.setChecked(true);
            }else if (jenis_kelamin_pref.equalsIgnoreCase("p")){
                rdn_perempuan.setChecked(true);
            }else{

            }
            edkeluarz.setText(tggl_keluar_pref);
            edmasukz.setText(tggl_masuk_pref);
            status_kirim = "ubah";
        }
        Toast.makeText(this, status_kirim, Toast.LENGTH_SHORT).show();
        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nis = ednis.getText().toString();
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
                tgl_keluar= terimakeluarz;
                tgl_masuk = terimamsukz;

                new  semu().execute();
            }
        });


    }


    class semu extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ScrollingActivity.this);
            pDialog.setMessage("Loading....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("nis", nis));
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
            params.add(new BasicNameValuePair("status_kirim", status_kirim));
            System.out.println(params);

            // Melakukan Proses Request HTTP Post dengan Parameter yang ada
            JSONObject json = jsonParser.makeHttpRequest(con.URL + "input_alumni.php", "POST",
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
                    Intent i = new Intent(ScrollingActivity.this, Password.class);
                    startActivity(i);
                    Toast.makeText(ScrollingActivity.this, nis, Toast.LENGTH_SHORT).show();
                    SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("nis", nis);
                    editor.putString("nama", nama);
                    editor.putString("email", email);
                    editor.putString("pendidikan", pendidikan);
                    editor.putString("pekerjaan", pekerjaan);
                    editor.putString("wilayah", wilayah);
                    editor.putString("device", device);
                    editor.putString("jns_kelamin", jns_kelamin);
                    editor.putString("tgl_keluar", tgl_keluar);
                    editor.putString("tgl_masuk", tgl_masuk);
                    editor.commit();
                    finish();
                }else  if (status_kirim.equalsIgnoreCase("ubah")){
                    startActivity(new Intent(ScrollingActivity.this,Pilihan.class));
                    finish();
                }


            }else {
                Toast.makeText(ScrollingActivity.this, "Data Belum Lengkap", Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {

        if (status_kirim.equalsIgnoreCase("ubah")){
            skipActivity(Pilihan.class);
            finish();

        }else {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ScrollingActivity.this);
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

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        no_hp1 = pref.getString("no_hp", "0");
        nis_pref = pref.getString("nis", "0");
        jenis_kelamin_pref = pref.getString("jenis_kelamin", "0");
        tggl_keluar_pref = pref.getString("tggl_keluar", "0");
        tggl_masuk_pref = pref.getString("tggl_masuk", "0");
        nama_pref = pref.getString("nama", "0");

    }

}