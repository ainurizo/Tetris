import javax.swing.*;

public class Frame extends JFrame {

    Panel panel = new Panel();

    public static void main(String[] args) {
        new Frame();
    }

    private Frame() {
        initFrame();
    }

    private void initFrame() {
        add(panel);
        setTitle("Tetris");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
}
