package ua.edu.ucu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Database {
    private List<Student> allStudents;
    private Map<String, Student> studentsByName;

    public Database() {
        allStudents = new ArrayList<>();
    }

    public List<Student> getAllStudents() {
        return allStudents;
    }

    public void readFromCSV(String filePath) {
        readFromCSV(filePath, Integer.MAX_VALUE);
    }

    public void readFromCSV(String filePath, int maxEntries) {
        String line = "";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            int count = 0;

            while ((line = br.readLine()) != null && count < maxEntries) {
                count++;

                String[] data = line.split(",");

                String name = data[0];
                String surname = data[1];
                String email = data[2];
                int year = Integer.parseInt(data[3]);
                int month = Integer.parseInt(data[4]);
                int day = Integer.parseInt(data[5]);
                String group = data[6];
                float rating = Float.parseFloat(data[7]);
                String phone = data[8];

                Student student = new Student(name, surname, email, year, month, day, group, rating, phone);
                allStudents.add(student);
            }

        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }    
}
