import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameWindow extends JFrame {
    private static GameWindow game_window;
    private static long last_frame_time;
    private static Image bg;
    private static Image game;
    private static Image drop;

    private static float drop_left = 200;
    private static float drop_top = -100;

    private static float drop_v = 200;
    private static int score;


    public static void main(String[] args) throws IOException {
        bg = ImageIO.read(GameWindow.class.getResourceAsStream("bg.jpg"));
        game = ImageIO.read(GameWindow.class.getResourceAsStream("game.jpg"));
        drop = ImageIO.read(GameWindow.class.getResourceAsStream("b.png"));
        game_window = new GameWindow();
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_window.setLocation(200, 100);
        game_window.setSize(906, 478);
        game_window.setResizable(false);
        last_frame_time = System.nanoTime();
        GameField game_field = new GameField();
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float drop_right = drop_left + drop.getWidth(null);
                float drop_bottom = drop_top + drop.getHeight(null);
                boolean is = x>= drop_left && x <= drop_right && y >= drop_top && y <= drop_bottom;
                if (is){
                    drop_top = -100;
                    drop_left = (int) (Math.random() * (game_field.getWidth() - drop.getWidth(null)));
                    drop_v += 20;
                    score++;
                    game_window.setTitle("Score:" + score);
                }
            }
        });
        game_window.add(game_field);
        game_window.setVisible(true);
    }

    private static void Repaint(Graphics g) {
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;

        drop_top = drop_top + drop_v * delta_time;
        g.drawImage(bg, 0 , 0, null);
        g.drawImage(drop, (int)drop_left , (int)drop_top, null);
        if (drop_top > game_window.getHeight())
        g.drawImage(game, -35 , -30, null);
    }

    private static class GameField extends JPanel{
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Repaint(g);
            repaint();
        }
    }
}