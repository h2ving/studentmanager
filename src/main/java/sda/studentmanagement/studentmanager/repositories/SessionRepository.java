package sda.studentmanagement.studentmanager.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sda.studentmanagement.studentmanager.domain.Session;

import java.util.Date;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findAll();

    Session findById(long sessionId);

    @Query(value = "select s.start_date_time \n" +
            "from `session` s \n" +
            "join `session_user` su on s.id = su.session_id\n" +
            "join `user` u on u.id = su.user_id\n" +
            "where u.id like %?1% and s.start_date_time > NOW()\n" +
            "order by s.start_date_time asc\n" +
            "limit 1;", nativeQuery = true)
    Date getNextSessionByUser(long sessionId);
}
