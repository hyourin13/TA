package mochamad.ulin.nuha.ta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
    String terimanis,nis, nama, email, pendidikan, pekerjaan, wilayah, device, no_hp,phonee,fnltoken;
    EditText ednis, ednama, edemail, eddidik, edkerja, edwilayah, eddevice, edhp, edlat, edlong;
    String refreshedToken,device_id;
    Server con = new Server();
    Button btok;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Toast.makeText(this, refreshedToken, Toast.LENGTH_SHORT).show();
        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        device_id = tm.getDeviceId();
        Toast.makeText(this, device_id, Toast.LENGTH_SHORT).show();
        ednis = (EditText) findViewById(R.id.input_nis);
        ednama = (EditText) findViewById(R.id.input_nama);
        edemail = (EditText) findViewById(R.id.input_email);
        eddidik = (EditText) findViewById(R.id.input_didik);
        edkerja = (EditText) findViewById(R.id.input_kerja);
        edwilayah = (EditText) findViewById(R.id.input_wilayah);
        btok = (Button) findViewById(R.id.btn_simpan);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        terimanis = bundle.getString("NIS");
        ednis.setText(terimanis);
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
            params.add(new BasicNameValuePair("fnltoken", fnltoken));
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

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ScrollingActivity.this);
                alertDialog.setTitle("BERHASIL");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Terima kasih Atas telah mendaftar sebagai . \nAlumni Nurul Jadid ");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(ScrollingActivity.this, Upload.class);
                        i.putExtra("NIS", nis);
                        startActivity(i);
                        //Toast.makeText(ScrollingActivity.this, nis, Toast.LENGTH_LONG).show();
                    }
                });
                alertDialog.show();
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
                Toast.makeText(ScrollingActivity.this, phonee, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(final AccountKitError error) {
            }
        });
    }

}