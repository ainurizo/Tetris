import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Panel extends JPanel implements ActionListener {

    public final int WIDTH = 16;
    public final int HEIGHT = 20;
    public final int DOT_SIZE = 20;
    private boolean STATE = true;
    private boolean FALL_BLOCK;
    private boolean CAN_MOVE_RIGHT = true;
    private boolean CAN_MOVE_LEFT = true;
    private boolean CAN_TURN_BLOCKS = true;
    private boolean RIGHT;
    private boolean LEFT;
    private boolean UP;
    private boolean DOWN;
    private int DELAY;
    private int Y = 0;
    private int count = 0;
    Cords centerCord;
    Timer timer;
    Floor floor = new Floor(WIDTH, HEIGHT);
    Blocks blocks = new Blocks(WIDTH, HEIGHT);

    public Panel() {
        initPanel();
        initLineBlock();
        addKeyListener(new Keyboard());
        setFocusable(true);
        setTimer();
    }

    private void initLineBlock() {
        blocks.setLineBlocks(getRandomNumbersForBlockWidth(), getRandomNumbersForBlockHeight());
        FALL_BLOCK = true;
    }

    private void setDELAY() {
        this.DELAY = 200;
    }

    private void initPanel() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(WIDTH * DOT_SIZE + 10, HEIGHT * DOT_SIZE));
    }

    private int getRandomNumbersForBlockWidth() {
        return (int) Math.ceil((Math.random() * 3));
    }

    private int getRandomNumbersForBlockHeight() {
        return (int) Math.ceil((Math.random() * 1));
    }


    private Cords getCenterCord() {
        centerCord = blocks.getCordsForBlocks().get(blocks.getCordsForBlocks().size() / 2);
        return centerCord;
    }


    private void setTimer() {
        setDELAY();
        timer = new Timer(DELAY, this);
        timer.start();
    }


    private void moveBlocks() {
        if (RIGHT && CAN_MOVE_RIGHT) {
            ArrayList<Cords> Right = new ArrayList<>();
            for (Cords cords : blocks.getCordsForBlocks()) {
                cords.x = cords.x + 1;
                Right.add(new Cords(cords.x, cords.y));
                blocks.cordsForBlocks = Right;
                RIGHT = false;
            }
        }
        if (LEFT && CAN_MOVE_LEFT) {
            ArrayList<Cords> Left = new ArrayList<>();
            for (Cords cords : blocks.getCordsForBlocks()) {
                cords.x = cords.x - 1;
                Left.add(new Cords(cords.x, cords.y));
                blocks.cordsForBlocks = Left;
                LEFT = false;
            }
        }
    }


    private void fallBlocks() {
        if (FALL_BLOCK) {
            System.out.println(centerCord.x + " " + centerCord.y);
            ArrayList<Cords> List = new ArrayList<>();
            for (Cords cords : blocks.getCordsForBlocks()) {
                cords.y = cords.y + 1;
                List.add(new Cords(cords.x, cords.y));
                blocks.cordsForBlocks = List;
            }
        }
    }

    private void hitFloor() {
        if (FALL_BLOCK) {
            for (Cords blockCords : blocks.getCordsForBlocks()) {
                for (Cords floorCords : floor.getFloorCords()) {
                    if (blockCords.y == floorCords.y - 1 && blockCords.x == floorCords.x) {
                        FALL_BLOCK = false;
                        DOWN = false;
                        floor.getFloorCords().addAll(blocks.cordsForBlocks);
                        //Вот этот гребаный return вместо break поменял все
                        return;
                    }
                }
            }
        } else {
            initLineBlock();
        }
    }


    private void hitRoof() {
        for (Cords floorCords : floor.getFloorCords()) {
            if (floorCords.y == 1) {
                STATE = false;
                break;
            }
        }
    }


    private boolean cantTurnBlocks() {
        if (getCenterCord().x == 0 || getCenterCord().x == WIDTH) {
            CAN_TURN_BLOCKS = false;
        } else {
            CAN_TURN_BLOCKS = true;
        }
        return CAN_TURN_BLOCKS;
    }


    private void turnBlocks() {
        if (UP && CAN_TURN_BLOCKS) {
            ArrayList<Cords> forTurningBlocks = new ArrayList<>();
            for (int Y = -1; Y <= 1; Y++) {
                for (int X = -1; X <= 1; X++) {
                    for (Cords blockCords : blocks.getCordsForBlocks()) {
                        if (blockCords.x == getCenterCord().x + X && blockCords.y == getCenterCord().y + Y) {
                            if (Y != 0 && X != 0) {
                                forTurningBlocks.add(new Cords(getCenterCord().x - Y, getCenterCord().y + X));
                            }
                            if (Y == 0 && X == 0) {
                                forTurningBlocks.add(new Cords(getCenterCord().x, getCenterCord().y));
                            }
                            if (Y == 0 && X != 0) {
                                forTurningBlocks.add(new Cords(getCenterCord().x, getCenterCord().y + X));
                            }
                            if (Y != 0 && X == 0) {
                                forTurningBlocks.add(new Cords(getCenterCord().x - Y, getCenterCord().y));
                            }
                        }
                    }
                }
            }
            UP = false;
            blocks.cordsForBlocks = forTurningBlocks;
        }
    }


    private void cantMoveBlocks() {
        for (Cords blockCords : blocks.getCordsForBlocks()) {
            for (Cords floorCords : floor.getFloorCords()) {
                if (blockCords.y == floorCords.y && floorCords.x == blockCords.x - 1) {
                    CAN_MOVE_LEFT = false;
                }
                if (blockCords.y == floorCords.y && floorCords.x == blockCords.x + 1) {
                    CAN_MOVE_RIGHT = false;
                }
            }
        }
    }

    private int getCount() {
        //Версия v2.0
        for (int height = 0; height < HEIGHT; height++) {
            count = 0;
            for (int width = 0; width <= WIDTH; width++) {
                for (Cords florCords : floor.getFloorCords()) {
                    if (florCords.x == width && florCords.y == height) {
                        count++;
                        if (count == 17) {
                            Y = height;
                        }
                    }
                }
            }
        }
        return Y;
    }

    private void clearBlocks() {
        //Все, это версия v2.0
        if (getCount() != 0) {
            ArrayList<Cords> List1 = new ArrayList<>();
            for (int height1 = 0; height1 < Y; height1++) {
                for (int width1 = 0; width1 <= WIDTH; width1++) {
                    for (Cords floorCords : floor.getFloorCords()) {
                        if (floorCords.x == width1 && floorCords.y == height1) {
                            List1.add(new Cords(floorCords.x, floorCords.y + 1));
                        }
                    }
                }
            }
            for (int height2 = Y + 1; height2 <= HEIGHT; height2++) {
                for (int width2 = 0; width2 <= WIDTH; width2++) {
                    for (Cords floorCords : floor.getFloorCords()) {
                        if (floorCords.x == width2 && floorCords.y == height2) {
                            List1.add(new Cords(floorCords.x, floorCords.y));
                        }
                    }
                }
            }
            floor.floorCords = List1;
            Y = 0;
        }
    }


    private void hitBorder() {
        for (Cords blockCords : blocks.getCordsForBlocks()) {
            if (blockCords.x == 0) {
                CAN_MOVE_LEFT = false;
            } else if (blockCords.x == WIDTH) {
                CAN_MOVE_RIGHT = false;
            }
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (STATE) {
            for (Cords cords : floor.getFloorCords()) {
                g.drawImage(floor.getFLOOR_IMAGE(), cords.x * DOT_SIZE, cords.y * DOT_SIZE, this);
            }
            for (Cords blockCord : blocks.getCordsForBlocks()) {
                g.drawImage(blocks.getBLOCK_IMAGE(), blockCord.x * DOT_SIZE, blockCord.y * DOT_SIZE, this);
            }
        } else {
            String gameOver = "Game Over";
            g.setColor(Color.BLUE);
            g.drawString(gameOver, (WIDTH * DOT_SIZE) / 2 - 30, (HEIGHT * DOT_SIZE) / 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (STATE) {
            getCenterCord();
            turnBlocks();
            getCount();
            clearBlocks();
            hitFloor();
            fallBlocks();
            hitBorder();
            cantMoveBlocks();
            cantTurnBlocks();
            moveBlocks();
            hitRoof();
        }
        repaint();
    }


    class Keyboard extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_RIGHT) {
                RIGHT = true;
                LEFT = false;
                UP = false;
                DOWN = false;
                CAN_MOVE_RIGHT = true;
            }
            if (key == KeyEvent.VK_LEFT) {
                LEFT = true;
                RIGHT = false;
                UP = false;
                DOWN = false;
                CAN_MOVE_LEFT = true;
            }
            if (key == KeyEvent.VK_UP) {
                UP = true;
                LEFT = false;
                RIGHT = false;
                DOWN = false;
            }
            if (key == KeyEvent.VK_DOWN) {
                UP = false;
                LEFT = false;
                RIGHT = false;
                DOWN = true;

            }
        }
    }
}
