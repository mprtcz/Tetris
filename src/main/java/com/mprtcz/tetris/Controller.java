package com.mprtcz.tetris;

import com.mprtcz.tetris.logger.TetrisGameLogger;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Azet on 2016-05-08.
 */
public class Controller {
    private final static Logger logger = Logger.getLogger(TetrisGameLogger.class.getName());
    private Level level = Level.CONFIG;

    public Canvas gameCanvas;
    public Button startButton;
    private GameAgent gameAgent;
    public Canvas nextBrickCanvas;
    public TextField pointsTextField;
    public CheckBox musicCheckBox;

    public void onStartButtonClicked(){

        if(gameAgent!=null){
            gameAgent.terminateGame();
        }

        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for(Thread t: threadSet){
            if(t.getName().equals("GameThread")){
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        gameAgent = new GameAgent(gameCanvas, nextBrickCanvas, pointsTextField);
        gameAgent.setMusic(musicCheckBox.isSelected());

        Thread thread = new Thread(this::playGameAgent);
        thread.setName("GameThread");
        thread.start();
    }

    public void onMusicCheckboxClicked(){
        if(gameAgent!=null){
            gameAgent.setMusic(musicCheckBox.isSelected());
        }
    }

    private void playGameAgent(){
        gameAgent.startGame();
    }

    public void initialize(){
        startButton.setOnKeyReleased(event -> gameAgent.handleKeyReleasedEvents(event));

        startButton.setOnKeyPressed(event -> gameAgent.handleKeyPressedEvents(event));
    }
}
