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
import util.PetsDataAccessor;
import views.html.*;
//import models.Person;
import play.data.FormFactory;
import javax.inject.Inject;
import java.io.File;
import java.io.OutputStream;
import java.util.*;

import static play.libs.Json.*;

public class Application extends Controller {

    public Result index() {
        return ok("Hello. Welcome to my pet shop!");
    }

    public Result listAllPets(){

        try {
            List<Pet> petList = PetsDataAccessor.getInstance().listAllPets();
            return ok(Json.toJson(petList));

        }catch (Exception e){
            return internalServerError("Oops!!!");
        }
    }

    public Result showPet(String name){

        try {
            Pet p = PetsDataAccessor.getInstance().findPet(name);

            if (p.getName() != null)
                return ok(Json.toJson(p));
            else
                return notFound("Pet not found!");
        }catch (Exception e){
            return internalServerError("Oops!!!");
        }
    }

    public Result addPet(String name, int age, String sex){

        try {
            boolean result = PetsDataAccessor.getInstance().addPet(name, age, sex);

            if(result)
                return created("Pet added!");
            else
                return badRequest("Pet already exists!");
        }catch (Exception e){
            return internalServerError("Oops!!!");
        }
    }

    public Result deletePet(String name){

        try {
            
            boolean result = PetsDataAccessor.getInstance().deletePet(name);

            if (result)
                return ok("Pet deleted!");
            else
                return notFound("Pet not found!");
        }catch (Exception e){
            return internalServerError("Oops!!!");
        }
    }

    public Result updatePet(){

        String name = "";
        String a = "";
        String sex = "";

        try {

            Map<String, String[]> data = request().body().asFormUrlEncoded();

            for (String key : data.keySet()) {
                switch (key) {
                    case "name":
                        name = data.get(key)[0];
                        break;
                    case "age":
                        a = data.get(key)[0];
                        break;
                    case "sex":
                        sex = data.get(key)[0];
                        break;
                }
            }
            int age = Integer.parseInt(a);

            boolean result = PetsDataAccessor.getInstance().updatePet(name, age, sex);
            if (result)
                return ok("Pet data updated!");
            else
                return notFound("This Pet does not exist!");
        }catch (NumberFormatException n){
            return badRequest("Invalid Data");
        }
        catch (Exception e){
            return internalServerError("Oops!!!");
        }
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
