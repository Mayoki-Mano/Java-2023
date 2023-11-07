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
    static void interactiveMode(PeopleService service, Subject[] subjects) throws IOException {
        Scanner scanner = new Scanner(System.in);
        boolean exit = true;
        while (exit) {
            try{
                int choice = menu(scanner);
                switch (choice) {
                    case 1: {
                        String fullName = null;
                        int birthYear = 0;
                        System.out.println("Enter FullName: ");
                        if (scanner.hasNextLine()) {
                            fullName = scanner.nextLine();
                        }
                        System.out.println("Enter BirthYear: ");
                        if (scanner.hasNextLine()) {
                            try {
                                birthYear = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Wrong number parameter");
                            }
                        }
                        service.createSaveStudent(fullName, birthYear, "+7987231312", subjects);
                        break;
                    }
                    case 2: {
                        String fullName = null;
                        int birthYear = 0;
                        System.out.println("Enter FullName: ");
                        if (scanner.hasNextLine()) {
                            fullName = scanner.nextLine();
                        }
                        System.out.println("Enter BirthYear: ");
                        if (scanner.hasNextLine()) {
                            try {
                                birthYear = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Wrong number parameter");
                            }
                        }
                        service.createSaveTeacher(fullName, birthYear, "+7987231321", MATH, 8);
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
                            }
                        }
                        String fullName = null;
                        int birthYear = 0;
                        System.out.println("Enter FullName: ");
                        if (scanner.hasNextLine()) {
                            fullName = scanner.nextLine();
                        }
                        System.out.println("Enter BirthYear: ");
                        if (scanner.hasNextLine()) {
                            try {
                                birthYear = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Wrong number parameter");
                            }
                        }
                        service.updatePerson(new Teacher(fullName, birthYear, "+7987752321", ART, 14), id);
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
                            }
                        }
                        String fullName = null;
                        int birthYear = 0;
                        System.out.println("Enter FullName: ");
                        if (scanner.hasNextLine()) {
                            fullName = scanner.nextLine();
                        }
                        System.out.println("Enter BirthYear: ");
                        if (scanner.hasNextLine()) {
                            try {
                                birthYear = Integer.parseInt(scanner.nextLine());
                            } catch (NumberFormatException e) {
                                System.out.println("Wrong number parameter");
                            }
                        }
                        service.updateInDir(new Teacher(fullName, birthYear, "+7987752321", ART, 14), id);
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
                            }
                        }

                        if (service.findInDir(id) instanceof Student student){
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
                System.out.println(e);
            }

        }
    }
}
