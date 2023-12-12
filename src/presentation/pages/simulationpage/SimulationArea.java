package presentation.pages.simulationpage;

import presentation.viewmodels.abstractions.ClientViewModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;

public class SimulationArea extends JPanel {
    private BufferedImage background;

    public SimulationArea() {
        setLayout(null); // Use absolute layout to place figures
        loadBackgroundImage();
    }

    private void loadBackgroundImage() {
        try {
            URL imageUrl = getClass().getResource("floor.jpg");
            background = ImageIO.read(imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            background = null;
        }
    }


    private Map<String, ClientFigure> clientFigureMap = new HashMap<>();
    private Map<Integer, TicketBoxFigure> ticketboxFigureMap = new HashMap<>();
    private List<EntranceFigure> entrancesFigures = new ArrayList<>();



    private List<ClientFigure> clientFigures = new ArrayList<>();

    public void addClientFigure(int posX, int posY, String id) {
        ClientFigure clientFigure = new ClientFigure(posX, posY, id);
        add(clientFigure);
        clientFigure.setBounds(posX, posY, 40, 40);
        //clientFigures.add(clientFigure);
        clientFigureMap.put(id, clientFigure);
        repaint();
    }

    public void addTicketBoxFigure(int posX, int posY, int id, boolean isOpen, int count, boolean isReserved) {
        TicketBoxFigure figure = new TicketBoxFigure(posX, posY, id, isOpen, count, isReserved);
        add(figure);
        figure.setBounds(posX, posY, 90, 75);
        ticketboxFigureMap.put(id, figure);
        repaint();
    }

    public void addEntranceFigure(int posX, int posY, int id, boolean isOpen, List<String> clientsIds) {
        EntranceFigure entrance = new EntranceFigure(posX, posY, id, isOpen, clientsIds);
        add(entrance);
        entrance.setBounds(posX, posY, 90, 75);
        repaint();
    }

    public boolean isClientOnPage(String id) {
        ClientFigure clientFigure = clientFigureMap.get(id);
        if (clientFigure == null) {
            return false;
        }
        return true;
    }

    public void animateClientsMovement(List<ClientViewModel> clients) {
        clients.forEach(client -> {
            ClientFigure clientFigure = clientFigureMap.get(client.getId() + "");
            if (clientFigure == null) {
                addClientFigure(client.getPosition().getX(), client.getPosition().getY(), client.getId() + "");
                clientFigure = clientFigureMap.get(client.getId() + "");
            }
            clientFigure.deltaX = (client.getPosition().getX() - clientFigure.posX) / 10;
            clientFigure.deltaY = (client.getPosition().getY() - clientFigure.posY) / 10;
        });
        Timer timer = new Timer(15, new ActionListener() {
            int steps = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (steps < 10) {
                    for (ClientFigure clientFigure : clientFigureMap.values()) {
                        int currentX = clientFigure.posX + clientFigure.deltaX;
                        int currentY = clientFigure.posY + clientFigure.deltaY;
                        clientFigure.setPosition(currentX, currentY);
                    }
                    repaint();
                    steps++;
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (background != null) {
            g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
        }
        for (ClientFigure clientFigure : clientFigures) {
            clientFigure.paintComponent(g); // This will paint each client figure
        }
        for (EntranceFigure entr : entrancesFigures) {
            entr.paintComponent(g);
        }
    }

    public Map<String, ClientFigure> getClientFigureMap() {
        return clientFigureMap;
    }
    public void removeClientFigure(String id) {
        ClientFigure clientFigure = clientFigureMap.get(id);
        if (clientFigure != null) {
            remove(clientFigure);
            clientFigureMap.remove(id);
            repaint();
        }
    }

    public void removeTicketBoxFigure(Integer id) {
        var ticketbox = ticketboxFigureMap.get(id);
        if (ticketbox != null) {
            remove(ticketbox);
            clientFigureMap.remove(id);
            repaint();
        }
    }

    public Map<Integer, TicketBoxFigure> getTicketboxFigureMap() {
        return ticketboxFigureMap;
    }

    public class ClientFigure extends JComponent {
        private int posX;
        private int posY;
        private final String id;
        private int deltaX;
        private int deltaY;
        private BufferedImage clientImage = null;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (clientImage != null) {
                g2d.drawImage(clientImage, 0, 0, getWidth(), getHeight(), this);
            }

            drawCenteredStringWithBackground(g2d, id, 0, 0, getWidth(), getHeight());
        }

        private void drawCenteredStringWithBackground(Graphics2D g, String text, int x, int y, int width, int height) {
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getAscent();
            int rectX = (width - textWidth) / 2 - 5; // 5px padding
            int rectY = (height - textHeight) / 2 + fm.getAscent() - textHeight - 5; // 5px padding
            int rectWidth = textWidth + 10; // 10px total padding
            int rectHeight = textHeight + 10; // 10px total padding

            // Draw the text background
            g.setColor(Color.WHITE);
            g.fillRect(rectX, rectY, rectWidth, rectHeight);

            // Draw the client ID
            int textX = x + (width - textWidth) / 2;
            int textY = y + (height - fm.getDescent()) / 2 + fm.getAscent();
            g.setColor(Color.BLACK);
            g.drawString(text, textX, textY);
        }


        public void setPosition(int x, int y) {
            this.posX = x;
            this.posY = y;
            setBounds(x, y, getWidth(), getHeight());
            repaint();
        }

        public ClientFigure(int posX, int posY, String id) {
            this.posX = posX;
            this.posY = posY;
            this.id = id;
            try {
                URL imageUrl = getClass().getResource("client.png");
                clientImage = ImageIO.read(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            setOpaque(false);
            setPreferredSize(new Dimension(20, 20)); // Assuming each client is represented by a 20x20 area
        }

        private void drawCenteredString(Graphics2D g, String text, int x, int y, int width, int height) {
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getAscent();
            int textX = x + (width - textWidth) / 2;
            int textY = y + ((height - fm.getHeight()) / 2) + fm.getAscent();
            g.drawString(text, textX, textY);
        }
    }

    private static class TicketBoxFigure extends JComponent {
        private final int posX;
        private final int posY;
        private final int count;
        private final int id;
        public boolean isOpen;
        public boolean isReserved;

        public TicketBoxFigure(int posX, int posY, int id, boolean isOpen, int count, boolean isReserved) {
            this.posX = posX;
            this.posY = posY;
            this.count = count;
            this.id = id;
            this.isOpen = isOpen;
            this.isReserved = isReserved;

            setOpaque(false);
            // The height now includes space for circles
            setPreferredSize(new Dimension(90, 75)); // Fixed width and height
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            BufferedImage boxImage = null;
            try {
                URL imageUrl;
                if (isReserved) {
                    imageUrl = getClass().getResource("rbox.png");
                } else{
                    imageUrl = getClass().getResource("box.png");
                }
                boxImage = ImageIO.read(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (boxImage != null) {
                Image scaledImage = boxImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                g2d.drawImage(scaledImage, 0, 0, this);
            } else {
                if (isReserved) {
                    g2d.setColor(new Color(144, 138, 44));
                } else if (isOpen) {
                    g2d.setColor(new Color(144, 238, 144)); // Light green color
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                    // Draw "Disabled" text if needed
                }
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
            }

            // Решта коду залишається незмінною
            g2d.setColor(Color.BLACK);
            drawCenteredString(g2d, "TB " + id, 0, 10, getWidth(), getHeight());
            drawCircleWithCount(g2d, this.count, getWidth());
        }

    }

    private static class EntranceFigure extends JComponent {
        private final int posX;
        private final int posY;
        private final int id;
        private final boolean isOpen;
        private final List<String> clientIds;
        private final int count;
        public EntranceFigure(int posX, int posY, int id, boolean isOpen, List<String> clientIds) {
            this.posX = posX;
            this.posY = posY;
            this.id = id;
            this.isOpen = isOpen;
            this.clientIds = clientIds;
            this.count = this.clientIds.size();
            setOpaque(false);
            // The height now includes space for circles
            //setPreferredSize(new Dimension(90, 75)); // Fixed width and height
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            BufferedImage image;
            try {
                // Завантаження зображення з ресурсів (змініть шлях до вашого файлу)
                URL imageUrl = getClass().getResource("door.png");
                image = ImageIO.read(imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
                image = null;
            }
            if (image != null) {
                if (!isOpen) {
                    // Встановлення напівпрозорості
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
                }
                // Відображення зображення
                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                // Повернення до повної непрозорості
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            }

            // Додатковий код для відображення тексту
            g2d.setColor(Color.BLACK);
            if (!isOpen) {
                drawCenteredString(g2d, "Disabled", 0, -10, getWidth(), getHeight());
            }
            drawCenteredString(g2d, "EN " + id, 0, 0, getWidth(), getHeight());
            // Тут ваш код для drawCircleWithCount...
             drawCircleWithCount(g2d, this.count, getWidth());
        }
    }
    private static void drawCenteredString(Graphics2D g, String text, int x, int y, int width, int height) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();

        // Вирахування позиції для тексту
        int textX = x + (width - textWidth) / 2;
        int textY = y + (height - fm.getHeight()) / 2 + fm.getAscent();

        // Вирахування позиції для прямокутника заливки
        int rectX = textX - 5; // 5 пікселів padding зліва
        int rectY = textY - textHeight; // Вирахування верхньої координати
        int rectWidth = textWidth + 10; // Додавання 5 пікселів padding з обох сторін
        int rectHeight = textHeight + 5; // Додавання 5 пікселів padding знизу

        // Малюємо білий прямокутник позаду тексту
        g.setColor(Color.WHITE);
        g.fillRect(rectX, rectY, rectWidth, rectHeight);

        // Малюємо текст поверх прямокутника
        g.setColor(Color.BLACK);
        g.drawString(text, textX, textY);
    }


    private static void drawCircleWithCount(Graphics2D g2d, int count, int width) {
        // Constants for the circle
        int circleDiameter = 20; // Diameter of the circle
        int circleX = width - circleDiameter - 5; // Position the circle at the right edge
        int circleY = 5; // A small margin from the top edge

        // Draw the circle with the count
        g2d.setColor(Color.BLUE);
        g2d.fillOval(circleX, circleY, circleDiameter, circleDiameter);

        // Draw the count inside the circle
        g2d.setColor(Color.WHITE);
        String countText = String.valueOf(count);
        drawCenteredString(g2d, countText, circleX, circleY, circleDiameter, circleDiameter);
    }
}