package ua.edu.ucu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DatabaseV2 extends Database {
    private Map<String, ArrayList<Student>> hashMap;

    public DatabaseV2() {
        hashMap = new HashMap<>();
    }

    @Override
    public void addStudent(Student student) {
        hashMap.computeIfAbsent(student.name + student.surname, k -> new ArrayList<Student>()).add(student);
    }

    @Override
    public List<Student> operation1(String name, String surname) {
        return hashMap.getOrDefault(name + surname, new ArrayList<>());
    }

    @Override
    public Set<String> operation2() {
        Set<String> groupsWithDuplicates = new HashSet<>();

        for(List<Student> studentsWithSameName : hashMap.values()) {
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
        for(List<Student> studentsWithSameName : hashMap.values()) {

            for(Student student : studentsWithSameName) {
                if (student.email.equals(email)) {
                    student.group = newGroup;
                }
            }
        }
    }
}
