package mochamad.ulin.nuha.ta;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryu on 23/05/2017.
 */

public class Signin extends AppCompatActivity implements View.OnClickListener {
    TextView daftar,masuk;
    EditText hp;
    Server con = new Server();
    private ProgressDialog pDialog;
    int success;
    private static final String TAG_SUCCESS = "success";
    JSONParser jsonParser = new JSONParser();
    String no_hp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.awal);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),"font/Lato-Medium.ttf");
        daftar = (TextView) findViewById(R.id.Create2);
        masuk = (TextView) findViewById(R.id.signin);
        hp = (EditText) findViewById(R.id.nomorr);
        daftar.setTypeface(custom_font);
        masuk.setTypeface(custom_font);
        hp.setTypeface(custom_font);
        daftar.setOnClickListener(this);
        masuk.setOnClickListener(this);
        bacaPreferensi();
        Toast.makeText(this, no_hp, Toast.LENGTH_SHORT).show();
        if (no_hp.toString().equals("0")){

        }else {
            Intent i = new Intent(Signin.this, Menu_utama.class);
            startActivity(i);
            finish();
        }
       // Toast.makeText(this, no_hp, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Create2:
                Intent pindah = new Intent(this, Login.class);
                startActivity(pindah);
                break;
            case R.id.signin:
                no_hp = hp.getText().toString();
                if (no_hp.equalsIgnoreCase("")){
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Signin.this);
                    alertDialog.setTitle("Gagal");
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage("Anda Belum Memasukkan Nomer");
                    hp.setText("");
                }else{
                    new semu().execute();
                }
                break;
        }
    }

    class semu extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Signin.this);
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
            params.add(new BasicNameValuePair("no_hp", no_hp));
            System.out.println(params);

            // Melakukan Proses Request HTTP Post dengan Parameter yang ada
            JSONObject json = jsonParser.makeHttpRequest(con.URL + "view_cari_alumni.php", "POST",
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

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Signin.this);
                alertDialog.setTitle("Sukses");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Anda Berhasil Login");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        startActivity(new Intent(Signin.this,Menu_utama.class));
                        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("no_hp", no_hp);
                        editor.commit();
                        finish();

                    }
                });


                alertDialog.show();
            }else {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Signin.this);
                alertDialog.setTitle("Gagal");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Nomer Anda Belum Terdaftar");
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        startActivity(new Intent(Signin.this,ErrorDaftar.class));
                        finish();

                    }
                });
                alertDialog.show();
            }
            pDialog.dismiss();
        }
    }


    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        no_hp = pref.getString("no_hp", "0");
    }


}
