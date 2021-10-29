package com.example.encryption;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.view.View;
import android.widget.*;
import java.io.*;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import androidx.appcompat.app.AppCompatActivity;

import static android.view.View.*;

public class TextPage extends AppCompatActivity {
    EditText etv;
    Button gnder,dal;
    TextView sfrgln,dosyadim,tv1;
    String path = Environment.getExternalStorageDirectory() + "/Encryption/Metinler/";
    String outputString;
    String AES="AES";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_text);
        dal=findViewById(R.id.dosyadanoku);
        etv=findViewById(R.id.tutacak);
        tv1=findViewById(R.id.alinacak);
        gnder=findViewById(R.id.kaydet);
        sfrgln=findViewById(R.id.sfralt);
        dosyadim=findViewById(R.id.dosyadim);

        Bundle bundle=getIntent().getExtras();
        if (bundle != null) {
            String sfrall=bundle.getString("sifregonder");
            sfrgln.setText("Veri Şifresi: "+sfrall);
        }
        // Create the parent path
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

      gnder.setOnClickListener(v -> {
          if(dosyadim.getText().toString().equals("")){
              Toast.makeText(TextPage.this, "Dosya İsmini Giriniz!!!", Toast.LENGTH_SHORT).show();
          }
        else {
              try {
                  FileOutputStream txtf = new FileOutputStream(path + dosyadim.getText().toString() + ".txt");

                  outputString=encrypt(etv.getText().toString(),sfrgln.getText().toString());
                  tv1.setText(outputString);
                  String mesaj = outputString;
                  txtf.write(mesaj.getBytes());
                  Toast.makeText(TextPage.this, "Dosyaya Yazma İşlemi Başarılı.", Toast.LENGTH_SHORT).show();
                  txtf.close();
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }
      });
        dal.setOnClickListener(v -> {
            if (dosyadim.getText().toString().equals("")){
                Toast.makeText(TextPage.this, "Dosya Adını Giriniz!!!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                try{
                    FileInputStream txtis= new FileInputStream(path+dosyadim.getText().toString()+".txt");
                    InputStreamReader isr= new InputStreamReader(txtis);
                    BufferedReader br=new BufferedReader(isr);
                    outputString= decrypt(br.readLine(),sfrgln.getText().toString());
                    tv1.setText(outputString);
                    Toast.makeText(TextPage.this, "Dosyadan Okuma İşlemi Başarılı.", Toast.LENGTH_SHORT).show();
                    txtis.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
        });
    }

    private String decrypt(String readLine, String password) throws Exception {
        SecretKeySpec key=(generateKey(password));
        Cipher c=Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        byte[] decodeValue=Base64.decode(readLine,Base64.DEFAULT);
        byte[] dcValue=c.doFinal(decodeValue);
        String decryptValue=new String(dcValue);
        return decryptValue;
    }

    private String encrypt(String Data, String password) throws Exception {
        SecretKeySpec key=(generateKey(password));
        Cipher c=Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal=c.doFinal(Data.getBytes());
        String ecryptionValue= Base64.encodeToString(encVal,Base64.DEFAULT);
        return ecryptionValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception {
        final MessageDigest digest=MessageDigest.getInstance("SHA-256");
        byte[] bytes=password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key= digest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(key,AES);
        return secretKeySpec;
    }

}





