package com.example.calculator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Button extends Rectangle {

    private double X;
    private double Y;

    private double WIDTH;
    private double HEIGHT;

    private int VALUE;

    private Color COLOR = Color.AQUAMARINE;
    public double OPACITY = 0.5;

    public Button(double x, double y, double width, double height, int value) {

        this.X = x;
        this.Y = y;
        this.WIDTH = width;
        this.HEIGHT = height;
        this.VALUE = value;
        this.setFill(COLOR);
        this.setOpacity(OPACITY);

    }

    public int getVALUE() {
        return VALUE;
    }

}
