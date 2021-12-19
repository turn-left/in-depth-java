package com.ethen.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;

public class QuickStartJavaDocTest {
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private MongoClient mongoClient;

    @Before
    public void init() {
        System.err.println("start connect mongo ...");
        mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("ethen_db");
        collection = database.getCollection("users");
        System.err.println("connected mongo @ localhost 27017 !!!");
    }

    @Test
    public void testInsert() {
        final Document doc = new Document();
        doc.append("username", "方鸿渐");
        doc.append("country", "China");
        doc.append("age", 26);
        doc.append("hobby", "guitar");
        doc.append("school", "克莱登");
        doc.append("height", 170);
        doc.append("balance", 1500);
        doc.append("balance_unit", "RMB_YUAN");
        final HashMap<String, Object> favMap = new HashMap<>();
        favMap.put("movies", Arrays.asList("碟中谍", "007:黑日危机", "源代码", "复仇者联盟"));
        favMap.put("cities", Arrays.asList("东莞", "广州", "宜昌"));
        doc.append("fav", favMap);


        final Document doc2 = new Document();
        doc2.append("username", "孙柔嘉");
        doc2.append("country", "China");
        doc2.append("age", 21);
        doc2.append("hobby", "sleep");
        doc2.append("school", "家里蹲");
        doc2.append("height", 153);
        doc2.append("balance", 150000);
        doc2.append("balance_unit", "RMB_YUAN");
        final HashMap<String, Object> favMap2 = new HashMap<>();
        favMap2.put("movies", Arrays.asList("天空之城", "千与千寻", "宫崎骏", "李佳琪"));
        favMap2.put("cities", Arrays.asList("东莞", "广州", "惠州"));
        doc2.append("fav", favMap2);
        collection.insertMany(Arrays.asList(doc, doc2));
    }


    @Test
    public void testQuery() {
        final FindIterable<Document> documents = collection.find();
        for (Document document : documents) {
            System.out.println(document);
        }
    }
}
