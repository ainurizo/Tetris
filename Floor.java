import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Floor {
    public ArrayList<Cords> floorCords;
    private Image FLOOR_IMAGE;
    private int width;
    private int height;

    public Floor(int width, int height) {
        this.width = width;
        this.height = height;
        setCords();
        setFloorImage();
    }

    private void setFloorImage() {
        ImageIcon iiFloor = new ImageIcon("img/DOWN_BLOCK.png");
        FLOOR_IMAGE = iiFloor.getImage();
    }

    public Image getFLOOR_IMAGE() {
        return FLOOR_IMAGE;
    }

    private void setCords() {
        floorCords = new ArrayList<>();
        for (int x = -1; x <= width + 1; x++) {
            for (int y = height; y < height + 1; y++) {

                floorCords.add(new Cords(x, y));
            }
        }
    }

    public ArrayList<Cords> getFloorCords() {
        return floorCords;
    }
}
