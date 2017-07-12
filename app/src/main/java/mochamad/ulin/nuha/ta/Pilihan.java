package mochamad.ulin.nuha.ta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class Pilihan extends AppCompatActivity implements View.OnClickListener {
    LinearLayout btn_profil,btn_foto,btn_lokasi,btn_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihan);
        initViews();
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
}
