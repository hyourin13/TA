package mochamad.ulin.nuha.ta;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by GOBLIN on 12/30/2016.
 */

public class tampildonasi extends Fragment {
    Server con = new Server();
    String url_tampil = con.URL + "tampil_donasi.php", id;
    ListView liv;
    HashMap<String, String> map;
    ProgressDialog progressDialog;
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> tampil = new ArrayList<HashMap<String, String>>();
    public static String TAGid_minta = "id_minta";
    public static String TAGnama = "nama";
    public static String TAGlat = "lat";
    public static String TAGlng = "lng";
    public static String TAGjudul_buku = "tema";
    public static String TAGdes = "ket";
    public static String TAGtgl = "tgl";
    JSONArray string_json = null;
    Lazynotif adapter;
    FloatingActionButton btadd;
    int success;
    private static final String TAG_SUCCESS = "success";
    TextView txtnama, txtjudul, txttanggal, txtid_minta, txt_lat, txt_lng, txt_des;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.notif, container, false);

        return view;
    }


    class ambiltampil extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Mohon Tunggu...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject json = jsonParser.makeHttpRequest(url_tampil, "POST", params);

            try {
                success = json.getInt(TAG_SUCCESS);
                tampil.clear();
                if (success == 1) {
                    string_json = json.getJSONArray("Hasil");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                for (int i = 0; i < string_json.length(); i++) {
                                    JSONObject object = string_json.getJSONObject(i);

                                    // Menampung hasil Object pada Variabel

                                    map = new HashMap<String, String>();
                                    map.put(TAGid_minta, object.getString(TAGid_minta));
                                    map.put(TAGnama, object.getString(TAGnama));
                                    map.put(TAGlat, object.getString(TAGlat));
                                    map.put(TAGlng, object.getString(TAGlng));
                                    map.put(TAGjudul_buku, object.getString(TAGjudul_buku));
                                    map.put(TAGdes, object.getString(TAGdes));
                                    map.put(TAGtgl, object.getString(TAGtgl));


                                    tampil.add(map);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
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
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }

}
