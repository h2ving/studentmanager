package sda.studentmanagement.studentmanager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import sda.studentmanagement.studentmanager.domain.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {

}
