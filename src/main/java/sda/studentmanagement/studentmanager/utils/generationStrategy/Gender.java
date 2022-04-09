package sda.studentmanagement.studentmanager.utils.generationStrategy;

public enum Gender {
    UNSPECIFIED (0),
    MALE (1),
    FEMALE (2);

    public final int genderID;

    private Gender(int genderID) {
        this.genderID = genderID;
    }
}
