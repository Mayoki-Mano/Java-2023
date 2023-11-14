package org.example;

import java.io.IOException;
import java.util.Queue;

public class Dispatcher implements Runnable {
    private final PeopleService service;
    private final Queue<Person> queue;

    public Dispatcher(PeopleService service, Queue<Person> queue) {
        this.service = service;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                while (!queue.isEmpty()) {
                    if (queue.peek()==null){
                        System.out.println("Null in queue");
                        queue.poll();
                    }else{
                        if (queue.peek() instanceof Student){
                            Student student =(Student) queue.poll();
                            service.createSaveStudent(student.getFullName(),student.getBirthYear(),student.getPhoneNumber(),student.getSubjects());
                        }else{
                            Teacher teacher = (Teacher) queue.poll();
                            service.createSaveTeacher(teacher.getFullName(),teacher.getBirthYear(),teacher.getPhoneNumber(),teacher.getSubject(),teacher.getWorkingHours());
                        }
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
