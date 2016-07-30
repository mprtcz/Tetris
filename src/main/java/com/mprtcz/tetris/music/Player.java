package com.mprtcz.tetris.music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

/**
 * Created by Azet on 2016-05-30.
 */
public class Player {

    private final URL resource = getClass().getResource("/TetrisMeetsMetal.mp3");
    private MediaPlayer mediaPlayer;

    public Player() {}

    public void playMusic(){
        Media media = new Media(resource.toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(this::playMusic);
        mediaPlayer.setVolume(1.0);
    }

    public void setVolume(int volume){
        mediaPlayer.setVolume(volume);
    }

    public void stopMusic(){
        if(mediaPlayer!=null) {
            mediaPlayer.stop();
        }
    }
}
