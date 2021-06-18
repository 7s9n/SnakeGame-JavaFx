package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.Random;

public class Fruit implements Drawable{
    private Point2D pos;
    private Random rand;
    private Image fruit;
    public Fruit(){
        this.iniVariable();
    }
    @Override
    public void draw (GraphicsContext context) {
        int xPos = (int)pos.getX() * Constants.CELL_SIZE;
        int yPos = (int)pos.getY() * Constants.CELL_SIZE;
        context.drawImage(fruit , xPos , yPos,Constants.CELL_SIZE,Constants.CELL_SIZE);
    }
    private void iniVariable(){
        this.fruit = new Image("file:/Users/Hussein Sarea/Snake/src/images/apple.png");
        this.rand = new Random();
        this.randomize();
    }
    public void randomize(){
        pos = null;
        int x = rand.nextInt(Constants.CELL_NUMBER - 1);
        int y = rand.nextInt(Constants.CELL_NUMBER -1);
        this.pos = new Point2D(x,y);
    }
    public Point2D getPos(){
        return this.pos;
    }
}
