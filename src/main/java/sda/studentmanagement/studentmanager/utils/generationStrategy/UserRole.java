package sda.studentmanagement.studentmanager.utils.generationStrategy;

import java.time.LocalDate;

public enum UserRole {
    STUDENT("Student") {
        @Override
        public LocalDate minDOB() {
            return LocalDate.of(2002, 1, 1);
        }

        @Override
        public LocalDate maxDOB() {
            return LocalDate.of(2015, 12, 31);
        }
    },
    PROFESSOR("Professor") {
        @Override
        public LocalDate minDOB() {
            return LocalDate.of(1950, 1, 1);
        }

        @Override
        public LocalDate maxDOB() {
            return LocalDate.of(2000, 12, 31);
        }
    },
    ADMIN("Admin") {
        @Override
        public LocalDate minDOB() {
            return LocalDate.of(1950, 1, 1);
        }

        @Override
        public LocalDate maxDOB() {
            return LocalDate.of(2000, 12, 31);
        }
    };

    private String roleName;

    public LocalDate minDOB() {
        return null;
    }

    public LocalDate maxDOB() {
        return null;
    }

    UserRole(String roleName)
    {
        this.roleName = roleName;
    }

    public String getRoleName(){
        return this.roleName;
    }
}
