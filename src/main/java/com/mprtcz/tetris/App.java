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
    private static boolean isLogger = false;

    @Override
    public void start(Stage window) throws Exception {
        if(isLogger) {
            TetrisGameLogger.initializeLogger();
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
        System.out.println(loader.getLocation());
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 600);

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
        if(args.length>0){
            if(args[0].equals("-log")){
                isLogger = true;
            }
        }
        launch(args);
    }
}
