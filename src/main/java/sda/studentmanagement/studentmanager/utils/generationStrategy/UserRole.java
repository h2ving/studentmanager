package sda.studentmanagement.studentmanager.utils.generationStrategy;

import java.time.LocalDate;

public enum UserRole {
    STUDENT {
        @Override
        public long minDOB() {
            return LocalDate.of(2002, 1, 1).toEpochDay();
        }

        @Override
        public long maxDOB() {
            return LocalDate.of(2015, 12, 31).toEpochDay();
        }
    },
    PROFESSOR {
        @Override
        public long minDOB() {
            return LocalDate.of(1950, 1, 1).toEpochDay();
        }

        @Override
        public long maxDOB() {
            return LocalDate.of(2000, 12, 31).toEpochDay();
        }
    };

    public long minDOB() {
        return 0;
    }

    public long maxDOB() {
        return 0;
    }
}
