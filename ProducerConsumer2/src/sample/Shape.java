package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public abstract class Shape {


        // A class representing shapes that can be displayed on a ShapeCanvas.
        // The subclasses of this class represent particular types of shapes.
        // When a shape is first constructed, it has height and width zero
        // and a default color of white.

        public int left;
        public int top;      // Position of top left corner of rectangle that bounds this shape.
        public int width;
        public int height;  // Size of the bounding rectangle.
        Color color = Color.WHITE;  // Color of this shape.
        public int num = 0;

        void reshape(int left, int top, int width, int height) {
            // Set the position and size of this shape.
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
        }

        void moveBy(int dx, int dy) {
            // Move the shape by dx pixels horizontally and dy pixels vertically
            // (by changing the position of the top-left corner of the shape).
            left += dx;
            top += dy;
        }

        public void setColor(Color color) {
            // Set the color of this shape
            this.color = color;
        }

        boolean containsPoint(int x, int y) {
            // Check whether the shape contains the point (x,y).
            // By default, this just checks whether (x,y) is inside the
            // rectangle that bounds the shape.  This method should be
            // overridden by a subclass if the default behavior is not
            // appropriate for the subclass.
            if (x >= left && x < left+width && y >= top && y < top+height)
                return true;
            else
                return false;
        }
         public abstract void draw(GraphicsContext g);

    public abstract boolean isInstance(String rectShape);
    // Draw the shape in the graphics context g.
        // This must be overriden in any concrete subclass.

    }  // end of class Shape


     class RectShape extends Shape {
         int n=1;
         Text text = new Text();
        // This class represents rectangle shapes.
        public void draw(GraphicsContext g) {
            text.setText("Q"+n);
            n++;
            text.setX(left);
            text.setY(top);
            g.setFill(color);
            g.fillRect(left,top,width,height);
            g.setStroke(Color.BLACK);
            g.strokeRect(left,top,width,height);
            g.setFill(Color.RED);
            g.fillText(Integer.toString(num), left+15, top-10);

        }

         @Override
         public boolean isInstance(String rectShape) {
            if(rectShape.equals("RectShape")){
                return true;
            }
             return false;
         }
     }


     class CircleShape extends Shape {
         int n=1;
         Text text = new Text();
        // This class represents oval shapes.
        public void draw(GraphicsContext g) {
            text.setText("M"+n);
            n++;
            text.setX(left);
            text.setY(top);
            g.setFill(color);
            g.fillOval(left,top,width,height);
            g.setStroke(Color.BLACK);
            g.strokeOval(left,top,width,height);


        }

         @Override
         public boolean isInstance(String circleShape) {
             if(circleShape.equals("CircleShape")){
                 return true;
             }
             return false;
         }

         boolean containsPoint(int x, int y) {
            double rx = width/2.0;   // horizontal radius of ellipse
            double ry = height/2.0;  // vertical radius of ellipse
            double cx = left + rx;   // x-coord of center of ellipse
            double cy = top + ry;    // y-coord of center of ellipse
            if ( (ry*(x-cx))*(ry*(x-cx)) + (rx*(y-cy))*(rx*(y-cy)) <= rx*rx*ry*ry )
                return true;
            else
                return false;
        }
    }

