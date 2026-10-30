package apitest;

import apiendpoints.UserEndpoints;
import apipayload.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class UserTests {
    Faker faker;
    User userPayload;
    public Logger logger;

    @BeforeClass
    public void setupData() {
        faker = new Faker();
        userPayload = new User();

        userPayload.setID(faker.idNumber().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(5, 10));
        userPayload.setPhone(faker.phoneNumber().cellPhone());

        logger = LogManager.getLogger(this.getClass());
        logger.debug("debugging.........");

    }

    @Test(priority = 1)
    public void testPostUser() {
        logger.info("********* creating user ***********");
        Response response = UserEndpoints.createUser(userPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("********* User is created ***********");

    }

    @Test(priority = 2)
    public void testGetUser() {
        logger.info("********* Reading user info ***********");
        Response response = UserEndpoints.readUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("********* User info is displayed ***********");

    }

    @Test(priority = 3)
    public void testupdateUserByName() {
        logger.info("********* Updating user ***********");
        //update firstname,lastname and email using payload
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());

        Response response = UserEndpoints.UpdateUser(this.userPayload.getUsername(), userPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        //checking the updated data
        Response afterUpdateResponse = UserEndpoints.readUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(afterUpdateResponse.getStatusCode(), 200);
        logger.info("********* User is updated ***********");
    }

    @Test(priority = 4)
    public void testDeleteuser() {
        logger.info("********* Deleting User ***********");
        Response response = UserEndpoints.deleteUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("********* User is deleted ***********");

    }


}
