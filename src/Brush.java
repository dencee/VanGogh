import processing.core.PApplet;
import processing.core.PImage;

public class Brush {
    PApplet processing;
    PImage painting;
    int paintingIndex;
    int x, y;
    int brushSize, maxBrushThickness, numberOfBristles, brushStagger;
    Bristle[] bristles;

    class Bristle {
        int startX, startY, endX, endY;
        int radius;

        Bristle(int x, int y, int r) {
            startX = x;
            startY = y;
            endX = x;
            endY = y;
            radius = r;
        }
    }

    public Brush(PApplet processing, PImage painting) {
        this.painting = painting;
        this.processing = processing;
        this.paintingIndex = 0;
        this.x = 0;
        this.y = 0;
        this.brushSize = 40;
        this.brushStagger = 6;
        this.maxBrushThickness = 20;
        this.numberOfBristles = 200;
        this.bristles = new Bristle[this.numberOfBristles];

        initializeBristles();
    }

    public void initializeBristles() {
        for (int i = 0; i < bristles.length; i++) {
            int bristleX = (int) processing.random(-brushSize, brushSize);
            int bristleY = (int) processing.random(-brushSize, brushSize);
            int bristleRadius = (int) processing.random(maxBrushThickness);

            this.bristles[i] = new Bristle(bristleX, bristleY, bristleRadius);
        }
    }

    public void update() {
        x += ((processing.mouseX - x) / brushStagger);
        y += ((processing.mouseY - y) / brushStagger);

        if (x < 0) {
            x = 0;
        } else if (x > processing.width) {
            x = processing.width;
        }

        if (y < 0) {
            y = 0;
        } else if (y > processing.height) {
            y = processing.height;
        }
    }

    public void draw() {
        // Must run whether mouse is pressed or not
        // to keep bristle x/y positions up to date
        for (int i = 0; i < bristles.length; i++) {
            int x1 = x + bristles[i].startX;
            int y1 = y + bristles[i].startY;

            if (processing.mousePressed) {
                float x2 = bristles[i].endX;
                float y2 = bristles[i].endY;
                float radius = bristles[i].radius;
                float dist = PApplet.dist(x1, y1, x2, y2);
                int pixel = painting.get((int) x2, (int) y2);
                processing.stroke(pixel);

                // The bigger the distance, i.e. the faster the mouse is moved,
                // the bigger and more coarse the line width
                processing.strokeWeight((radius * dist) / 50);

                processing.line(x1, y1, x2, y2);
            }

            bristles[i].endX = x1;
            bristles[i].endY = y1;
        }
    }

    public void setNewPainting(PImage newPainting) {
        this.painting = newPainting;
        processing.getSurface().setSize(newPainting.width, newPainting.height);

        // Sets window to move to the upper left corner
        processing.getSurface().setLocation(10, 10);
    }
}
