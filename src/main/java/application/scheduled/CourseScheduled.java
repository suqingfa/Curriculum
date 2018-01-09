package application.scheduled;

import application.ContextHolder;
import application.entity.AddCourse;
import application.entity.Config;
import application.model.CourseList;
import application.model.Result;
import application.repository.AddCourseRepository;
import application.repository.CourseRepository;
import application.retrofit.RetrofitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Component
@Transactional
public class CourseScheduled
{
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private AddCourseRepository addCourseRepository;

    private int nextCoursePage = 1;
    private RetrofitService retrofitService = RetrofitService.getInstance();

    @Scheduled(fixedDelay = 2 * 60 * 1000)
    public void login() throws Exception
    {
        boolean login = retrofitService.login();
        if (!login)
        {
            log.error("login fail");
        }
        else
        {
            log.info("login success");
        }
    }

    @Scheduled(fixedDelay = 100, initialDelay = 500)
    public void getCourseList() throws Exception
    {
        Config config = ContextHolder.getConfig();
        if (config == null || !config.isGetCourseList())
        {
            return;
        }

        Response<Result<CourseList>> response = retrofitService.courseList(nextCoursePage++).execute();
        Result<CourseList> result = response.body();

        if (!response.isSuccessful() || !"success".equals(result.getResult()))
        {
            log.error("Get course list fail, page = {}", nextCoursePage);
            return;
        }

        courseRepository.save(result.getObject().getResultList());
        log.info("Get course list success, page = {} ", nextCoursePage - 1);

        if (nextCoursePage == result.getObject().getTotalPages())
        {
            nextCoursePage = 1;
        }
    }

    @Scheduled(fixedDelay = 50, initialDelay = 500)
    public void addCourse() throws Exception
    {
        List<AddCourse> list = addCourseRepository.findByAdd(false);
        if (list.size() == 0)
        {
            log.info("AddCourse list size == 0");
            return;
        }

        for (AddCourse addCourse : list)
        {
            Response<Result> response = retrofitService.addCourse(addCourse.getKCH(), addCourse.getKXH()).execute();
            Result result = response.body();

            if (response.isSuccessful() && "success".equals(result.getResult()))
            {
                addCourse.setAdd(true);
            }
            addCourse.setResult(result.getMsg());
            addCourseRepository.save(addCourse);

            log.warn("Add course kch:{} kxh:{} result:{}", addCourse.getKCH(), addCourse.getKXH(), addCourse
                    .getResult());
        }
    }
}
