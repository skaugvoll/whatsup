package com.skaugvoll.timetable;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sigveskaugvoll on 26.01.2017.
 */
public class DatabaseHandler {


    // Specify the global objects
    private MongoClient mongoclient;
    private MongoDatabase timetableDatabase;
    private MongoCollection<Document> timetableCollection; // this will be the object we interact with.


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
            this.timetableCollection.insertOne(document);
        }
        catch(Exception e){
            System.err.println("Something went wrong with inserting the document");
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
            System.err.println("Something went wrong with inserting the documents");
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
