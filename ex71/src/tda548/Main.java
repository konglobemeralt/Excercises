package tda548;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JFrame {

    private static final int DELAY = 1000;

    private class DemoPanel extends JPanel {

        // clock ticks every DELAY milliseconds

        private int s = 0;
        public void tick() { s = s+1; }

        // helper functions

        // returns the average of the inputs. Tip: good for finding midpoints
        double average(double v1, double v2) {
            return (v1+v2) / 2.0;
        }

        // returns the average of the inputs. Tip: average(x1,x1,x2) is good
        // for finding a point that is 1/3 of the way towards another point
        double average(double v1, double v2, double v3) {
            return (v1+v2+v3) / 3.0;
        }

        // returns the x coordinate of point (x,y) rotated `angle` degrees around
        // point (centerX,centerY); used in constructing triangles etc.
        double rotateX(double centerX, double centerY, double x, double y, double angle) {
            angle = Math.PI * 2.0 * (angle / 360.0);
            return centerX + (x-centerX) * Math.cos(angle) - (y-centerY) * Math.sin(angle);
        }

        // returns the y coordinate of point (x,y) rotated `angle` degrees around
        // point (centerX,centerY); used in constructing triangles etc.
        double rotateY(double centerX, double centerY, double x, double y, double angle) {
            angle = Math.PI * 2.0 * (angle / 360.0);
            return centerY + (x-centerX) * Math.sin(angle) + (y-centerY) * Math.cos(angle);
        }

        // example 1

        void spiral(double angle, int n, Graphics g) {
            if (getWidth() < n) { return; }
            int x = (int)rotateX(0,0,n,0,angle) + getWidth() / 2;
            int y = (int)rotateY(0,0,n,0,angle) + getHeight() / 2;
            g.fillOval(x-2,y-2,4,4); // this draws an oval
            spiral(angle + 5,n+1,g); // this is a recursive call that draws the rest of the spiral
        }

        // example 2

      //  void tree(double dx, double x, double y, Graphics g) {
      //      if (dx < 0.5) { return; }
      //      double new_dx = dx / 2;
      //      double new_y = y - 20;
      //      double new_x1 = x - new_dx;
      //      double new_x2 = x + new_dx;
      //      int c = (int)(256 * (x / getWidth()));
      //      g.setColor(new Color(255,0,c));
      //      g.drawLine((int)x,(int)y,(int)new_x1,(int)new_y);
      //      g.drawLine((int)x,(int)y,(int)new_x2,(int)new_y);
      //      tree(new_dx,new_x1,new_y,g); // this recursive call draws the left side
      //      tree(new_dx,new_x2,new_y,g); // this recursive call draws the right side
      //  }

        // example 3

        void triangle(double x1, double y1, double x2, double y2, double x3, double y3, int depth, Graphics g) {
            if(depth == 0)
            {
                return;
            }
            else
            {
                g.drawLine((int)x1,(int)y1,(int)x2,(int)y2);
                g.drawLine((int)x2,(int)y2,(int)x3,(int)y3);
                g.drawLine((int)x3,(int)y3,(int)x1,(int)y1);


                double newX1 = (x1 + x2)/2;
                double newY1 = (y1 + y2)/2;
                double newX2 = (x1 + x3)/2;
                double newY2 = (y1 + y3)/2;
                double newX3 = (x2 + x3)/2;
                double newY3 = (y2 + y3)/2;


                triangle(newX1, newY1, newX2, newY2, newX3, newY3, depth -1, g);
                triangle(newX1, newY1, newX2, newY2, newX3, newY3, depth -1, g);
                triangle(newX1, newY1, newX2, newY2, newX3, newY3, depth -1, g);

            }



        }


        void boxes(double x, double y, double width, double height, Graphics g) {

            if((int)height <= 1)
            {
                return;
            }
            else
            {
                g.drawRect((int)x, (int)y, (int)width, (int)height);
                double newX = x - x/2;
                double newY = y - y/2;
                double newHeight = height / 2;
                double newWidth = width / 2;

                boxes(x, y, newWidth, newHeight, g);
                boxes(x+newWidth, y+newWidth, newWidth, newHeight, g);
            }
        }

        void tree(double x1, double y1, double angle, int depth, Graphics g)
        {
            if (depth == 0)
            {
                return;
            }
            double newX = x1 +  (Math.cos(Math.toRadians(angle)) * depth * 5);
            double newY = y1 +  (Math.sin(Math.toRadians(angle)) * depth * 5);
            g.drawLine((int)x1, (int)y1, (int)newX, (int)newY);
            tree(newX, newY, angle - 25, depth - 1, g);
            tree(newX, newY, angle + 25, depth - 1, g);


        }



        // paint method

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.fillRect(0,0,this.getWidth(),this.getHeight());
            g.setColor(Color.WHITE);

           //// compute coordinates for a triangle
           double x = getWidth()/2;
           double y = getHeight() * 0.72;
           double x1 = x - (getHeight() * 0.35);
           double x2 = x + (getHeight() * 0.35);
           double y1 = y;
           double y2 = y;
           double x3 = rotateX(x1,y1,x2,y2,-60);
           double y3 = rotateY(x1,y1,x2,y2,-60);

            //Cords for box
            double boxX = getWidth()/4;
            double boxY = getHeight()/4;
            double boxX1 = getWidth()/2;
            double boxY1 = getHeight()/2;




            if (9 <= s) { s = 0; }

            if (s < 3) {
                g.setColor(Color.YELLOW);
               // spiral(0,0,g);

                boxes(boxX,boxY, boxX1, boxY1, g);
            } else if (s < 6) {
                g.setColor(Color.WHITE);
                int angle = -90;
                tree(getWidth()/2, getHeight()/2 + 100, angle, 10, g);
            } else {
                g.setColor(Color.GREEN);
                triangle(x1,y1,x2,y2,x3,y3,10, g);
            }

        }

    }
    public Main() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,520); setLocation(50,50);
        DemoPanel panel = new DemoPanel();
        add(panel); setVisible(true);
        ActionListener al = (e -> { panel.repaint(); panel.tick(); });
        Timer t = new Timer(DELAY,al); t.start();
    }
    public static void main(String[] args) {
        Main f = new Main();
    }
}

