package presentation.pages.simulationpage;

import presentation.viewmodels.stubs.ClientViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationArea extends JPanel {
    private Map<String, ClientFigure> clientFigureMap = new HashMap<>();
    private List<TicketBoxFigure> figures = new ArrayList<>();
    private List<EntranceFigure> entrancesFigures = new ArrayList<>();

    public SimulationArea() {
        setLayout(null); // Use absolute layout to place figures
    }

    private List<ClientFigure> clientFigures = new ArrayList<>();

    public void addClientFigure(int posX, int posY, String id) {
        ClientFigure clientFigure = new ClientFigure(posX, posY, id);
        add(clientFigure);
        clientFigure.setBounds(posX, posY, 20, 20);
        //clientFigures.add(clientFigure);
        clientFigureMap.put(id, clientFigure);
        repaint();
    }

    public void addTicketBoxFigure(int posX, int posY, int id, boolean isOpen, int count) {
        // Now pass this list to the TicketBoxFigure constructor
        TicketBoxFigure figure = new TicketBoxFigure(posX, posY, id, isOpen, count);
        figures.add(figure);
        add(figure);
        // Adjust the bounds as needed to fit all the client circles

        figure.setBounds(posX, posY, 90, 75);
        repaint();
    }

    public void addEntranceFigure(int posX, int posY, int id, boolean isOpen, List<String> clientsIds) {
        EntranceFigure entrance = new EntranceFigure(posX, posY, id, isOpen, clientsIds);
        entrancesFigures.add(entrance);
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

    public void animateClientMovement(String id, int newX, int newY) {
        ClientFigure clientFigure = clientFigureMap.get(id);
        if (clientFigure == null) {
            return;
        }
        Timer timer = new Timer(10, new ActionListener() {
            private int currentX = clientFigure.posX;
            private int currentY = clientFigure.posY;
            private int deltaX = (newX - currentX) / 10; // 10 кроків анімації
            private int deltaY = (newY - currentY) / 10;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Math.abs(currentX - newX) > Math.abs(deltaX) || Math.abs(currentY - newY) > Math.abs(deltaY)) {
                    clientFigure.setPosition(currentX, currentY);
                    currentX += deltaX;
                    currentY += deltaY;
                } else {
                    ((Timer) e.getSource()).stop();
                    clientFigure.setPosition(newX, newY);
                }
                repaint();
            }
        });
        timer.start();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (ClientFigure clientFigure : clientFigures) {
            clientFigure.paintComponent(g); // This will paint each client figure
        }
        for (EntranceFigure entr : entrancesFigures) {
            entr.paintComponent(g);
        }
    }

    public class ClientFigure extends JComponent {
        private int posX;
        private int posY;
        private final String id;


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
            setOpaque(false);
            setPreferredSize(new Dimension(20, 20)); // Assuming each client is represented by a 20x20 area
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw the client as a circle
            g2d.setColor(Color.BLUE);
            g2d.fillOval(0, 0, getWidth(), getHeight()); // Fill the oval with the size of the component

            // Draw the client ID inside the circle
            g2d.setColor(Color.WHITE);
            drawCenteredString(g2d, id, 0, 0, getWidth(), getHeight());
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
        private final boolean isOpen;

        public TicketBoxFigure(int posX, int posY, int id, boolean isOpen, int count) {
            this.posX = posX;
            this.posY = posY;
            this.count = count;
            this.id = id;
            this.isOpen = isOpen;
            setOpaque(false);
            // The height now includes space for circles
            setPreferredSize(new Dimension(90, 75)); // Fixed width and height
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Enable anti-aliasing for smoother shapes and text
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw the TicketBox rectangle
            if (isOpen) {
                g2d.setColor(new Color(144, 238, 144)); // Light green color
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // Fill the rectangle

            } else {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // Fill the rectangle

                // Draw Disabled if box is closed
                g2d.setColor(Color.BLACK);
                drawCenteredString(g2d, "Disabled", 0, -10, getWidth(), getHeight());

            }
            // Draw the TicketBox ID inside the rectangle
            g2d.setColor(Color.BLACK);
            drawCenteredString(g2d, "TB " + id, 0, 10, getWidth(), getHeight()); // Center the ID string in the rectangle


            // Constants for the circle
            int circleDiameter = 20; // Diameter of the circle
            int circleX = getWidth() - circleDiameter - 5; // Position the circle at the right edge
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

    private static class EntranceFigure extends JComponent {
        private final int posX;
        private final int posY;
        private final int id;
        private final boolean isOpen;
        private final List<String> clientIds;
        public EntranceFigure(int posX, int posY, int id, boolean isOpen, List<String> clientIds) {
            this.posX = posX;
            this.posY = posY;
            this.id = id;
            this.isOpen = isOpen;
            this.clientIds = clientIds;
            setOpaque(false);
            // The height now includes space for circles
            //setPreferredSize(new Dimension(90, 75)); // Fixed width and height
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (isOpen) {
                g2d.setColor(new Color(173, 216, 230)); // Light blue color
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // Fill the rectangle
            } else {
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // Fill the rectangle

                // Draw Disabled if entrance is closed
                g2d.setColor(Color.BLACK);
                drawCenteredString(g2d, "Disabled", 0, -10, getWidth(), getHeight());
            }
            g2d.setColor(Color.BLACK);
            drawCenteredString(g2d, "EN " + id, 0, 0, getWidth(), getHeight()); // Center the ID string in the rectangle

        }
    }
    private static void drawCenteredString(Graphics2D g, String text, int x, int y, int width, int height) {
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        int textX = x + (width - textWidth) / 2;
        int textY = y + (height - fm.getDescent()) / 2 + textHeight / 2;
        g.drawString(text, textX, textY);
    }
}