package mochamad.ulin.nuha.ta;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Password extends AppCompatActivity {
    EditText edt1,edt2;
    Button btn1;
    Server con = new Server();
    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_hasil = "Hasil";
    int success;

    public static String TAGnis = "nis";
    String nis1;

    String device, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        edt1 = (EditText) findViewById(R.id.edt_cek_password);
        edt2 = (EditText) findViewById(R.id.edt_cek_password2);
        btn1 = (Button) findViewById(R.id.btn_cek_pass);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String satu = edt1.getText().toString();
                String dua = edt2.getText().toString();
                if (satu.equalsIgnoreCase("")){
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Password.this);
                    alertDialog.setTitle("Gagal");
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage("Anda Belum Menginputkan Password");
                }else {
                    if (satu.equalsIgnoreCase(dua)){
                        TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                        device = tm.getDeviceId();
                        pass = satu;
                        new semu().execute();
                    }else{
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Password.this);
                        alertDialog.setTitle("Gagal");
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage("Password Tidak Cocok");
                    }
                }
            }
        });

    }


    class semu extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Password.this);
            pDialog.setMessage("Loading....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(getApplicationContext(), values[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            // Berhubung Tidak ada proses Where dalam Query maka Parameternya
            // Kosong
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("pass", pass));
            params.add(new BasicNameValuePair("device", device));
            System.out.println(params);

            // Melakukan Proses Request HTTP Post dengan Parameter yang ada
            JSONObject json = jsonParser.makeHttpRequest(con.URL + "input_pass.php", "POST",
                    params);

            // menampilkan log JSON pada logcat
            Log.d("Create Response", json.toString());

            // check untuk proses penyimpanan
            try {
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {

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

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Password.this);
                alertDialog.setTitle("Sukses");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Sukses Disimpan");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        new semu2().execute();
                        Intent i = new Intent(Password.this, Daftar.class);
                        startActivity(i);
                        finish();

                    }
                });
                alertDialog.show();
            }else {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Password.this);
                alertDialog.setTitle("Gagal");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Ntahlah");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub


                    }
                });
                alertDialog.show();
            }
            pDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Password.this);
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


    class semu2 extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Password.this);
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
            params.add(new BasicNameValuePair("device", device));
            System.out.println(params);

            // Melakukan Proses Request HTTP Post dengan Parameter yang ada
            JSONObject json = jsonParser.makeHttpRequest(con.URL + "view_ambil_nis.php", "POST",
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
                Intent i = new Intent(Password.this, Daftar.class);
                SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("nis", nis1);
                editor.commit();
                startActivity(i);
                finish();

            }else {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Password.this);
                alertDialog.setTitle("Gagal");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("NIS Anda Tidak Ditemukan");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub


                    }
                });
                alertDialog.show();
            }
            pDialog.dismiss();
        }
    }




}
