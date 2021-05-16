package vue;

import javax.swing.*;

import ai.Configuration;

import java.awt.*;

public class NodeView extends JButton {
    private static final long serialVersionUID = 1L;
	public Point p;
    private Configuration configuration = Configuration.getConfiguration();
    public static Color c = Color.white;

    // condtruction of the case of the square game
    
    public NodeView(Point p) {
        this.p = p;
        setBackground(c);
        setForeground(Color.BLACK);
        setFont(new Font("Sans Serif", Font.BOLD ,80));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(450/configuration.getSize(), 450/configuration.getSize());
    }
}
