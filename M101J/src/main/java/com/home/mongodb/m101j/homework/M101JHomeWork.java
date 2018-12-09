package com.home.mongodb.m101j.homework;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.home.mongodb.m101j.util.Helpers.printJson;

/**
 * Created by DP052402 on 11/30/2018.
 */
public class M101JHomeWork {
    public static  void main(String[] args){
        M101JHomeWork thisClass = new M101JHomeWork();
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("students");
        MongoCollection<Document> collection = db.getCollection("grades");
        thisClass.homework2_3(collection);
    }

    public void homework2_3(MongoCollection<Document> coll){
        List<Document> results = coll.find(new Document("type","homework")).sort(new Document("student_id", 1).append("score", 1)).into(new ArrayList <Document>());
        System.out.println(results.size());
        int previousStudentId=-1;
        for(Document student: results){
            int studentId = student.getInteger("student_id").intValue();
            if(studentId!=previousStudentId){
                printJson(student);
                previousStudentId=studentId;
                coll.deleteOne(new Document("_id",student.get("_id")));
            }
        }
    }
}

