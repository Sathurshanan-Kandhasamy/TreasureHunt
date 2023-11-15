package TreasureHunt;

import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class FileManager
{
    String saveFile = "GameSave.csv";

    public void SaveDataToFile(GameData gameData)
    {
        try
        {
            BufferedWriter buffer = new BufferedWriter(new FileWriter(saveFile));

            buffer.write(gameData.treasurePosition.width + "," + gameData.treasurePosition.height);
            buffer.newLine();
            buffer.write(gameData.guessNumber +"");
            buffer.newLine();
            buffer.write(gameData.lastGuessDistance + "");
            buffer.newLine();

            for (int x = 0; x < gameData.gameGrid.length; x++)
            {
                for (int y = 0; y < gameData.gameGrid[x].length; y++)
                {
                    if (gameData.gameGrid[x][y].text.isEmpty())
                    {
                        continue;
                    }
                    buffer.write(x + "," + y + "," + gameData.gameGrid[x][y].text + "," + gameData.gameGrid[x][y].Colour);
                    buffer.newLine();
                }
            }

            buffer.close();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public GameData ReadDataFromFile()
    {
        try
        {
            //Create an empty gameData object to hold our file data as it is read in
            GameData data = new GameData();
            //Create the reader to perform the file reading
            BufferedReader buffer = new BufferedReader(new FileReader(saveFile));
            //Read the first line and split it by the comma and store it inan array
            String[] tempTreasure = buffer.readLine().split(",");
            //Convert then pass the array values into a new dimension object to set the treasure position
            data.treasurePosition = new Dimension(Integer.parseInt(tempTreasure[0]),Integer.parseInt(tempTreasure[1]));
            //Read the next line and convert it before passing iot to the game data.
            data.guessNumber = Integer.parseInt(buffer.readLine());
            //Read the next line and convert it before passing iot to the game data.
            data.lastGuessDistance = Double.parseDouble(buffer.readLine());

            //Create a new 2D array to store our grid cell data from the file.
            GridCellData[][] grid = new GridCellData[10][10];

            String line;
            while ((line = buffer.readLine()) != null)
            {
                //Split the current line and store its values
                String[] temp = line.split(",");
                //Convert the first 2 values into integers that represent the grid coordinates
                int col = Integer.parseInt(temp[0]);
                int row = Integer.parseInt(temp[1]);
                //Create a new GridCellData object at the retrieved coordinates
                grid[col][row] = new GridCellData();
                //Set the values of that coordinates using the values of the split line
                grid[col][row].text = temp[2];
                grid[col][row].Colour = temp[3];
            }
            //Pass the grid into the game data object
            data.gameGrid = grid;
            //CLose the reader to allow the file to finish processing
            buffer.close();
            //Return the completed data.
            return data;
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            //Return null if something went wrong.
            return null;
        }
    }
}
