package application.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class AddCourse implements Serializable
{
    // 课程号
    @Id
    private String KCH;
    // 课序号
    private String KXH;

    // 是否添加成功
    private boolean add;
    private String result;
}
