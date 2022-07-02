package sda.studentmanagement.studentmanager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sda.studentmanagement.studentmanager.domain.Grade;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAll();

    Grade findById(long gradeId);

    @Query(value = "select AVG(g.grade) as average from grade g where g.user_id LIKE %?1%", nativeQuery = true)
    Number getAverageByUser(long gradeId);
}
