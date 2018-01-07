package application.model;

import application.entity.Course;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class CourseList implements Serializable
{
    private int currentPage;
    private int lastIndex;
    private int nextPage;
    private int perPageNum;
    private int prePage;
    private int startIndex;
    private int totalPages;
    private int totalRows;

    private List<Course> resultList;
}
