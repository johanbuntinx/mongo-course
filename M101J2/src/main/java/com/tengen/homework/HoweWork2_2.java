package com.tengen.homework;

import com.google.common.collect.Maps;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: johan
 * Date: 10/24/14
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class HoweWork2_2 {

    public static void main(String[] args) throws UnknownHostException {
        DBCollection collection = getCollection();

        QueryBuilder builder = QueryBuilder.start("type").is("homework");
        DBCursor cursor = collection.find(builder.get());
        cursor.sort(new BasicDBObject("student_id",1).append("score",1));
        Integer previousId = -1;
        Integer currentId;
        int removedRecords = 0;
        Map<Integer, Integer> homeworkScoresPerStudent = newHashMap();
        try{
            while (cursor.hasNext()){
                DBObject doc = cursor.next();
                currentId = (Integer) doc.get("student_id");
                //System.out.println("previousId = " + previousId+" currentId = " + currentId);
                addScoreForStudent(homeworkScoresPerStudent, currentId);
                System.out.print("currentId = " + currentId + " score: " + doc.get("score"));
                if (!currentId.equals(previousId)){
                    System.out.println(" lowest score for student "+currentId+" -->This one will be removed");
//                    collection.remove(new BasicDBObject("_id",doc.get("_id")));
                    removedRecords++;
                }else {
                    System.out.println();
                }

                previousId = currentId;
            }
        }finally{
            cursor.close();
        }

        System.out.println("removedRecords = " + removedRecords);
        System.out.println("homeworkScoresPerStudent = " + homeworkScoresPerStudent);
        System.out.println("numberOfStudents= " + homeworkScoresPerStudent.keySet().size());


    }

    private static void addScoreForStudent(Map<Integer, Integer> homeworkScoresPerStudent, Integer currentId) {
        Integer numberOfScores = homeworkScoresPerStudent.get((currentId));
        if (numberOfScores == null){
            numberOfScores = 1;
        }else {
            numberOfScores++;
        }
        homeworkScoresPerStudent.put(currentId,numberOfScores);
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

    private static DBCollection getCollection() throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("students");
        DBCollection collection = courseDB.getCollection("grades");
        return collection;
    }
}
