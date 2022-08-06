package sda.studentmanagement.studentmanager.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sda.studentmanagement.studentmanager.domain.Course;
import sda.studentmanagement.studentmanager.domain.Session;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.utils.generationStrategy.*;

import java.time.*;
import java.util.*;


public class RandomThings {
    private static final Random random = new Random();

    public static String getScience() {
        String[] science_first_bit = new String[]{"Physical", "Nuclear", "Mechanical", "Electromagnetical", "Thermodynamical", "Kinetical", "Chemical", "Inorganical", "Electrochemistrical", "Analytical", "Molecular", "Anatomical", "Botanical", "Biological", "Zoological", "Neurobiological", "Marine", "Embryological", "Ecological", "Paleontological", "Genetical", "Digital", "Algoritmical", "Virtual", "Cybernetical", "Empirical", "Ethological", "Astronomical", "Meteorological", "Geological", "Atmospherical", "Glaciological", "Climatological", "Structural", "Medical", "Supplementary", "Theorethical", "Logical", "Forensical", "Logistical", "Military", "Auxiliar", "Practical", "Computer", "Artificial"};
        String[] science_second_bit = new String[]{"Physics", "Mechanics", "Electromagnetic", "Fission", "Thermodynamic", "Kinetic", "Chemistry", "Economy", "Electrochemistry", "Analytic", "Surgery", "Deviology", "Anatomy", "Botany", "Biology", "Zoology", "Neurobiology", "Studies", "Embryology", "Ecology", "Paleontology", "Genetics", "Ethic", "Ethology", "Astronomy", "Biopsy", "Autopsy", "Criminology", "Meteorology", "Geology", "Sounding", "Glaciology", "Climatology", "Structures", "Medicine", "Metallurgy"};
        StringBuilder science = new StringBuilder(science_first_bit[random.nextInt(science_first_bit.length - 1)]);

        for (int j = 0; j < (random.nextInt(2)); j++) {
            String additionalbit = science.toString();
            while (science.toString().contains(additionalbit)) {
                additionalbit = science_first_bit[random.nextInt(science_first_bit.length - 1)];
            }
            science.append(" ").append(additionalbit.toLowerCase());
        }
        science.append(" ").append(science_second_bit[random.nextInt(science_second_bit.length - 1)].toLowerCase());

        return science.toString();
    }

    // Random start date
    public static LocalDate getCourseStartDate(int daysBeforeNow, int daysAfrerNow) {
        long randomDate;

        long dateBeforeNow = LocalDate.now().minusDays(daysBeforeNow).toEpochDay();
        long dateAfterNow = LocalDate.now().plusDays(daysAfrerNow).toEpochDay();
        randomDate = random.nextLong(dateBeforeNow, dateAfterNow);

        return LocalDate.ofEpochDay(randomDate);
    }

    // Random end date
    public static LocalDate getCourseEndDate(LocalDate StartDate, int minLengthInDays, int maxLengthInDays) {
        long randomDate;

        long EndsNotEarlierThan = StartDate.plusDays(minLengthInDays).toEpochDay();
        long EndsNotLaterThan = StartDate.plusDays(maxLengthInDays).toEpochDay();
        randomDate = random.nextLong(EndsNotEarlierThan, EndsNotLaterThan);

        return LocalDate.ofEpochDay(randomDate);
    }

    // Random academic hours
    public static int getCourseAcademicHours(LocalDate startDate, LocalDate endDate) {
        long courseLengthInDays = startDate.until(endDate).getDays();
        return random.nextInt(1, (int) courseLengthInDays * 5 + 1);
    }

    public static Course getRandomCourse() {
        Course course = new Course();
        course.setName(getScience());
        course.setDescription(course.getName());
        course.setStartDate(getCourseStartDate(5, 15));
        course.setEndDate(getCourseEndDate(course.getStartDate(), 10, 30));
        course.setAcademicHours(getCourseAcademicHours(course.getStartDate(), course.getEndDate()));
        course.setRemote(random.nextBoolean());
        course.setId(null);
        List<User> userlist = new ArrayList<>();
        course.setUsers(userlist);
        return course;
    }

