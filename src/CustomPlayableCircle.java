import javafx.animation.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


public class CustomPlayableCircle extends Circle {


/* >>>> class attributes <<<< */


    private static final double GRAVITY = 0.5;

    private double Vx = 0.0;
    private double Vy = 0.0;
    public boolean inAir = false;

    public double yGroundReference;

    // scene borders relative to the class
    private double lowerSceneBorder;
    private double upperSceneBorder;
    private double leftSceneBorder;
    private double rightSceneBorder;


/* >>>> constructors <<<< */


    public CustomPlayableCircle(double radius) {
        super(radius);
    }
    public CustomPlayableCircle(double radius, Paint paint) {
        super(radius, paint);
    }
    public CustomPlayableCircle() {
    }
    public CustomPlayableCircle(double centerX, double centerY, double radius) {
        super(centerX, centerY, radius);
    }
    public CustomPlayableCircle(double centerX, double centerY, double radius, Paint paint) {
        super(centerX, centerY, radius, paint);
    }


/* >>>> setters and getters <<<< */


    // Scene borders setter
    public void setSceneBorders(double upperSceneBorder, double lowerSceneBorder, double rightSceneBorder, double leftSceneBorder) {
        this.upperSceneBorder = upperSceneBorder;
        this.lowerSceneBorder = lowerSceneBorder;
        this.rightSceneBorder = rightSceneBorder;
        this.leftSceneBorder = leftSceneBorder;
    }

    // velocity getters and setters
    public double getVx() {
        return Vx;
    }
    public void setVx(double vx) {
        this.Vx = vx;
    }
    public double getVy() {
        return Vy;
    }
    public void setVy(double vy) {
        this.Vy = vy;
    }


    /* >>>> Update position function <<<< */


    public void updatePosition() {

        // add gravity
        Vy += GRAVITY;

        // set new positions
        double newX = getCenterX() + Vx;
        double newY = getCenterY() + Vy;

        // checks if the object hits the borders of the scene
        if (newX > rightSceneBorder - getRadius()) newX = rightSceneBorder - getRadius();
        if (newX < leftSceneBorder + getRadius()) newX = leftSceneBorder + getRadius();
        if (newY >= yGroundReference - getRadius()) {
            newY = yGroundReference - getRadius();
            Vy = 0.0;
            inAir = false;
        }
        if (newY < upperSceneBorder + getRadius()) newY = upperSceneBorder + getRadius();

        // apply the new position
        setCenterX(newX);
        setCenterY(newY);
    }


/* >>>> deformation functions <<<< */


    public void xDeformation(double factor) {
        ScaleTransition deformationAnimation = new ScaleTransition(Duration.millis(100), this);
        deformationAnimation.setFromX(1);
        deformationAnimation.setToX(factor);
        deformationAnimation.setAutoReverse(true);
        deformationAnimation.setCycleCount(2);
        deformationAnimation.play();

    }
    public void yDeformation(double factor) {
        ScaleTransition deformationAnimation = new ScaleTransition(Duration.millis(100), this);
        deformationAnimation.setFromY(1);
        deformationAnimation.setToY(factor);
        deformationAnimation.setAutoReverse(true);
        deformationAnimation.setCycleCount(2);
        deformationAnimation.play();
    }


/* >>>> pressed key handler function <<<< */


    public void handlePressedKey(KeyCode key) {
        if (key == KeyCode.RIGHT) {
            setVx(10);
        }
        if (key == KeyCode.LEFT) {
            setVx(-10);
        }
        if (key == KeyCode.SPACE && !inAir) {
            setVy(-10);
            inAir = true;
        }
    }


/* >>>> Released key handler function <<<< */


    public void handleReleasedKey(KeyCode key) {
        if (key == KeyCode.RIGHT || key == KeyCode.LEFT) setVx(0.0);
    }


/* >>>> ..... <<<< */


}
