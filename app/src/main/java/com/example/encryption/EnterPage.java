package com.example.encryption;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.encryption.R.id.mail1;
import static com.example.encryption.R.id.sifre1;

public class EnterPage extends AppCompatActivity {
    Button login;
    EditText mail2;
    EditText sifre2;
    String mail;
    String sifre;
    String textm,texts;
    vt v1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_enter);
        mail2= findViewById(mail1);
        sifre2=findViewById(sifre1);
        v1=new vt(this);
        SQLiteDatabase db =v1.getReadableDatabase();
        Cursor goster=db.query(
                "Bilgiler",
                new String[]{"*"},
                null,null,null,null,null);
        goster.moveToNext();
        mail=goster.getString(goster.getColumnIndex("mail"));
        sifre=goster.getString(goster.getColumnIndex("sifre"));
        db.close();
/*
        mail="mehmet";
        sifre="1";
*/
        login=(Button)findViewById(R.id.Login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textm=mail2.getText().toString();
                texts=sifre2.getText().toString();
                if (textm.equals(mail)&&texts.equals(sifre)) {
                    Intent transition = new Intent(EnterPage.this, LoginPage.class);
                    startActivity(transition);
                }
                else {
                    Toast.makeText(EnterPage.this, "Hatalı Şifre yada Email!!!", Toast.LENGTH_SHORT).show();
                }
            }
      });
    }


}
