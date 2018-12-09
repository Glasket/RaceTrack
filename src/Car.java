import java.awt.*;

public class Car {

    private int carNum;
    private int carX, carY;
    private Image image;

    public Car(int carNum, int carX, int carY, Image image) {
        this.carNum = carNum;
        this.carX = carX;
        this.carY = carY;
        this.image = image;
    }

    public int getCarNum() {
        return carNum;
    }

    public int getX() {
        return carX;
    }

    public void setX(int carX) {
        this.carX = carX;
    }

    public int getY() {
        return carY;
    }

    public Image getImage() {
        return image;
    }

    public void addToX(int amount) {
        this.carX += amount;
    }

}
