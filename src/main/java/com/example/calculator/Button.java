package com.example.calculator;

import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Button {

    private final double WIDTH;     // button width
    private final double HEIGHT;    // button height

    private final Rectangle buttonRectangle; // button design
    public double OPACITY = 0.5;    // button opacity

    private Text iconText;  // icon text that appears on button
        private final String [] icons = {   // list of icons organized

                "C", "±", ".", "=",

                "1", "2", "3", "+",

                "4", "5", "6", "-",

                "7", "8", "9", "×",

                "<", "0", "@", "÷",

        };
        private final Color ICON_COLOR = Color.CORAL; // color of icon
        private final double ICON_SIZE; // size of icon

    private final int ORDER; // button position in array

    // BUTTON CONSTRUCTOR: takes position, size, and array position as arguments
    public Button(double x, double y, double width, double height, int order) {

        this.WIDTH = width;     // set width
        this.HEIGHT = height;   // set height

        this.ICON_SIZE = this.WIDTH - (this.WIDTH / 7.5);   // set icon font size

        this.buttonRectangle = new Rectangle(x, y, this.WIDTH, this.HEIGHT); // set button position and size
        this.buttonRectangle.setFill(Color.SANDYBROWN);  // set button color
        this.buttonRectangle.setOpacity(this.OPACITY);   // set button opacity

        this.ORDER = order; // set button position in array

        this.setIcon(); // set button icon

    }

    // Get the button's rectangle
    public Rectangle getButtonRectangle() { return buttonRectangle; }

    // Set button icon
    private void setIcon() {

        this.iconText = new Text(); // create text for icon

        this.iconText.setText(getIconText()); // set text for iconText

        this.iconText.setTextOrigin(VPos.CENTER); // set text origin

        this.iconText.setFont(Font.font("Courier New", this.ICON_SIZE)); // set text font and size
        this.iconText.setFill(this.ICON_COLOR); // set text color

        this.iconText.setTextAlignment(TextAlignment.CENTER); // set text alignment

    }

    // Set text position
    public void setTextPosition(double x, double y, double answer_bgnd_height) {
        this.iconText.setX((this.WIDTH / 4) + x);                           // set x position
        this.iconText.setY(answer_bgnd_height + (this.HEIGHT / 2) + y);     // set y position
    }

    public Text getText() {
        return this.iconText;
    }   // return icon text object

    public String getIconText() {
        return icons[this.ORDER];
    }   // return icon string object

}
