package com.skaugvoll.timetable;

import javafx.beans.NamedArg;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

/**
 * Created by sigveskaugvoll on 19.01.2017.
 */
public class PlanningView extends Scene {


    // Create the shared timeTableModel the controllers want to use!
    static TimeTableModel model = new TimeTableModel();

    public PlanningView(@NamedArg("root") Parent root) {
        super(root);
        BorderPane borderPane = (BorderPane) super.getRoot(); // the root layout
        // Basic configuration of the root layout:
            // should set width and height!


        // Create the TimeTable
        TimetableComponent timetable = new TimetableComponent();


        // Create the Form!
        FormComponent form = new FormComponent();


        // Set the different components into the main layout
        borderPane.setLeft(timetable.getTimeTable());
        borderPane.setCenter(form.getForm());


    }
}
