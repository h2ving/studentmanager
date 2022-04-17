package sda.studentmanagement.studentmanager.utils.generationStrategy;

import java.time.LocalDate;

public enum UserRole {
    STUDENT("Student") {
        @Override
        public long minDOB() {
            return LocalDate.of(2002, 1, 1).toEpochDay();
        }

        @Override
        public long maxDOB() {
            return LocalDate.of(2015, 12, 31).toEpochDay();
        }
    },
    PROFESSOR("Professor") {
        @Override
        public long minDOB() {
            return LocalDate.of(1950, 1, 1).toEpochDay();
        }

        @Override
        public long maxDOB() {
            return LocalDate.of(2000, 12, 31).toEpochDay();
        }
    },
    ADMIN("Admin") {
        @Override
        public long minDOB() {
            return LocalDate.of(1950, 1, 1).toEpochDay();
        }

        @Override
        public long maxDOB() {
            return LocalDate.of(2000, 12, 31).toEpochDay();
        }
    };

    private String roleName;

    public long minDOB() {
        return 0;
    }

    public long maxDOB() {
        return 0;
    }

    private UserRole(String roleName)
    {
        this.roleName = roleName;
    }

    public String getRoleName(){
        return this.roleName;
    }
}
