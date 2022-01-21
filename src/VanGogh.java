import processing.core.PApplet;
import processing.core.PImage;

public class VanGogh extends PApplet {
    PImage[] paintings;
    PImage starryNight, portrait, wheatField, painterOnRoad;
    PImage currentPainting;
    PImage canvas;
    PImage paintbrushCursor;
    Brush brush;
    int currentPaintingIndex;
    boolean initializeCanvas = true;

    @Override
    public void settings() {
        // Arbitrary initial size.
        // These values are changed when the first painting is loaded in setup().
        size(800, 600);
    }

    @Override
    public void setup() {
        canvas = loadImage("canvas.jpg");
        starryNight = loadImage("starryNight.jpg");
        portrait = loadImage("strawHatPortrait.jpg");
        wheatField = loadImage("wheatField.jpg");
        painterOnRoad = loadImage("painterOnRoad.jpg");

        paintings = new PImage[4];
        paintings[0] = starryNight;
        paintings[1] = portrait;
        paintings[2] = wheatField;
        paintings[3] = painterOnRoad;
        currentPaintingIndex = 0;
        currentPainting = paintings[currentPaintingIndex];

        surface.setResizable(true);
        surface.setSize(currentPainting.width, currentPainting.height);
        surface.setLocation(10, 10);

        paintbrushCursor = loadImage("paintbrushCur.png");
        paintbrushCursor.resize(22 * 2, 28 * 2);
        cursor(paintbrushCursor);

        brush = new Brush(this, currentPainting);
    }

    @Override
    public void draw() {
        if (initializeCanvas) {
            canvas.resize(width, height);
            image(canvas, 0, 0);
            initializeCanvas = false;
        }

        brush.update();
        brush.draw();
    }

    @Override
    public void keyPressed() {
        if (keyPressed) {
            if (key == 'r') {
                image(canvas, 0, 0);
            } else if (key == 32) {
                // SPACE key pressed--go to next painting
                currentPaintingIndex = (currentPaintingIndex + 1) % paintings.length;
                currentPainting = paintings[currentPaintingIndex];

                brush.setNewPainting(currentPainting);

                canvas.resize(width, height);
                initializeCanvas = true;
            }
        }
    }

    public static void main(String[] args) {
        PApplet.main(VanGogh.class.getName());
    }
}
