package application.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Course implements Serializable
{
    @Id
    private int NUM;

    // 课程号
    private String KCH;
    // 课序号
    private String KXH;

    // 教师号
    private String JSH;
    // 教师名
    private String JSM;

    // 课程类别
    private String KCLB;
    // 课程类别名称
    private String KCLBMC;

    // 课程名
    private String KCM;

    // 开课学院号
    private String KKXSH;
    // 开课学院名
    private String KKXSM;

    // 上课时间地点
    private String SJDD;

    // 学分
    private double XF;

    // 课余量
    private int kyl;
}
