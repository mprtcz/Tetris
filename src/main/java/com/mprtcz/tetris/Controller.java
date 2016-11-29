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
    public Canvas nextBrickCanvas;
    public TextField pointsTextField;
    public CheckBox musicCheckBox;

    private GameAgent gameAgent;

    public void onStartButtonClicked() {
        buttonText buttonState;

        if (gameAgent != null) {
            buttonState = buttonText.START_GAME;
            startButton.setText(buttonState.getText());
            gameAgent.terminateGame();
            gameAgent = null;
            Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
            for (Thread t : threadSet) {
                if (t.getName().equals("GameThread")) {
                    try {
                        t.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            buttonState = buttonText.STOP_GAME;
            startButton.setText(buttonState.getText());
            gameAgent = new GameAgent(gameCanvas, nextBrickCanvas, pointsTextField);
            gameAgent.setMusic(musicCheckBox.isSelected());
            Thread thread = new Thread(this::playGameAgent);
            thread.setName("GameThread");
            thread.start();
        }
    }

    public void onMusicCheckboxClicked() {
        if (gameAgent != null) {
            gameAgent.setMusic(musicCheckBox.isSelected());
        }
    }

    private void playGameAgent() {
        gameAgent.startGame();
    }

    public void initialize() {
        startButton.setOnKeyReleased(event -> gameAgent.handleKeyReleasedEvents(event));
        startButton.setOnKeyPressed(event -> gameAgent.handleKeyPressedEvents(event));
    }

    private enum buttonText {
        STOP_GAME("Stop Game"),
        START_GAME("START");

        private String text;

        buttonText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
