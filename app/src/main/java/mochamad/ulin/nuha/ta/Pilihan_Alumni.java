package mochamad.ulin.nuha.ta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Pilihan_Alumni extends AppCompatActivity {
     Button btn1,btn2,btn3;
    EditText edt1,edt2;
    String nama, wilayah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihan__alumni);
        btn1 =  (Button) findViewById(R.id.btn_A_cari_semua); //lihat semua
        btn2 =  (Button) findViewById(R.id.btn_A_cari_wilayah); //cari wilayah
        btn3 =  (Button) findViewById(R.id.btn_A_cari_nama); //cari nama
        edt1 = (EditText) findViewById(R.id.cari_A_nama); // cari nama
        edt2 = (EditText) findViewById(R.id.cari_A_wilayah); //cari wilayah


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent coba  = new Intent(Pilihan_Alumni.this, Menu_Alumni.class);
                startActivity(coba);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = edt1.getText().toString();
                //Toast.makeText(Pilihan_Alumni.this, nama, Toast.LENGTH_SHORT).show();
                Intent coba  = new Intent(Pilihan_Alumni.this, Menu_Alumni_Nama.class);
                coba.putExtra("NAMA", nama);
                startActivity(coba);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wilayah = edt2.getText().toString();
                Intent coba  = new Intent(Pilihan_Alumni.this, Menu_Alumni_Lokasi.class);
                coba.putExtra("WILAYAH", wilayah);
                startActivity(coba);
            }
        });



    }
}
