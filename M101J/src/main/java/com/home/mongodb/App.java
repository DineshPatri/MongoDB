package com.home.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import com.mongodb.client.MongoDatabase;
import org.eclipse.jetty.server.Server;

import static java.util.Arrays.asList;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost().build()// default it creates pool of 100 connections-- immutable
        //MongoClient client = new MongoClient( new ServerAddress("localhost",27017));
        //MongoClient client = new MongoClient(asList(new ServerAddress("localhost",27017)));// can be used for replica set servers
        MongoClient client = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase db =client.getDatabase("video");//MongoDatabase objects are immutable -- careful in using
        MongoCollection<Document> collection = db.getCollection("movieDetails");
    }
}
