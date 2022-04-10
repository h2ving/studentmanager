package sda.studentmanagement.studentmanager.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sda.studentmanagement.studentmanager.domain.User;
import sda.studentmanagement.studentmanager.utils.generationStrategy.*;

import java.time.LocalDate;
import java.util.Random;


public class RandomThings {
    private static Random random = new Random();

    public static String getScience() {
        String[] science_first_bit = new String[]{"Physical", "Nuclear", "Mechanical", "Electromagnetical", "Thermodynamical", "Kinetical", "Chemical", "Inorganical", "Electrochemistrical", "Analytical", "Molecular", "Anatomical", "Botanical", "Biological", "Zoological", "Neurobiological", "Marine", "Embryological", "Ecological", "Paleontological", "Genetical", "Digital", "Algoritmical", "Virtual", "Cybernetical", "Empirical", "Ethological", "Astronomical", "Meteorological", "Geological", "Atmospherical", "Glaciological", "Climatological", "Structural", "Medical", "Supplementary", "Theorethical", "Logical", "Forensical", "Logistical", "Military", "Auxiliar", "Practical", "Computer", "Artificial"};
        String[] science_second_bit = new String[]{"Physics", "Mechanics", "Electromagnetic", "Fission", "Thermodynamic", "Kinetic", "Chemistry", "Economy", "Electrochemistry", "Analytic", "Surgery", "Deviology", "Anatomy", "Botany", "Biology", "Zoology", "Neurobiology", "Studies", "Embryology", "Ecology", "Paleontology", "Genetics", "Ethic", "Ethology", "Astronomy", "Biopsy", "Autopsy", "Criminology", "Meteorology", "Geology", "Sounding", "Glaciology", "Climatology", "Structures", "Medicine", "Metallurgy"};
        String science = science_first_bit[random.nextInt(science_first_bit.length - 1)];

        for (int j = 0; j < (random.nextInt(2)); j++) {
            String additionalbit = science;
            while (science.contains((CharSequence) additionalbit)) {
                additionalbit = science_first_bit[random.nextInt(science_first_bit.length - 1)];
            }
            science += " " + additionalbit.toLowerCase();
        }
        science += " " + science_second_bit[random.nextInt(science_second_bit.length - 1)].toLowerCase();

        return science;
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
        long randomDate = 0;

        if (role == UserRole.STUDENT) {
            randomDate = random.nextLong(UserRole.STUDENT.minDOB(), UserRole.STUDENT.maxDOB());
            randomDOB = LocalDate.ofEpochDay(randomDate);
        }

        if (role == UserRole.PROFESSOR) {
            randomDate = random.nextLong(UserRole.PROFESSOR.minDOB(), UserRole.PROFESSOR.maxDOB());
            randomDOB = LocalDate.ofEpochDay(randomDate);
        }

        return randomDOB;
    }

    public static String getPassword() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode("Th1s1sMyP4ss"); // KISS for a development
    }

    public static String getPhoneNumber(){
        String phoneNumber = "+3725";
        for(int i = 0; i<6;  i++){
            phoneNumber += random.nextInt(0,9);
        }
        return phoneNumber;
    }

    public static User generateRandomUser(){
        Gender userGender;
        if (random.nextInt(1, 3) == 1) { // Genders are distributed evenly. Bound is exclusive, so there is 3
            userGender = Gender.MALE;
        } else userGender = Gender.FEMALE;

        UserRole userRole;
        if (random.nextInt(0, 100) >= 90) { // 1/10 chance to generate a prof
            userRole = UserRole.PROFESSOR;
        } else userRole = UserRole.STUDENT;

        User user = new User();
        user.setFirstName(RandomThings.getName(userGender));
        user.setLastName(RandomThings.getLastname());
        user.setEmail(user.getFirstName().substring(0, 1).toLowerCase() + user.getLastName().toLowerCase() + "@sdaacademy.uni");
        user.setPassword(RandomThings.getPassword());
        //user.setRole(userRole.name());
        user.setGender(userGender.name());
        user.setDOB(RandomThings.getDOB(userRole));
        user.setMobile(RandomThings.getPhoneNumber());
        return user;
    }

}
