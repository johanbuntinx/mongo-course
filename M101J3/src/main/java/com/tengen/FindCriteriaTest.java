package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: johan
 * Date: 10/24/14
 * Time: 12:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class FindCriteriaTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection collection = courseDB.getCollection("insertTest");
        collection.drop();
        for (int i = 0; i < 10; i++) {
            Random random = new Random();
            collection.insert(
                    new BasicDBObject("x", random.nextInt(2))
                            .append("y", random.nextInt(100))
                            .append("z", random.nextInt(1000)));
        }

        System.out.println("\nCount:");
        long count = collection.count();
        System.out.println("count = " + count);

        QueryBuilder builder = QueryBuilder.start("x").is(0).and("y").greaterThan(10).lessThan(70);

        System.out.println("\nCount:");
        count = collection.count(builder.get());
        System.out.println("count = " + count);


        System.out.println("\nFind :");
        DBCursor cursor = collection.find();
                //builder.get(), new BasicDBObject("y",true).append("_id", true));
        cursor.sort(new BasicDBObject("_id",-1)).skip(2).limit(5);
        try{
            while (cursor.hasNext()){
                System.out.println(cursor.next());
            }
        }finally{
            cursor.close();
        }


    }
}
