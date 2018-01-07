package application.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Config implements Serializable
{
    @Id
    private String id;

    private String username;
    private String password;
    private boolean getCourseList;
}
