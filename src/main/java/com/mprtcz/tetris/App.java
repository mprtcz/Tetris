package com.mprtcz.tetris;

import com.mprtcz.tetris.logger.TetrisGameLogger;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Azet on 2016-05-08.
 */
public class App extends javafx.application.Application {
    private final static Logger logger = Logger.getLogger(TetrisGameLogger.class.getName());
    private Level level = Level.INFO;

    @Override
    public void start(Stage window) throws Exception {
        TetrisGameLogger.initializeLogger();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
        System.out.println(loader.getLocation());
        Parent root = loader.load();

        Scene scene = new Scene(root, 1024, 768);

        window.setOnCloseRequest(e -> {
            logger.log(level, "Close Requested.");
            Platform.exit();
            System.exit(0);
        });

        window.setTitle("Tetris");
        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
