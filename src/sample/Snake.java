package sample;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.LinkedList;
import java.util.List;

public class Snake implements Drawable{
    private final List<Point2D> body;
    private Point2D velocity;
    private boolean newBlock;
    private Image head;
    private Image tail;

    public Snake(){
        this.body = new LinkedList<>();
        this.setUp();
    }

    @Override
    public void draw (GraphicsContext context) {
        this.updateHeadGraphics();
        this.updateTailGraphics();
        for (int index = 0; index < body.size(); ++index) {
            int xPos = (int)body.get(index).getX() * Constants.CELL_SIZE;
            int yPos = (int)body.get(index).getY() * Constants.CELL_SIZE;
            if (index == 0)
                context.drawImage(head,xPos,yPos,Constants.CELL_SIZE,Constants.CELL_SIZE);
            else if (index == body.size() -1)
                context.drawImage(tail,xPos,yPos,Constants.CELL_SIZE,Constants.CELL_SIZE);
            else{
                var previousBlock = body.get(index + 1).subtract(body.get(index));
                var nextBlock = body.get(index - 1).subtract(body.get(index));
                if (previousBlock.getX() == nextBlock.getX())
                    context.drawImage(Constants.bodyVertical,xPos,yPos,Constants.CELL_SIZE,Constants.CELL_SIZE);
                else if (previousBlock.getY() == nextBlock.getY())
                    context.drawImage(Constants.bodyHorizontal,xPos,yPos,Constants.CELL_SIZE,Constants.CELL_SIZE);
                else{
                    if ( (previousBlock.getX() == -1 && nextBlock.getY() == -1) || (previousBlock.getY() == -1 && nextBlock.getX() == -1) )
                        context.drawImage(Constants.bodyTl,xPos,yPos,Constants.CELL_SIZE,Constants.CELL_SIZE);
                    else if ( (previousBlock.getX() == -1 && nextBlock.getY() == 1) || (previousBlock.getY() == 1 && nextBlock.getX() == -1) )
                        context.drawImage(Constants.bodyBl,xPos,yPos,Constants.CELL_SIZE,Constants.CELL_SIZE);
                    else if ( (previousBlock.getX() == 1 && nextBlock.getY() == -1) || (previousBlock.getY() == -1 && nextBlock.getX() == 1) )
                        context.drawImage(Constants.bodyTr,xPos,yPos,Constants.CELL_SIZE,Constants.CELL_SIZE);
                    else
                        context.drawImage(Constants.bodyBr,xPos,yPos,Constants.CELL_SIZE,Constants.CELL_SIZE);
                }
            }
        }
    }
    private void updateHeadGraphics(){
        var headRelation = body.get(1).subtract(body.get(0));
        if (headRelation.getX() == 1 && headRelation.getY() == 0)
            head = Constants.headLeft;
        else if (headRelation.getX() == -1 && headRelation.getY() == 0)
            head = Constants.headRight;
        else if (headRelation.getX() == 0 && headRelation.getY() == 1)
            head = Constants.headUp;
        else
            head = Constants.headDown;
    }
    private void updateTailGraphics(){
        int len = body.size();
        var tailRelation = body.get(len - 2).subtract(body.get(len - 1));
        if (tailRelation.getX() == 1 && tailRelation.getY() == 0)
            tail = Constants.tailLeft;
        else if (tailRelation.getX() == -1 && tailRelation.getY() == 0)
            tail = Constants.tailRight;
        else if (tailRelation.getX() == 0 && tailRelation.getY() == 1)
            tail = Constants.tailUp;
        else
            tail = Constants.tailDown;
    }
    public synchronized void move(){
        Point2D v = new Point2D(this.body.get(0).getX() + velocity.getX() , this.body.get(0).getY() + velocity.getY());
        if (v.getX() < 0)
            v = v.add(30 , 0);
        else if (v.getX() >= Constants.CELL_SIZE)
            v = v.subtract(30 ,0);
        if (v.getY() < 0)
            v = v.add(0,Constants.CELL_NUMBER);
        else if (v.getY() >= Constants.CELL_NUMBER)
            v = v.subtract(0 , Constants.CELL_NUMBER);

        if (!newBlock){
            this.body.remove(this.body.size() -1);
        }
        this.body.add(0,v);
        this.newBlock = false;
    }
    public void addBlock(){
        this.newBlock = true;
    }
    public void setVelocity(Point2D velocity){
        if (this.velocity.equals(velocity))
            return;
        this.velocity = null;
        this.velocity = velocity;
    }
    public Point2D getHead(){
        return this.body.get(0);
    }
    public List<Point2D> getBody(){
        return this.body;
    }
    private void setUp(){
        this.body.add(new Point2D(15,15));
        this.body.add(new Point2D(14,15));
        this.velocity = new Point2D(1,0);
        this.newBlock = false;
    }

    public void reset(){
        this.body.clear();
        this.setUp();
    }
    public Point2D getVelocity() {
        return this.velocity;
    }
}
