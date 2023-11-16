package org.example;

import java.io.IOException;
import java.util.Scanner;

import static org.example.Subject.ART;
import static org.example.Subject.MATH;

public interface InteractiveMode {
    private static int menu(Scanner scanner) {
        System.out.println("Enter your choice: ");
        System.out.println("1. Create new student ");
        System.out.println("2. Create new teacher ");
        System.out.println("3. Delete people from HashMap (id)");
        System.out.println("4. Delete people from directory (id)");
        System.out.println("5. Update people from HashMap (id)");
        System.out.println("6. Update people from directory (id)");
        System.out.println("7. Find people from HashMap (id)");
        System.out.println("8. Find people from directory (id)");
        System.out.println("9. Exit");
        int choice = -1;
        if (scanner.hasNextLine()) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Wrong number parameter");
            }
        }
        return choice;
    }
    static void interactiveMode(PeopleService service) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean exit = true;
        while (exit) {
            try{
                int choice = menu(scanner);
                switch (choice) {
                    case 1: {
                        String fullName = "";
                        int birthYear = 0;
                        String phoneNumber="";
                        System.out.println("Enter FullName: ");
                        if (scanner.hasNextLine()) {
                            fullName = scanner.nextLine();
                        }
                        System.out.println("Enter BirthYear: ");
                        if (scanner.hasNextLine()) {
                            try {
                                birthYear = Integer.parseInt(scanner.nextLine());
                                if (birthYear<0){
                                    throw new NumberFormatException();
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Wrong number parameter");
                                break;
                            }
                        }
                        System.out.println("Enter PhoneNumber: ");
                        if (scanner.hasNextLine()) {
                            phoneNumber = scanner.nextLine();
                        }
                        if (!phoneNumber.matches("^\\+[1-9]\\d{10}$")){
                            System.out.println("Wrong phone parameters");
                            break;
                        }
                        System.out.println("Student parameters:");
                        Subject[] subjects1= Subject.getSubjectsFromInput(scanner);
                        if (subjects1==null){
                            break;
                        }
                        service.createSaveStudent(fullName, birthYear, phoneNumber, subjects1);
                        break;
                    }
                    case 2: {
                        String fullName = "";
                        int birthYear = 0;
                        String phoneNumber="";
                        System.out.println("Enter FullName: ");
                        if (scanner.hasNextLine()) {
                            fullName = scanner.nextLine();
                        }
                        System.out.println("Enter BirthYear: ");
                        if (scanner.hasNextLine()) {
                            try {
                                birthYear = Integer.parseInt(scanner.nextLine());
                                if (birthYear<0){
                                    throw new NumberFormatException();
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Wrong number parameter");
                                break;
                            }
                        }
                        System.out.println("Enter PhoneNumber: ");
                        if (scanner.hasNextLine()) {
                            phoneNumber = scanner.nextLine();
                        }
                        if (!phoneNumber.matches("^\\+[1-9]\\d{10}$")){
                            System.out.println("Wrong phone parameters");
                            break;
                        }
                        System.out.println("Teacher parameters:");
                        Subject subject= Subject.getSubjectFromInput(scanner);
                        if (subject==null){
                            break;
                        }
                        System.out.println("Enter working hours:");
                        String input = "";
                        if (scanner.hasNextLine()) {
                            input = scanner.nextLine();
                        }
                        int workingHours = -1;
                        try {
                            workingHours = Integer.parseInt(input);
                            if (workingHours>24 || workingHours<0){
                                throw new NumberFormatException("Wrong number parameter");
                            }
                        }catch (NumberFormatException e){
                            System.err.println(e.getMessage());
                            break;
                        }
                        service.createSaveTeacher(fullName, birthYear, phoneNumber, subject, workingHours);
                        break;
                    }
                    case 3: {
                        int id = -1;
                        System.out.println("Enter id: ");
                        if (scanner.hasNextLine()) {
                            try {
                                id = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Wrong number parameter");
                                break;
                            }
                        }
                        service.deletePeople(id);
                        break;
                    }
                    case 4: {
                        int id = -1;
                        System.out.println("Enter id: ");
                        if (scanner.hasNextLine()) {
                            try {
                                id = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Wrong number parameter");
                                break;
                            }
                        }
                        service.deleteInDir(id);
                        break;
                    }
                    case 5: {
                        int id = -1;
                        System.out.println("Enter id: ");
                        if (scanner.hasNextLine()) {
                            try {
                                id = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Wrong number parameter");
                                break;
                            }
                        }
                        if (!service.map.containsKey(id)){
                            System.out.println("No such id");
                            break;
                        }
                        String fullName = "";
                        int birthYear = 0;
                        String phoneNumber="";
                        System.out.println("Enter FullName: ");
                        if (scanner.hasNextLine()) {
                            fullName = scanner.nextLine();
                        }
                        System.out.println("Enter BirthYear: ");
                        if (scanner.hasNextLine()) {
                            try {
                                birthYear = Integer.parseInt(scanner.nextLine());
                                if (birthYear<0){
                                    throw new NumberFormatException();
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Wrong number parameter");
                                break;
                            }
                        }
                        System.out.println("Enter PhoneNumber: ");
                        if (scanner.hasNextLine()) {
                            phoneNumber = scanner.nextLine();
                        }
                        if (!phoneNumber.matches("^\\+[1-9]\\d{10}$")){
                            System.out.println("Wrong phone parameters");
                            break;
                        }
                        if (service.map.get(id) instanceof Student){
                            System.out.println("Student parameters:");
                            Subject[] subjects1= Subject.getSubjectsFromInput(scanner);
                            if (subjects1==null){
                                break;
                            }
                            service.updateInDir(new Student(fullName, birthYear, phoneNumber, subjects1), id);
                        }else{
                            System.out.println("Teacher parameters:");
                            Subject subject= Subject.getSubjectFromInput(scanner);
                            if (subject==null){
                                break;
                            }
                            System.out.println("Enter working hours:");
                            String input = "";
                            if (scanner.hasNextLine()) {
                                input = scanner.nextLine();
                            }
                            int workingHours = -1;
                            try {
                                workingHours = Integer.parseInt(input);
                                if (workingHours>24 || workingHours<0){
                                    throw new NumberFormatException("Wrong number parameter");
                                }
                            }catch (NumberFormatException e){
                                System.err.println(e.getMessage());
                                break;
                            }
                            service.updateInDir(new Teacher(fullName, birthYear, phoneNumber, subject, workingHours), id);
                        }
                        break;
                    }
                    case 6: {
                        int id = 0;
                        System.out.println("Enter id: ");
                        if (scanner.hasNextLine()) {
                            try {
                                id = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Wrong number parameter");
                                break;
                            }
                        }
                        if (!service.peopleInDirExists(id)){
                            break;
                        }
                        String fullName = "";
                        int birthYear = 0;
                        String phoneNumber="";
                        System.out.println("Enter FullName: ");
                        if (scanner.hasNextLine()) {
                            fullName = scanner.nextLine();
                        }
                        System.out.println("Enter BirthYear: ");
                        if (scanner.hasNextLine()) {
                            try {
                                birthYear = Integer.parseInt(scanner.nextLine());
                                if (birthYear<0){
                                    throw new NumberFormatException();
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Wrong number parameter");
                                break;
                            }
                        }
                        System.out.println("Enter PhoneNumber: ");
                        if (scanner.hasNextLine()) {
                            phoneNumber = scanner.nextLine();
                        }
                        if (!phoneNumber.matches("^\\+[1-9]\\d{10}$")){
                            System.out.println("Wrong phone parameters");
                            break;
                        }
                        if (service.findInDir(id) instanceof Student){
                            System.out.println("Student parameters:");
                            Subject[] subjects1= Subject.getSubjectsFromInput(scanner);
                            if (subjects1==null){
                                break;
                            }
                            service.updateInDir(new Student(fullName, birthYear, phoneNumber, subjects1), id);
                        }else{
                            System.out.println("Teacher parameters:");
                            Subject subject= Subject.getSubjectFromInput(scanner);
                            if (subject==null){
                                break;
                            }
                            System.out.println("Enter working hours:");
                            String input = "";
                            if (scanner.hasNextLine()) {
                                input = scanner.nextLine();
                            }
                            int workingHours = -1;
                            try {
                                workingHours = Integer.parseInt(input);
                                if (workingHours>24 || workingHours<0){
                                    throw new NumberFormatException("Wrong number parameter");
                                }
                            }catch (NumberFormatException e){
                                System.err.println(e.getMessage());
                                break;
                            }
                            service.updateInDir(new Teacher(fullName, birthYear, phoneNumber, subject, workingHours), id);
                        }
                        break;
                    }
                    case 7: {
                        int id = 0;
                        System.out.println("Enter id: ");
                        if (scanner.hasNextLine()) {
                            try {
                                id = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Wrong number parameter");
                                break;
                            }
                        }
                        service.printPerson(service.map.get(id));
                        break;
                    }
                    case 8: {
                        int id = 0;
                        System.out.println("Enter id: ");
                        if (scanner.hasNextLine()) {
                            try {
                                id = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Wrong number parameter");
                                break;
                            }
                        }
                        if (service.findInDir(id)==null){
                            break;
                        }
                        if (service.findInDir(id) instanceof Student){
                            Student student = (Student) service.findInDir(id);
                            if (student==null){
                                break;
                            }
                            service.createSaveStudent(student.getFullName(),student.getBirthYear(),student.getPhoneNumber(),student.getSubjects());
                        }else{
                            Teacher teacher = (Teacher) service.findInDir(id);
                            if (teacher==null){
                                break;
                            }
                            service.createSaveTeacher(teacher.getFullName(),teacher.getBirthYear(),teacher.getPhoneNumber(),teacher.getSubject(), teacher.getWorkingHours());
                        }
                        service.printPerson(service.findInDir(id));
                        break;
                    }
                    case 9: {
                        exit = false;
                        scanner.close();
                        break;
                    }
                    default: {
                        System.out.println("Wrong menu choice");
                        break;
                    }
                }
            }catch (IOException e){
                System.out.println(e.getMessage());
            }

        }
    }
}
