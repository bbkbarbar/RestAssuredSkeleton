package hu.nusz;

import groovy.json.JsonBuilder;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestDemo1 {

    @Test()
    void getWeatherDetails(){
        //baseURI

        //RestAssured.baseURI = "https://10.12.3.5:443/cgi-bin/auth_ext.pl";
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

        // request object
        RequestSpecification httpRequest = RestAssured.given();

        //Response response = httpRequest.request(Method.GET, "?login=aaktest&password=aaktest&cversion=v1&db=0");
        Response response = httpRequest.request(Method.GET, "/Budapest" );

        JSONParser parser = new JSONParser();
        JSONObject j = null;
        try {
            j = (JSONObject) parser.parse(response.getBody().asString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(j,"JSON object from response body is null.");

        System.out.println(response.getBody().asString());

        String hStr = j.get("Humidity").toString();
        String[] sA = hStr.split(" ");
        Assert.assertTrue (Integer.valueOf(sA[0]) < 83, "Túl magas páratartalom (" + sA[0] + ")");

        Assert.assertEquals(response.getStatusCode(), 200);

    }

    @Test()
    void getDHTDataFromMock(){
        //baseURI

        RestAssured.baseURI = "http://localhost:8083/pure";

        // request object
        RequestSpecification httpRequest = RestAssured.given();

        //Response response = httpRequest.request(Method.GET, "?login=aaktest&password=aaktest&cversion=v1&db=0");
        Response response = httpRequest.request(Method.GET, "" );

        JSONParser parser = new JSONParser();
        JSONObject j = null;
        try {
            j = (JSONObject) parser.parse(response.getBody().asString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(j,"JSON object from response body is null.");

        System.out.println(response.getBody().asString());

        JSONObject j2 = (JSONObject) j.get("values");
        System.out.println("\n\n" + j2.toString());

        System.out.println("Homerseklet: " + j2.get("tempC"));


    }

}
