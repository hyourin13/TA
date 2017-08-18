package mochamad.ulin.nuha.ta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Pilihan_Z extends AppCompatActivity {
    Button btnz1,btnz2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihan__z);
        btnz1 = (Button) findViewById(R.id.btn_daftar_nis);
        btnz2 = (Button) findViewById(R.id.btn_daftar_hp);
        btnz1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coba = new Intent(Pilihan_Z.this, Cek_nis.class);
                startActivity(coba);
            }
        });

        btnz2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cobak = new Intent(Pilihan_Z.this, Verifikasi_satu.class);
                startActivity(cobak);
            }
        });


    }
}
