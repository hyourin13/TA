package mochamad.ulin.nuha.ta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kuncoro on 29/02/2016.
 */
public class DetailNews extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    NetworkImageView thumb_image;
    TextView judul,pengarang,edisi,penerbit,fisik,subject;
    WebView catatan;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    SwipeRefreshLayout swipe;
    String id_news;

    private static final String TAG = DetailNews.class.getSimpleName();

    public static final String TAG_ID 		    = "id";
    public static final String TAG_JUDUL 	    = "judul";
    public static final String TAG_PENGARANG 	= "pengarang";
    public static final String TAG_ISI 		= "isi";
    public static final String TAG_GAMBAR	= "gambar";
    public static final String TAG_EDISI	= "edisi";
    public static final String TAG_PENERBIT	= "penerbit";
    public static final String TAG_SUBJECT	= "subject";
    public static final String TAG_FISIK	= "fisik";

    private static final String url_detail 	= Server.URL + "detail_news.php";
    String tag_json_obj = "json_obj_req";
    String nis2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_news);

        //thumb_image = (NetworkImageView) findViewById(R.id.gambar_news);
       /* judul 		= (TextView) findViewById(R.id.judul_news);
        pengarang 		= (TextView) findViewById(R.id.txtpengarang);
        catatan 		= (WebView) findViewById(R.id.txtcatatan);
        edisi 		= (TextView) findViewById(R.id.txtedisi);
        penerbit 		= (TextView) findViewById(R.id.txtterbit);
        subject 		= (TextView) findViewById(R.id.txtsubjek);
        fisik 		= (TextView) findViewById(R.id.txtdeskripsifisik);*/

       // swipe       = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nis2 = bundle.getString("NIS");
        Toast.makeText(this, nis2, Toast.LENGTH_SHORT).show();

       /* swipe.setOnRefreshListener(this);

        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           if (!id_news.isEmpty()) {
                               callDetailNews(id_news);
                           }
                       }
                   }
        );*/

    }

    private void callDetailNews(final String id){
        swipe.setRefreshing(true);

        StringRequest strReq = new StringRequest(Request.Method.POST, url_detail, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response " + response.toString());
                swipe.setRefreshing(false);

                try {
                    JSONObject obj = new JSONObject(response);

                    judul.setText(obj.getString(TAG_JUDUL));
                    pengarang.setText(obj.getString(TAG_PENGARANG));
                    edisi.setText(obj.getString(TAG_EDISI));
                    penerbit.setText(obj.getString(TAG_PENERBIT));
                    fisik.setText(obj.getString(TAG_FISIK));
                    subject.setText(obj.getString(TAG_SUBJECT));

                    catatan.loadData("<p style=\"color:black;font-size:12px;text-align: justify\">"+obj.getString(TAG_ISI)+"</p>","text/html","utf-8");

                    if (obj.getString(TAG_GAMBAR)!=""){
                        thumb_image.setImageUrl(obj.getString(TAG_GAMBAR), imageLoader);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Detail News Error: " + error.getMessage());
                Toast.makeText(DetailNews.this,
                        error.getMessage(), Toast.LENGTH_LONG).show();
                swipe.setRefreshing(false);
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRefresh() {
        callDetailNews(id_news);
    }
}