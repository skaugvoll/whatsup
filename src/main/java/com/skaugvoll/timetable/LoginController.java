package com.skaugvoll.timetable;

/**
 * Created by sigveskaugvoll on 26.01.2017.
 */
public class LoginController {

    LoginView view;

    public LoginController(LoginView view){
        this.view = view;

    }


    public void login(String uname, String psw) {
        if(!checkPassword(psw)){
            view.unsuccessfull.setText("Wrong username or password");
            view.unsuccessfull.setVisible(true);
        }
        else{
            // change scene!
        }

    }

    private boolean checkPassword(String psw) {
        // Query database on username,
        // then, check if typed password equals saved password
        return false;
    }

    public void register(String username, String password){
        // check if username is taken (false --> not in database)
        if(!checkUsername(username)){
            view.unsuccessfull.setText("Username already taken");
            view.unsuccessfull.setVisible(true);
        }
    }

    private boolean checkUsername(String username) {
        // return true if in database
        // return false if not in database
        return false;
    }


}
