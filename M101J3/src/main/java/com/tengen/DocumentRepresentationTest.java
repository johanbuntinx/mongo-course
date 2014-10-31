package com.tengen;

import com.mongodb.BasicDBObject;

import java.util.Arrays;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: johan
 * Date: 10/24/14
 * Time: 12:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class DocumentRepresentationTest {
    public static void main(String[] args) {
        BasicDBObject doc = new BasicDBObject();
        doc.put("username","johan");
        doc.put("date", new Date());
        doc.put("languages", Arrays.asList("a","b")) ;
        doc.put("address",
                new BasicDBObject("street", "Molenstraat").
                        append("town","Alken").
                        append("postalCode", "3570"));
    }

}
