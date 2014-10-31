package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: johan
 * Date: 10/16/14
 * Time: 9:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldMongoDB {
    public static void main(String[] args) throws UnknownHostException {
              MongoClient mongoClient = new MongoClient(new ServerAddress("localhost", 27017));

        DB db = mongoClient.getDB("test");
        DBCollection collection = db.getCollection("names");
        DBObject doc = collection.findOne();
        System.out.println("doc = " + doc);
    }
}
