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
    private static final String GAME_THREAD_NAME = "GameThread";

    public Canvas gameCanvas;
    public Button startButton;
    public Canvas nextBrickCanvas;
    public TextField pointsTextField;
    public CheckBox musicCheckBox;

    private GameAgent gameAgent;

    public void onStartButtonClicked() {
        if (gameAgent != null) {
            stopExistingGame();
        } else {
            startNewGame();
        }
    }

    private void stopExistingGame() {
        setStartButtonText(ButtonText.START_GAME);
        nullifyAndTerminateGame();
        joinGameThread();
    }

    private void startNewGame() {
        setStartButtonText(ButtonText.STOP_GAME);
        initializeNewGameAgent();
        fireUpGameThread();
    }

    private void fireUpGameThread() {
        Thread thread = new Thread(this::playGameAgent);
        thread.setName(GAME_THREAD_NAME);
        thread.start();
    }

    private void nullifyAndTerminateGame() {
        gameAgent.terminateGame();
        gameAgent = null;
    }

    private void joinGameThread() {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        threadSet.stream().filter(t -> t.getName().equals(GAME_THREAD_NAME)).forEach(this::joinThread);
    }

    private void joinThread(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initializeNewGameAgent() {
        gameAgent = new GameAgent(gameCanvas, nextBrickCanvas, pointsTextField);
        gameAgent.playMusic(musicCheckBox.isSelected());
    }

    private void setStartButtonText(ButtonText buttonState) {
        startButton.setText(buttonState.getText());
    }

    public void onMusicCheckboxClicked() {
        if (gameAgent != null) {
            gameAgent.playMusic(musicCheckBox.isSelected());
        }
    }

    private void playGameAgent() {
        gameAgent.startGame();
    }

    public void initialize() {
        startButton.setOnKeyReleased(event -> gameAgent.handleKeyReleasedEvents(event));
        startButton.setOnKeyPressed(event -> gameAgent.handleKeyPressedEvents(event));
    }

    private enum ButtonText {
        STOP_GAME("Stop Game"),
        START_GAME("START");

        private String text;

        ButtonText(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}
