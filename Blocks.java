import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Blocks {

    private Image BLOCK_IMAGE;
    public ArrayList<Cords> cordsForBlocks;
    public int width;
    public int height;

    public Blocks(int width, int height) {
        this.width = width;
        this.height = height;
        setBlockImage();
    }

    public Image getBLOCK_IMAGE() {
        return BLOCK_IMAGE;
    }

    private void setBlockImage() {
        ImageIcon iiBlock = new ImageIcon("img/TETRIS_PIECE.png");
        BLOCK_IMAGE = iiBlock.getImage();
    }

    public void setLineBlocks(int blockWidth, int blockHeight) {
        cordsForBlocks = new ArrayList<>();
        for (int x = width / 2; x < width / 2 + blockWidth; x++) {
            for (int y = 0; y < blockHeight; y++) {
                cordsForBlocks.add(new Cords(x, y));
            }
        }
    }

    public ArrayList<Cords> getCordsForBlocks() {
        return cordsForBlocks;
    }
}
