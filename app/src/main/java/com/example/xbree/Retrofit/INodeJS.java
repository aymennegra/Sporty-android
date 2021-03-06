package com.example.xbree.Retrofit;

import android.widget.EditText;

import com.example.xbree.Entities.Evenement;
import com.example.xbree.Entities.Participants;
import com.example.xbree.Entities.User;
import com.example.xbree.Entities.UserCommentaire;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface INodeJS {
    @POST("register")
    @FormUrlEncoded
    Observable<String> registerUser (@Field("email") String email,
                                    @Field("name") String name,
                                    @Field("lname") String lname,
                                    @Field("phone") String phone,
                                    @Field("password") String password);

    @POST("commenter")
    @FormUrlEncoded
    Observable<String> commenterUser (@Field("commentaire") String commentaire,
                                     @Field("id_user") int id_user,
                                     @Field("nom_user") String nom_user);

    @POST("login")
    @FormUrlEncoded
    Observable<String> loginUser (@Field("email") String email,
                                 @Field("password") String password);

    @GET("/user/{email}")

    Call<User> getUser(@Path("email") String email);

    @GET("/GetUE/{id_user}")
    Call<User> getUserE(@Path("id_user") int id);


    @POST("/evenement/add")
    @FormUrlEncoded
    Observable<String> addEvenement (@Field("nom") String nom,
                                     @Field("type") String type,
                                     @Field("dateDebut") String dateDebut,
                                     @Field("dateFin") String dateFin,
                                     @Field("distance") int distance,
                                     @Field("lieu") String lieu,
                                     @Field("infoline") int infoline,
                                     @Field("description") String description,
                                     @Field("prix") int prix,
                                     @Field("nbPlace") int nbPlace,
                                     @Field("id_user") int id_user);

    @Multipart
    @POST("/upload")
    Call<ResponseBody> postImage(@Part MultipartBody.Part image, @Part("upload") RequestBody name);

    @GET("/uploads/{upload}")
    Call<ResponseBody> getImage(@Path("upload") String n);

    @GET("/GetEvents/")
    Call<List<Evenement>> getEventsList();

    @GET("/GetComs/")
    Call<List<UserCommentaire>> getComsList();

    @PUT("/user/update{id}")
    @FormUrlEncoded
    Call<User> updateProfile(@Field("name") String name,
                             @Field("lname") String lname,
                             @Field("email") String email,
                             @Field("password") String password,
                             @Field("phone") int phone);

    @DELETE("/user/{id}")
    Call<User> DeleteUser(@Path("id") int id);
    @GET("/GetParticipants")
    Call<List<Participants>> getParticipantList();


    @DELETE("/particip/delete/{id}")
    Call<Participants> deleteparticipant(@Path("id") int id_evenement);

    @GET("/GetEvenementUser/{id_user}")
    Call<List<Evenement>> getparticipList(@Path("id_user") int id_user);

    @PUT("/evenement/update/{id}")
    Call<Evenement> updateEv(@Path("id") int id);

    @POST("/participant/add/")
    @FormUrlEncoded
    Observable<String> addParticipant (@Field("id_user") int id_user,
                                       @Field("id_evenement") int id_evenement);



}
