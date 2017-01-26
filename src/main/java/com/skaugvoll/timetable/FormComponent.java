package com.skaugvoll.timetable;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by sigveskaugvoll on 19.01.2017.
 */
public class FormComponent {

    /*
    * Layout, Vertikal box, med horisontale boxer inni, på denne måten kan eg lett
    * lage lage en layout med dette utseende:
    *       Label :  input
    *       Label :  input
    *            ...
    *       Reset :  Submit
    *
    * */

    FormController controller;

    // Define all the view-elements we want to use and notify the controller with changes and let the controller change
    VBox        vbox; // main layout / component root!
    TextField   subject;
    TextArea    note;
    CheckBox[]  cbs;
    ComboBox<String> from;
    ComboBox<String> to;



    // Inits the view with default and prompt text
    public FormComponent() {
//Lage hoved layout for denne komponent
        vbox = new VBox();
        vbox.setPadding(new Insets(10,10,10,5));
        vbox.setSpacing(20);

        Label title = new Label("..Task administration..");
        title.setAlignment(Pos.CENTER);

        title.setPrefWidth(500);
        vbox.getChildren().addAll(title);

// Trenger input for å velge hvilke dager
        HBox dayBox = new HBox();
        dayBox.setSpacing(10);
        Label dayLabel = new Label("Choose day(s):");
        vbox.getChildren().add(dayLabel); // add to this child component;

        // Choiceboxes init!
        String[] days = new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        cbs = new CheckBox[5]; // 5 days --> 5 checkboxes
        for(int i = 0; i < days.length; i++){
            cbs[i] = new CheckBox(days[i]); // create a checkbox with the right day name.
            dayBox.getChildren().add(cbs[i]);
        }

        // add to component root
        vbox.getChildren().add(dayBox);

// Trenger input for å velge klokkeslett
        HBox timeBox = new HBox();
        timeBox.setSpacing(10);
        Label fromLabel = new Label("From: ");
        // add choicebox for from
        from = new ComboBox<>();


        Label toLabel = new Label("To: ");
        // add choicebox for to
        to = new ComboBox<>();

        // add to the component root
        timeBox.getChildren().addAll(fromLabel,from,toLabel,to); // add to this child component;
        vbox.getChildren().add(timeBox);

// Tekst felt for å skrive tittel / subject,
        HBox subjectBox = new HBox();
        subjectBox.setSpacing(10);
        Label subjectLabel = new Label("Subject: ");

        subject = new TextField();
        subject.setPromptText("IT2901 meeting");
        subjectBox.getChildren().addAll(subjectLabel, subject);

        // tekst område for å skrive detaljert. [hvis bruker trykker på et tidsrom, få opp beskrivelse?]
        note = new TextArea();
        note.setPromptText("What happens then?");

        vbox.getChildren().addAll(subjectBox, note);

// Trenger knapp for å  resette / submitte til kontroller som så skal fylle inn i tabell
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(20);
        Button resetBtn = new Button("Cancel");
        resetBtn.setOnAction(e -> {
            subject.clear();
            note.clear();
        });



        Button submitBtn = new Button("Save");
        submitBtn.setOnAction(e -> {
            // Gather all the dynamic information: Days, to, from, subject, note:
            // and send it to the controller, to update the model and view.
            controller.onSubmit(subject.getText(),note.getText());

        });

        buttonBox.getChildren().addAll(resetBtn,submitBtn);
        buttonBox.setAlignment(Pos.BASELINE_RIGHT);
        vbox.getChildren().add(buttonBox);

        controller = new FormController(this);
    }


    // notify : Make all the different dynamic elements notify the controller about change, and ready to validate new value


    // Setters : Let the controller update all the dynamic elements with the model-values.
    public void setFrom(ComboBox<String> from) {
        this.from = from;
    }

    public void setTo(ComboBox<String> to) {
        this.to = to;
    }


    // Getters: Let the controller extract the values from elements;

    public Node getForm() {
        return this.vbox;
    }

    public CheckBox[] getCheckboxes(){
        return this.cbs;
    }


    public ComboBox<String> getFrom() {
        return from;
    }

    public ComboBox<String> getTo() {
        return to;
    }


}
