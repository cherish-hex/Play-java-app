package util;

import com.mongodb.*;
import models.Pet;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cherish.sham on 8/11/16.
 */
public class PetsDataAccessor {


    private static PetsDataAccessor da;
    private static MongoClient mongoClient = new MongoClient("localhost", 27017);
    private static DB db = mongoClient.getDB("test");
    private static DBCollection collection = db.getCollection("petscollection");


    public static PetsDataAccessor getInstance() {
        if (da == null)
            return new PetsDataAccessor();
        else
            return da;
    }

    private PetsDataAccessor() {
    }

    public List<Pet> listAllPets() {

        List<Pet> petList = new ArrayList<>();
        DBCursor cursorDoc = collection.find();

        while (cursorDoc.hasNext()) {
            DBObject o = cursorDoc.next();
            Pet p = new Pet(o.get("name").toString(), ((int) o.get("age")), o.get("sex").toString());
            petList.add(p);
        }

        return petList;
    }

    public Pet findPet(String name) {

        Pet p = new Pet();
        BasicDBObject query = new BasicDBObject("name", name);
        DBCursor cur = collection.find(query);

        if (cur.hasNext()) {
            DBObject o = cur.next();
            p = new Pet(o.get("name").toString(), ((int) o.get("age")), o.get("sex").toString());
        }
        return p;
    }

    public boolean addPet(String name, int age, String sex){

        BasicDBObject query = new BasicDBObject("name", name);
        DBCursor cur = collection.find(query);

        if (cur.hasNext()) {
            return false;
        }
        else{
            BasicDBObject document = new BasicDBObject();
            document.put("name", name);
            document.put("age", age);
            document.put("sex", sex);
            collection.insert(document);
            return true;
        }
    }

    public boolean deletePet(String name){

        BasicDBObject document = new BasicDBObject("name", name);
        DBCursor cur = collection.find(document);

        if (cur.hasNext()) {
            collection.remove(document);
            return true;
        }
        else
            return false;
    }

    public boolean updatePet(String name, int age, String sex){

        BasicDBObject document = new BasicDBObject();
//        updateDocument.append("$set", new BasicDBObject().append("age", 8));
        BasicDBObject query = new BasicDBObject().append("name", name);

        DBCursor curs = collection.find(query);
        if (curs.hasNext()) {
            document.put("name", name);
            document.put("age", age);
            document.put("sex", sex);
            collection.update(query, document);
            return true;
        }
        else return false;
    }
}
