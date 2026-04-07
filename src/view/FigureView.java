package view;

import model.Figure;

import java.awt.*;

public class FigureView {
    private Figure figure;

    public FigureView(Figure figure) {
        this.figure = figure;
    }

    public void draw(Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval(x + 5, y + 5, 70, 70);

        g2d.setColor(figure.isWhite() ? Color.WHITE : Color.BLACK);
        g2d.fillOval(x + 3, y + 3, 74, 74);

        g2d.setColor(Color.BLACK);
        g2d.drawOval(x + 3, y + 3, 74, 74);

        // Дамочка
        if (figure.isQueen()) {
            g2d.setColor(figure.isWhite() ? Color.BLACK : Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            String q = "Q";
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(q);
            int textHeight = fm.getAscent();
            g2d.drawString(q, x + 40 - textWidth / 2, y + 50);
        }
    }
}