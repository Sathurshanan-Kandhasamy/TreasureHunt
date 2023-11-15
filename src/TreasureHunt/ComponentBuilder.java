/*
This Library is designed to assist in the development of UI based applications in Java by providing a series
of methods designed to build UI components based upon the given parameters such as size, location, text etc.
*/
package TreasureHunt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ComponentBuilder
{
    /**
     * This method takes the parameters given within the method call to generate a JLabel
     * component and locate it using a SpringLayout and using the frame as the reference for the positioning process.
     * The label will be given text to display and a location on screen,
     * before being returned to the caller in working fashion. The size of the label will be set to whatever size is needed
     * to display the given text in the current font setting..
     *
     * @param text      the text to be printed on the label
     * @param xPos      the distance in pixels from the top left corner along the X-axis of the form
     * @param yPos      the distance in pixels from the top left corner along the Y-axis of the form
     * @param layout    the SpringLayout component used for positioning label
     * @param frame     the JFrame the label is being added to which is also acting as the reference for the positioning.
     *
     * @return          the completed label is returned to the caller once configured.
     */
    public static JLabel CreateALabel(String text, int xPos, int yPos, SpringLayout layout, JFrame frame)
    {
        JLabel myNewLabel = new JLabel(text);
        layout.putConstraint(SpringLayout.WEST,myNewLabel,xPos,SpringLayout.WEST,frame);
        layout.putConstraint(SpringLayout.NORTH,myNewLabel,yPos,SpringLayout.NORTH,frame);
        frame.add(myNewLabel);
        return myNewLabel;
    }

    /**
     * This method takes the parameters given within the method call to generate a JLabel
     * component and locate it using a SpringLayout. This variation of the method will take a Component reference
     * to allow the JLabel being created to be positioned using another component as its anchor.
     * The label will be given text to display and a location on screen,
     * before being returned to the caller in working fashion. The size of the label will be set to whatever size is needed
     * to display the given text in the current font setting.
     *
     * @param text      the text to be printed on the label
     * @param xPos      the distance in pixels from the top left corner along the X-axis of the form
     * @param yPos      the distance in pixels from the top left corner along the Y-axis of the form
     * @param layout    the SpringLayout component used for positioning label
     * @param frame     the JFrame the label is being added to.
     * @param component the component from which this components position is to be derived.
     *
     * @return          the completed label is returned to the caller once configured.
     */
    public static JLabel CreateALabel(String text, int xPos, int yPos, SpringLayout layout,
                                      JFrame frame, Component component)
    {
        JLabel myNewLabel = new JLabel(text);
        layout.putConstraint(SpringLayout.WEST,myNewLabel,xPos,SpringLayout.WEST,component);
        layout.putConstraint(SpringLayout.NORTH,myNewLabel,yPos,SpringLayout.NORTH,component);
        frame.add(myNewLabel);
        return myNewLabel;
    }

    /**
     * This method takes the parameters given within the method call to generate a JButton
     * component and locate it using a SpringLayout and using the frame as the reference for the positioning process.
     * The button will be given a size, location, button text and action listener and then be returned to the caller in working fashion.
     *
     * @param text      the text to be written on the button
     * @param width     the desired width of the button in units
     * @param height    the desired height of the button in units
     * @param xPos      the distance in pixels from the top left corner along the X-axis of the form
     * @param yPos      the distance in pixels from the top left corner along the Y-axis of the form
     * @param layout    the SpringLayout component used for positioning the button
     * @param frame     the JFrame the button is being added to which is also acting as the reference for the positioning..
     * @param listener  the ActionListener that the button sends messages to when pressed.
     *
     * @return          the completed button is returned to the caller once configured.
     */
    public static JButton CreateAButton(String text, int width, int height, int xPos, int yPos,
                                        ActionListener listener, SpringLayout layout, JFrame frame)
    {
        JButton myNewButton = new JButton(text);
        myNewButton.addActionListener(listener);
        myNewButton.setPreferredSize(new Dimension(width,height));
        layout.putConstraint(SpringLayout.WEST,myNewButton,xPos,SpringLayout.WEST,frame);
        layout.putConstraint(SpringLayout.NORTH,myNewButton,yPos,SpringLayout.NORTH,frame);
        frame.add(myNewButton);
        return myNewButton;
    }

    /**
     * This method takes the parameters given within the method call to generate a JButton
     * component and locate it using a SpringLayout. This variation of the method will take a Component reference
     * to allow the JButton being created to be positioned using another component as its anchor.
     * The button will be given a size, location, button text and action listener and then be returned to the caller in working fashion.
     *
     * @param text      the text to be written on the button
     * @param width     the desired width of the button in units
     * @param height    the desired height of the button in units
     * @param xPos      the distance in pixels from the top left corner along the X-axis of the form
     * @param yPos      the distance in pixels from the top left corner along the Y-axis of the form
     * @param layout    the SpringLayout component used for positioning the button
     * @param frame     the JFrame the button is being added to.
     * @param listener  the ActionListener that the button sends messages to when pressed.
     * @param component the component from which this components position is to be derived.
     *
     * @return          the completed button is returned to the caller once configured.
     */
    public static JButton CreateAButton(String text, int width, int height, int xPos, int yPos,
                                        ActionListener listener, SpringLayout layout, JFrame frame, Component component)
    {
        JButton myNewButton = new JButton(text);
        myNewButton.addActionListener(listener);
        myNewButton.setPreferredSize(new Dimension(width,height));
        layout.putConstraint(SpringLayout.WEST,myNewButton,xPos,SpringLayout.WEST,component);
        layout.putConstraint(SpringLayout.NORTH,myNewButton,yPos,SpringLayout.NORTH,component);
        frame.add(myNewButton);
        return myNewButton;
    }

    /**
     * This method takes the parameters given within the method call to generate a JTextField
     * component and locate it using a SpringLayout and using the frame as the reference for the positioning process.
     * The text field will be given a size and location, before being returned to the caller in working fashion.
     *
     * @param size  the number of columns the JTextField will be sized to visibly show. 1 column = width of lowercase letter 'm' for current font
     * @param xPos  the distance in pixels from the top left corner along the X-axis of the form
     * @param yPos      the distance in pixels from the top left corner along the Y-axis of the form
     * @param layout    the SpringLayout component used for positioning the text field
     * @param frame     the JFrame the text field is being added to which is also acting as the reference for the positioning..
     *
     * @return          the completed button is returned to the caller once configured.
     */
    public static JTextField CreateATextField(int size, int xPos, int yPos, SpringLayout layout, JFrame frame)
    {
        JTextField myNewTextField = new JTextField(size);
        layout.putConstraint(SpringLayout.WEST,myNewTextField,xPos,SpringLayout.WEST,frame);
        layout.putConstraint(SpringLayout.NORTH,myNewTextField,yPos,SpringLayout.NORTH,frame);
        frame.add(myNewTextField);
        return myNewTextField;
    }

    /**
     * This method takes the parameters given within the method call to generate a JTextField
     * component and locate it using a SpringLayout. This variation of the method will take a Component reference
     * to allow the JTextField being created to be positioned using another component as its anchor.
     * The text field will be given a size and location, before being returned to the caller in working fashion.
     *
     * @param size  the number of columns the JTextField will be sized to visibly show. 1 column = width of lowercase letter 'm' for current font
     * @param xPos  the distance in pixels from the top left corner along the X-axis of the form
     * @param yPos      the distance in pixels from the top left corner along the Y-axis of the form
     * @param layout    the SpringLayout component used for positioning the text field
     * @param frame     the JFrame the text field is being added to.
     * @param component the component from which this components position is to be derived.
     *
     * @return          the completed button is returned to the caller once configured.
     */
    public static JTextField CreateATextField(int size, int xPos, int yPos, SpringLayout layout,
                                              JFrame frame, Component component)
    {
        JTextField myNewTextField = new JTextField(size);
        layout.putConstraint(SpringLayout.WEST,myNewTextField,xPos,SpringLayout.WEST,component);
        layout.putConstraint(SpringLayout.NORTH,myNewTextField,yPos,SpringLayout.NORTH,component);
        frame.add(myNewTextField);
        return myNewTextField;
    }
}
