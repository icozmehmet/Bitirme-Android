package com.example.encryption;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.*;
import java.io.*;
import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class CameraPage extends AppCompatActivity {
    ImageView iv,iv2;
    Button bytcevir,dosyaoku;
    TextView sfral;
    EditText dosyam;
    byte[] bytes;
    String path = Environment.getExternalStorageDirectory() + "/Encryption/Resimler/";
    String AES="AES/CBC/PKCS5Padding";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;
        Bundle b;
        b = data.getExtras();
        Bitmap bm=(Bitmap)b.get("data");
        iv.setImageBitmap(bm);
        convertByteArray(bm);
    }

   public void convertByteArray(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,60,byteArrayOutputStream);
        bytes=byteArrayOutputStream.toByteArray();
        bitmap.recycle();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_camera);
        dosyam=findViewById(R.id.dosyaadi);
        sfral=findViewById(R.id.sifrealc);
        iv2 = findViewById(R.id.imageView4);
        iv= findViewById(R.id.imageView);
        Bundle bundle =getIntent().getExtras();
        if (bundle != null) {
            String sfreeal=bundle.getString("sifregonder");
            sfral.setText("Veri Şifresi: "+sfreeal);
        }
        Button camera= findViewById(R.id.camera);
        // Create the parent path
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        camera.setOnClickListener(v -> {
            if(quitUnless(dosyam.getText().toString().equals(""),"Dosya İsmini Giriniz!!!!"))
                return;
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, 1);

        });
         bytcevir = findViewById(R.id.bytcevir);
         bytcevir.setOnClickListener(v -> {
             if(quitUnless(dosyam.getText().toString().equals(""),"Dosya İsmini Giriniz!!!!"))
                 return;
             try {
                 final String filename = path + dosyam.getText().toString() + ".png";
                 //Resmi yazdırma
                 FileOutputStream imgf = new FileOutputStream(filename);
                 byte[] output =encrypt(bytes,sfral.getText().toString());
                 Log.w("Şifresiz bytes:","Şifresiz Bytes:"+bytes.length);
                 imgf.write(output);
                 Toast.makeText(CameraPage.this, "Dosyaya Yazma İşlemi Başarılı.", Toast.LENGTH_SHORT).show();
                 imgf.close();
             } catch (Exception e) {
                 e.printStackTrace();
             }

         });
         dosyaoku=findViewById(R.id.dosyaoku);
         dosyaoku.setOnClickListener(v -> {
             if(quitUnless(dosyam.getText().toString().equals(""),"Dosya Adını Giriniz!!!"))
                return;
             try
             {
                     final String filename = path + dosyam.getText().toString() + ".png";
                     //resim okuma
                     File file = new File(filename);

                     FileInputStream fis = new FileInputStream(file);
                     long len = file.length();
                     byte[] data = new byte[(int) len];
                     fis.read(data,0, (int) len);
                     byte[] output =decrypt(data,sfral.getText().toString());
                     Log.d("Byte final","len:"+output.length);
                  /*   Bitmap bitmap = BitmapFactory.decodeByteArray(output,0, output.length);//null dönüyor.
                     Log.d("bit",bitmap.toString());
                     iv2.setImageBitmap(Bitmap.createScaledBitmap(
                             bitmap,
                             iv2.getWidth(),
                             iv2.getHeight(),
                             false));

                   */
                     Toast.makeText(CameraPage.this, "Dosyadan Okuma İşlemi Başarılı.", Toast.LENGTH_SHORT).show();
                     fis.close();
             }
             catch (Exception e) {
                     e.printStackTrace();
             }


         });
    }
    private Boolean quitUnless(Boolean condition, String message){
        if(condition)
            Toast.makeText(CameraPage.this, message, Toast.LENGTH_SHORT).show();
        return condition;

    }

    private byte[] decrypt(byte[] data, String password) throws Exception {
        SecretKeySpec key=(generateKey(password));
        Cipher c=Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE,key);
        //byte[] decodeValue=Base64.decode(readLine,Base64.DEFAULT);
        return c.doFinal(data);

    }
    private byte[] encrypt(byte[] Data, String password) throws Exception {
        SecretKeySpec key=(generateKey(password));
        Cipher c=Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        return c.doFinal(Data);
        //return Base64.encodeToString(encVal,Base64.DEFAULT);

    }

    private SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest=MessageDigest.getInstance("SHA-256");
        byte[] bytes=password.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);
        byte[] key= digest.digest();
        SecretKeySpec secretKeySpec=new SecretKeySpec(key,AES);
        return secretKeySpec;
    }
}





