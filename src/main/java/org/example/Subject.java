package org.example;

import java.util.Scanner;

public enum Subject {
    MATH, SCIENCE, HISTORY, LITERATURE, ART;
    public static Subject getSubjectFromInput(Scanner scanner){

        System.out.println("Введите число, чтобы добавить предмет:");
        System.out.println("0 - MATH");
        System.out.println("1 - SCIENCE");
        System.out.println("2 - HISTORY");
        System.out.println("3 - LITERATURE");
        System.out.println("4 - ART");
        String input="";
        if (scanner.hasNextLine()) {
            input = scanner.nextLine();
        }
        Subject subject = null;
        try {
            int subjectIndex = Integer.parseInt(input);
            if (subjectIndex>4 || subjectIndex<0){
                throw new NumberFormatException("Wrong number parameter");
            }
            subject = Subject.values()[subjectIndex];
        }catch (NumberFormatException e){
            System.err.println(e.getMessage());
        }
        return subject;
    }
    public static Subject[] getSubjectsFromInput(Scanner scanner) {

        System.out.println("Введите числа, чтобы добавить предметы:");
        System.out.println("0 - MATH");
        System.out.println("1 - SCIENCE");
        System.out.println("2 - HISTORY");
        System.out.println("3 - LITERATURE");
        System.out.println("4 - ART");

        String input="";
        if (scanner.hasNextLine()) {
            input = scanner.nextLine();
        }
        Subject[] subjectsArray = null;

        if (input.matches("^[0-4]+(\\s+[0-4]+)*$")) {

            String[] inputArray = input.split("\\s+");


            subjectsArray = new Subject[inputArray.length];

            try {
                for (int i = 0; i < inputArray.length; i++) {
                    int subjectIndex = Integer.parseInt(inputArray[i]);
                    subjectsArray[i] = Subject.values()[subjectIndex];
                }
            }catch (NumberFormatException e){
                System.err.println(e.getMessage());
            }

            System.out.println("Выбранные предметы:");
            for (Subject subject : subjectsArray) {
                System.out.println(subject);
            }
        } else {
            System.out.println("Ошибка: Введена недопустимая строка.");
        }
        return subjectsArray;
    }
}
