package com.example.encryption;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import androidx.appcompat.app.AppCompatActivity;
public class LoginPage extends AppCompatActivity {
    Button sesac ,text1,camera,proil,passn;
    EditText sifre;
    private vt vt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);
        sifre = findViewById(R.id.sifreleme);
        passn = findViewById(R.id.newp);
        camera = findViewById(R.id.camera);
        sesac = findViewById(R.id.bytcevir);
        text1 = findViewById(R.id.text);
        proil = findViewById(R.id.profil);
        vt1 = new vt(this);
        String path = Environment.getExternalStorageDirectory() + "/Encryption/";

        // Create the parent path
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Cursor cursor=kytgetr();
        göster(cursor);
        passn.setOnClickListener(v -> {
            guncelle(sifre.getText().toString());
            Toast.makeText(this, "Şifreniz Güncellendi", Toast.LENGTH_SHORT).show();
        });
        camera.setOnClickListener(v -> {
            Intent transition = new Intent(LoginPage.this, CameraPage.class);
            transition.putExtra("sifregonder",sifre.getText().toString());
            startActivity(transition);
        });

        sesac.setOnClickListener(v -> {
            Intent transition = new Intent(LoginPage.this, VoicePage.class);
            transition.putExtra("sifregonder",sifre.getText().toString());
            startActivity(transition);
        });
        text1.setOnClickListener(v -> {
            Intent transition = new Intent(LoginPage.this, TextPage.class);
            transition.putExtra("sifregonder",sifre.getText().toString());
            startActivity(transition);
        });

        proil.setOnClickListener(v -> {
            Intent transition = new Intent(LoginPage.this, ProfilPage.class);
            startActivity(transition);
        });
    }
    private Cursor kytgetr(){
        SQLiteDatabase db=vt1.getReadableDatabase();
        Cursor okunana=db.query("Bilgiler", new String[]{"encrypion"},null,null,null,null,null,null);
        return okunana;
    }
    private  void göster(Cursor pnew){
        pnew.moveToNext();
        String pasn=pnew.getString(pnew.getColumnIndex("encrypion"));
        sifre.setText(pasn);
    }

    private void guncelle(String pnew) {
        SQLiteDatabase db=vt1.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("encrypion",pnew);
        Log.d("Yni Sifre",pnew);
        db.update("Bilgiler",cv,null,null);
        db.close();

    }

}