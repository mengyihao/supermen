package com.example.soundpooldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.btn_SoundPool);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SoundPool.Builder builder = new SoundPool.Builder();
                SoundPool soundPool = builder.build();
                final HashMap<Integer, Integer> soundsId = new HashMap<>();
                int rawId = getResources().getIdentifier(
                        "yx"+1,
                        "raw",
                        getPackageName()
                );
                int soundId = soundPool.load(
                        MainActivity.this,
                        rawId,
                        1
                );
                soundsId.put(1,soundId);
                soundPool.setOnLoadCompleteListener(
                        new SoundPool.OnLoadCompleteListener() {
                            @Override
                            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                                soundPool.play(
                                        soundsId.get(1),
                                        1,1,
                                        1,0,1
                                );
                            }
                        }
                );
            }
        });
    }
}
