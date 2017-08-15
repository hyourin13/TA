package mochamad.ulin.nuha.ta;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ryu on 12/30/2016.
 */

public class tampilnotif extends AppCompatActivity{
    View v;
    Server kon = new Server();
    String url_tampil = kon.URL + "tampilnotif.php";
    ListView liv;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> mhs = new ArrayList<HashMap<String, String>>();
    ProgressDialog progressDialog;
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> tampil = new ArrayList<HashMap<String, String>>();
    public static String TAGtgl = "tgl";
    public static String TAGnotif = "notifikasi";
    String id;
    JSONArray string_json = null;
    Lazynotif adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notif);
        liv = (ListView) findViewById(R.id.listView);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = connMgr.getActiveNetworkInfo();
        if (networkinfo != null && networkinfo.isConnected()) {
            //aksi ketika ada koneksi internet
            new ambiltampil().execute();
        } else {
            //aksi ketika tidak ada koneksi internet
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("KONEKSI");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Maaf ada masalah dengan koneksi, coba periksa koneksi internet anda.");
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
    public void SetListViewAdapter(ArrayList<HashMap<String, String>> berita) {
        adapter = new Lazynotif(tampilnotif.this, berita);
        liv.setAdapter(adapter);
    }

    class ambiltampil extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(tampilnotif.this);
            progressDialog.setMessage("Mohon Tungu...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jsonParser.makeHttpRequest(url_tampil, "POST", params);
            try {
                string_json = json.getJSONArray("Hasil");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tampil.clear();
                            for (int i = 0; i < string_json.length(); i++) {
                                JSONObject object = string_json.getJSONObject(i);

                                // Menampung hasil Object pada Variabel
                                String tgl = object.getString(TAGtgl);
                                String notif = object.getString(TAGnotif);
                                map = new HashMap<String, String>();
                                map.put(TAGtgl, tgl);
                                map.put(TAGnotif, notif);
                                tampil.add(map);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
}
