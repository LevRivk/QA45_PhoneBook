package okhttp;

import dto.ContactDtoLombok;
import dto.ContactsDto;
import dto.TokenDto;
import dto.UserDtoLombok;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.BaseApi;

import java.io.IOException;

public class GetAllUserContactTests implements BaseApi {
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
                tokenDto = GSON.fromJson(response.body().string(), TokenDto.class);

            }else System.out.println("Login failed"+response.code());
        } catch (IOException e) {
            System.out.println("Created exeption");
            throw new RuntimeException(e);
        }
    }
    @Test
    public void getAllUserContactsPositiveTests(){
        Request request = new Request.Builder()
                .url(BASE_URL+ADD_NEW_CONTACT_PATH)
                .addHeader("Authorization",tokenDto.getToken())
                .get()
                .build();
try(Response response = OK_HTTP_CLIENT.newCall(request).execute()){
    if (response.isSuccessful()){
        ContactsDto contactsDto = GSON.fromJson(response.body()
                .string(),ContactsDto.class);
        for (ContactDtoLombok contactDtoLombok:contactsDto.getContacts()){
            System.out.println(contactDtoLombok.toString());
        }

    }else System.out.println("No contacts"+response.code());
}catch (IOException e){
    System.out.println("Created exeption");
    throw new RuntimeException();
}
    }
}
