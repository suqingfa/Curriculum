package application.repository;

import application.entity.AddCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddCourseRepository extends JpaRepository<AddCourse, String>
{
    List<AddCourse> findByAdd(boolean add);
}
