package okhttp;

import dto.ContactDtoLombok;
import dto.ResponceMessageDto;
import dto.TokenDto;
import dto.UserDtoLombok;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.BaseApi;

import java.io.IOException;

import static utils.RandomUtils.*;

public class AddNewContactTests implements BaseApi {
    TokenDto tokenDto;
    @BeforeClass
    public void login(){
        UserDtoLombok userDtoLombok= UserDtoLombok.builder()
                .username("piddk0fs8t@yandex.ru")
                .password("123TYUads$")
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(userDtoLombok),JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+LOGIN_PATH)
                .post(requestBody)
                .build();
        try(Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            if (response.isSuccessful()){
                System.out.println("Login is successful"+userDtoLombok.toString());
            tokenDto = GSON.fromJson(response.body().string(),TokenDto.class);

            }else System.out.println("Login failed"+response.code());
        } catch (IOException e) {
            System.out.println("Created exeption");
            throw new RuntimeException(e);
        }
    }
    @Test  (invocationCount = 3)
    public void addNewContactPositiveTest(){
        ContactDtoLombok contactDtoLombok = ContactDtoLombok.builder()
                .name(generateString(5))
                .lastName(generateString(8))
                .email(generateEmail(10))
                .phone(generatePhone(10))
                .address(generateString(10))
                .build();
        RequestBody requestBody = RequestBody.create(GSON.toJson(contactDtoLombok),JSON);
        Request request = new Request.Builder()
                .url(BASE_URL+ADD_NEW_CONTACT_PATH)
                .addHeader("Authorization",tokenDto.getToken())
                .post(requestBody)
                .build();
        try(Response response = OK_HTTP_CLIENT.newCall(request).execute()){
            if (response.isSuccessful()){
                ResponceMessageDto responceMessageDto = GSON.fromJson(response.body().string(), ResponceMessageDto.class);
                System.out.println(responceMessageDto.getMessage());
                Assert.assertTrue(responceMessageDto.getMessage().contains("Contact was added!"));


            }else Assert.fail("Login failed"+response.code());
        } catch (IOException e) {
            System.out.println("Created exeption");
            throw new RuntimeException(e);
        }
    }
}
