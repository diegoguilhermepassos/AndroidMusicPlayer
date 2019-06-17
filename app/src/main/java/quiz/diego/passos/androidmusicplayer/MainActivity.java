package quiz.diego.passos.androidmusicplayer;


import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    int[] buttons = new int[]{R.id.button_player, R.id.button_player2};
    Button buttonPlayer, buttonPlayer2;
    MediaPlayer player;
    int lasMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPlayer = findViewById(buttons[0]);
        buttonPlayer2 = findViewById(buttons[1]);
        buttonPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play(R.raw.cartoon_on_e_on);
                changeBackground(buttonPlayer, player.isPlaying());
            }
        });

        buttonPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play( R.raw.simply_red_you_make_me_feel_brand_new);
                changeBackground(buttonPlayer2, player.isPlaying());
            }
        });

        player = new MediaPlayer();
    }

    void play(int musicId){
        if(isMyMusicPlayng(musicId)){
            startPause();
        }else{
            loadSound( musicId);
        }
    }

    boolean isMyMusicPlayng(int myMusicId){
        return myMusicId == lasMusic;
    }

    void loadSound(int soundId) {
        Uri musicUri = Uri.parse("android.resource://" + getPackageName() + "/" + soundId);
        try {
            player.stop();
            player.reset();
            player.setDataSource(getApplicationContext(), musicUri);
            player.prepare();
            player.start();
            lasMusic = soundId;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void startPause() {
        if(player.isPlaying()){
            player.pause();
        }else{
            player.start();
        }
    }

    void changeBackground(Button button, boolean isPlayng) {
        if(!isPlayng){
            button.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
        }else{
            button.setBackgroundResource(R.drawable.ic_pause_circle_filled_black_24dp);
        }
        for(int btId : buttons){
            if(btId != button.getId()){
                Button btAux = findViewById(btId);
                btAux.setBackgroundResource(R.drawable.ic_play_circle_filled_black_24dp);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
    }
}