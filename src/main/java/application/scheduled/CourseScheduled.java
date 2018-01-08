package application.scheduled;

import application.model.Result;
import application.retrofit.RetrofitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
public class CourseScheduled
{
    private RetrofitService retrofitService = RetrofitService.getInstance();

    private String username;
    private String password;
    private Map<String, String> map;

    private void init()
    {
        if (username != null)
        {
            return;
        }

        map = new HashMap<>();
        Scanner scanner = new Scanner(getClass().getResourceAsStream("/list.txt"));
        if (scanner.hasNext())
        {
            username = scanner.next();
        }
        if (scanner.hasNext())
        {
            password = scanner.next();
        }
        while (scanner.hasNext())
        {
            map.put(scanner.next(), scanner.next());
        }

        if (username == null || password == null || map.size() == 0)
        {
            log.error("fail");
            System.exit(0);
        }
    }

    @Scheduled(fixedDelay = 2 * 60 * 1000)
    public void login() throws Exception
    {
        init();

        String passwordMd5 = Hex.encodeHexString(DigestUtils.md5(password));
        Response<String> response = retrofitService.login(username, passwordMd5).execute();
        if (!response.isSuccessful() || !"success".equals(response.body()))
        {
            log.error("login fail");
            System.exit(0);
        }
    }

    @Scheduled(fixedRate = 50, initialDelay = 500)
    public void addCourse()
    {
        init();

        map.forEach((kch, kxh) ->
        {
            try
            {
                Response<Result> response = retrofitService.addCourse(kch, kxh).execute();
                Result result = response.body();
                if (response.isSuccessful() && "success".equals(result.getResult()))
                {
                    log.warn("success kch:{} kxh:{} {}", kch, kxh, result.getMsg());
                }
                else
                {
                    log.info("kch:{} kxh:{} {}", kch, kxh, result.getMsg());
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }
}
