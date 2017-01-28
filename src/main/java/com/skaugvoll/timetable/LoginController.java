package com.skaugvoll.timetable;


import javafx.scene.layout.BorderPane;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.bson.Document;
import java.util.HashMap;

/**
 * Created by sigveskaugvoll on 26.01.2017.
 */
public class LoginController {

    LoginView view;
    DatabaseHandler dbHandler = Main.dbHandler;
    TimeTableModel model = Main.model;

    public LoginController(LoginView view){
        this.view = view;

    }


    public void login(String uname, String psw) {
        if(!checkPassword(uname,psw)){
            view.unsuccessfull.setText("Wrong username or password");
            view.unsuccessfull.setVisible(true);
        }
        else{
            System.out.println("successfully logged inn");
            // thread: set user in model)
            Thread t1 = new Thread(new Runnable() {

                @Override
                public void run() {
                    model.setUser(dbHandler.getByUsername(uname));
                    System.out.println("Thread Done!");
                }
            });
            t1.start();

            // Change scene (To use a thread on this as well, Use Platform.runlater() because its opperate on a FX thread)
            Main.window.setScene(new PlanningView(new BorderPane())); // change scene

        }

    }

    private boolean checkPassword(String uname, String psw) {
        // Query database on username,
        Document user = dbHandler.getByUsername(uname);
        System.out.println("USER: " + user);
        System.out.println("USER: " + user.toJson());
        System.out.println("USER: " + user.toString());
        if(user == null){
            return false;
        }
        // then, check if typed password equals saved password
        //extract saved password



        String savedPassword = user.getString("password");

        System.out.println("saved password: " + savedPassword);
        return BCrypt.checkpw(psw,savedPassword);
    }

    public void register(String username, String password){
        // check if username is taken (false --> not in database)
        if(checkUsername(username)){
            view.unsuccessfull.setText("Username already taken");
            view.unsuccessfull.setVisible(true);
        }
        // register the user and hash the password with BCrypt before saving it.

        dbHandler.insertDocument(createUser(username,password));



    }

    private Document createUser(String username, String password) {
        HashMap<String,String> userCredentials = new HashMap<>();

        // Bcrypt the password
        String hashedPsw = BCrypt.hashpw(password, BCrypt.gensalt());

        // Add userCredentials to the HashMap / user settings
        userCredentials.put("username", username);
        userCredentials.put("password", hashedPsw);


        //Create and return the user object
        return dbHandler.createDocument(userCredentials);

    }

    private boolean checkUsername(String username) {
        // return true if in database
        if(dbHandler.getByUsername(username) != null) {
            return true;
        }
        // retrun false if not in database
        return false;
    }


}
