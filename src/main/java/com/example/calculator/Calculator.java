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

    final double CALCULATOR_WIDTH = 500;
        final double CALCULATOR_WIDTH_OFFSET = 14;
    final double CALCULATOR_HEIGHT = 750;
        final double CALCULATOR_HEIGHT_OFFSET = 36;
        final double ANSWER_BGND_HEIGHT = CALCULATOR_HEIGHT / 5;

    final double GAP = 10;

    final int BUTTONS_ACROSS = 4;
    final int BUTTONS_HIGH = 5;

    final int MAX_CHARACTERS = 15;


    private final String [] parts = new String[2];
    private String part = "";
    private final Text partText = new Text("");
    private boolean isDecimal = false;
    private char currentOperation = 0;
    private String answer = "";

    @Override
    public void start(Stage stage) {

        // Build the entire calculator application
        buildCalculator(stage);

    }

    public static void main(String[] args) {
        launch();
    }

    void buildCalculator(Stage stage) {

        buildStage(stage);

    }

    void buildStage(Stage stage) {

        stage.setTitle("Calculator");
        Image icon = new Image("calculator.png");
        stage.getIcons().add(icon);

        stage.setWidth(CALCULATOR_WIDTH + CALCULATOR_WIDTH_OFFSET);
        stage.setHeight(CALCULATOR_HEIGHT + CALCULATOR_HEIGHT_OFFSET);

        stage.setResizable(false);

        double x = 1700 - CALCULATOR_WIDTH;
        double y = 0;

        stage.setX(x);
        stage.setY(y);

        buildRoot(stage);

        stage.show();

    }

    void buildRoot(Stage stage) {

        Group root = new Group();

        stage.setScene(new Scene(root, Color.LEMONCHIFFON));
        designCalculator(root);
        buildButtons(root);

    }

    void buildButtons(Group root) {

        int TOTAL_BUTTONS = BUTTONS_ACROSS * BUTTONS_HIGH;

        Button [] buttons = makeButtonDesigns(root, TOTAL_BUTTONS);

        configureButtons(root, buttons);

    }

    Button [] makeButtonDesigns(Group root, int numButtons) {

        Button [] buttons = new Button[numButtons];

        addButtonDesigns(root, buttons);

        return buttons;

    }

    void addButtonDesigns(Group root, Button [] buttons) {

        final double BUTTON_WIDTH = ((CALCULATOR_WIDTH - GAP) / BUTTONS_ACROSS) - GAP;
        final double BUTTON_HEIGHT = ((CALCULATOR_HEIGHT - ANSWER_BGND_HEIGHT - GAP) / BUTTONS_HIGH) - GAP;

        double x_current = GAP;
        double y_current = ANSWER_BGND_HEIGHT + GAP;

        int btnNum = 0;
        for(int i = 0; i < BUTTONS_HIGH; i++) {
            for(int j = 0; j < BUTTONS_ACROSS; j++) {

                buttons[btnNum] = new Button(x_current, y_current, BUTTON_WIDTH, BUTTON_HEIGHT, btnNum);

                buttons[btnNum].setTextPosition((BUTTON_WIDTH + GAP) * j, (BUTTON_HEIGHT + GAP) * i, ANSWER_BGND_HEIGHT + 7);

                root.getChildren().add(buttons[btnNum].getText());
                root.getChildren().add(buttons[btnNum].getButton());

                x_current += BUTTON_WIDTH + GAP;

                btnNum++;

            }
            x_current = GAP;
            y_current += BUTTON_HEIGHT + GAP;
        }

    }

    void configureButtons(Group root, Button [] buttons) {

        buildOutputText(root);

        configureDigits(buttons);
        configureOperations(buttons);
        configureEquals(buttons);
        configureClear(buttons);
        configureNegation(buttons);
        configureDecimal(buttons);
        configureDelete(buttons);
        configureAnswer(buttons);

    }


    void buildOutputText(Group root) {

        partText.setX(GAP * 2);
        partText.setY(GAP * 2);
        root.getChildren().add(partText);

    }


    void configureDigits(Button [] buttons) {

        int buttonOffset = 0;

        for(int i = 0; i < 10; i++) {

            buttonOffset = buttonOffsetUpdater(buttonOffset, i);
            int number_offset = numberOffsetIndicator(i);
            final int number = i + number_offset;

            buttons[i + BUTTONS_ACROSS + buttonOffset].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                if (part.length() < MAX_CHARACTERS) {

                    part += Integer.toString(number);
                    partText.setText(part);

                }

            });

        }

    }

    int buttonOffsetUpdater(int offset, int i) {

        if(i == 9) {
            offset += 2;
        }
        else if(i % 3 == 0 && i != 0) {
            offset += 1;
        }

        return offset;

    }

    int numberOffsetIndicator(int i) {

        if(i == 9) {
            return -9;
        }
        else {
            return 1;
        }

    }

    void configureOperations(Button [] buttons) {

        char [] operations = new char[]{'+', '-', '×', '÷'};

        for(int i = 0; i < 4; i++) {

            final int operation = i;
            int number = i * 4 + 7;

            buttons[number].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                currentOperation = operations[operation];
                configureOperation();

            });

            currentOperation = 0;

        }

    }


    void configureOperation() {

        if (part.isEmpty())
        { resultOperation(); }

        else if (parts[0] == null && parts[1] == null)
        { halfOperation(); }

        else if (parts[0] != null && parts[1] == null)
        { completeOperation(); }

    }

    void resultOperation() {

        parts[0] = answer;
        partText.setText("");

    }

    void halfOperation() {

        parts[0] = part;
        part = "";
        partText.setText(part);
        isDecimal = false;

    }

    void completeOperation() {

        double result = performOperation();
        printOperationResult(result);
        resetOperationVariables();

    }

    double performOperation() {

        parts[1] = part;

        double partNumber1 = Double.parseDouble(parts[0]);
        double partNumber2 = Double.parseDouble(parts[1]);

        return resultFromOperations(partNumber1, partNumber2);

    }

    void printOperationResult(double result) {

        parts[0] = Double.toString(result);

        if(parts[0].endsWith(".0")) {
            parts[0] = parts[0].substring(0, parts[0].length() - 2);
        }

        partText.setText(parts[0]);

    }

    void resetOperationVariables() {

        parts[1] = null;
        part = "";
        isDecimal = false;

    }

    void configureEquals(Button [] buttons) {

        buttons[3].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> completeEquals());

    }

    void completeEquals() {

        double result = returnResult();
        answer = printEqualsResult(result);
        resetEqualsVariables();

    }

    double returnResult() {

        parts[1] = part;

        double partNumber1 = 0;
        double partNumber2 = 0;

        if (parts[0] != null) { partNumber1 = Double.parseDouble(parts[0]); }
        if (parts[1] != null && !part.isEmpty()) { partNumber2 = Double.parseDouble(parts[1]); }

        return resultFromOperations(partNumber1, partNumber2);

    }

    double resultFromOperations(double partNumber1, double partNumber2) {

        double result = 0;

        if(currentOperation == '+') {
            result = partNumber1 + partNumber2;
        }
        else if(currentOperation == '-') {
            result = partNumber1 - partNumber2;
        }
        else if(currentOperation == '×') {
            result = partNumber1 * partNumber2;
        }
        else if(currentOperation == '÷') {
            result = partNumber1 / partNumber2;
        }

        return result;

    }

    String printEqualsResult(double result) {

        String resultStr = Double.toString(result);

        if(resultStr.endsWith(".0")) {
            resultStr = resultStr.substring(0, resultStr.length() - 2);
        }

        partText.setText(resultStr);

        return resultStr;

    }

    void resetEqualsVariables() {

        currentOperation = '0';
        part = "";
        parts[0] = null;
        parts[1] = null;
        isDecimal = false;

    }

    void configureClear(Button [] buttons) {

        buttons[0].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            part = "";
            parts[0] = null;
            parts[1] = null;
            isDecimal = false;
            partText.setText(part);

        });

    }

    void configureNegation(Button [] buttons) {

        buttons[1].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            if (!part.isEmpty())
            { part = negate(part); }

            else if (!answer.isEmpty())
            { answer = negate(answer); }

        });

    }

    String negate(String numStr) {

        if (numStr.charAt(0) == '-') {

            numStr = numStr.substring(1);
            partText.setText(numStr);

        } else {

            numStr = "-" + numStr;
            partText.setText(numStr);

        }

        return numStr;

    }

    void configureDecimal(Button [] buttons) {

        buttons[2].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            if(!isDecimal) { if(part.isEmpty()) { part += "0."; } else { part += "."; } }
            isDecimal = true;
            partText.setText(part);

        });

    }

    void configureDelete(Button [] buttons) {

        buttons[16].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            if (!Objects.equals(part, "")) {
                part = part.substring(0, part.length() - 1);
                partText.setText(part);
            }

        });

    }

    void configureAnswer(Button [] buttons) {

        buttons[18].getButton().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

            part = answer;
            partText.setText(part);

        });

    }


    void designCalculator(Group root) {

        double answer_background_height = CALCULATOR_HEIGHT / 5;

        root.getChildren().add(buildBarBackgroundRectangle(answer_background_height));
        root.getChildren().add(buildBarBackgroundTriangle(answer_background_height));
        root.getChildren().add(buildButtonsBackgroundTriangle(answer_background_height));
        root.getChildren().add(buildBarRectangle(answer_background_height));

    }

    Rectangle buildBarBackgroundRectangle(double answer_background_height) {

        Rectangle bg_rect_top = new Rectangle();

        bg_rect_top.setX(0);
        bg_rect_top.setY(0);

        bg_rect_top.setWidth(CALCULATOR_WIDTH);
        bg_rect_top.setHeight(answer_background_height);

        bg_rect_top.setFill(Color.MEDIUMTURQUOISE);

        return bg_rect_top;

    }

    Polygon buildBarBackgroundTriangle(double answer_background_height) {

        Polygon answer_background_triangle = new Polygon();

        answer_background_triangle.getPoints().setAll(

                                 0.0,0.0,           CALCULATOR_WIDTH,0.0,
//                                        ._______.
//                                         \     /
//                                          \   /
//                                           \ /
//                                            v
                          (CALCULATOR_WIDTH/2),answer_background_height

        );

        answer_background_triangle.setFill(Color.PALETURQUOISE);

        return answer_background_triangle;

    }

    Polygon buildButtonsBackgroundTriangle(double answer_background_height) {

        Polygon bottom_background_triangle = new Polygon();

        bottom_background_triangle.getPoints().setAll(

                         (CALCULATOR_WIDTH/2), answer_background_height,
//                                            ^
//                                           / \
//                                          /   \
//                                         /_____\
                   0.0, CALCULATOR_HEIGHT,         CALCULATOR_WIDTH, CALCULATOR_HEIGHT

        );

        bottom_background_triangle.setFill(Color.LIGHTYELLOW);

        return bottom_background_triangle;

    }

    Rectangle buildBarRectangle(double answer_background_height) {

        Rectangle answer_bar = new Rectangle();
        answer_bar.setX(GAP);
        answer_bar.setY(GAP);
        answer_bar.setWidth(CALCULATOR_WIDTH - GAP - GAP);
        answer_bar.setHeight(answer_background_height - GAP - GAP);

        answer_bar.setFill(Color.SEASHELL);
        answer_bar.setOpacity(0.5);

        return answer_bar;

    }


}