package mochamad.ulin.nuha.ta;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_Masukan extends SwipeBackActivity {
    String nis, masukan,nis2;
    EditText ednis,ednama;
    ProgressDialog pDialog;
    int success;
    private static final String TAG_SUCCESS = "success";
    JSONParser jsonParser = new JSONParser();
    Server con = new Server();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__masukan);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
         ednis = (EditText) findViewById(R.id.massukan_nis);
         ednama = (EditText) findViewById(R.id.masukan_masuk);
        bacaPreferensi();
        ednis.setText(nis2);
        Button btn_kirimm = (Button) findViewById(R.id.btn_masuk);
        btn_kirimm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nis = ednis.getText().toString();
                masukan = ednama.getText().toString();
                new semu().execute();
            }
        });
    }


    class semu extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_Masukan.this);
            pDialog.setMessage("Loading....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... arg0) {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("nis", nis));
            params.add(new BasicNameValuePair("masuk", masukan));
            System.out.println(params);

            // Melakukan Proses Request HTTP Post dengan Parameter yang ada
            JSONObject json = jsonParser.makeHttpRequest(con.URL + "input_alumni_masukan.php", "POST",
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
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Activity_Masukan.this);
                alertDialog.setTitle("Sukses");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Berhasil Mengirim Masukan");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        ednama.setText(null);
                        ednis.setText(null);
                    }
                });
                alertDialog.show();

            }else {
                Toast.makeText(Activity_Masukan.this, "Data Belum Lengkap", Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();
        }
    }

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);

        nis2 = pref.getString("nis", "0");

    }


}
