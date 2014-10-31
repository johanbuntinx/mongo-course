package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: johan
 * Date: 10/24/14
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class UpdateTest {
    public static void main(String[] args) throws UnknownHostException {
        DBCollection collection = createCollection();

        List<String> names = Arrays.asList("alice","bobby","cathy","david","ethan");
        for (String name : names) {
            collection.insert(new BasicDBObject("_id",name));
        }
        //collection.update(new BasicDBObject("_id", "alice"), new BasicDBObject("age",24));
        //collection.update(new BasicDBObject("_id", "frank"),new BasicDBObject("$set", new BasicDBObject("gender","female")),true,false);
        //collection.update(new BasicDBObject(),new BasicDBObject("$set", new BasicDBObject("title","M")),true,true);
        collection.remove(new BasicDBObject("_id", "alice"));
        printCollection(collection);


    }

    private static void printCollection(DBCollection collection) {

        DBCursor cursor = collection.find();
        try{
            while (cursor.hasNext()){
                System.out.println(cursor.next());
            }
        }finally{
            cursor.close();
        }
    }

    private static DBCollection createCollection() throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection collection = courseDB.getCollection("insertTest");
        collection.drop();
        return collection;
    }
}
