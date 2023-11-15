/**
 * Simple treasure hunt game where the player presses buttons in an attempt to find the location of the treasure.
 * After each press the game lets the player know hotter or colder depending on whether the most recent press
 * is closer or further than the one before.
 */
package TreasureHunt;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

/**
 * The main game screen of the application where all user interaction takes place.
 */
public class GameScreen extends JFrame implements ActionListener
{
    FileManager file = new FileManager();
    JLabel lblTitle;
    JButton btnReset;
    JButton btnLoad,btnSave;

    //An array of custom game button components - See GameButton Class
    GameButton[][] gameButtons = new GameButton[10][10];
    SpringLayout myLayout = new SpringLayout();

    //Random class to generate random numbers for treasure location.
    Random rand = new Random();
    GameButton treasure;
    //Distance of the treasure from the last button pressed.
    double lastGuessDistance = 100;
    int guessNumber = 1;

    /**
     * Primary constructor of GameScreen. Initiates GUI building and setup of the game state for the first game.
     */
    public GameScreen()
    {
        //Basic Frame Setup
        setSize(400,425);
        setLocation(400,200);
        setLayout(myLayout);

        //GUI Component building
        SetupHeaderLabel();
        btnReset = ComponentBuilder.CreateAButton("RESET",80,25,300,350,this,myLayout,this);
        btnLoad = ComponentBuilder.CreateAButton("Load",80,25,10,350,this,myLayout,this);
        btnSave = ComponentBuilder.CreateAButton("Save",80,25,100,350,this,myLayout,this);
        BuildGameButtonGrid();


        //Listener to look for Window Closing event triggered by pressing window close button.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                System.exit(0);
            }
        });

        //Sets treasure position for first game.
        ResetTreasurePosition();

        //Final Frame Setup and Opening.
        setResizable(false);
        setVisible(true);
    }

    /**
     * Action listener handler for button press events. Checks which button has been pressed and performs the
     * relevant logic for the press.
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        //Activates if btnReset button is pressed.
        if (e.getSource() == btnReset)
        {
            ResetTreasurePosition();
            ResetButtonsToDefault();
        }

        //Gets the class type of the event source component and compares it against the
        //class type GameButton. If event source is a GameButton type object the enclosed functionality executes.
        if (e.getSource().getClass().equals(GameButton.class))
        {
            //Grabs the source GameButton of the event
            GameButton button = (GameButton)e.getSource();
            //Calculates the unit distance(button distance) to the treasure target from the pressed button.
            double distance = CalculateDistanceToTarget(button);
            //Prints and then increments the current guess count.
            button.setText(""+guessNumber);
            guessNumber++;
            //Provided user feedback
            CheckWhetherHotterOrColder(button, distance);
            CheckForWin(button);
            //Updates the stored guess distance for the next press.
            lastGuessDistance = distance;
        }

        if (e.getSource() == btnSave)
        {
            GameData game = new GameData();
            game.guessNumber = guessNumber;
            game.lastGuessDistance = lastGuessDistance;
            game.treasurePosition = new Dimension(treasure.xPosition, treasure.yPosition);

            GridCellData[][] grid = new GridCellData[10][10];
            //Cycle through the Game grid, so we can read its values and save them to the data model.
            for (int x = 0; x < gameButtons.length; x++)
            {
                for (int y = 0; y < gameButtons[x].length; y++)
                {
                    //Create the object inside the grid position, so we can give it values.
                    grid[x][y] = new GridCellData();
                    //Set the text value of the game button into the grid.
                    grid[x][y].text = gameButtons[x][y].getText();
                    //Get the background colour fo the grid position as a text value
                    String colourText = ConvertColourValueToText(gameButtons[x][y].getBackground());
                    //Set the colour text value into the grid.
                    grid[x][y].Colour = colourText;
                }
            }
            //Put the grid we just created into the game data model.
            game.gameGrid = grid;

            file.SaveDataToFile(game);
        }

        if (e.getSource() == btnLoad)
        {
            //Read the file to get the saved game data
            GameData gameData = file.ReadDataFromFile();
            //Use the dimension values from the gameData to set which grid position is the treasure cell
            treasure = gameButtons[gameData.treasurePosition.width][gameData.treasurePosition.height];
            //Set the counters from the previous game
            guessNumber = gameData.guessNumber;
            lastGuessDistance = gameData.lastGuessDistance;

            ResetButtonsToDefault();
            //Cycle through the game grid retrieved from the file
            for (int x = 0; x < gameData.gameGrid.length; x++)
            {
                for (int y = 0; y < gameData.gameGrid[x].length; y++)
                {
                    //Check ig the current game data cell is null or not
                    if (gameData.gameGrid[x][y] == null)
                    {
                        continue;
                    }
                    //Retrieve the game data cell text and place it in the game buttons grid at the same position.
                    String text = gameData.gameGrid[x][y].text;
                    gameButtons[x][y].setText(text);
                    //Retrieve the game data cell colour and place it in the game buttons grid at the same position.
                    //This will require converting the colour text name to an official colour value.
                    Color colour = ConvertColourTextToValue(gameData.gameGrid[x][y].Colour);
                    gameButtons[x][y].setBackground(colour);
                }
            }
        }
    }

    private String ConvertColourValueToText(Color colourValue)
    {
        if (colourValue == Color.red)
        {
            return "RED";
        }
        else if(colourValue == Color.cyan)
        {
            return  "BLUE";
        }
        else
        {
            return "WHITE";
        }
    }

    private Color ConvertColourTextToValue(String colourText)
    {
        if (colourText.equalsIgnoreCase("RED"))
        {
            return Color.red;
        }
        else if(colourText.equalsIgnoreCase("BLUE"))
        {
            return Color.cyan;
        }
        else
        {
            return Color.white;
        }
    }

    /**
     * Creates a form header with a defined border and colourised background at the top of the frame.
     */
    private void SetupHeaderLabel() {
        //Builds initial label
        lblTitle = ComponentBuilder.CreateALabel("Treasure Hunt",5,0,myLayout,this);
        //Colours background behind label with rectangular box area. To achieve this the label
        // must be set to opaque for the color to be viewed as the JLabel initial background state is transparent.
        //Setting the preferred size gives the label fixed dimensions otherwise the label defaults its size to
        //whatever is required for the text to show
        lblTitle.setPreferredSize(new Dimension(385,30));
        lblTitle.setOpaque(true);
        lblTitle.setBackground(Color.yellow);
        lblTitle.setForeground(Color.red);
        //Setting a new font for the label and aligning the text to be centralised in the label bounds.
        lblTitle.setFont(new Font("Arial", Font.BOLD|Font.ITALIC,20));
        lblTitle.setHorizontalAlignment(JLabel.CENTER);
        //Adds a plain line border with plain colouring.
        lblTitle.setBorder(new LineBorder(Color.black,2));
    }

    /**
     * Calculates a random X and Y coordinate to be used to determine the position of the treasure.
     * Stores treasure button into variable for use and resets distance and count values for the next game.
     */
    private void ResetTreasurePosition() {
        int xPos = rand.nextInt(10);
        int yPos = rand.nextInt(10);
        treasure = gameButtons[xPos][yPos];
        lastGuessDistance = 100;
        guessNumber = 1;
    }

    /**
     * Utilises a 2D Array to build a grid of interactable game button components to compose a game board. Each
     * button's position is calculated during the loop iterations and the position is stored into the individual
     * buttons to assist in later calculations.
     */
    private void BuildGameButtonGrid() {
        for (int x = 0; x < gameButtons.length; x++)
        {
            for (int y = 0; y < gameButtons[x].length; y++)
            {
                int buttonSize = 30;
                int xPos = 40 + x * buttonSize;
                int yPos = 40 + y * buttonSize;

                CreateAGameButton(x, y, buttonSize, xPos, yPos);
            }
        }
    }

    /**
     * Builds an individual GameButton object based upon the provided parameters and places it into the
     * game screen JFrame.
     *
     * @param x X coordinate position inside the 2D array.
     * @param y Y coordinate position inside the 2D array.
     * @param buttonSize The desired height and width size of the game buttons in the grid.
     * @param xPos The distance of the button from the left of the frame along the x-Axis.
     * @param yPos The distance of the button from the Top of the frame along the y-Axis.
     */
    private void CreateAGameButton(int x, int y, int buttonSize, int xPos, int yPos) {
        //Creates button and sets initial size and position on the board before adding
        //listener and adding to frame.
        gameButtons[x][y] = new GameButton();
        gameButtons[x][y].setPreferredSize(new Dimension(buttonSize,buttonSize));
        gameButtons[x][y].addActionListener(this);
        myLayout.putConstraint(SpringLayout.WEST,gameButtons[x][y],xPos,SpringLayout.WEST,this);
        myLayout.putConstraint(SpringLayout.NORTH,gameButtons[x][y],yPos,SpringLayout.NORTH,this);
        add(gameButtons[x][y]);
        //Stores button's grid position and resets it to the desired default visual styling.
        gameButtons[x][y].setxPosition(x);
        gameButtons[x][y].setyPosition(y);
        gameButtons[x][y].Reset();
    }


    /**
     * Calculates the distance of the provided button from the position of the treasure location.
     * This is done using Pythagoras' Theorem (a² = b² + c²)to determine the exact distance in units based upon
     * the unit size of one button width.
     *
     * @param button The button most recently pressed by the user.
     * @return The distance to the treasure in a double type.
     */
    private double CalculateDistanceToTarget(GameButton button) {
        double xDistance = Math.abs(treasure.getxPosition() - button.getxPosition());
        double yDistance = Math.abs(treasure.getyPosition() - button.getyPosition());
        double sides = (xDistance * xDistance) + (yDistance*yDistance);
        return Math.sqrt(sides);
    }

    /**
     * Compares the distance provided against the distance of the previous button press and determines
     * whether it is closer or further than the previous. If closer the provided button is highlighted in red(hotter),
     * otherwise it is changed blue(colder).
     *
     * @param button The last button pressed which needs to provide feedback to user.
     * @param distance The previously calculated distance of the button to the treasure.
     */
    private void CheckWhetherHotterOrColder(GameButton button, double distance) {
        if (distance < lastGuessDistance)
        {
            button.setBackground(Color.red);
        }
        else if(distance >= lastGuessDistance)
        {
            button.setBackground(Color.cyan);
        }
    }

    /**
     * Checks whether the pressed button is the same as the treasure button. If so it is changes gold and the dollar
     * sign ($) is printed onto the button.
     *
     * @param button The button last pressed which is being checked.
     */
    private void CheckForWin(GameButton button) {
        //Checks whether button coordinates match treasure coordinates
        if(treasure.getxPosition() == button.getxPosition() && treasure.getyPosition() == button.getyPosition())
        {
            button.setBackground(Color.yellow);
            button.setText("$");
        }
    }

    /**
     * Cycles through the GameButton grid and requests each button perform its Reset() function which clears all
     * text and sets the button styling and colouring back to its default state.
     */
    private void ResetButtonsToDefault() {
        for (int i = 0; i < gameButtons.length; i++)
        {
            for (int j = 0; j < gameButtons[i].length; j++)
            {
                gameButtons[i][j].Reset();
            }
        }
    }
}
