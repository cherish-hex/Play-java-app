package controllers;


import models.Pet;
import play.Logger;
import play.libs.Json;
import play.mvc.*;
import util.PetsDataAccessor;
import java.util.*;


public class Application extends Controller {

    public Result index() {
        Logger.info("Got: " + request().method() + " URI: " + request().uri());

        return ok("Hello. Welcome to my pet shop!");
    }

    public Result listAllPets(){

        Logger.info("Got: " + request().method() + " URI: " + request().uri());

        try {
            List<Pet> petList = PetsDataAccessor.getInstance().listAllPets();
            return ok(Json.toJson(petList));

        }catch (Exception e){
            Logger.error("Error: " + " URI: " + request().uri());
            return internalServerError("Oops!!!");
        }
    }

    public Result showPet(String name){

        Logger.info("Got: " + request().method() + " URI: " + request().uri());

        try {
            Pet p = PetsDataAccessor.getInstance().findPet(name);

            if (p.getName() != null)
                return ok(Json.toJson(p));
            else
                return notFound("Pet not found!");

        }catch (Exception e){
            Logger.error("Error: " + " URI: " + request().uri());
            return internalServerError("Oops!!!");
        }
    }

    public Result addPet(String name, int age, String sex){

        Logger.info("Got: " + request().method() + " URI: " + request().uri());

        try {
            if(name.length() == 0 || (age > 31 || age < 0)
                    || !(sex.equals("male") || sex.equals("female"))) {
                return badRequest("Invalid Data!");
            }

            boolean result = PetsDataAccessor.getInstance().addPet(name, age, sex);

            if(result)
                return created("Pet added!");
            else
                return badRequest("Pet already exists!");

        }catch (Exception e){
            Logger.error("Error: " + " URI: " + request().uri());
            return internalServerError("Oops!!!");
        }
    }

    public Result deletePet(String name){

        Logger.info("Got: " + request().method() + " URI: " + request().uri());

        try {
            boolean result = PetsDataAccessor.getInstance().deletePet(name);

            if (result)
                return ok("Pet deleted!");
            else
                return notFound("Pet not found!");

        }catch (Exception e){
            Logger.error("Error: " + " URI: " + request().uri());
            return internalServerError("Oops!!!");
        }
    }

    public Result updatePet(){

        Logger.info("Got: " + request().method() + " URI: " + request().uri());

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
            if(name.length() == 0 || (age > 31 || age < 0)
                    || !(sex.equals("male") || sex.equals("female"))) {
                return badRequest("Invalid Data!");
            }

            boolean result = PetsDataAccessor.getInstance().updatePet(name, age, sex);
            if (result)
                return ok("Pet data updated!");
            else
                return notFound("Pet not found!");

        }catch (NumberFormatException n){
            Logger.error("Error: " + " URI: " + request().uri());
            return badRequest("Invalid Data!");
        }
        catch (Exception e){
            Logger.error("Error: " + " URI: " + request().uri());
            return internalServerError("Oops!!!");
        }
    }
}
