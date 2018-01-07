package application;

import application.model.CourseList;
import application.model.Result;
import application.retrofit.RetrofitService;
import org.junit.Assert;
import org.junit.Test;
import retrofit2.Response;

public class JavaTest
{
    @Test
    public void testCourseList() throws Exception
    {
        RetrofitService retrofitService = RetrofitService.getInstance();
        Assert.assertTrue(retrofitService.login());

        Response<Result<CourseList>> response = retrofitService.courseList(1).execute();
        Assert.assertTrue(response.isSuccessful());

        Result<CourseList> result = response.body();
        Assert.assertEquals(result.getResult(), "success");
    }

    @Test
    public void testAddCourse() throws Exception
    {
        RetrofitService retrofitService = RetrofitService.getInstance();
        Assert.assertTrue(retrofitService.login());

        Response<Result> response = retrofitService.addCourse("0061803210", "900").execute();
        Assert.assertTrue(response.isSuccessful());

        System.out.println(response.body());
    }
}
