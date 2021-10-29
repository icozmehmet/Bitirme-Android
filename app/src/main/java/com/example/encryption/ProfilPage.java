package com.example.encryption;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class ProfilPage extends AppCompatActivity {
    private vt v1;
    EditText netv ,metv,setv;
    TextView gstr;
    Button ud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_profil);
        v1 = new vt(this);
        netv=findViewById(R.id.nameetv);
        metv=findViewById(R.id.mailetv);
        setv=findViewById(R.id.sifreetv);
        ud=findViewById(R.id.update);
        gstr=findViewById(R.id.goster);
        gstr.setMovementMethod(new ScrollingMovementMethod());

        Cursor cursor=kaytgetir();
        kytgoster(cursor);
        ud.setOnClickListener((View v) -> {
            if (metv.getText().toString().contains("@")&&metv.getText().toString().contains(".com")) {
                if(setv.getText().toString().length()<7)
                    Toast.makeText(this, "Şifre 7 Karakterden Küçük Olamaz!!!", Toast.LENGTH_SHORT).show();
                else {
                    guncelle(netv.getText().toString(), metv.getText().toString(), setv.getText().toString());
                    Toast.makeText(this, "Güncelleme Başarılı!!!", Toast.LENGTH_SHORT).show();
                }
            }
            else
                Toast.makeText(this, "Geçerli Bir Mail Adresi Giriniz!!!", Toast.LENGTH_SHORT).show();
        });
    }

    public void guncelle(String isim ,String mail,String sifre){
        SQLiteDatabase db=v1.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("isim",isim);
        cv.put("mail",mail);
        cv.put("sifre",sifre);
        db.update("Bilgiler",cv,null,null);
        db.close();
        kytgoster(kaytgetir());
    }
    private final String[] sutunlar={"*" };
    private Cursor kaytgetir(){
        SQLiteDatabase db =v1.getReadableDatabase();
        return db.query("Bilgiler",sutunlar,null,null,null,null,null);
    }
    private void kytgoster(Cursor goster){
        StringBuilder builder=new StringBuilder();
        while (goster.moveToNext()){
            String isimm=goster.getString(goster.getColumnIndex("isim"));
            String maill=goster.getString(goster.getColumnIndex("mail"));
            String sifree=goster.getString(goster.getColumnIndex("sifre"));
            String veris=goster.getString(goster.getColumnIndex("encrypion"));
            builder.append("isim: ").append(isimm +"\n");
            builder.append("mail: ").append(maill +"\n");
            builder.append("sifre: ").append(sifree +"\n");
            builder.append("Veri Şifresi: ").append(veris+"\n");
            builder.append("----------------------------").append("\n");
        }
       TextView text= findViewById(R.id.goster);
        text.setText(builder);
    }
}



