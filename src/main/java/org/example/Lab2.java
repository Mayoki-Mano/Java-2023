package org.example;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static org.example.InteractiveMode.interactiveMode;
import static org.example.Subject.*;

public class Lab2 {

    public static void main(String[] args) {
        PeopleService service = new PeopleService("people-directory/");
        Subject[] subjects = {MATH, SCIENCE, HISTORY, LITERATURE, ART};
        Queue<Person> queue = new LinkedList<>();
        Runnable controller = new FileController("management-files/", queue);
        Thread thread1 = new Thread(controller);
        thread1.start();
        Runnable dispatcher = new Dispatcher(service, queue);
        Thread thread2 = new Thread(dispatcher);
        thread2.start();
        try {
            interactiveMode(service, subjects);
            thread1.interrupt();
            thread2.interrupt();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}