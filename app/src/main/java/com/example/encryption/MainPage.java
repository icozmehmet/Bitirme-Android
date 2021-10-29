package com.example.encryption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPage extends AppCompatActivity {
Button changed ,newmwmber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_main);
        changed = findViewById(R.id.camera);
        changed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent transition = new Intent(MainPage.this, EnterPage.class);
                startActivity(transition);
            }
        });
        newmwmber= findViewById(R.id.Login);
        newmwmber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transition1 = new Intent(MainPage.this, NewPage.class);
                startActivity(transition1);
            }
        });
    }
}



