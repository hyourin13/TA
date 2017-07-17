package mochamad.ulin.nuha.ta;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.location.DetectedActivity;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

public class Pilihan extends SwipeBackActivity implements View.OnClickListener {
    LinearLayout btn_profil,btn_foto,btn_lokasi,btn_data;
    String no_hp,nis_pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihan);
        initViews();
        setDragEdge(SwipeBackLayout.DragEdge.TOP);
        bacaPreferensi();
    }

    private void initViews() {
        btn_profil = (LinearLayout) findViewById(R.id.btn_lht_profilll);
        btn_profil.setOnClickListener(this);

        btn_foto = (LinearLayout) findViewById(R.id.btn_ubah_foto);
        btn_foto.setOnClickListener(this);

        btn_lokasi = (LinearLayout) findViewById(R.id.btn_ubah_lokasi);
        btn_lokasi.setOnClickListener(this);

        btn_data = (LinearLayout) findViewById(R.id.btn_ubah_data);
        btn_data.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_lht_profilll:
                Intent i = new Intent(Pilihan.this, Detail.class);
                i.putExtra("NIS", nis_pref);
                startActivity(i);
                break;
            case R.id.btn_ubah_foto:
                skipActivity(Upload.class);
                break;
            case R.id.btn_ubah_lokasi:
                skipActivity(Daftar.class);
                break;
            case R.id.btn_ubah_data:
                skipActivity(ScrollingActivity.class);

        }
    }

    private void skipActivity(Class<?> classOf) {
        Intent intent = new Intent(getApplicationContext(), classOf);
        startActivity(intent);
    }

    private void bacaPreferensi() {
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        no_hp = pref.getString("no_hp", "0");
        nis_pref = pref.getString("nis", "0");
    }
}