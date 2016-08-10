package controllers;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.*;
import models.Pet;
import play.*;
import play.mvc.*;
import views.html.*;
//import models.Person;
import play.data.FormFactory;
import javax.inject.Inject;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static play.libs.Json.*;

public class Application extends Controller {

//    @Inject
//    FormFactory formFactory;


    MongoClient mongoClient = new MongoClient("localhost", 27017);
    DB db = mongoClient.getDB("test");
    DBCollection collection = db.getCollection("petscollection");


    public Result index() {
        return ok("Hello. Welcome to my pet shop!");
    }

    public Result listAllPets(){

        try {
//        JSONObject json = new JSONObject();
//        JSONArray array=new JSONArray();
        StringBuilder result = new StringBuilder();
        JSON
        ObjectMapper mapper = new ObjectMapper();
        DBCursor cursorDoc = collection.find();
        for (int i=0;cursorDoc.hasNext();i++) {
            DBObject o = cursorDoc.next();
            Pet p = new Pet(o.get("name").toString(),((int)o.get("age")),o.get("sex").toString());
            result.append(mapper.writeValueAsString(p));
        }
            return ok(result.toString());
        }catch (Exception e){
            return internalServerError("Oops");
        }
    }

    public Result showPet(String petName){

        BasicDBObject query = new BasicDBObject("name",petName);
        DBCursor curs = collection.find(query);

        if (curs.hasNext())
            return ok("Found " + curs.next());
        else
            return notFound("Pet not found!");
    }

    public Result addPet(String name, int age, String sex){

        BasicDBObject document = new BasicDBObject();
        document.put("name", name);
        document.put("age", age);
        document.put("sex", sex);
        collection.insert(document);
        return ok("Pet added!");
    }

    public Result deletePet(String name){

        BasicDBObject document = new BasicDBObject();
        document.put("name", name);
        collection.remove(document);
        return ok("Pet deleted!");
    }

    public Result updatePet(String name, int age, String sex){

        BasicDBObject document = new BasicDBObject();
//        updateDocument.append("$set", new BasicDBObject().append("age", 8));
        BasicDBObject query = new BasicDBObject().append("name", name);

        DBCursor curs = collection.find(query);
        if (curs.hasNext()) {
            document.put("name", name);
            document.put("age", age);
            document.put("sex", sex);
            collection.update(query, document);
            return ok("Pet data updated!");
        }
        else return notFound("The Pet does not exist");
    }

//    @Transactional
//    public Result addPerson() {
//        Person person = formFactory.form(Person.class).bindFromRequest().get();
//        JPA.em().persist(person);
//        return redirect(routes.Application.index());
//    }

//    @Transactional(readOnly = true)
//    public Result getPersons() {
//        List<Person> persons = (List<Person>) JPA.em().createQuery("select p from Person p").getResultList();
//        return ok(toJson(persons));
//    }
}
