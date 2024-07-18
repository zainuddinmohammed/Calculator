package com.example.calculator;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        final double CALCULATOR_WIDTH = 500;
            final double CALCULATOR_WIDTH_OFFSET = 14;
        final double CALCULATOR_HEIGHT = 750;
            final double CALCULATOR_HEIGHT_OFFSET = 36;

        final double ANSWER_BGND_HEIGHT = 150;

        final double STAGE_X = 1150;
        final double STAGE_Y = 40;

        final int BUTTONS_WIDTH = 4;
        final int BUTTONS_HEIGHT = 5;
        final int TOTAL_BUTTONS = BUTTONS_WIDTH * BUTTONS_HEIGHT;

        final double GAP = 10;

        final double BUTTON_WIDTH = ((CALCULATOR_WIDTH - GAP) / BUTTONS_WIDTH) - GAP;
        final double BUTTON_HEIGHT = ((CALCULATOR_HEIGHT - ANSWER_BGND_HEIGHT - GAP) / BUTTONS_HEIGHT) - GAP;

        Group root = new Group();

        Scene scene = new Scene(root, Color.LEMONCHIFFON);

        stage.setTitle("Calculator");

        Image icon = new Image("calculator.png");

        stage.getIcons().add(icon);


        stage.setWidth(CALCULATOR_WIDTH + CALCULATOR_WIDTH_OFFSET);
        stage.setHeight(CALCULATOR_HEIGHT + CALCULATOR_HEIGHT_OFFSET);

        stage.setResizable(false);

        stage.setX(STAGE_X);
        stage.setY(STAGE_Y);

        Rectangle bg_rect_top = new Rectangle();

        bg_rect_top.setX(0);
        bg_rect_top.setY(0);

        bg_rect_top.setWidth(500);
        bg_rect_top.setHeight(ANSWER_BGND_HEIGHT);

        bg_rect_top.setFill(Color.MEDIUMTURQUOISE);


        Polygon answer_background_triangle = new Polygon();

        answer_background_triangle.getPoints().setAll(

                                 0.0,0.0, CALCULATOR_WIDTH,0.0,
//                                        ._______.
//                                         \     /
//                                          \   /
//                                           \ /
//                                            v
                              (CALCULATOR_WIDTH / 2), ANSWER_BGND_HEIGHT

        );

        answer_background_triangle.setFill(Color.PALETURQUOISE);

        Polygon bottom_background_triangle = new Polygon();

        bottom_background_triangle.getPoints().setAll(

                             (CALCULATOR_WIDTH / 2), ANSWER_BGND_HEIGHT,
//                                            ^
//                                           / \
//                                          /   \
//                                         /_____\
                         0.0, CALCULATOR_HEIGHT, CALCULATOR_WIDTH, CALCULATOR_HEIGHT

        );

        bottom_background_triangle.setFill(Color.LIGHTYELLOW);


        root.getChildren().add(bg_rect_top);
        root.getChildren().add(answer_background_triangle);
        root.getChildren().add(bottom_background_triangle);


        Rectangle answer_bar = new Rectangle();
        answer_bar.setX(GAP);
        answer_bar.setY(GAP);
        answer_bar.setWidth(CALCULATOR_WIDTH - GAP - GAP);
        answer_bar.setHeight(ANSWER_BGND_HEIGHT - GAP - GAP);

        answer_bar.setFill(Color.SEASHELL);
        answer_bar.setOpacity(0.5);

        root.getChildren().add(answer_bar);

        double x_current = GAP;
        double y_current = ANSWER_BGND_HEIGHT + GAP;



        Rectangle [] buttons = new Rectangle[TOTAL_BUTTONS];
        int button_counter = 0;

        for(int i = 0; i < BUTTONS_HEIGHT; i++) {
            for(int j = 0; j < BUTTONS_WIDTH; j++) {

                buttons[button_counter] = new Rectangle(x_current, y_current, BUTTON_WIDTH, BUTTON_HEIGHT);

                buttons[button_counter].setFill(Color.SANDYBROWN);
                buttons[button_counter].setOpacity(0.5);

                root.getChildren().add(buttons[button_counter]);

                x_current += BUTTON_WIDTH + GAP;

                button_counter++;

            }
            x_current = GAP;
            y_current += BUTTON_HEIGHT + GAP;
        }



        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}