package com.tengen;

import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: johan
 * Date: 10/24/14
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class FindTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection collection = courseDB.getCollection("insertTest");
        collection.drop();
        for (int i = 0; i < 10000; i++) {
            collection.insert(new BasicDBObject("x", new Random().nextInt(1000)));
        }

        System.out.println("Find one:");
        DBObject one = collection.findOne();
        System.out.println("one = " + one);
        System.out.println("\nFind all:");
        DBCursor cursor = collection.find();
        try{
            while (cursor.hasNext()){
                System.out.println(cursor.next());
            }
        }finally{
            cursor.close();
        }
        System.out.println("\nCount:");
        long count = collection.count();
        System.out.println("count = " + count);

    }
}
