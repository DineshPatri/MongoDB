/*
 * Copyright 2015 MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.home.mongodb.myexploreobjects;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.home.mongodb.m101j.util.Helpers.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.descending;

/**
 * Explore more on builders like
 * com.mongodb.client.model.Projections
 * com.mongodb.client.model.Filters
 * com.mongodb.client.model.Sorts
 */
public class FindTest {
    public static void main(String[] args) {
        FindTest thisClass = new FindTest();
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("course");
        MongoCollection<Document> collection = database.getCollection("findTest");
        MongoCollection<Document> collection2 = database.getCollection("findWithSortTest");
        thisClass.exploreSorts(collection2);
        collection.drop();
        //thisClass.exploreFind(collection);
        //thisClass.exploreProjections(collection);

    }
    public void exploreFind(MongoCollection<Document> collection ){

        // insert 10 documents
        for (int i = 0; i < 10; i++) {
            collection.insertOne(new Document("x", i));
        }

        System.out.println("Find one:");
        Document first = collection.find().first();
        printJson(first);

        System.out.println("Find all with into: ");
        List<Document> all = collection.find().into(new ArrayList<Document>());
        for (Document cur : all) {
            printJson(cur);
        }

        System.out.println("Find all with iteration: ");
        try(MongoCursor<Document> cursor = collection.find().iterator();){//need to close the cursor, to avoid memory leaks when exceptions occur
            while (cursor.hasNext()) {
                Document cur = cursor.next();
                printJson(cur);
            }
        }

        System.out.println("Count:");
        long count = collection.countDocuments();//count() is deprecated;
        System.out.println(count);
    }
    public void exploreProjections(MongoCollection<Document> collection ){
        for (int i = 0; i < 10; i++) {
            collection.insertOne(new Document()
                    .append("x", new Random().nextInt(2))
                    .append("y", new Random().nextInt(100))
                    .append("i", i));
        }

        Bson filter = and(eq("x", 0), gt("y", 10), lt("y", 90));

//        Bson projection = new Document("y", 1).append("i", 1).append("_id", 0);
        Bson projection = fields(include("y", "i"), excludeId());

        List<Document> all = collection.find(filter)
                .projection(projection)
                .into(new ArrayList<Document>());

        for (Document cur : all) {
            printJson(cur);
        }
    }
    public void exploreSorts(MongoCollection<Document> collection){
        Bson projection = fields(include("i", "j"));
        Bson sort = new Document("i",-1).append("j",1);// first sorts by i in desc order and then where ever the i is same the j value will be sorted in asc order.
        //Bson sort = descending("j", "i");

        List<Document> all = collection.find()
                .projection(projection)
                .sort(sort)
                //.skip(2)//skip first 20 results, -20 will skip last 20 results
                //.limit(50)// limit to total of 50 results.
                .into(new ArrayList<Document>());
        for (Document cur : all) {
            printJsonWithoutIndent(cur);
        }
    }
}

