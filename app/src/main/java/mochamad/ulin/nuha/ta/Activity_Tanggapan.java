package mochamad.ulin.nuha.ta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madchen on 9/20/2017.
 */

public class Activity_Tanggapan extends AppCompatActivity {

    String Simpaan;
    List<subjects> subjectsList;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    ProgressBar progressBar;

    String HTTP_JSON_URL = "http://commit.paiton.biz/nurja/tampil_tanggap.php";

    String GET_JSON_FROM_SERVER_NAME = "klasifikasi";
    String GET_JSON_FROM_SERVER_ID   = "id_tanggapan";
    String GET_JSON_FROM_SERVER_NAMA = "nama";
    String GET_JSON_FROM_SERVER_TGL = "waktu_tanggap";

    JsonArrayRequest jsonArrayRequest ;

    RequestQueue requestQueue ;

    View ChildView ;

    int GetItemPosition ;

    ArrayList<String> SubjectNames;
    ArrayList<String> SubjectID;
    ArrayList<String> SubjectJeneng;
    ArrayList<String> Subjecttgl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tanggap);

        subjectsList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView1);

        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);

        progressBar.setVisibility(View.VISIBLE);

        SubjectNames = new ArrayList<>();
        SubjectID = new ArrayList<>();
        SubjectJeneng = new ArrayList<>();
        Subjecttgl = new ArrayList<>();


        JSON_DATA_WEB_CALL();

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(Activity_Tanggapan.this, new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent motionEvent) {

                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

                ChildView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if(ChildView != null && gestureDetector.onTouchEvent(motionEvent)) {

                    GetItemPosition = Recyclerview.getChildAdapterPosition(ChildView);
                    Simpaan = SubjectID.get(GetItemPosition);
                    Toast.makeText(Activity_Tanggapan.this, Simpaan, Toast.LENGTH_LONG).show();

                    Intent i = new Intent(Activity_Tanggapan.this, Detail_Tangggap.class);
                    i.putExtra("NIS", Simpaan);
                    startActivity(i);
                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(HTTP_JSON_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        progressBar.setVisibility(View.GONE);

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        for(int i = 0; i<array.length(); i++) {

            subjects GetDataAdapter2 = new subjects();

            JSONObject json = null;
            try {
                json = array.getJSONObject(i);

                GetDataAdapter2.setName(json.getString(GET_JSON_FROM_SERVER_NAME));
                GetDataAdapter2.setID(json.getString(GET_JSON_FROM_SERVER_ID));
                GetDataAdapter2.setJeneng(json.getString(GET_JSON_FROM_SERVER_NAMA));
                GetDataAdapter2.settgl(json.getString(GET_JSON_FROM_SERVER_TGL));

                SubjectNames.add(json.getString(GET_JSON_FROM_SERVER_NAME));
                SubjectID.add(json.getString(GET_JSON_FROM_SERVER_ID));
                SubjectJeneng.add(json.getString(GET_JSON_FROM_SERVER_NAMA));
                Subjecttgl.add(json.getString(GET_JSON_FROM_SERVER_TGL));


            } catch (JSONException e) {

                e.printStackTrace();
            }
            subjectsList.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewCardViewAdapter(subjectsList, this);

        recyclerView.setAdapter(recyclerViewadapter);

    }
}