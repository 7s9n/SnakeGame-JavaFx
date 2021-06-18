package sample;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Window extends Stage {
    private double width;
    private double height;
    private final Pane pane;
    private final Canvas canvas;
    private final GraphicsContext context;
    private final Scene surface;
    public Window(){
        this.width = 900;
        this.height = 600;
        this.canvas = new Canvas(width , height);
        this.context = canvas.getGraphicsContext2D();
        this.pane = new AnchorPane(canvas);
        this.surface = new Scene(pane , width , height ,Color.valueOf("242324"));
        this.setScene(surface);
        this.setResizable(false);
    }
    public Window(int width , int height){
        this();
        this.canvas.setWidth(width);
        this.canvas.setHeight(height);
        this.width = width;
        this.height = height;
    }
    public void draw(Drawable object){
        object.draw(context);
    }
    public void draw(Drawable... objects){
        for (var object : objects){
            object.draw(this.context);
        }
    }
    public void clear(){
        this.context.clearRect(0,0,Constants.WINDOW_WIDTH , Constants.WINDOW_HEIGHT);
    }
    public Canvas getCanvas(){
        return this.canvas;
    }
}
