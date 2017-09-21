package mochamad.ulin.nuha.ta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tanggapan extends AppCompatActivity {
    TextView telpon;
    ProgressDialog progressDialog;
    JSONParser jsonParser = new JSONParser();
    JSONArray string_json = null;
    Customlaporan adapter;
    HashMap<String, String> map;
    public static String TAGnomer_telpon = "nomer_telepon";
    public static String TAGkeluhan = "keluhan";
    public static String TAGfoto = "image";
    public static String TAGtalamat = "alamat";
    public static String TAGstatus = "status";
    Server con = new Server();
    private ProgressDialog pDialog;
    String url_cari = "http://commit.paiton.biz/nurja/tampil_tanggapan.php";
    ArrayList<HashMap<String, String>> tampil = new ArrayList<HashMap<String, String>>();
    String pencarian;
    ListView lv;
    String nis2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tanggapan);
        bacaPreferensi();
        telpon = (TextView) findViewById(R.id.tv_list_jalan_telepon);
        telpon.setText(nis2);
        lv = (ListView) findViewById(R.id.list_view_jalan);
        new cari().execute();
    }

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        nis2 = pref.getString("nis", "0");

    }


    class cari extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(Tanggapan.this);
            progressDialog.setMessage("Mohon Tungu...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAGnomer_telpon, nis2));
            JSONObject json = jsonParser.makeHttpRequest(url_cari, "GET", params);

            try {
                string_json = json.getJSONArray("Hasil");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tampil.clear();
                        try {
                            for (int i = 0; i < string_json.length(); i++) {
                                JSONObject object = string_json.getJSONObject(i);
                                map = new HashMap<String, String>();
                                map.put(TAGkeluhan, object.getString(TAGkeluhan));
                                map.put(TAGfoto, object.getString(TAGfoto));
                                map.put(TAGtalamat, object.getString(TAGtalamat));
                                map.put(TAGstatus, object.getString(TAGstatus));
                                tampil.add(map);
                            }

                        } catch (JSONException e) {
                            Log.d("Create Response", e.toString());
                        }
                    }
                });
            } catch (
                    JSONException e
                    )

            {
                e.printStackTrace();
            }

            return null;
        }

        //proses pencarian
        @Override
        protected void onPostExecute(String s) {

            progressDialog.dismiss();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SetListViewAdapter(tampil);
                }
            });

        }
    }

    private void SetListViewAdapter(ArrayList<HashMap<String, String>> tampil) {
        adapter = new Customlaporan(Tanggapan.this, tampil);
        lv.setAdapter(adapter);
    }
}
