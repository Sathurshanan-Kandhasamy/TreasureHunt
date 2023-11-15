package TreasureHunt;

import javax.swing.*;
import java.awt.*;

/**
 * Custom button class that extends the default Java Swing JButton component class. This variant adds additional
 * variables to store internal data about the button and to provide a reset function to set the button back to pre-defined
 * style and colouring when triggered.
 */
public class GameButton extends JButton
{
    int xPosition = 0;
    int yPosition = 0;

    /**
     * Resets the button to a default style and colour setting.
     */
    public void Reset()
    {
        setMargin(new Insets(0,0,0,0));
        setText("");
        setBackground(Color.white);
    }

    //Default Getters and Setters.
    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }
}
