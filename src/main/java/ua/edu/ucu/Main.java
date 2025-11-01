package ua.edu.ucu;

public class Main {

    public static void main(String[] args) {
        Database database = new Database();
        database.readFromCSV(System.getProperty("user.dir") + "/students.csv");
    }
}