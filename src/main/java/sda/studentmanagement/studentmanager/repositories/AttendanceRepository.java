package sda.studentmanagement.studentmanager.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sda.studentmanagement.studentmanager.domain.Attendance;

import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findAll();

    Page<Attendance> findAll(Pageable pageable);

    Attendance findById(long attendanceId);

    @Query(value = "select count(*) * 100.0 / sum(count(*)) over() as attendance\n" +
            "from attendance a \n" +
            "where a.user_id like %?1% \n" +
            "group by a.did_attend\n" +
            "limit 1;", nativeQuery = true)
    Number getAverageAttendanceByUser(long attendanceId);
}
