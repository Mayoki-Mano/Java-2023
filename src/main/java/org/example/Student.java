package org.example;

import java.util.HashMap;

class Student extends Person {
    private Subject [] subjects;
    private HashMap<Subject, Double> averageGrades;
    public Student(){
        super("defaultName", 2000, "88005553535");

    }
    public Student(String fullName, int birthYear, String phoneNumber, Subject [] subjects) {
        super(fullName, birthYear, phoneNumber);
        this.subjects = subjects;
        this.averageGrades = new HashMap<>();
    }

    public Subject [] getSubjects() {
        return subjects;
    }

    public double getAverageGrade(Subject subject) {
        return averageGrades.getOrDefault(subject, 0.0);
    }

    public void addAverageGrade(Subject subject, double grade) {
        averageGrades.put(subject, grade);
    }
}
