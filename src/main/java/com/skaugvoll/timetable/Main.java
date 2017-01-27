package com.skaugvoll.timetable;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * To make a JavaFX, application, you need to have a class that extends Application,
 * and have implemented the method start(){}.
 *
 * You also need to have a psv main function that calle the launch() function, to start the GUI.
 *
 */
public class Main extends Application
{

    public static DatabaseHandler dbHandler = new DatabaseHandler();
    public static Stage window;

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        window = primaryStage; // just renaming the window (stage, not scene), for easier remembering and usage
        window.setTitle("Timetable Deluxe"); // Set the title for the window
        window.setHeight(500);
        window.setWidth(700);


        //Create the main /start scene
        //Scene scene = new PlanningView(new BorderPane());

        //Set the Scene on the stage (make it visible)
        //window.setScene(scene);
        window.setScene(new LoginView(new BorderPane()));


        //displays the GUI (remove curtains from stage and show scene)
        window.show();
    }
}

/*
*
* Eg blir å trenge en form å knytte alle views sammen på. skal eg lage en egen klasse som heite bare view,
* som inneholder en boarderpane, også bruke den som utgangspunkt i et view!
*    - Ja dette virker lurt, da programmet teknisk sett har bare ett view / scene,
*      med mange flere forskjellige elementer / layouts.
*      Derfor burde TimeTableView gjøres om til komponent!
*
* */