package sda.studentmanagement.studentmanager.dto;

import lombok.Data;

@Data
public class AddGradesFormDto {
    private long sessionId;

    private Grades[] grades;

    @Data
    public static class Grades {
        public Integer userId;
        public Integer grade;
    }
}