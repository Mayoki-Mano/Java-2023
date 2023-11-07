package org.example;

class Teacher extends Person {
    private Subject subject;
    private int workingHours;

    Teacher(){
        super("defaultName", 2000, "88005553535");
    }
    public Teacher(String fullName, int birthYear, String phoneNumber, Subject subject, int workingHours) {
        super(fullName, birthYear, phoneNumber);
        this.subject = subject;
        this.workingHours = workingHours;
    }

    public Subject getSubject() {
        return subject;
    }

    public int getWorkingHours() {
        return workingHours;
    }
}
