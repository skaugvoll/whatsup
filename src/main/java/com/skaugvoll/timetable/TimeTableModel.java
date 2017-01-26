package com.skaugvoll.timetable;



import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

/**
 * Created by sigveskaugvoll on 19.01.2017.
 */
public class TimeTableModel {


// UI-model setup
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    //Declare different types of properties, as final
    public static final String SCHEDULE_UPDATE_PROPERTY = "scheduleUpdate";

    // add a PropertyChangeListener to notify when changes happens!
    public void addPropertyChangeLisener(PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

    // remove a PropertyChangeListener so you don't notify when changes happens!
    public void removePropertyChangeListener(PropertyChangeListener listener){
        pcs.removePropertyChangeListener(listener);
    }

    // now ready to "alert" listeners when changes happens using the pcs.firePropertyChange(TYPE_PROPERTY, oldvalue, newValue);



// Model methods!
    Map<String, ArrayList<String[]>> schedule = new HashMap<>(); // save the schedule as {"Monday" : [subject,from,to,note],[subjectfrom,to,note]}


// Setters / updaters : used by controllers to update the model, and notify other controllers to update their view
    public void updateSchedule(String day, String[] task) throws IllegalArgumentException {
        ArrayList<String[]> tasks = schedule.get(day); // get the current schedule
        ArrayList<String[]> oldtasks = schedule.get(day); // create copy of
        if(tasks == null){ // check if there is an schedule or it's empty
            tasks = new ArrayList<>();  // if emtpy, create a new schedule
        }
        if(tasks.contains(task)){ // if there is no need to update the schedule, just ignore.
            return;
        }
        else {

            tasks.add(task); // add new task to schedule
            System.out.println("FIRE AWAY");
            schedule.put(day, tasks); // update the model schedule
            pcs.firePropertyChange("scheduleUpdate", oldtasks, tasks); // notify controller(s) with old and new value
        }

        /* for debugging purposes: RMB4P
        System.out.println(schedule.size());
        // For each value! not each key!
        for( Map.Entry<String, ArrayList<String[]>> entry : schedule.entrySet()) {
            System.out.println(entry.getKey() + " : " + printList(entry.getValue()));

        }
        */
    }


    // Print the schedule
    private String printList(ArrayList<String[]> value) {
         String task = ""  ;
        for (String[] s: value){
            task +=  Arrays.toString(s) + ", ";
        }
        return task;
    }



// Getters : used by controllers to update view!
    public Map<String, ArrayList<String[]>> getSchedule(){
        return this.schedule;
    }

}
