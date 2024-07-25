


package com.example.calculator;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class Calculator extends Application {

    // GLOBAL CONSTANTS
    final double CALCULATOR_WIDTH = 500;    // width of calculator app page
        final double CALCULATOR_WIDTH_OFFSET = 14;  // small offset to fix errors in display
    final double CALCULATOR_HEIGHT = 750;   // height of calculator app page
        final double CALCULATOR_HEIGHT_OFFSET = 36; // small offset to fix errors in display

    final double GAP = 10;  // gap length between buttons, answer box, app border, etc.

    final int BUTTONS_ACROSS = 4;   // number of buttons going across
    final int BUTTONS_HIGH = 5;     // number of buttons going down

    final int MAX_CHARACTERS = 15;  // maximum number of digits allowed to be displayed on answer box

    // START FUNCTION
    @Override
    public void start(Stage stage) {

        // Build the entire calculator application
        buildCalculator(stage);

    }

    // MAIN FUNCTION
    public static void main(String[] args) {

        // launch the start function
        launch();

    }

    // GLOBAL VARIABLES (for button lambda expressions)
    private String part = "";   // saves numbers that are typed in
    private final String [] parts = new String[2];  // array to hold both sides of an expression
    private final Text partText = new Text("");  // current display on answer box
    private boolean isDecimal = false;  // indicates if number is a decimal or not
    private char currentOperation = 0;  // indicates the current operation being used (+, -, ×, ÷)
    private String answer = ""; // saves the previous operation result

    // BUILD CALCULATOR: Sets and displays calculator app name, icon, size, position, and builds app root
    void buildCalculator(Stage stage) {

        stage.setTitle("Calculator");                   // app title
        Image icon = new Image("calculator.png");    // getting icon
        stage.getIcons().add(icon);                     // setting icon to app

        stage.setWidth(CALCULATOR_WIDTH + CALCULATOR_WIDTH_OFFSET);     // setting calculator width
        stage.setHeight(CALCULATOR_HEIGHT + CALCULATOR_HEIGHT_OFFSET);  // setting calculator height

        stage.setResizable(false);  // app window will not be resizable

        double x = 1700 - CALCULATOR_WIDTH; // getting x position of calculator window on display
        double y = 0;                       // getting y position of calculator window on display

        stage.setX(x);  // setting x position
        stage.setY(y);  // setting y position

        buildRoot(stage);   // create app design and functionality

        stage.show();   // display the app at the very end

    }

    // BUILD ROOT: Sets up the root and scene for the app and passes it into other methods for calculator design and function
    void buildRoot(Stage stage) {

        Group root = new Group();   // creating root group

        stage.setScene(new Scene(root, Color.LEMONCHIFFON));    // setting scene for background color
        designCalculator(root); // general calculator design
        buildButtons(root);     // button design and functionality

    }

    // DESIGN CALCULATOR: Creates the background and input/output ("answer") display text
    void designCalculator(Group root) {

        double answer_background_height = CALCULATOR_HEIGHT / 5;    // background behind answer display

        // colors and shapes
        root.getChildren().add(buildBarBackgroundRectangle(answer_background_height));
        root.getChildren().add(buildBarBackgroundTriangle(answer_background_height));
        root.getChildren().add(buildButtonsBackgroundTriangle(answer_background_height));
        root.getChildren().add(buildBarRectangle(answer_background_height));

        buildDisplayText(root);  // answer display text

    }

        // Top background rectangle shape
        Rectangle buildBarBackgroundRectangle(double answer_background_height) {

            Rectangle bg_rect_top = new Rectangle();    // creating rectangle background

            bg_rect_top.setX(0);    // x position
            bg_rect_top.setY(0);    // y position

            bg_rect_top.setWidth(CALCULATOR_WIDTH);             // width
            bg_rect_top.setHeight(answer_background_height);    // height

            bg_rect_top.setFill(Color.MEDIUMTURQUOISE); // color

            return bg_rect_top; // returning rectangle

        }

        // Top triangle shape
        Polygon buildBarBackgroundTriangle(double answer_background_height) {

            Polygon answer_background_triangle = new Polygon(); // creating triangle background

            // setting points
            answer_background_triangle.getPoints().setAll(

                                     0.0,0.0,           CALCULATOR_WIDTH,0.0,
    //                                        ._______.
    //                                         \     /
    //                                          \   /
    //                                           \ /
    //                                            v
                              (CALCULATOR_WIDTH/2),answer_background_height

            );

            answer_background_triangle.setFill(Color.PALETURQUOISE); // color

            return answer_background_triangle; // returning triangle

        }

        // Bottom triangle shape
        Polygon buildButtonsBackgroundTriangle(double answer_background_height) {

            Polygon bottom_background_triangle = new Polygon(); // creating triangle background

            // setting points
            bottom_background_triangle.getPoints().setAll(

                              (CALCULATOR_WIDTH/2), answer_background_height,
    //                                            ^
    //                                           / \
    //                                          /   \
    //                                         /_____\
                       0.0, CALCULATOR_HEIGHT,         CALCULATOR_WIDTH, CALCULATOR_HEIGHT

            );

            bottom_background_triangle.setFill(Color.LIGHTYELLOW); // color

            return bottom_background_triangle; // returning triangle

        }

        // Answer display bar
        Rectangle buildBarRectangle(double answer_background_height) {

            Rectangle answer_bar = new Rectangle(); // creating display bar rectangle
            answer_bar.setX(GAP);   // x position
            answer_bar.setY(GAP);   // y position
            answer_bar.setWidth(CALCULATOR_WIDTH - GAP - GAP);          // width
            answer_bar.setHeight(answer_background_height - GAP - GAP); // height

            answer_bar.setFill(Color.SEASHELL); // color
            answer_bar.setOpacity(0.5); // opacity

            return answer_bar;  // returning display bar

        }

        // Set up the display text
        void buildDisplayText(Group root) {

            partText.setX(GAP * 2);
            partText.setY(GAP + (CALCULATOR_HEIGHT / 10));
            partText.setFont(Font.font("Courier New", 50));
            root.getChildren().add(partText);

        }

    // BUILD BUTTONS: Designs and programs calculator buttons for app functionality
    void buildButtons(Group root) {

        int TOTAL_BUTTONS = BUTTONS_ACROSS * BUTTONS_HIGH;  // total number of buttons

        Button [] buttons = createButtons(root, TOTAL_BUTTONS); // array that holds all buttons

        configureButtons(buttons);  // create button functionality

    }

        // CREATE BUTTONS FUNCTION: Creates and organizes buttons for display on the calculator and returns them in an array
        Button [] createButtons(Group root, int numButtons) {

            Button [] buttons = new Button[numButtons]; // array to be returned later

            double ANSWER_BGND_HEIGHT = CALCULATOR_HEIGHT / 5;  // height of the top box of the calculator, for formatting buttons

            final double BUTTON_WIDTH = ((CALCULATOR_WIDTH - GAP) / BUTTONS_ACROSS) - GAP;                      // width of each button (evenly spaced across width of calculator)
            final double BUTTON_HEIGHT = ((CALCULATOR_HEIGHT - ANSWER_BGND_HEIGHT - GAP) / BUTTONS_HIGH) - GAP; // height of each button (evenly spaced across height of calculator)

            double x_current = GAP;                         // starting x position of the first button (gap between left border)
            double y_current = ANSWER_BGND_HEIGHT + GAP;    // starting y position of the first button (under display bar)

            int btnNum = 0; // setting up a button counter
            for(int i = 0; i < BUTTONS_HIGH; i++) { // for every column of buttons
                for(int j = 0; j < BUTTONS_ACROSS; j++) { // for every row of buttons

                    // create a new button
                    buttons[btnNum] = new Button(x_current, y_current, BUTTON_WIDTH, BUTTON_HEIGHT, btnNum);

                    // create and position button text
                    buttons[btnNum].setTextPosition((BUTTON_WIDTH + GAP) * j, (BUTTON_HEIGHT + GAP) * i, ANSWER_BGND_HEIGHT + 7);

                    // add button text and design to calculator
                    root.getChildren().add(buttons[btnNum].getText());
                    root.getChildren().add(buttons[btnNum].getButtonRectangle());

                    // adjust positioning for the next button
                    x_current += BUTTON_WIDTH + GAP;

                    btnNum++; // count to the next button

                }
                x_current = GAP; // reset x spacing for next row
                y_current += BUTTON_HEIGHT + GAP; // increase y spacing to move onto next row
            }

            return buttons; // return the array

        }

        // CONFIGURE BUTTONS: Function to add functionality to each button on calculator
        void configureButtons(Button [] buttons) {

            configureDigits(buttons);       // digits       symbol: (0-9)
            configureOperations(buttons);   // operations   symbol: (+, -, ×, ÷)
            configureEquals(buttons);       // equals       symbol: (=)
            configureClear(buttons);        // clear        symbol: (C)
            configureNegation(buttons);     // negation     symbol: (±)
            configureDecimal(buttons);      // decimal      symbol: (.)
            configureDelete(buttons);       // delete       symbol: (<)
            configureAnswer(buttons);       // answer       symbol: (@)

        }

            // CONFIGURE DIGITS: Set up functionality of digit buttons
            void configureDigits(Button [] buttons) {

                // Order of digits in buttons array are not consistent, an offset variable can help
                int buttonOffset = 0;

                // for every digit
                for(int i = 0; i < 10; i++) {

                    buttonOffset = buttonOffsetUpdater(buttonOffset, i);    // set the button offset (to maneuver array correctly)
                    int number_offset = numberOffsetIndicator(i);           // set the number offset (for displaying correct number)
                    final int number = i + number_offset;                   // set the number (to be displayed)

                    // When digits 1-9 and lastly 0 are clicked
                    buttons[i + BUTTONS_ACROSS + buttonOffset].getButtonRectangle().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                        // if too many numbers are not typed in yet
                        if (part.length() < MAX_CHARACTERS) {

                            // Only type 0 if the display box is not empty
                            if(!(number == 0 && part.isEmpty())) {
                                // add this digit to the display box
                                part += Integer.toString(number);
                                partText.setText(part);
                            }

                        }

                    });

                }

            }

                // helps the for loop in configureDigits() maneuver buttons array properly
                int buttonOffsetUpdater(int offset, int i) {

                    // if the for loop has reached 9 (corresponding to the button that displays 0, 0 is last because it is at the bottom of the calculator)
                    if(i == 9) {
                        offset += 2; // skips the × and < buttons straight to the 0 button
                    }
                    // or if the for loop has reached a number divisible by 3 (we have reached the end of a row for digits)
                    else if(i % 3 == 0 && i != 0) {
                        offset += 1; // skip the next button and go straight to the next row of digits
                    }

                    return offset; // return the offset

                }

                // helps the for loop in configureDigits() know what button it is on
                int numberOffsetIndicator(int i) {

                    // if our for loop has reached button 0
                    if(i == 9) {
                        return -9; // subtract 9 to set the offset to 0, otherwise it will add 1 and give 10
                    }
                    // if our for loop has not yet reached button 0 (it is currently between 0 and 9)
                    else {
                        return 1; // add 1 to set the offset to the next number
                    }

                }

            // CONFIGURE OPERATIONS: Set up functionality of operators (+, -, ×, ÷)
            void configureOperations(Button [] buttons) {

                char [] operations = new char[]{'+', '-', '×', '÷'}; // put operators in an array

                // for each operator
                for(int i = 0; i < 4; i++) {

                    final int operation = i; // save the iteration to a final variable for the next lambda expression
                    int number = i * 4 + 7; // determine the position of the operation in the buttons array

                    // when any of the four operator's buttons are clicked
                    buttons[number].getButtonRectangle().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                        currentOperation = operations[operation]; // set the current operation to its respective index on the operations array
                        configureOperation(); // configure the operation itself

                    });

                    currentOperation = 0; // reset the current operation

                }

            }

                // CONFIGURE OPERATIONS: Carries out any of the four operations
                void configureOperation() {

                    // if the operation was pressed without any operands
                    if (part.isEmpty())
                    { resultOperation(); }      // use the answer as the first operand

                    // if the operation was pressed with an incoming operand
                    else if (parts[0] == null && parts[1] == null)
                    { halfOperation(); }        // set the first operand

                    // if the operation was pressed for an incoming operand when one operand was already set
                    else if (parts[0] != null && parts[1] == null)
                    { completeOperation(); }    // complete the operation for the two operands and set up for the new operand

                }

                    // Operation was pressed without any previous operands, operation is the first button clicked on run or clicked right after '='
                    void resultOperation() {

                        parts[0] = answer; // set the answer as the first operand
                        partText.setText(""); // temporarily clear the display bar

                    }

                    // Operation was pressed with incoming operand (normal case)
                    void halfOperation() {

                        parts[0] = part; // set the first operand to the text on the display bar
                        part = ""; // reset to take in the next operand
                        partText.setText(part); // clear the display bar
                        isDecimal = false; // reset decimal

                    }

                    // Operation was pressed with an existing operand in the memory (in the case where an operation is applied multiple times in a row)
                    void completeOperation() {

                        double result = performOperation(); // perform the operation
                        printOperationResult(result); // print the result
                        resetOperationVariables(); // reset specific variables

                    }

                        // Performs operation for the two operands
                        double performOperation() {

                            parts[1] = part; // set the second operand

                            double partNumber1 = Double.parseDouble(parts[0]); // convert first operand to double
                            double partNumber2 = Double.parseDouble(parts[1]); // convert second operand to double

                            return resultFromOperations(partNumber1, partNumber2); // determine and return result

                        }

                        // Print the operation result on the display bar
                        void printOperationResult(double result) {

                            // convert result to string
                            parts[0] = Double.toString(result);

                            // if the result of the operation has an equivalent integer, do not display the ".0"
                            if(parts[0].endsWith(".0")) {
                                parts[0] = parts[0].substring(0, parts[0].length() - 2); // cutting off the ".0"
                            }

                            partText.setText(parts[0]); // display the current result

                        }

                        // Resetting variables for next operation
                        void resetOperationVariables() {

                            parts[1] = null;
                            part = "";
                            isDecimal = false;

                        }

            // CONFIGURE EQUALS OPERATION: Sets up the equals button on the calculator
            void configureEquals(Button [] buttons) {

                buttons[3].getButtonRectangle().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> completeEquals());

            }

                // Complete the equals operation
                void completeEquals() {

                    double result = returnResult(); // get the result
                    answer = printEqualsResult(result); // print the result
                    resetEqualsVariables(); // reset necessary variables

                }

                    // Return result from operation
                    double returnResult() {

                        parts[1] = part; // set the second operand

                        // initialize doubles
                        double partNumber1 = 0;
                        double partNumber2 = 0;

                        // convert strings to doubles (with validation)
                        if (parts[0] != null) { partNumber1 = Double.parseDouble(parts[0]); }
                        if (parts[1] != null && !part.isEmpty()) { partNumber2 = Double.parseDouble(parts[1]); }

                        // return result
                        return resultFromOperations(partNumber1, partNumber2);

                    }

                        // Determine result from operation
                        double resultFromOperations(double partNumber1, double partNumber2) {

                            double result = 0; // set up result variable

                            // Complete operation depending on the operation indicated by currentOperation

                            if(currentOperation == '+')
                            { result = partNumber1 + partNumber2; }

                            else if(currentOperation == '-')
                            { result = partNumber1 - partNumber2; }

                            else if(currentOperation == '×')
                            { result = partNumber1 * partNumber2; }

                            else if(currentOperation == '÷' && partNumber2 != 0)
                            { result = partNumber1 / partNumber2; }

                            else if(currentOperation == 0 && partNumber2 != 0)    // in the case that '=' is clicked without proper operands
                            { result = Double.parseDouble(partText.getText()); } // do not change text

                            return result; // return result

                        }

                    // Display result
                    String printEqualsResult(double result) {

                        String resultStr = Double.toString(result); // Convert result to string

                        // Ignore ".0" ending if result has equivalent integer
                        if(resultStr.endsWith(".0"))
                        { resultStr = resultStr.substring(0, resultStr.length() - 2); }

                        // If the result is greater than 15 characters, display "OVERFLOW"
                        if(resultStr.length() > 15)
                        { partText.setText("OVERFLOW"); return "0"; }

                        // Otherwise, display result
                        else
                        { partText.setText(resultStr); return resultStr; }

                    }

                    // Reset necessary variables
                    void resetEqualsVariables() {

                        currentOperation = '0';
                        part = "";
                        parts[0] = null;
                        parts[1] = null;
                        isDecimal = false;

                    }

            // Configure the clear button
            void configureClear(Button [] buttons) {

                buttons[0].getButtonRectangle().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                    // reset all necessary variables

                    part = "";
                    parts[0] = null;
                    parts[1] = null;
                    isDecimal = false;
                    partText.setText(part);

                });

            }

            // Configure the negation button
            void configureNegation(Button [] buttons) {

                buttons[1].getButtonRectangle().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                    // if a typed number is being negated
                    if (!part.isEmpty())
                    { part = negate(part); } // negate the typed number

                    // if a result is being negated
                    else if (!answer.isEmpty())
                    { answer = negate(answer); } // negate the result

                });

            }

                // Negation function
                String negate(String numStr) {

                    // The number being examined is stored as a string

                    // If the number has a "-" in the beginning
                    if (numStr.charAt(0) == '-') {

                        numStr = numStr.substring(1); // remove the "-"
                        partText.setText(numStr); // set display

                    // if the number does not have a "-" in the beginning
                    } else {

                        numStr = "-" + numStr; // add a "-" to the beginning
                        partText.setText(numStr); // set display

                    }

                    return numStr; // return the number

                }

            // Configure decimal button
            void configureDecimal(Button [] buttons) {

                buttons[2].getButtonRectangle().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                    // Only add a decimal point if one has not already been added

                    // If nothing is typed onto the display, display "0."
                    // Otherwise, add a decimal point

                    if(!isDecimal) { if(part.isEmpty()) { part += "0."; } else { part += "."; } }

                    isDecimal = true; // prevents more decimal points from being added
                    partText.setText(part); // set display

                });

            }

            // Configure delete button
            void configureDelete(Button [] buttons) {

                buttons[16].getButtonRectangle().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                    // Proceed to deletion only if the display is not empty
                    if (!Objects.equals(part, "")) {
                        part = part.substring(0, part.length() - 1); // cut the last character off the number on display
                        partText.setText(part); // set display
                    }

                });

            }

            // Configure answer button
            void configureAnswer(Button [] buttons) {

                buttons[18].getButtonRectangle().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {

                    part = answer; // set the display number to the previous result
                    partText.setText(part); // set display

                });

            }

}