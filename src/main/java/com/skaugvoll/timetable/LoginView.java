package com.skaugvoll.timetable;

import javafx.beans.NamedArg;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

/**
 * Created by sigveskaugvoll on 25.01.2017.
 */
public class LoginView extends Scene {


    public LoginView(@NamedArg("root") Parent root) {
        super(root);
        super.getStylesheets().add("/loginCSS.css");

        BorderPane layout = (BorderPane) super.getRoot();
        layout.getStyleClass().add("layout");

        // Create the centerComponent
        VBox centerComponent = new VBox();
        centerComponent.setAlignment(Pos.CENTER);



        // Create the layout for the sign in form, GridPane should do good!
        GridPane loginComponent = new GridPane();
        loginComponent.setAlignment(Pos.CENTER);
        loginComponent.setGridLinesVisible(true);

/*
        final int numCols = 4 ;
        final int numRows = 4 ;
        // instanciate columns
        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            //colConst.setPercentWidth(100.0 / numCols);
            loginComponent.getColumnConstraints().add(colConst);
        }
        // instanciate rows
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            //rowConst.setPercentHeight(100.0 / numRows);
            loginComponent.getRowConstraints().add(rowConst);
        }
*/

        //Labels:
        Label welcome = new Label("Welcome!");
        Label usrLabel = new Label("Username: ");
        Label pswLabel = new Label("Password: ");

        //Inputs:
        TextField usrInput = new TextField();
        PasswordField pswInput = new PasswordField();
        Button submit = new Button("Login");


        //Configuration:
        welcome.getStyleClass().add("welcome");

        usrInput.setPromptText("Clark");
        pswInput.setPromptText("Kent");

        usrInput.setFocusTraversable(false);
        pswInput.setFocusTraversable(false);

        submit.getStyleClass().add("submit");
        submit.setOnAction( e -> {
            // do something
        });

        //Add elements to logincomponent
        loginComponent.add(usrLabel,1,1);
        loginComponent.add(usrInput,2,1);
        loginComponent.add(pswLabel,1,2);
        loginComponent.add(pswInput,2,2);
        //loginComponent.add(submit,2,3);

        //Add elements to centerComponent
        centerComponent.getChildren().addAll(welcome,loginComponent,submit);


        //Add component to layout
        layout.setCenter(centerComponent);



    }






}
