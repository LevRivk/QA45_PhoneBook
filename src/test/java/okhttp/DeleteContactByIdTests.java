package okhttp;

import dto.ContactDtoLombok;
import dto.ContactsDto;
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

public class DeleteContactByIdTests  implements BaseApi {
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
    public void deleteContactByIdPositiveTests(){
        String idFirstContact = "";
        Request requestGet = new Request.Builder()
                .url(BASE_URL+ADD_NEW_CONTACT_PATH)
                .addHeader("Authorization",tokenDto.getToken())
                .get()
                .build();
        try(Response response = OK_HTTP_CLIENT.newCall(requestGet).execute()){
            if (response.isSuccessful()){
                ContactsDto contactsDto = GSON.fromJson(response.body()
                        .string(),ContactsDto.class);
                for (ContactDtoLombok contactDtoLombok:contactsDto.getContacts()){
                    System.out.println(contactDtoLombok.toString());
                }
                idFirstContact = contactsDto.getContacts()[0].getId();
                System.out.println("First Id = "+ idFirstContact.toString());


            }else System.out.println("No contacts"+response.code());
        }catch (IOException e){
            System.out.println("Created exeption");
            throw new RuntimeException();
        }
        Request requestDelite = new Request.Builder()
                .url(BASE_URL+ADD_NEW_CONTACT_PATH+"/" + idFirstContact)
                .addHeader("Authorization",tokenDto.getToken())
                .delete()
                .build();
        try(Response response = OK_HTTP_CLIENT.newCall(requestDelite).execute()){
            if (response.isSuccessful()){
                System.out.println("Delete first contact");
                Assert.assertEquals(response.code(),200);

            }else Assert.fail("Delete failed"+response.code());
        } catch (IOException e) {
            System.out.println("Created exception");
            throw new RuntimeException(e);
        }

    }
    @Test
    public void deleteContactWithoutIdNegativeTests(){
        Request requestDelite = new Request.Builder()
                .url(BASE_URL+ADD_NEW_CONTACT_PATH+"/"+" ")
                .addHeader("Authorization",tokenDto.getToken())
                .delete()
                .build();
        try(Response response = OK_HTTP_CLIENT.newCall(requestDelite).execute()){
            if (!response.isSuccessful()){
                System.out.println("Delete failed");
                Assert.assertEquals(response.code(),500);

            }else Assert.fail("Delete success"+response.code());
        } catch (IOException e) {
            System.out.println("Created exception");
            throw new RuntimeException(e);
        }

    }
    @Test
    public void deleteContactWrongIdNegativeTests(){
        Request requestDelite = new Request.Builder()
                .url(BASE_URL+ADD_NEW_CONTACT_PATH+"/"+"WrongId")
                .addHeader("Authorization",tokenDto.getToken())
                .delete()
                .build();
        try(Response response = OK_HTTP_CLIENT.newCall(requestDelite).execute()){
            if (!response.isSuccessful()){
                System.out.println("Delete failed");
                Assert.assertEquals(response.code(),400);

            }else Assert.fail("Delete success"+response.code());
        } catch (IOException e) {
            System.out.println("Created exception");
            throw new RuntimeException(e);
        }

    }
    @Test
    public void deleteContactUnauthorizedNegativeTests(){
        String idFirstContact = "";
        Request requestGet = new Request.Builder()
                .url(BASE_URL+ADD_NEW_CONTACT_PATH)
                .addHeader("Authorization",tokenDto.getToken())
                .get()
                .build();
        try(Response response = OK_HTTP_CLIENT.newCall(requestGet).execute()){
            if (response.isSuccessful()){
                ContactsDto contactsDto = GSON.fromJson(response.body()
                        .string(),ContactsDto.class);
                for (ContactDtoLombok contactDtoLombok:contactsDto.getContacts()){
                    System.out.println(contactDtoLombok.toString());
                }
                idFirstContact = contactsDto.getContacts()[0].getId();
                System.out.println("First Id = "+ idFirstContact.toString());


            }else System.out.println("No contacts"+response.code());
        }catch (IOException e){
            System.out.println("Created exeption");
            throw new RuntimeException();
        }
        Request requestDelite = new Request.Builder()
                .url(BASE_URL+ADD_NEW_CONTACT_PATH+"/"+idFirstContact)
                .addHeader("Authorization","*")
                .delete()
                .build();
        try(Response response = OK_HTTP_CLIENT.newCall(requestDelite).execute()){
            if (!response.isSuccessful()){
                System.out.println("Delete failed");
                Assert.assertEquals(response.code(),401);

            }else Assert.fail("Delete success"+response.code());
        } catch (IOException e) {
            System.out.println("Created exception");
            throw new RuntimeException(e);
        }

    }
    @Test
    public void deleteContactNotFoundIdNegativeTest() { // все равно падает там другая ошибка но сам тест тип правильный
         String idContact = "";
        Request requestGet = new Request.Builder()
                .url(BASE_URL + ADD_NEW_CONTACT_PATH)
                .addHeader("Authorization", tokenDto.getToken())
                .get()
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(requestGet).execute()) {
            if (response.isSuccessful()) {
                ContactsDto contactsDto = GSON.fromJson(response.body()
                        .string(), ContactsDto.class);
                for (ContactDtoLombok contactDtoLombok : contactsDto.getContacts()) {
                    System.out.println(contactDtoLombok.toString());
                }
                idContact = contactsDto.getContacts()[0].getId();
                System.out.println("First Id =" + idContact.toString());

            } else System.out.println("No contacts" + response.code());
        } catch (IOException e) {
            System.out.println("Created exception");
            throw new RuntimeException();
        }
        Request requestDelete = new Request.Builder()
                .url(BASE_URL+ADD_NEW_CONTACT_PATH+"/"+idContact)
                .addHeader("Authorization", tokenDto.getToken())
                .delete()
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(requestDelete).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Delete first contact");
                Assert.assertEquals(response.code(), 200);

            } else Assert.fail("Delete failed " + response.code());
        } catch (IOException e) {
            Assert.fail("Created exception");
            throw new RuntimeException(e);
        }
        Request requestDelete2 = new Request.Builder()
                .url(BASE_URL+ADD_NEW_CONTACT_PATH+"/"+idContact)
                .addHeader("Authorization", tokenDto.getToken())
                .delete()
                .build();
        try (Response response = OK_HTTP_CLIENT.newCall(requestDelete2).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Delete failed");
                Assert.assertEquals(response.code(), 404);

            } else Assert.fail("Delete success " + response.code());
        } catch (IOException e) {
            Assert.fail("Created exception");
            throw new RuntimeException(e);
        }
    }
}
