package com.example.encryption;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
public class NewPage extends AppCompatActivity {
    Button bnew;
    EditText isim,mail,sifre,verset;
    private vt v1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_new);
        v1 = new vt(this);
        SQLiteDatabase db=v1.getReadableDatabase();
        Cursor sonuc = db.query("Bilgiler",new String[]{"*"},null,null,null,null,null);
        if (sonuc.getCount() > 0)
        {
            Toast.makeText(NewPage.this, "Zaten kayıtlısın", Toast.LENGTH_SHORT).show();
            return;
        }
        db.close();
        isim = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        sifre = findViewById(R.id.password);
        verset=findViewById(R.id.encryptet);
        bnew = findViewById(R.id.Login);
            bnew.setOnClickListener(v -> {
                if (isim.getText().toString().isEmpty()|| sifre.getText().toString().isEmpty() || mail.getText().toString().isEmpty()||
                        verset.getText().toString().isEmpty()){
                    Toast.makeText(NewPage.this, "BOSLUKLARI DOLDURUN!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mail.getText().toString().contains("@")&& mail.getText().toString().contains(".com")) {
                    if (sifre.getText().toString().length() < 7)
                        Toast.makeText(this, "Şifre 7 Karakterden Kücük Olamaz!!!", Toast.LENGTH_SHORT).show();
                    else {
                        try {
                            kytekle(isim.getText().toString(), mail.getText().toString(), sifre.getText().toString(), verset.getText().toString());
                            Toast.makeText(NewPage.this, "KAYİT İŞLEMİ BAŞARILI!!!", Toast.LENGTH_SHORT).show();
                            Intent trans = new Intent(NewPage.this, LoginPage.class);
                            startActivity(trans);
                        } finally {
                            v1.close();
                        }
                    }
                }
                else
                    Toast.makeText(this, "Geçerli Bir Mail adresi Girinz!!!", Toast.LENGTH_SHORT).show();

                });
    }
    private void kytekle(String isims,String mails,String sifres,String veris){
        SQLiteDatabase db= v1.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("isim",isims);
        cv.put("mail",mails);
        cv.put("sifre",sifres);
        cv.put("encrypion",veris);
        db.insertOrThrow("Bilgiler",null,cv);
        db.close();
    }
}
