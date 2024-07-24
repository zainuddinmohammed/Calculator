package com.example.calculator;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Button {

    private double X;
    private double Y;

    private double WIDTH;
    private double HEIGHT;

    private Rectangle button;
        private Color BUTTON_COLOR = Color.SANDYBROWN;
        public double OPACITY = 0.5;

    private Text icon;
        private String [] icons = {

                "C", "±", ".", "=",

                "1", "2", "3", "+",

                "4", "5", "6", "-",

                "7", "8", "9", "×",

                "<", "0", "@", "÷",

        };
        private Color ICON_COLOR = Color.CORAL;
        private double ICON_SIZE;

    private int ORDER;

    public Button(double x, double y, double width, double height, int order) {

        this.X = x;
        this.Y = y;

        this.WIDTH = width;
        this.HEIGHT = height;

        this.ICON_SIZE = this.WIDTH - (this.WIDTH / 7.5);

        this.button = new Rectangle(this.X, this.Y, this.WIDTH, this.HEIGHT);
        this.button.setFill(this.BUTTON_COLOR);
        this.button.setOpacity(this.OPACITY);

        this.ORDER = order;

        this.setIcon();

    }

    public Rectangle getButton() { return button; }

    private void setIcon() {

        this.icon = new Text();

        this.icon.setText(getIcon());

        this.icon.setTextOrigin(VPos.CENTER);

        this.icon.setFont(Font.font("Courier New", this.ICON_SIZE));
        this.icon.setFill(this.ICON_COLOR);

        this.icon.setTextAlignment(TextAlignment.CENTER);

    }

    public void setTextPosition(double x, double y, double answer_bgnd_height) {
        this.icon.setX((this.WIDTH / 4) + x);
        this.icon.setY(answer_bgnd_height + (this.HEIGHT / 2) + y);
    }

    public Text getText() {
        return this.icon;
    }

    public String getIcon() {
        return icons[this.ORDER];
    }

}
