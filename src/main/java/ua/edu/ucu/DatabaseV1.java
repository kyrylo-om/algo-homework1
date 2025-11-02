package ua.edu.ucu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DatabaseV1 extends Database {
    private List<Student> list;

    public DatabaseV1() {
        list = new ArrayList<>();
    }

    @Override
    public void addStudent(Student student) {
        list.add(student);
    }

    @Override
    public List<Student> operation1(String name, String surname) {
        List<Student> matches = new ArrayList<>();

        for (Student student : list) {
            if (student.name.equals(name) && student.surname.equals(surname)) {
                matches.add(student);
            }
        }
        return matches;
    }

    @Override
    public Set<String> operation2() {
        Set<String> groupsWithDuplicates = new HashSet<>();

        for (int i = 0; i < list.size(); i++) {
            Student student1 = list.get(i);

            for (int j = i + 1; j < list.size(); j++) {
                Student student2 = list.get(j);

                if (student1.group.equals(student2.group)) {
                    if (student1.name.equals(student2.name) && student1.surname.equals(student2.surname)) {
                        groupsWithDuplicates.add(student1.group);
                        break; 
                    }
                }
            }
        }

        return groupsWithDuplicates;
    }

    @Override
    public void operation3(String email, String newGroup) {
        for (Student student : list) {
            if (student.email.equals(email)) {
                student.group = newGroup;
            }
        }
    }

    public List<Student> sortBuiltIn() {
        List<Student> sortedList = new ArrayList<>(list);

        Comparator<Student> byMonthAndDay = Comparator.comparingInt((Student s) -> s.birthMonth).thenComparingInt((Student s) -> s.birthDay);


        Collections.sort(sortedList, byMonthAndDay);
        
        return sortedList;
    }

    public List<Student> sortCustom() {
        // bucket sort

        final int NUM_MONTHS = 12;

        List<List<Student>> buckets = new ArrayList<>(NUM_MONTHS);
        for (int i = 0; i < NUM_MONTHS; i++) {
            buckets.add(new ArrayList<>());
        }

        for (Student student : list) {
            buckets.get(student.birthMonth - 1).add(student);
        }

        Comparator<Student> byDayComparator = Comparator
                                    .comparingInt(s -> s.birthDay);
        
        for (List<Student> bucket : buckets) {
            Collections.sort(bucket, byDayComparator);
        }

        List<Student> sortedList = new ArrayList<>(list.size());
        for (List<Student> bucket : buckets) {
            sortedList.addAll(bucket);
        }

        return sortedList;
    }
}
