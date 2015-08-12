package test;

/**

 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.

 * All rights reserved. Use is subject to license terms.

 */

import javafx.application.Application;

import javafx.scene.Group;

import javafx.scene.Scene;

import javafx.stage.Stage;

import javafx.scene.control.ProgressBar;

 

/**

 * A sample that demonstrates the ProgressBar control.

 *

 * @see javafx.scene.control.ProgressBar

 * @related controls/ProgressIndicator

 */

public class ProgressBarSample extends Application {

 

    private void init(Stage primaryStage) {

        Group root = new Group();

        primaryStage.setResizable(false);

        primaryStage.setScene(new Scene(root, 400,100));

 

        double y = 15;

        final double SPACING = 15;

        ProgressBar p1 = new ProgressBar();

        p1.setLayoutY(y);

 

        y += SPACING;

        ProgressBar p2 = new ProgressBar();

        p2.setPrefWidth(150);

        p2.setLayoutY(y);
  

 

        y += SPACING;

        ProgressBar p3 = new ProgressBar();

        p3.setPrefWidth(200);

        p3.setLayoutY(y);

 

        y = 15;

        ProgressBar p4 = new ProgressBar();

        p4.setLayoutX(215);

        p4.setLayoutY(y);

        p4.setProgress(0.25);

 

        y += SPACING;

        ProgressBar p5 = new ProgressBar();

        p5.setPrefWidth(150);

        p5.setLayoutX(215);

        p5.setLayoutY(y);

        p5.setProgress(0.50);

 

        y += SPACING;

        ProgressBar p6 = new ProgressBar();

        p6.setPrefWidth(200);

        p6.setLayoutX(215);

        p6.setLayoutY(y);

        p6.setProgress(1);

         

        root.getChildren().addAll(p1,p2,p3,p4,p5,p6);

    }    

 

    public double getSampleWidth() { return 400; }

 

    public double getSampleHeight() { return 100; }

 

    @Override public void start(Stage primaryStage) throws Exception {

        init(primaryStage);

        primaryStage.show();

    }

    public static void main(String[] args) { launch(args); }

}