    public static String getName(Gender gender) {
        if (gender == Gender.MALE) {
            String[] firstName = new String[]{"Adam", "Alex", "Aaron", "Ben", "Carl", "Dan", "David", "Edward", "Fred", "Frank", "George", "Hal", "Hank", "Ike", "John", "Jack", "Joe", "Larry", "Monte", "Matthew", "Mark", "Nathan", "Otto", "Paul", "Peter", "Roger", "Roger", "Steve", "Thomas", "Tim", "Ty", "Victor", "Walter"};
            return firstName[random.nextInt(firstName.length)];
        } else if (gender == Gender.FEMALE) {
            String[] firstName = new String[]{"Abigail", "Amelia", "Aria", "Ava", "Avery", "Camila", "Charlotte", "Chloe", "Eleanor", "Elizabeth", "Ella", "Ellie", "Emily", "Emma", "Evelyn", "Gianna", "Grace", "Harper", "Hazel", "Isabella", "Layla", "Luna", "Madison", "Mia", "Mila", "Nora", "Olivia", "Penelope", "Riley", "Scarlett", "Sofia", "Sophia", "Zoey"};
            return firstName[random.nextInt(firstName.length)];
        }
        return "";
    }

    public static String getLastname() {
        String[] lastName = new String[]{"Anderson", "Ashwoon", "Aikin", "Bateman", "Bongard", "Bowers", "Boyd", "Cannon", "Cast", "Deitz", "Dewalt", "Ebner", "Frick", "Hancock", "Haworth", "Hesch", "Hoffman", "Kassing", "Knutson", "Lawless", "Lawicki", "Mccord", "McCormack", "Miller", "Myers", "Nugent", "Ortiz", "Orwig", "Ory", "Paiser", "Pak", "Pettigrew", "Quinn", "Quizoz", "Ramachandran", "Resnick", "Sagar", "Schickowski", "Schiebel", "Sellon", "Severson", "Shaffer", "Solberg", "Soloman", "Sonderling", "Soukup", "Soulis", "Stahl", "Sweeney", "Tandy", "Trebil", "Trusela", "Trussel", "Turco", "Uddin", "Uflan", "Ulrich", "Upson", "Vader", "Vail", "Valente", "Van Zandt", "Vanderpoel", "Ventotla", "Vogal", "Wagle", "Wagner", "Wakefield", "Weinstein", "Weiss", "Woo", "Yang", "Yates", "Yocum", "Zeaser", "Zeller", "Ziegler", "Bauer", "Baxster", "Casal", "Cataldi", "Caswell", "Celedon", "Chambers", "Chapman", "Christensen", "Darnell", "Davidson", "Davis", "DeLorenzo", "Dinkins", "Doran", "Dugelman", "Dugan", "Duffman", "Eastman", "Ferro", "Ferry", "Fletcher", "Fietzer", "Hylan", "Hydinger", "Illingsworth", "Ingram", "Irwin", "Jagtap", "Jenson", "Johnson", "Johnsen", "Jones", "Jurgenson", "Kalleg", "Kaskel", "Keller", "Leisinger", "LePage", "Lewis", "Linde", "Lulloff", "Maki", "Martin", "McGinnis", "Mills", "Moody", "Moore", "Napier", "Nelson", "Norquist", "Nuttle", "Olson", "Ostrander", "Reamer", "Reardon", "Reyes", "Rice", "Ripka", "Roberts", "Rogers", "Root", "Sandstrom", "Sawyer", "Schlicht", "Schmitt", "Schwager", "Schutz", "Schuster", "Tapia", "Thompson", "Tiernan", "Tisler"};
        return lastName[random.nextInt(lastName.length)];
    }

