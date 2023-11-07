package org.example;

import java.io.IOException;
import java.util.Arrays;
import java.util.InvalidPropertiesFormatException;

class PeopleService extends CachePeopleDAO {
    static int peopleId = 0;

    public PeopleService(String directory) {
        super(directory);
    }
    void printPerson(Person person) throws InvalidPropertiesFormatException {
        if (person==null){
            throw new InvalidPropertiesFormatException("No such person");
        }
        if (person instanceof Student){
            System.out.println("FullName: "+person.getFullName());
            System.out.println("BirthYear: "+person.getBirthYear());
            System.out.println("PhoneNumber: "+person.getPhoneNumber());
            System.out.println("Subjects: "+ Arrays.toString(((Student) person).getSubjects()));
        }else{
            System.out.println("FullName: "+person.getFullName());
            System.out.println("BirthYear: "+person.getBirthYear());
            System.out.println("PhoneNumber: "+person.getPhoneNumber());
            System.out.println("Subjects: "+ ((Teacher) person).getSubject());
        }
    }
    void createSaveTeacher(String fullName, int birthYear, String phoneNumber, Subject subject, int workingHours) throws IOException {
        Teacher teacher = new Teacher(fullName, birthYear, phoneNumber, subject, workingHours);
        Integer id = createPeopleId();
        map.put(id, teacher);
        createInDir(id);
    }

    void createSaveStudent(String fullName, int birthYear, String phoneNumber, Subject[] subjects) throws IOException {
        Student student = new Student(fullName, birthYear, phoneNumber, subjects);
        Integer id = createPeopleId();
        map.put(id, student);
        createInDir(id);
    }

    private Integer createPeopleId() {
        while (map.containsKey(peopleId)) {
            ++peopleId;
        }
        return peopleId;
    }

    void deletePeople(int id) {
        map.remove(id);
    }

    void updatePerson(Person person, Integer id) throws InvalidPropertiesFormatException {
        if (map.containsKey(id)) {
            map.replace(id, person);
        } else {
            throw new InvalidPropertiesFormatException("Wrong Id");
        }
    }
}
