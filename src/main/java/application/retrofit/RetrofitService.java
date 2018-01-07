package application.retrofit;

import application.ContextHolder;
import application.entity.Config;
import application.model.CourseList;
import application.model.Result;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import retrofit2.*;
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

    default boolean login() throws Exception
    {
        Config config = ContextHolder.getConfig();
        if (config == null)
        {
            return false;
        }

        String passwordMd5 = Hex.encodeHexString(DigestUtils.md5(config.getPassword()));
        Response<String> response = login(config.getUsername(), passwordMd5).execute();
        return response.isSuccessful() && "success".equals(response.body());
    }

    @FormUrlEncoded
    @POST("/b/xk/xs/kcsearch")
    Call<Result<CourseList>> courseList(@Field("type") String type,              // type
                                        @Field("currentPage") int currentPage,
                                        @Field("kch") String kch,                // 课程号
                                        @Field("jsh") String jsh,                // 教师号
                                        @Field("skxq") String skxq,              // 上课星期
                                        @Field("skjc") String skjc,              // 上课节次
                                        @Field("kkxsh") String kkxsh);           // 开课学院号

    default Call<Result<CourseList>> courseList(int currentPage)
    {
        return courseList("kc", currentPage, "", "", "", "", "");
    }

    @POST("b/xk/xs/add/{kch}/{kxh}")
    Call<Result> addCourse(@Path("kch") String kch, @Path("kxh") String kxh);
}
