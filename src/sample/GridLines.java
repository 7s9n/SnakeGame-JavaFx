package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GridLines implements Drawable{
    @Override
    public void draw (GraphicsContext context) {
        context.setStroke(Color.rgb(255,255,255,0.3));
        context.setLineWidth(1);
        // vertical lines
        for (int i = 0; i <= Constants.WINDOW_WIDTH; i += Constants.CELL_SIZE) {
            context.strokeLine(i ,0 , i ,Constants.WINDOW_HEIGHT +1 );
        }
        for (int j = Constants.CELL_SIZE; j <= Constants.WINDOW_HEIGHT; j += Constants.CELL_SIZE) {
            context.strokeLine(0,j,Constants.WINDOW_WIDTH +1,j);
        }
    }
}
