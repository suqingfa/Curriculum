package application.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "login")
public class LoginProperties
{
    private String username;
    private String password;
    private boolean getCourseList = true;
}
