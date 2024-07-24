package com.example.calculator;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class Calculator extends Application {

    private final String [] parts = new String[2];
    private String part = "";
    private final Text partText = new Text("");
    private boolean isDecimal = false;
    private char currentOperation = 0;
    private String answer = "";

    @Override
    public void start(Stage stage) {

        final double CALCULATOR_WIDTH = 500;
            final double CALCULATOR_WIDTH_OFFSET = 14;
        final double CALCULATOR_HEIGHT = 750;
            final double CALCULATOR_HEIGHT_OFFSET = 36;

        final double ANSWER_BGND_HEIGHT = 150;

        final double STAGE_X = 1150;
        final double STAGE_Y = 40;

        final int BUTTONS_ACROSS = 4;
        final int BUTTONS_HIGH = 5;
        final int TOTAL_BUTTONS = BUTTONS_ACROSS * BUTTONS_HIGH;

        final double GAP = 10;

        final double BUTTON_WIDTH = ((CALCULATOR_WIDTH - GAP) / BUTTONS_ACROSS) - GAP;
        final double BUTTON_HEIGHT = ((CALCULATOR_HEIGHT - ANSWER_BGND_HEIGHT - GAP) / BUTTONS_HIGH) - GAP;

        final int MAX_CHARACTERS = 15;

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

                                 0.0,0.0,           CALCULATOR_WIDTH,0.0,
//                                        ._______.
//                                         \     /
//                                          \   /
//                                           \ /
//                                            v
                              (CALCULATOR_WIDTH/2),ANSWER_BGND_HEIGHT

        );

        answer_background_triangle.setFill(Color.PALETURQUOISE);

        Polygon bottom_background_triangle = new Polygon();

        bottom_background_triangle.getPoints().setAll(

                           (CALCULATOR_WIDTH/2), ANSWER_BGND_HEIGHT,
//                                            ^
//                                           / \
//                                          /   \
//                                         /_____\
                   0.0, CALCULATOR_HEIGHT,         CALCULATOR_WIDTH, CALCULATOR_HEIGHT

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

        Button [] buttons = new Button[TOTAL_BUTTONS];
        int btnNum = 0;

        for(int i = 0; i < BUTTONS_HIGH; i++) {
            for(int j = 0; j < BUTTONS_ACROSS; j++) {

                buttons[btnNum] = new Button(x_current, y_current, BUTTON_WIDTH, BUTTON_HEIGHT, btnNum);


                buttons[btnNum].setTextPosition(
                        (BUTTON_WIDTH + GAP) * j, (BUTTON_HEIGHT + GAP) * i,
                        ANSWER_BGND_HEIGHT + 7
                );

                root.getChildren().add(buttons[btnNum].getText());
                root.getChildren().add(buttons[btnNum].getButton());

                x_current += BUTTON_WIDTH + GAP;

                btnNum++;

            }
            x_current = GAP;
            y_current += BUTTON_HEIGHT + GAP;
        }

        partText.setX(GAP * 2);
        partText.setY(GAP * 2);
        root.getChildren().add(partText);

        int button_offset = 0;

        for(int i = 0; i < 10; i++) {

            if(i == 9) {
                button_offset += 2;
            }
            else if(i % 3 == 0 && i != 0) {
                button_offset += 1;
            }

            int number_offset = 1;
                if(i == 9) {
                    number_offset = -9;
                }
                final int number = i + number_offset;

            buttons[i + 4 + button_offset].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                if (part.length() < MAX_CHARACTERS) {

                    part += Integer.toString(number);
                    partText.setText(part);

                }

            });

        }

        buttons[0].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            part = "";

            parts[0] = null;
            parts[1] = null;

            isDecimal = false;

            partText.setText(part);

        });

        buttons[1].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            if (!part.isEmpty()) {
                if (part.charAt(0) == '-') {
                    part = part.substring(1);
                    partText.setText(part);
                } else {
                    part = "-" + part;
                    partText.setText(part);
                }
            } else if (!answer.isEmpty()) {
                if (answer.charAt(0) == '-') {
                    answer = answer.substring(1);
                    partText.setText(answer);
                } else {
                    answer = "-" + answer;
                    partText.setText(answer);
                }
            }

        });

        buttons[16].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            if (!Objects.equals(part, "")) {
                part = part.substring(0, part.length() - 1);
                partText.setText(part);
            }

        });

        buttons[2].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            if(!isDecimal) { if(part.isEmpty()) { part += "0."; } else { part += "."; } }
            isDecimal = true;
            partText.setText(part);

        });


        buttons[18].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            part = answer;
            partText.setText(part);

        });


        buttons[7].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            currentOperation = '+';

            if (part.isEmpty()) {
                parts[0] = answer;
                partText.setText(parts[0]);
            }

            else if (parts[0] == null && parts[1] == null) {
                parts[0] = part;
                part = "";
                partText.setText(part);
                isDecimal = false;
            }

            else if (parts[0] != null && parts[1] == null) {

                parts[1] = part;

                double partNumber1 = Double.parseDouble(parts[0]);
                double partNumber2 = Double.parseDouble(parts[1]);

                double result = partNumber1 + partNumber2;

                parts[0] = Double.toString(result);

                if(parts[0].endsWith(".0")) {
                    parts[0] = parts[0].substring(0, parts[0].length() - 2);
                }

                partText.setText(parts[0]);

                parts[1] = null;
                part = "";
                isDecimal = false;

            }

        });

        buttons[11].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            currentOperation = '-';

            if (part.isEmpty()) {
                parts[0] = answer;
                partText.setText(parts[0]);
            }

            else if (parts[0] == null && parts[1] == null) {
                parts[0] = part;
                part = "";
                partText.setText(part);
                isDecimal = false;
            }

            else if (parts[0] != null && parts[1] == null) {

                parts[1] = part;

                double partNumber1 = Double.parseDouble(parts[0]);
                double partNumber2 = Double.parseDouble(parts[1]);

                double result = partNumber1 - partNumber2;

                parts[0] = Double.toString(result);

                if(parts[0].endsWith(".0")) {
                    parts[0] = parts[0].substring(0, parts[0].length() - 2);
                }

                partText.setText(parts[0]);

                parts[1] = null;
                part = "";
                isDecimal = false;

            }

        });

        buttons[15].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            currentOperation = '×';

            if (part.isEmpty()) {
                parts[0] = answer;
                partText.setText(parts[0]);
            }

            else if (parts[0] == null && parts[1] == null) {
                parts[0] = part;
                part = "";
                partText.setText(part);
                isDecimal = false;
            }

            else if (parts[0] != null && parts[1] == null) {

                parts[1] = part;

                double partNumber1 = Double.parseDouble(parts[0]);
                double partNumber2 = Double.parseDouble(parts[1]);

                double result = partNumber1 * partNumber2;

                parts[0] = Double.toString(result);

                if(parts[0].endsWith(".0")) {
                    parts[0] = parts[0].substring(0, parts[0].length() - 2);
                }

                partText.setText(parts[0]);

                parts[1] = null;
                part = "";
                isDecimal = false;

            }

        });

        buttons[19].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            currentOperation = '÷';

            if (part.isEmpty()) {
                parts[0] = answer;
                partText.setText(parts[0]);
            }

            else if (parts[0] == null && parts[1] == null) {
                parts[0] = part;
                part = "";
                partText.setText(part);
                isDecimal = false;
            }

            else if (parts[0] != null && parts[1] == null) {

                parts[1] = part;

                double partNumber1 = Double.parseDouble(parts[0]);
                double partNumber2 = Double.parseDouble(parts[1]);

                double result = partNumber1 / partNumber2;

                parts[0] = Double.toString(result);

                if(parts[0].endsWith(".0")) {
                    parts[0] = parts[0].substring(0, parts[0].length() - 2);
                }

                partText.setText(parts[0]);

                parts[1] = null;
                part = "";
                isDecimal = false;

            }

        });

        buttons[3].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            if (currentOperation == '+') {

                parts[1] = part;

                double partNumber1 = 0;
                double partNumber2 = 0;

                if (parts[0] != null) { partNumber1 = Double.parseDouble(parts[0]); }
                if (parts[1] != null && !part.isEmpty()) { partNumber2 = Double.parseDouble(parts[1]); }

                double result = partNumber1 + partNumber2;
                String resultStr = Double.toString(result);

                if(resultStr.endsWith(".0")) {
                    resultStr = resultStr.substring(0, resultStr.length() - 2);
                }

                partText.setText(resultStr);

                currentOperation = '0';

                part = "";

                parts[0] = null;
                parts[1] = null;

                answer = resultStr;

                isDecimal = false;

            }

            if (currentOperation == '-') {

                parts[1] = part;

                double partNumber1 = 0;
                double partNumber2 = 0;

                if (parts[0] != null) { partNumber1 = Double.parseDouble(parts[0]); }
                if (parts[1] != null && !part.isEmpty()) { partNumber2 = Double.parseDouble(parts[1]); }

                double result = partNumber1 - partNumber2;
                String resultStr = Double.toString(result);

                if(resultStr.endsWith(".0")) {
                    resultStr = resultStr.substring(0, resultStr.length() - 2);
                }

                partText.setText(resultStr);

                currentOperation = '0';

                part = "";

                parts[0] = null;
                parts[1] = null;

                answer = resultStr;

                isDecimal = false;

            }

            if (currentOperation == '×') {

                parts[1] = part;

                double partNumber1 = 0;
                double partNumber2 = 0;

                if (parts[0] != null) { partNumber1 = Double.parseDouble(parts[0]); }
                if (parts[1] != null && !part.isEmpty()) { partNumber2 = Double.parseDouble(parts[1]); }

                double result = partNumber1 * partNumber2;
                String resultStr = Double.toString(result);

                if(resultStr.endsWith(".0")) {
                    resultStr = resultStr.substring(0, resultStr.length() - 2);
                }

                partText.setText(resultStr);

                currentOperation = '0';

                part = "";

                parts[0] = null;
                parts[1] = null;

                answer = resultStr;

                isDecimal = false;

            }

            if (currentOperation == '÷') {

                parts[1] = part;

                double partNumber1 = 0;
                double partNumber2 = 0;

                if (parts[0] != null) { partNumber1 = Double.parseDouble(parts[0]); }
                if (parts[1] != null && !part.isEmpty()) { partNumber2 = Double.parseDouble(parts[1]); }

                double result = partNumber1 / partNumber2;
                String resultStr = Double.toString(result);

                if(resultStr.endsWith(".0")) {
                    resultStr = resultStr.substring(0, resultStr.length() - 2);
                }

                partText.setText(resultStr);

                currentOperation = '0';

                part = "";

                parts[0] = null;
                parts[1] = null;

                answer = resultStr;

                isDecimal = false;

            }

        });


        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}