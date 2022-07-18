package sda.studentmanagement.studentmanager.dto;

import lombok.Data;

@Data
public class AddAttendancesFormDto {
    private long sessionId;

    private Attendances[] attendances;

    @Data
    public static class Attendances {
        public Integer userId;
        public boolean attendance;
    }
}
