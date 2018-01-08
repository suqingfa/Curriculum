package application.retrofit;

import application.model.Result;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

import java.net.CookieManager;

public interface RetrofitService
{
    static RetrofitService getInstance()
    {
        OkHttpClient client = new OkHttpClient
                .Builder()
                .cookieJar(new JavaNetCookieJar(new CookieManager()))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://bkjwxk.sdu.edu.cn/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(RetrofitService.class);
    }

    @FormUrlEncoded
    @POST("/b/ajaxLogin")
    Call<String> login(@Field("j_username") String username, @Field("j_password") String passwordMD5);

    @POST("b/xk/xs/add/{kch}/{kxh}")
    Call<Result> addCourse(@Path("kch") String kch, @Path("kxh") String kxh);
}
