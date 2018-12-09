import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private GUI() {
        add(new RaceTrack());
        setTitle("RaceTrack");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }

}
