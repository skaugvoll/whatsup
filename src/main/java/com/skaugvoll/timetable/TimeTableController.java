package com.skaugvoll.timetable;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import java.util.Map;

/**
 * Created by sigveskaugvoll on 19.01.2017.
 */
public class TimeTableController implements PropertyChangeListener {


    private TimetableComponent view; // the view we want to listen to and update
    private GridPane root; // temporary grid to manipulate and update, then push to actual grid
    private TimeTableModel model; // the model we want to use and manipulate

    // Start controller
    public TimeTableController(TimetableComponent view) {
        this.view = view; // register the view that wants to use this controller
        this.root = view.getTimeTable();
        setModel(Main.model); // set the model we want to read / write to.
        initView();
    }


    // Making the UI - model:
    private void setModel(TimeTableModel model) {
        // Check that there is not existing a model, if so, remove it
        if (this.model != null) {
            this.model.removePropertyChangeListener(this);
        }
        // Set the correct model
        this.model = model;

        // check that correct model is instantiated and then add it as a something to listen to!
        if (this.model != null) {
            this.model.addPropertyChangeLisener(this);
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent event) {
        // check that event is from the model we want to listen to!
        if (event.getSource() != this.model) {
            return; // if it's not the model we want to listen to, just ignore it!
        }

        String propertyName = event.getPropertyName(); // Get the property that is announced changed!
        System.out.println("Event");
        // check for right update method to the corresponding announced change! then update view!
        if(model.SCHEDULE_UPDATE_PROPERTY.equals(propertyName)){
            System.out.println("Under control!");
            updateViewSchedule();
        }

    }



    // Initialize the view!

    private void initView() {
        view.setGrid(rootConfig());
    }

    // init code:
    private GridPane rootConfig() {
        root = new GridPane(); // create temporary Grid to manipulate before updating the actual grid!
        root.setGridLinesVisible(true); // make the gridlines visible
        root.setPadding(new Insets(10, 10, 10, 10)); // Top, right, bottom, left
        //There is no padding between cells, so every cell is on top of each other
        //root.setVgap(8); // Vertical spacing, space between each cell
        //root.setHgap(10); // Horizontal spacing, space between each cell

        // A timetable for school has 5 days (columns) and 12 rows (times: 8:15 --> 2000)
        // + One row for column specification and one row for row specification
        // Total of 6 Columns, 13 Rows

        // Handle click on task in timetable!
        root.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {

                for( Node node: root.getChildren()) {

                    if( node instanceof VBox) {
                        if( node.getBoundsInParent().contains(e.getSceneX(),  e.getSceneY())) {
                            System.out.println( "Node: " + node + " at " + GridPane.getRowIndex( node) + "/" + GridPane.getColumnIndex( node));
                        }
                    }
                }
            }
        });




        for (int r = 0; r < 13; r++) {

            // set the height of all 13 rows
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPrefHeight(40);
            root.getRowConstraints().add(rowConst);

            // Set the width of all 6 columns
            if (r < 6) {
                ColumnConstraints colConst = new ColumnConstraints();
                colConst.setPrefWidth(100);
                root.getColumnConstraints().add(colConst);
            }

            // Insert information at the top row and left-most column
            for (int c = 0; c < 6; c++) {
                if (r == 0 && c == 0) { //this is a information row (top)
                    Pane p = new Pane();
                    p.setStyle("-fx-background-color: darkgray");
                    root.add(p,c,r);
                    continue;
                } else if (r == 0) {
                    Label day = new Label(getDay(c));
                    root.add(day, c, r);
                    root.setHalignment(day, HPos.CENTER);
                } else if (c == 0) { //this is a information col (left)
                    Label time = new Label(String.valueOf(r + 7) + ":15" + " - " + String.valueOf(r + 8) + ": 00");
                    root.add(time, c, r);
                    root.setHalignment(time, HPos.CENTER);
                }
            }
        }
        return root;


    }

    private String getDay(int r) {
        switch (r) {
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            default:
                return "Everyday";
        }

    }

    private int getColumn(String key){
        switch(key) {
            case "Monday":
                return  1;

            case "Tuesday":
                return 2;

            case "Wednesday":
                return 3;

            case "Thursday":
                return 4;

            case "Friday":
                return 5;
            default:
                return 0;
        }

    }


// View_Listeners
// View_updaters

    private void updateViewSchedule() {
        // manipulate root with new schedule
        /*
        * Monday        = column        1,      08:15-09:00  = row 1,
        * Tuesday       = column        2,      09:15-10:00  = row 2,
        * Wednesday     = column        3,         ...
        * Thursday      = column        4,         ...
        * Friday        = column        5,      19:15-20:00  = row 13
        */

        Map<String, ArrayList<String[]>> schedule = model.getSchedule();
        // for each day, update the schedule
        for(String key : schedule.keySet()){

            // find time --> find column
            ArrayList<String[]> times = schedule.get(key); // [  [task], [task], [task] ]
            // update column, set rowspan equal to to - from on x  if (xx:yy)
            for(String[] task : times) {  // task == [subject, from, to, note]
                String subject  =    task[0];
                String from     =    task[1];
                String to       =    task[2];
                String note     =    task[3];

                // Validate time periode, and get how many rows to "color"
                if(!isAfter(from,to)){
                    throw new IllegalStateException("from is before to");
                }


                VBox cell = new VBox();
                cell.setStyle("-fx-background-color: #336699;");

                Label s = new Label(subject); // make a node out of the subject
                Label n = new Label(note);  // make a node out of the note
                cell.getChildren().addAll(s,n);
                // use row and column(s) to update a cell in the root:
                //System.out.println("Gonna try to update the grid!");
                root.add(cell,getColumn(key),getStartRow(from),1,getRowspan(from,to));
            }

        }


        // update view grid
        view.setGrid(root);
    }



// model_updaters

// Validators / help-functions:
    /**
     * Return true if from has lover value than to.
     *
     * */
    private boolean isAfter(String from, String to){
        return Integer.valueOf(from.replace(":","")) < Integer.valueOf(to.replace(":",""));
    }

    /**
     * Returns
     *
     * */
    private int getRowspan(String from, String to){
        int rs = 0;
        // knows that from < to

        // Convert 08:15 --> 815, ...  20.00 --> 2000
        int f        =    Integer.valueOf(from.replace(":",""));
        int t        =    Integer.valueOf(to.replace(":",""));

        int temp = t - f;
        temp = round(temp,100);
        if(temp <= 100){
            // return 1  rowspan
            return 1;
        }
        else if(temp >100 && temp < 1000){
            // return the first character evt. temp / 100
            return temp / 100;
        }
        else if(temp >= 1000){
            // return the first two characters
            return  Integer.valueOf(String.valueOf(temp).substring(0,2));
        }

        return rs;
    }

    private int getStartRow(String from){


        if(from.length() == 3){ // starting time between 815 and 915
            return Integer.valueOf(from.charAt(0)) - 7; //
            //System.out.println(startrow);
            //System.out.println(column);
        }
        else{ // has to be between 10 - 20
            return  Integer.valueOf(from.substring(0,2))-7;
            //System.out.println(startrow);
        }
    }



    private int round (int number,int multiple){
        int result = multiple;
        //If not already multiple of given number
        if (number % multiple != 0){
            int division = (number / multiple)+1;
            result = division * multiple;
        }
        return result;
    }


}
