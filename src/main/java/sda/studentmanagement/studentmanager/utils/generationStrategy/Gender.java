package sda.studentmanagement.studentmanager.utils.generationStrategy;

public enum Gender {
    UNSPECIFIED ("Unspecified"),
    MALE ("Male"),
    FEMALE ("Female");

    private String genderName;

    private Gender(String genderName) {
        this.genderName = genderName;
    }

    public String getGenderName(){
        return this.genderName;
    }
}
