package controllers;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mongodb.*;
import models.Pet;
import play.*;
import play.libs.Json;
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
            List<Pet> petList = new ArrayList<>();
       //StringBuilder result = new StringBuilder();
       // ObjectNode res = Json.newObject();
       // ObjectMapper mapper = new ObjectMapper();
        DBCursor cursorDoc = collection.find();
        for (int i=0;cursorDoc.hasNext();i++) {
            DBObject o = cursorDoc.next();
            Pet p = new Pet(o.get("name").toString(),((int)o.get("age")),o.get("sex").toString());
            petList.add(p);
          //result.append(mapper.writeValueAsString(p));
          //  JsonNode personJson = Json.toJson(p);
          //  result.append(personJson);
        }
            return ok(Json.toJson(petList));
            //return ok(result.toString());
        }catch (Exception e){
            return internalServerError("Oops");
        }
    }

    public Result showPet(String petName){

        BasicDBObject query = new BasicDBObject("name",petName);
        DBCursor cur = collection.find(query);

        if (cur.hasNext()) {
            DBObject o = cur.next();
            Pet p = new Pet(o.get("name").toString(),((int)o.get("age")),o.get("sex").toString());
            return ok(Json.toJson(p));
        }
        else
            return notFound("Pet not found!");
    }

    public Result addPet(String name, int age, String sex){

        BasicDBObject query = new BasicDBObject("name", name);
        DBCursor cur = collection.find(query);

        if (cur.hasNext()) {
            return badRequest("Pet already exists");
        }
        else{
            BasicDBObject document = new BasicDBObject();
            document.put("name", name);
            document.put("age", age);
            document.put("sex", sex);
            collection.insert(document);
            return created("Pet added!");
        }
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
        else return notFound("This Pet does not exist");
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
