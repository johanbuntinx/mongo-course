package com.tengen.homework;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.*;

import static com.google.common.collect.Maps.newHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: johan
 * Date: 10/24/14
 * Time: 2:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class HoweWork3_1 {

    public static void main(String[] args) throws UnknownHostException {
        DBCollection collection = getCollection("school","students");


        //DBObject doc = collection.findOne();
//        System.out.println("doc = " + doc);
//        BasicDBList scoresList = (BasicDBList) doc.get("scores");
//        System.out.println("scoresList = " + scoresList);
//        Scores scores = new Scores(scoresList);
//        scores.removeLowestHomeworkScore();

        DBCursor cursor = collection.find();

        try{
            while (cursor.hasNext()){
                DBObject doc = cursor.next();
                System.out.println("doc = " + doc);
                BasicDBList scoresList = (BasicDBList) doc.get("scores");
                System.out.println("scoresList = " + scoresList);
                Scores scores = new Scores(scoresList);
                List scoresWithoutLowestHomework = scores.removeLowestHomeworkScore();

               // collection.update(new BasicDBObject("_id", doc.get("_id")),new BasicDBObject("$set", new BasicDBObject("scores",scoresWithoutLowestHomework)));

            }
        }finally{
            cursor.close();
        }

    }

    private static class Scores {
        private List<Object> dbScores;

        private Scores(List<Object> dbScores) {
            this.dbScores = dbScores;
        }

        public List removeLowestHomeworkScore(){
            Predicate<Object> isHomework = new Predicate<Object>() {
                @Override
                public boolean apply(Object input) {
                    return ((Map) input).get("type").equals("homework");
                }
            };
            List nonHomeWorkScores = Lists.newArrayList(Collections2.filter(dbScores, Predicates.not(isHomework)));
            System.out.println("nonHomeWorkScores = " + nonHomeWorkScores);
            List<Object> homeWorkScores = Lists.newArrayList(Collections2.filter(dbScores, isHomework));
            Comparator byScoreDesc = new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    return - ((Double) ((Map) o1).get("score")).compareTo((Double) ((Map) o2).get("score"));

                }
            };
            Collections.sort(homeWorkScores, byScoreDesc);

            System.out.println("homeWorkScores = " + homeWorkScores);

            homeWorkScores.remove(homeWorkScores.size()-1);
            List allWithoutLowestHomeworkScore = Lists.newArrayList(nonHomeWorkScores);
            allWithoutLowestHomeworkScore.addAll(homeWorkScores);
            System.out.println("allWithoutLowestHomeworkScore = " + allWithoutLowestHomeworkScore);
            return allWithoutLowestHomeworkScore;

        }



    }

//    public static void main(String[] args) throws UnknownHostException {
//        DBCollection collection = getCollection();
//
//        QueryBuilder builder = QueryBuilder.start("type").is("homework");
//        DBCursor cursor = collection.find(builder.get());
//        cursor.sort(new BasicDBObject("student_id",1).append("score",1));
//        Integer previousId = -1;
//        Integer currentId;
//        int removedRecords = 0;
//        Map<Integer, Integer> homeworkScoresPerStudent = newHashMap();
//        try{
//            while (cursor.hasNext()){
//                DBObject doc = cursor.next();
//                currentId = (Integer) doc.get("student_id");
//                //System.out.println("previousId = " + previousId+" currentId = " + currentId);
//                addScoreForStudent(homeworkScoresPerStudent, currentId);
//                System.out.print("currentId = " + currentId + " score: " + doc.get("score"));
//                if (!currentId.equals(previousId)){
//                    System.out.println(" lowest score for student "+currentId+" -->This one will be removed");
////                    collection.remove(new BasicDBObject("_id",doc.get("_id")));
//                    removedRecords++;
//                }else {
//                    System.out.println();
//                }
//
//                previousId = currentId;
//            }
//        }finally{
//            cursor.close();
//        }
//
//        System.out.println("removedRecords = " + removedRecords);
//        System.out.println("homeworkScoresPerStudent = " + homeworkScoresPerStudent);
//        System.out.println("numberOfStudents= " + homeworkScoresPerStudent.keySet().size());
//
//
//    }

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

    private static DBCollection getCollection(String db, String collectionName) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB(db);
        DBCollection collection = courseDB.getCollection(collectionName);
        return collection;
    }
}
