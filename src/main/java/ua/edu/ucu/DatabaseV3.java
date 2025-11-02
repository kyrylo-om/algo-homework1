package ua.edu.ucu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DatabaseV3 extends Database {
    private Map<String, ArrayList<Student>> hashMapByName;
    private Map<String, Student> hashMapByEmail;

    public DatabaseV3() {
        hashMapByName = new HashMap<>();
        hashMapByEmail = new HashMap<>();
    }

    @Override
    public void addStudent(Student student) {
        hashMapByName.computeIfAbsent(student.name + student.surname, k -> new ArrayList<Student>()).add(student);
        hashMapByEmail.put(student.email, student);
    }

    @Override
    public List<Student> operation1(String name, String surname) {
        return hashMapByName.getOrDefault(name + surname, new ArrayList<>());
    }

    @Override
    public Set<String> operation2() {
        Set<String> groupsWithDuplicates = new HashSet<>();

        for(List<Student> studentsWithSameName : hashMapByName.values()) {
            Set<String> seenGroups = new HashSet<>();

            for(Student student : studentsWithSameName) {
                String group = student.group;

                if (seenGroups.contains(group)) {
                    groupsWithDuplicates.add(group);
                }
                else {
                    seenGroups.add(group);
                }
            }
        }

        return groupsWithDuplicates;
    }

    @Override
    public void operation3(String email, String newGroup) {
        Student student = hashMapByEmail.getOrDefault(email, null);

        if (student != null) {
            student.group = newGroup;
        }
    }
}
