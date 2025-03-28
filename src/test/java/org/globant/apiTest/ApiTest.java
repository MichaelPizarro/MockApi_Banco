package org.globant.apiTest;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import org.globant.model.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    private final String BASE_URL = "https://67e40c9a2ae442db76d2d123.mockapi.io/api/v1";

    @Test
    void verifyAndClean(){
        /*Obtenemos la cantidad de usuarios y la almancenamos en la variable cantidadUsers*/
        int cantidadUsers = given()
                                .when()
                                .get(BASE_URL+"/users")
                                .then().statusCode(200)
                                .extract()
                                .path("size()");

        /*Si hay usuarios los eliminamos*/
        if(cantidadUsers > 0){
            given()
                    .contentType(ContentType.JSON)
                    .when()
                    .delete(BASE_URL+"/users")
                    .then().statusCode(200);
        }

        //Confirmación que el endpoint está vacío
        given()
                .when()
                .get(BASE_URL+"/users")
                .then().statusCode(200)
                .body("size()", equalTo(0));


    }

    @Test
    void createUsersValidateUniqueEmail(){
        Faker faker = new Faker(); //La clase Faker nos servirá para crear usuarios aleatorios
        Map<String, Usuario> user = new HashMap<>(); //Guardamos los usuarios en la lista user

            while(user.size() < 10){
                String name = faker.name().firstName();
                String email = faker.internet().emailAddress();
                int age = faker.number().numberBetween(0,99);

                //Comprobamos que el email no esté duplicado
                if(!user.containsKey(email)) {
                    Usuario usuario = new Usuario(name, email, age);
                    user.put(email, usuario);

                }
            }


        //Enviamos el usuario mediante el POST
        for (Usuario usuario: user.values()) {
            given()
                    .contentType(ContentType.JSON)
                    .body(usuario)
                    .when()
                    .post(BASE_URL+"/users")
                    .then().statusCode(201);
        }



    }

    @Test
    void noDuplicatedEmails(){
        // Hacer la petición GET al endpoint
        List<String> emails = given()
                .when()
                .get(BASE_URL+"/users") // Endpoint directo
                .then()
                .statusCode(200) // Verifica que la respuesta sea 200
                .extract()
                .jsonPath()
                .getList("email");//Obtenemos la lista de correos

        // Convertir la lista a un Set para eliminar duplicados
        Set<String> uniqueEmails = emails.stream().collect(Collectors.toSet());

        // Validamos que no haya duplicados
        Assertions.assertTrue(emails.size() == uniqueEmails.size());
    }

    @Test
    void updateNumberAccount(){ //Comprobamos que que hayan cuentas
        given()
                .when()
                .get(BASE_URL+"/accounts")
                .then()
                .statusCode(200);
    }

    @Test
    void validateDeposit(){
        //Guardamos el salgo de la cuenta con id 7
        String saldo = given()
                             .when()
                             .get(BASE_URL+"/accounts/7")
                             .then().statusCode(200)
                             .extract()
                             .path("amount");

        saldo = saldo.replace(",","."); //Reemplazamos la coma por punto
        double saldoFinal = Double.parseDouble(saldo)+600.00; //Pasamos el String a double y sumamos una cifra,
                                                              // en este caso 600.00

        String bodyRequest = """
                
                    {
                        "amount": "%s",
                        "userId": "7",
                        "id": "7"
                    }
                
                                
                            """.formatted(String.format("%.2f",saldoFinal)); //Guardamos el bodyRequest en una variable

        given()
                .contentType(ContentType.JSON)
                .body(bodyRequest)
                .when()
                .put(BASE_URL+"/accounts/7") //Actualizamos la cuenta con id 7
                .then().statusCode(200);
    }

    @Test
    void validateWithdrawal(){

        String saldo = given()
                .when()
                .get(BASE_URL+"/accounts/7")
                .then().statusCode(200)
                .extract()
                .path("amount");

        saldo = saldo.replace(",",".");

        if(saldo.equals("0.00")){ // Comprobamos que el saldo sea suficiente
            System.out.println("No hay dinero suficiente");
        }else {
            double saldoFinal = Double.parseDouble(saldo)-300.00; //Retiramos el dinero y hacemos la resta al saldo

            String bodyRequest = """
                
                    {
                        "amount": "%s",
                        "userId": "7",
                        "id": "7"
                    }
                
                                
                            """.formatted(String.format("%.2f",saldoFinal));

            given()
                    .contentType(ContentType.JSON)
                    .body(bodyRequest)
                    .when()
                    .put(BASE_URL+"/accounts/7")
                    .then().statusCode(200);
        }
    }


}
