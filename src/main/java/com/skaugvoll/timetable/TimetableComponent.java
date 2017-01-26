package com.skaugvoll.timetable;
import javafx.scene.layout.GridPane;



/**
 * Created by sigveskaugvoll on 19.01.2017.
 */
public class TimetableComponent {

    TimeTableController controller;
    GridPane grid;

    public TimetableComponent() {
        grid = new GridPane(); // GridPane to contain Timetable
        controller = new TimeTableController(this);



    }



// Setters
    public void setGrid(GridPane grid){
        this.grid = grid;
    }


// Getters
    public GridPane getTimeTable(){
        return this.grid;
    }

}
