package com.example.calculator;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Group root = new Group();

        Scene scene = new Scene(root, Color.LEMONCHIFFON);

        stage.setTitle("Calculator");

        Image icon = new Image("calculator.png");

        stage.getIcons().add(icon);


        stage.setWidth(514);
        stage.setHeight(750);
        stage.setResizable(false);

        stage.setX(1150);
        stage.setY(40);


        Text zero = new Text();
        zero.setText("0");
        zero.setFont(Font.font("Verdana", 100));
        zero.setFill(Color.CORAL);

        zero.setX(225);
        zero.setY(640);



        Rectangle bg_rect_top = new Rectangle();

        bg_rect_top.setX(0);
        bg_rect_top.setY(0);

        bg_rect_top.setWidth(500);
        bg_rect_top.setHeight(150);

        bg_rect_top.setFill(Color.MEDIUMTURQUOISE);



        Polygon bg_tri_top = new Polygon();
        bg_tri_top.getPoints().setAll(
                0.0, 0.0,
                250.0, 150.0,
                500.0, 0.0
        );
        bg_tri_top.setFill(Color.PALETURQUOISE);

        Polygon bg_tri_bottom = new Polygon();
        bg_tri_bottom.getPoints().setAll(
                0.0, 750.0,
                250.0, 150.0,
                500.0, 750.0
        );
        bg_tri_bottom.setFill(Color.LIGHTYELLOW);


        root.getChildren().add(bg_rect_top);
        root.getChildren().add(bg_tri_top);
        root.getChildren().add(bg_tri_bottom);


        Rectangle bg_answer = new Rectangle();
        bg_answer.setX(10);
        bg_answer.setY(10);
        bg_answer.setWidth(480);
        bg_answer.setHeight(130);

        bg_answer.setFill(Color.SEASHELL);
        bg_answer.setOpacity(0.5);

        root.getChildren().add(bg_answer);



        root.getChildren().add(zero);


        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}