import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

class RaceTrack extends JPanel implements ActionListener {

    private boolean finished = false;
    private Car[] cars = new Car[3];
    private RaceWorker[] workers = new RaceWorker[3];
    private boolean running = false;

    public RaceTrack() {
        setBounds(0, 0, 500, 200);
        setLayout(null);

        JButton start = new JButton("Start");
        JButton pause = new JButton("Pause");
        JButton reset = new JButton("Reset");

        start.setActionCommand("start");
        pause.setActionCommand("pause");
        reset.setActionCommand("reset");

        start.addActionListener(this);
        pause.addActionListener(this);
        reset.addActionListener(this);

        start.setBounds(149, 9, 70, 25);
        pause.setBounds(224, 9, 70, 25);
        reset.setBounds(299, 9, 70, 25);

        try {
            File imageFile = new File("sportive-car.png");
            this.cars[0] = new Car(1, 29, 42, ImageIO.read(imageFile));
            this.cars[1] = new Car(2, 29, 82, ImageIO.read(imageFile));
            this.cars[2] = new Car(3, 29, 122, ImageIO.read(imageFile));
        } catch(IOException e) {
            e.printStackTrace();
        }

        add(start);
        add(pause);
        add(reset);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.lightGray);
        g.fillRect(61, 52, 375, 12);
        g.fillRect(61, 92, 375, 12);
        g.fillRect(61, 132, 375, 12);

        for (Car car : cars) {
            g.drawImage(car.getImage(), car.getX(), car.getY(), this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String command = evt.getActionCommand();
        switch (command) {
            case "start":
                if (!running) {
                    for (int i = 0; i < 3; i++) {
                        workers[i] = new RaceWorker(cars[i]);
                    }
                    for (RaceWorker worker : workers) {
                        worker.execute();
                    }
                    running = true;
                }
                break;
            case "pause":
                if (running) {
                    for (RaceWorker worker : workers) {
                        worker.cancel(true);
                    }
                    running = false;
                }
                break;
            case "reset":
                for (RaceWorker worker : workers) {
                    if(!worker.isCancelled()) {
                        worker.cancel(true);
                    }
                }
                if (running) {
                    running = false;
                }
                for (Car car : cars) {
                    car.setX(29);
                }
                if (finished) {
                    finished = false;
                }
                revalidate();
                repaint();
                break;
        }
    }

    private synchronized void finish(Car car) {
        if (!finished) {
            finished = true;
            JOptionPane.showMessageDialog(this, "Car " + car.getCarNum() + " wins!");
        }
    }

    // Subclass for multithreading
    private class RaceWorker extends SwingWorker<Void, Void> {

        private Random rand = new Random();
        private Car car;

        private RaceWorker(Car car) {
            this.car = car;
        }

        @Override
        protected Void doInBackground() {
            while(!finished) {
                car.addToX(rand.nextInt(11));
                publish();
                if (car.getX() >= 405) {break;}
                try {
                    Thread.sleep(50);
                } catch(InterruptedException e) {
                    return null;
                }
            }
            finish(car);

            return null;
        }

        @Override
        protected void process(List<Void> chunks) {
            revalidate();
            repaint();
        }
    }

}