    public static LocalDate getDOB(UserRole role) {
        LocalDate randomDOB = null;
        if (role == UserRole.STUDENT) {
            randomDOB = randomDate(UserRole.STUDENT.minDOB(), UserRole.STUDENT.maxDOB());
        }

        if (role == UserRole.PROFESSOR) {
            randomDOB = randomDate(UserRole.STUDENT.minDOB(), UserRole.STUDENT.maxDOB());
        }

        return randomDOB;
    }

    public static String getPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode("1234"); // KISS for a development
    }

    public static String getPhoneNumber() {
        StringBuilder phoneNumber = new StringBuilder("+3725");
        for (int i = 0; i < 6; i++) {
            phoneNumber.append(random.nextInt(0, 9));
        }
        return phoneNumber.toString();
    }

    public static List<Session> generateRandomSessions(Course course) {
        List<Session> sessionList = new ArrayList<>();
        int minDuration = 1;
        int maxDuration = 4;
        int courseLength = course.getAcademicHours();
        int sessionLength;

        do {
            if (courseLength > maxDuration) {
                sessionLength = random.nextInt(minDuration, maxDuration + 1);
                courseLength = courseLength - sessionLength;
            } else {
                sessionLength = courseLength;
                courseLength = 0;
            }

            Session session = new Session();
            session.setDescription(course.getName() + " session");
            session.setCourse(course);
            session.setAcademicHours(sessionLength);
            session.setStartDateTime(randomEventDateTime(course.getStartDate(), course.getEndDate(), true, true).plusHours(random.nextInt(8, 16 - sessionLength)));
            session.setUser(null);
            sessionList.add(session);
        }
        while (courseLength != 0);

        // Sorting by date
        sessionList.sort(Comparator.comparing(Session::getStartDateTime));

        // Enumeration
        int i = 0;
        for (Session session : sessionList) {
            i++;
            String perfix;
            if (i < sessionList.size()) {
                perfix = " " + i;
            } else {
                perfix = " Finale";
            }
            session.setDescription(session.getDescription() + perfix);
        }

        return sessionList;
    }

    private static LocalDateTime randomEventDateTime(LocalDate startDate, LocalDate endDate, boolean onlyWorkweek, boolean atMidnight) {
        LocalDate date = randomDate(startDate, endDate);
        if (onlyWorkweek) {
            while (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                date = randomDate(startDate, endDate);
            }
        }
        if (atMidnight) {
            return date.atStartOfDay();
        } else {
            return date.atTime(random.nextInt(23), random.nextInt(59));
        }
    }


    private static LocalDate randomDate(LocalDate startDate, LocalDate endDate) {
        long randomDate = random.nextLong(startDate.toEpochDay(), endDate.toEpochDay());
        return LocalDate.ofEpochDay(randomDate);
    }

    private static LocalDateTime randomDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        ZoneOffset offset = OffsetDateTime.now().getOffset();
        long startLong = startDateTime.toEpochSecond(offset);
        long endLong = endDateTime.toEpochSecond(offset);
        long randomDate = random.nextLong(startLong, startLong);

        return LocalDateTime.ofInstant(Instant.ofEpochSecond(randomDate), offset);
    }

    public static User generateRandomUser(UserRole userRole) {
        Gender userGender;
        if (random.nextInt(1, 3) == 1) { // Genders are distributed evenly. Bound is exclusive, so there is 3
            userGender = Gender.MALE;
        } else userGender = Gender.FEMALE;

        User user = new User();
        user.setFirstName(RandomThings.getName(userGender));
        user.setLastName(RandomThings.getLastname());
        user.setEmail(user.getFirstName().substring(0, 1).toLowerCase() + user.getLastName().toLowerCase() + "@sdaacademy.uni");
        user.setPassword(RandomThings.getPassword());
        user.setGender(userGender.getGenderName());
        user.setDOB(RandomThings.getDOB(userRole));
        user.setMobile(RandomThings.getPhoneNumber());
        user.setCourses(new ArrayList<>());

        return user;
    }

}
