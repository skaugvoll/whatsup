package com.skaugvoll.timetable;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by sigveskaugvoll on 24.01.2017.
 */
public class DatabaseApiHandler {

    // Config variables
    private static final String databaseName = _DatabaseConfiguration.databaseName;
    private static final String collectionName = _DatabaseConfiguration.collectionName;

    private final String usr = _DatabaseConfiguration.usr;
    private final String psw = _DatabaseConfiguration.psw;
    public final String apiKey = _DatabaseConfiguration.apiKey;



    //Endpoints! --> make own file and make them static!
    public final String LIST_DATABASES = "https://api.mlab.com/api/1/databases/"+databaseName+"?apiKey="+apiKey;
    public final String INSERT = "https://api.mlab.com/api/1/databases/"+ databaseName+"/collections/"+collectionName+"?apiKey="+apiKey;





    public void listDatabases(){
        try{
            HttpResponse<JsonNode> jsonResponse = Unirest.get(LIST_DATABASES).asJson();
            System.out.println(jsonResponse.getBody());
            System.out.println(jsonResponse.getStatus());

            Unirest.shutdown();
        }
        catch (UnirestException e ){
            System.err.println("Unirest Exception: " + e);
        }
        catch (IOException e ){
            System.err.println("Unirest shutdown exception: " + e);
        }
    }



    public void insertIntoDb(JSONObject obj){
        try {
            HttpResponse<JsonNode> jsonResponse = Unirest.post(INSERT)
                    .header("Content-Type","application/json")
                    .body(obj)
                    .asJson();
            System.out.println(jsonResponse.getBody());
            System.out.println(jsonResponse.getStatus());
        }
        catch (UnirestException e){
            System.err.println("Unirest Exception: " + e);
        }
    }

    public static void main(String[] args) {
        DatabaseApiHandler dbc = new DatabaseApiHandler();
        //dbc.listDatabases();

        JSONObject user = new JSONObject();
        user.put("First_name","Sigve");
        user.put("Last_name", "Skaugvoll");

        dbc.insertIntoDb(user);

    }


}
