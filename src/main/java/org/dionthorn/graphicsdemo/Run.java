package org.dionthorn.graphicsdemo;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Run extends Application {

    public static String PROGRAM_VERSION = "v0.1";
    public static int SCREEN_WIDTH = 1980;
    public static int SCREEN_HEIGHT = 1080;
    private final long FPS = TimeUnit.SECONDS.toNanos(1L) / 60;
    private long startTime = System.nanoTime();
    private GraphicsContext gc;
    private Random rand = new Random();
    private int counter = 0;
    private int counterLimit = 10;
    private int counterInverse = counterLimit * 10;

    public void render() {
        PixelWriter test = gc.getPixelWriter();
        if(counter < counterLimit) {
            for(int x=0; x<SCREEN_WIDTH; x++) {
                for (int y = 0; y < SCREEN_WIDTH; y++) {
                    if(x % 2 == 0 && y % 2 == 0) {
                        test.setColor(x, y, Color.rgb(x < 255 ? x : rand.nextInt(255), y < 255 ? y : rand.nextInt(255), x < 255 ? x : rand.nextInt(255)));
                    } else if(x % 3 == 0 && y % 3 == 0) {
                        test.setColor(x, y, Color.rgb(rand.nextInt(55) + 100, rand.nextInt(55) + 100, rand.nextInt(55) + 100));
                    } else {
                        test.setColor(x, y, Color.rgb(rand.nextInt(55), rand.nextInt(55), rand.nextInt(55)));
                    }
                }
            }
            counter++;
        } else if(counter >= counterLimit) {
            counterInverse--;
            if(counterInverse <= 0) {
                counter = 0;
                counterInverse = counterLimit * 10;
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Graphics Demo " + PROGRAM_VERSION);
        primaryStage.setResizable(false);
        Group rootGroup = new Group();
        Scene rootScene = new Scene(rootGroup, SCREEN_WIDTH, SCREEN_HEIGHT, Color.BLACK);
        primaryStage.sizeToScene();
        Canvas canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        rootGroup.getChildren().add(canvas);

        // Keyboard handling
        rootScene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.ESCAPE) {
                primaryStage.close();
            }
        });

        // Staging and animator setup
        primaryStage.setScene(rootScene);
        primaryStage.show();
        AnimationTimer animator = new AnimationTimer() {
            @Override
            public void handle(long arg0) {
                long currentTime = System.nanoTime();
                if (FPS <= (currentTime - startTime)) {
                    render();
                    startTime = currentTime;
                }
            }
        };
        animator.start();
    }

    public static void main(String[] args) {
        // Launch the JavaFX Application this will take us to @Override public void start(Stage primaryStage)
        launch(args);
    }

}
