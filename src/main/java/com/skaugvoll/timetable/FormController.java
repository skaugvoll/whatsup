package com.skaugvoll.timetable;



import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


/**
 * Created by sigveskaugvoll on 19.01.2017.
 */
public class FormController implements PropertyChangeListener {

    private FormComponent view; // the view we want to listen to and update
    private TimeTableModel model; // the model we want to use and manipulate

    // Start controller
    public FormController(FormComponent view){
        this.view = view; // register the view that wants to use this controller
        setModel(Main.model); // set the model we want to read / write to.
        initTimeComponent();
    }


    // Making the UI - model:
    private void setModel (TimeTableModel model){
        // Check that there is not existing a model, if so, remove it
        if(this.model != null){
            this.model.removePropertyChangeListener(this);
        }
        // Set the correct model
        this.model = model;

        // check that correct model is instantiated and then add it as a something to listen to!
        if(this.model != null){
            this.model.addPropertyChangeLisener(this);
        }
    }

    public void propertyChange(PropertyChangeEvent event){
        // check that event is from the model we want to listen to!
        if(event.getSource() != this.model){
            return; // if it's not the model we want to listen to, just ignore it!
        }

        String propertyName = event.getPropertyName(); // Get the property that is announced changed!
        // check for right update method to the corresponding announced change! then update view!
    }



/**
 * Controller Code
*/
    // Initialize the component, and fill elements with values
    private void initTimeComponent() {
        ComboBox<String> from = view.getFrom();
        for(int i = 8; i < 20; i++){
            if(i < 10 ? from.getItems().add("0"+i + ":15") : from.getItems().add(String.valueOf(i) + ":15"));
        }
        ComboBox<String> to =view.getTo();
        for(int i = 9; i <= 20; i++){
            if(i < 10 ? to.getItems().add("0"+i+":00") : to.getItems().add(String.valueOf(i) + ":00"));
        }
        view.setFrom(from);
        view.setTo(to);
    }



// View_listeners methods: (methods view can call to register change)

    // Handle submitted changes in view, need to update model and view.
    public void onSubmit(String subject, String note) {
        CheckBox[] d = view.getCheckboxes();
        String from = view.getFrom().getValue();
        String to = view.getTo().getValue();

        for(CheckBox day : d){

            String[] task = new String[] {subject, from, to, note}; // [subject,from,to,note]
            if(day.selectedProperty().getValue()) {
                String selectedDay = day.getText();
                // update models schedule on the given day
                try {
                    updateModelWithNewTask(selectedDay,task);
                }
                catch (Exception e){
                    System.out.println("WRONG happen, catch catched");
                }
            }


        }

    }

// view_updaters methods:


// model_updaters methods:
    private void updateModelWithNewTask(String day, String[] task){
        model.updateSchedule(day, task);
    }

/*
// Controller methods:


*/

}
