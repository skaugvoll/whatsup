package com.skaugvoll.timetable;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*; // allow usaged of predefined filters, such as "eq", "and", etc.


import com.mongodb.util.JSON;
import org.bson.Document;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by sigveskaugvoll on 26.01.2017.
 */
public class DatabaseHandler {


    // Specify the global objects
    private MongoClient mongoclient;
    private MongoDatabase timetableDatabase;
    private MongoCollection<Document> timetableCollection; // this will be the object we interact with.

    public DatabaseHandler(){
        connectTodatabase();
    }


    /**
     * Connects to the database and collection
     * */
    public void connectTodatabase(){
        boolean connectionEstablished = false;
        try{
            // Create a connection to the Mongo database instance
            mongoclient = new MongoClient(_DatabaseConfiguration.uri);

            // Connect and ACCESS the given database in the mongo instance
            timetableDatabase = mongoclient.getDatabase(_DatabaseConfiguration.databaseName); // If a database does not exist, MongoDB creates the database when you first store data for that database.

            // Once you have a MongoDatabase instance, use its method to access a collection in the database.
            timetableCollection = timetableDatabase.getCollection(_DatabaseConfiguration.collectionName); // If a collection does not exist, MongoDB creates one
            connectionEstablished = true;
        }
        catch (Exception e){
            System.err.println("Something went wrong during connecting to database: " + e);
            connectionEstablished = false;
        }
        finally {
            System.out.println("Connection established: " + connectionEstablished);
        }
    }

    /**
     * Returns a mongoDB object (entity) from a hashmap
     * map : hashmap<String, String>
     *
     * */
    public Document createDocument(HashMap<String, String> map){
        Document doc = new Document();
        for(String key : map.keySet()){ // iterate through the hashmap we want to create a document off.
            doc.append(key,map.get(key));  // add each pair to the document
        }

        return doc; // return the document
    }

    /**
     * Insert ONE document
     * */
    public void insertDocument(Document document){
        try{
            System.out.println(document.toJson());
            this.timetableCollection.insertOne(document);
            System.out.println("Success insertion one document");
        }
        catch(Exception e){
            System.err.println("Something went wrong with inserting the document: " + e);
        }
    }

    /**
     * Insert multiple documents,
     *
     * */
    public void insertDocuments(ArrayList<Document> documents){
        try{
            this.timetableCollection.insertMany(documents);
        }
        catch(Exception e){
            System.err.println("Something went wrong with inserting the documents: " + e);
        }
    }

    /**
     * Count how many documents are in the collection
     *
     * returns datatype 'long'
     * */
    public long countDocuments(){
        long numberOfDocuments = 0;
        try{
            numberOfDocuments = this.timetableCollection.count();
        }
        catch (Exception e){
            System.out.println("Something went wrong when counting");
        }
        finally {
            return numberOfDocuments;
        }
    }

    /**
     * Updates a specific document in the colletion
     *
     *
     * */

    public void updateScheduleByUsername(String username, Map schedule){
        System.out.println("BEFORE UPDATING");
        // convert Map to Document
        this.timetableCollection.updateOne(eq("username", username), new Document("$set", new Document("schedule", JSON.serialize(schedule)))); // Serializes an object into it's JSON form, returns String containing JSON form of the object
        System.out.println("AFTER UPDATING");
    }


    /**
     * Queryes the database for given username, and returnes first document that matches
     * There should only be one Document in the database with the given username
     *
     * */
    public Document getByUsername(String username){

        try{
            return timetableCollection.find(eq("username",username)).first();
        }
        catch (Exception e){
            System.out.println("Could not find user? -- catch is catched: " + e);
            return null;
        }

    }


    // Create method to convert HashMap Schedule to Document

    // Create method to convert Document Schedule to hashMap

    // Create method to convert String on Json format to hashMap



    /*
    *  Method to test under development;
    *  Until I figure out how to write the God damn maven unit test...
    *
     */
    public static void main(String[] args) {
        DatabaseHandler handler = new DatabaseHandler();
        handler.connectTodatabase();
    }



}
