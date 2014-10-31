package com.tengen;

import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: johan
 * Date: 10/24/14
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class InsertTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection collection = courseDB.getCollection("insertTest");
        collection.drop();
        DBObject doc = new BasicDBObject("_id", new ObjectId()).append("x",1);
        DBObject doc2 = new BasicDBObject().append("x",2);
        System.out.println("doc = " + doc);
        collection.insert(doc);
//        collection.insert(doc);
        System.out.println("doc = " + doc);
    }
}
