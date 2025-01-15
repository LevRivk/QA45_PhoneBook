package okhttp;

import dto.ErrorMessageDto;
import dto.TokenDto;
import dto.UserDtoLombok;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import utils.BaseApi;

import java.io.IOException;

import static utils.RandomUtils.generateEmail;

public class LoginTests implements BaseApi {
    SoftAssert softAssert = new SoftAssert();
    UserDtoLombok userDtoLombok;
    @BeforeClass
    public void registration(){
         userDtoLombok = UserDtoLombok.builder()
                .username(generateEmail(10))
                .password("123TYUads$")
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(userDtoLombok),JSON);//Запрос
        Request request = new Request.Builder()
                .url(BASE_URL+REGISTRATION_PATH)
                .post(requestBody)
                .build();
        try {
            Response response = OK_HTTP_CLIENT.newCall(request).execute();
            if (response.isSuccessful())
                System.out.println("Registration is successful"+userDtoLombok.toString());
            else System.out.println("Registration failed"+response.code());
        } catch (IOException e) {
            System.out.println("Created exeption");
            throw new RuntimeException(e);
        }
    }
    @Test
    public void loginPositiveTest(){
        RequestBody requestBody = RequestBody.create(GSON.toJson(userDtoLombok),JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_PATH)
                .post(requestBody)
                .build();
        try(Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            if (response.isSuccessful()){
                System.out.println("Login is successful"+userDtoLombok.toString());
                TokenDto tokenDto = GSON.fromJson(response.body().string(),TokenDto.class);
                System.out.println(tokenDto.getToken());
                Assert.assertFalse(tokenDto.getToken().isBlank());
            }else Assert.fail("Login failed"+response.code());
        } catch (IOException e) {
            Assert.fail("Created exeption");
            throw new RuntimeException(e);
        }
    }
    @Test
    public void loginNegativeTestWrongPassword(){
        userDtoLombok.setPassword("Wrongpassword");
        RequestBody requestBody = RequestBody.create(GSON.toJson(userDtoLombok),JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_PATH)
                .post(requestBody)
                .build();
        try(Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            if (!response.isSuccessful()){
                ErrorMessageDto errorMessageDto = GSON.fromJson(response.body().string(), ErrorMessageDto.class);
                System.out.println(response.toString());
                softAssert.assertEquals(errorMessageDto.getStatus(),401);
                System.out.println(errorMessageDto.getMessage().toString());
                softAssert.assertEquals(errorMessageDto.getMessage().toString(),"Login or Password incorrect");
                System.out.println(errorMessageDto.getError());
                softAssert.assertEquals(errorMessageDto.getError(),"Unauthorized");
                softAssert.assertAll();

            }else Assert.fail("Login successful"+response.code());
        } catch (IOException e) {
            Assert.fail("Created exeption");
            throw new RuntimeException(e);
        }
    }
}
