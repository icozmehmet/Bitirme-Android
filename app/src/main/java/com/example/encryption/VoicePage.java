package com.example.encryption;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import androidx.appcompat.app.AppCompatActivity;
public class VoicePage extends AppCompatActivity {
    MediaRecorder mRecorder;
    MediaPlayer mplayer;
    String cikisdosyasi=null;
    TextView sfrall,bytesss;
    EditText dosyaadim;
    Button byts;
    String path = Environment.getExternalStorageDirectory() + "/Encryption/Sesler/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_voice);
        byts=findViewById(R.id.bytss);
        sfrall=findViewById(R.id.sfralv);
        bytesss=findViewById(R.id.bytes);
            Bundle bundle=getIntent().getExtras();
        if (bundle != null) {
            String sfreeal=bundle.getString("sifregonder");
            sfrall.setText("Veri Şifresi: "+sfreeal);
        }
            // Create the parent path
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        dosyaadim=findViewById(R.id.dosyaad);
        mRecorder=new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        byts.setOnClickListener(v -> {
            int bytesReader;
            try{
                    InputStream is= new FileInputStream(cikisdosyasi);
                ByteArrayOutputStream bos=new ByteArrayOutputStream();
                byte[] b=new byte[1024];
                while ((bytesReader=is.read(b))!=-1){
                        bos.write(b,0,bytesReader);
                }
                byte[] bytss=bos.toByteArray();
                bytesss.setText(String.valueOf(bytss.length));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public void btnkayitbaslat(View v){
        try {
            cikisdosyasi=path+dosyaadim.getText().toString()+".3gp";
            mRecorder.setOutputFile(cikisdosyasi);
            mRecorder.prepare();
            mRecorder.start();
            Toast.makeText(this, "Kayıt Başlıyor...", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void btnkayitdurdur(View v){
            mRecorder.stop();
        Toast.makeText(this, "Kayıt Durdurluyor...", Toast.LENGTH_SHORT).show();
    }
    public void btnoynat(View v){
        mplayer=new MediaPlayer();
        try {
            mplayer.setDataSource(cikisdosyasi);
            mplayer.prepare();
            mplayer.start();
            Toast.makeText(this, "Kayıt Oynatılıyor...", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void btndurdur(View v){
        if(mplayer!=null){
            mplayer.stop();
            mplayer.release();
            mplayer = null;
            Toast.makeText(this, "Kayıt Oynatma Durduruluyor...", Toast.LENGTH_SHORT).show();
        }
    }
}



