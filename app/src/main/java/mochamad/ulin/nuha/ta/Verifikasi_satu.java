package mochamad.ulin.nuha.ta;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

public class Verifikasi_satu extends AppCompatActivity {
    CheckBox ck1,ck2,ck3,ck4,ck5,ck6,ck7,ck8,ck9;
    ImageView img1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi1);
        ck1 = (CheckBox) findViewById(R.id.ck_v1_g1);
        ck2 = (CheckBox) findViewById(R.id.ck_v1_g2);
        ck3 = (CheckBox) findViewById(R.id.ck_v1_g3);
        ck4 = (CheckBox) findViewById(R.id.ck_v1_g4);
        ck5 = (CheckBox) findViewById(R.id.ck_v1_g5);
        ck6 = (CheckBox) findViewById(R.id.ck_v1_g6);
        ck7 = (CheckBox) findViewById(R.id.ck_v1_g7);
        ck8 = (CheckBox) findViewById(R.id.ck_v1_g8);
        ck9 = (CheckBox) findViewById(R.id.ck_v1_g9);

        img1= (ImageView) findViewById(R.id.imageView5);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ck9.isChecked()){
                    if (ck8.isChecked()){
                        if (ck5.isChecked()){
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Verifikasi_satu.this);
                            alertDialog.setTitle("Sukses");
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage("Silahkan Lanjut Ke Tahap Registrasi");
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    startActivity(new Intent(Verifikasi_satu.this,ScrollingActivity_HP.class));

                                }
                            });
                            alertDialog.show();

                        }else{
                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Verifikasi_satu.this);
                            alertDialog.setTitle("Gagal");
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage("Coba Lagi ");
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    startActivity(new Intent(Verifikasi_satu.this,DrowRanger.class));

                                }
                            });
                            alertDialog.show();

                        }
                    }else{
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Verifikasi_satu.this);
                        alertDialog.setTitle("Gagal");
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage("Coba Lagi ");
                        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                startActivity(new Intent(Verifikasi_satu.this,DrowRanger.class));

                            }
                        });
                        alertDialog.show();
                    }
                }else {
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Verifikasi_satu.this);
                    alertDialog.setTitle("Gagal");
                    alertDialog.setCancelable(false);
                    alertDialog.setMessage("Coba Lagi ");
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            startActivity(new Intent(Verifikasi_satu.this,DrowRanger.class));

                        }
                    });
                    alertDialog.show();
                }

            }
        });




    }
}
