package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;


public class MainGame {
    private Snake snake;
    private Fruit fruit;
    private boolean isAlive;
    private Window window;
    private Timeline gameLoop;
    private GridLines gridLines;
    private MediaPlayer mediaPlayer;
    public MainGame(){
        this.initVariable();
    }

    private void clear(){
        window.clear();
    }
    private void update(){
        this.snake.move();
        this.checkCollision();
        this.checkFail();
    }
    private void render(){
        window.draw(gridLines);
        window.draw(snake , fruit);
    }
    private void checkCollision(){
        if (this.snake.getHead().equals(this.fruit.getPos())){
            this.playCrushSound();
            this.snake.addBlock();
            randomFruitInvalidPlace();
        }
    }
    private void randomFruitInvalidPlace(){
        this.fruit.randomize();
        this.snake.getBody().forEach((block)->{
            if (block.equals(fruit.getPos())){
                randomFruitInvalidPlace();
                System.out.println("Fruit collide with snake body.");
            }
        });
    }
    private void checkFail(){
        var body = this.snake.getBody();
        var head = body.get(0);
        for (int i = 1 ; i < body.size(); ++i){
            if (head.equals(body.get(i))){
                isAlive = false;
                gameLoop.pause();
            }
        }
    }
    private void initVariable(){
        this.window = new Window(Constants.WINDOW_WIDTH , Constants.WINDOW_HEIGHT);
        this.snake = new Snake();
        this.fruit = new Fruit();
        this.isAlive = true;
        this.gridLines = new GridLines();
        this.addListener();
        Media media = new Media(new File("src/sound/crunch.wav").toURI().toString());
        this.mediaPlayer = new MediaPlayer(media);
        this.mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.stop());
    }
    private void startAgain(){
        this.snake.reset();
        this.gameLoop.playFromStart();
        this.isAlive = true;
        this.fruit.randomize();
    }
    private void playCrushSound(){
        mediaPlayer.setStartTime(Duration.seconds(0));
        this.mediaPlayer.play();
    }
    private void addListener(){
        window.getCanvas().addEventFilter(KeyEvent.KEY_PRESSED , (event)->{
            var direction = this.snake.getVelocity();
            switch (event.getCode()){
                case LEFT -> {
                    if (direction.getX() != 1)
                        snake.setVelocity(new Point2D(-1 , 0));
                }
                case RIGHT -> {
                    if (direction.getX() != -1)
                        snake.setVelocity(new Point2D(1 , 0));
                }
                case UP -> {
                    if (direction.getY() != 1)
                        snake.setVelocity(new Point2D(0 , -1));
                }
                case DOWN -> {
                    if (direction.getY() != -1)
                        snake.setVelocity(new Point2D(0 , 1));
                }
                case SPACE -> {
                    if (this.gameLoop.getStatus() == Timeline.Status.PAUSED && isAlive)
                        this.gameLoop.play();
                    else if (this.gameLoop.getStatus() == Timeline.Status.PAUSED && !isAlive)
                        this.startAgain();
                    else
                        this.gameLoop.pause();
                }
            }
        });
        window.getCanvas().setFocusTraversable(true);
    }
    public void run(){
        gameLoop = new Timeline();
        gameLoop.getKeyFrames().add(new KeyFrame(Duration.millis(60.0),(event) -> {
            clear();
            render();
            update();
        }));
        window.show();
        gameLoop.setCycleCount(Timeline.INDEFINITE);
        gameLoop.play();
    }

}
