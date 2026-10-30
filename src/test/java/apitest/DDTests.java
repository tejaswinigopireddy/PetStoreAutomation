package apitest;

import apiendpoints.UserEndpoints;
import apipayload.User;
import apiutilities.DataProviders;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import static io.restassured.RestAssured.*;

public class DDTests {

    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void testPostUser(String userID, String uname, String fname, String lname, String useremail, String pwd,String phone) {
        User Userpayload = new User();
        Userpayload.setID(Integer.parseInt(userID));
        Userpayload.setUsername(uname);
        Userpayload.setFirstName(fname);
        Userpayload.setLastName(lname);
        Userpayload.setEmail(useremail);
        Userpayload.setPassword(pwd);
        Userpayload.setPhone(phone);

        Response response = UserEndpoints.createUser(Userpayload);
        Assert.assertEquals(response.getStatusCode(),200);

    }

    @Test(priority = 2, dataProvider = "UserNames", dataProviderClass = DataProviders.class)
    public void testDeleteUser(String uname) {
        Response response = UserEndpoints.deleteUser(uname);
        Assert.assertEquals(response.getStatusCode(),200);

    }


}
