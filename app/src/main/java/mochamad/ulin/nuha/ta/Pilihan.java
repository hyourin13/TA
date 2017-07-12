package mochamad.ulin.nuha.ta;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class Pilihan extends AppCompatActivity {
    Button profil,foto,lokasi,data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihan);
        profil = (Button) findViewById(R.id.btn_lihat_profil);
        foto = (Button) findViewById(R.id.btn_ubah_foto);
        lokasi = (Button) findViewById(R.id.btn_ubah_lokasi);
        data = (Button) findViewById(R.id.btn_ubah_data);

    }
}
