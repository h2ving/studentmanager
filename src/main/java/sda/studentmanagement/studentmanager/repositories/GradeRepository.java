package sda.studentmanagement.studentmanager.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sda.studentmanagement.studentmanager.domain.Grade;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAll();

    Grade findById(long gradeId);

    @Query(value = "select AVG(g.grade) as average from grade g where g.user_id LIKE %?1%", nativeQuery = true)
    Number getAverageByUser(long gradeId);

    @Query(value = """
            select *\s
            from grade g\s
            where g.user_id like %?1%""",
    countQuery = """
            select count(*)\s
            from grade g\s
            where g.user_id like %?1%""", nativeQuery = true)
    Page<Grade> getUserGradesByUserId(long userId, Pageable pageable);

    @Query(value = """
            select *\s
            from grade g \s
            join `session` s\s
            on g.session_id  = s.id\s
            where s.course_id like %?1%""",
            countQuery = """
                    select count(*)\s
                    from grade g \s
                    join `session` s\s
                    on g.session_id  = s.id\s
                    where s.course_id like %?1%""", nativeQuery = true)
    Page<Grade> getCourseGradesByCourseId(long courseId, Pageable pageable);
}
