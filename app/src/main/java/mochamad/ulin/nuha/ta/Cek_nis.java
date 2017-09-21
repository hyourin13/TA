package mochamad.ulin.nuha.ta;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Cek_nis extends Activity {
    TextView cek;
    Button nis;
    String niss;

    Server con = new Server();
    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    JSONParser jsonParser = new JSONParser();
    private static final String TAG_hasil = "Hasil";
    int success;
    public static String TAGnis = "nis";
    public static String TAGnm = "nm";
    public static String TAGjns_klmn = "jns_klmn";
    public static String TAGtgl_masuk = "tgl_masuk";
    public static String TAGtgl_keluar = "tgl_keluar";



    String nama,jns,tglmasuk,tglkeluar,nis1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_nis);
        cek = (TextView) findViewById(R.id.cek_nis);
        nis = (Button) findViewById(R.id.btn_cek);

        nis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                niss = cek.getText().toString();
                if (niss.equalsIgnoreCase("")){
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cek_nis.this);
                    alertDialog.setTitle("Gagal");
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage("Anda Belum Memasukkan Nomer");
                    nis.setText("");
                }else{
                    Toast.makeText(Cek_nis.this, niss, Toast.LENGTH_SHORT).show();
                    new semu().execute();
                }
            }
        });
    }


    class semu extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Cek_nis.this);
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
            params.add(new BasicNameValuePair("niss", niss));
            System.out.println(params);

            // Melakukan Proses Request HTTP Post dengan Parameter yang ada
            JSONObject json = jsonParser.makeHttpRequest(con.URL + "view_cari_nis_alumni.php", "POST",
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
                    nama = hasil.getString(TAGnm);
                    jns = hasil.getString(TAGjns_klmn);
                    tglmasuk = hasil.getString(TAGtgl_masuk);
                    tglkeluar = hasil.getString(TAGtgl_keluar);

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

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cek_nis.this);
                alertDialog.setTitle("Sukses");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Data Ditemukan");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Intent i = new Intent(Cek_nis.this, ScrollingActivity.class);
                        i.putExtra("NIS", niss);
                        i.putExtra("Nama", nama);
                        i.putExtra("JNS", jns);
                        i.putExtra("MASUKZ", tglmasuk);
                        i.putExtra("KELUARZ", tglkeluar);
                        startActivity(i);
                        finish();

                    }
                });
                alertDialog.show();
            }else {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cek_nis.this);
                alertDialog.setTitle("Gagal");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("NIS Anda Tidak Ditemukan");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        startActivity(new Intent(Cek_nis.this,Cek_nis.class));
                        finish();

                    }
                });
                alertDialog.show();
            }
            pDialog.dismiss();
        }
    }


}
