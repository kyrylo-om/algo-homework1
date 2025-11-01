package ua.edu.ucu;

public class Student {
    String name;
    String surname;
    String email;
    int birthYear;
    int birthMonth;
    int birthDay;
    String group;
    float rating;
    String phoneNumber;

    public Student(String name, String surname, String email, int birthYear, int birthMonth, int birthDay, String group, float rating, String phoneNumber) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDay = birthDay;
        this.group = group;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
    }
}