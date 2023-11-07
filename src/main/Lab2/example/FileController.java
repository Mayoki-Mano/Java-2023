package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Queue;

public class FileController implements Runnable {
    private final File directory;
    private final Queue<Person> queue;

    public FileController(String directoryPath, Queue<Person> queue) {
        this.directory = new File(directoryPath);
        this.queue = queue;
    }

    @Override
    public void run() {
        PeopleService service=new PeopleService(directory+"/");
        while (true) {
            try {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.exists()) {
                            if (file.getName().matches("\\d+\\.json")) {
                                int id = Integer.parseInt(file.getName().replace(".json", ""));
                                queue.offer(service.findInDir(id));
                                if (!file.delete()) {
                                    throw new AccessDeniedException("File deleting error");
                                }
                            }
                        }
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (IOException e) {
                System.out.println(e);
                break;
            }
        }
    }
}
