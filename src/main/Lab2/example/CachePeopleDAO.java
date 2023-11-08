package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;

public class CachePeopleDAO implements DAO {
    ObjectMapper objectMapper;
    Map<Integer, Person> map;
    private final String directory;

    public CachePeopleDAO(String directory) {
        this.objectMapper = new ObjectMapper();
        this.map = new HashMap<>();
        this.directory = directory;
    }

    public void createInDir(int id) {
        try {
            if (map.containsKey(id)) {
                final var path = Path.of(directory);
                if (Files.exists(path) && Files.isDirectory(path)) {
                    objectMapper.writeValue(new File(directory + id + ".json"), map.get(id));
                } else {
                    throw new IOException("Wrong directory parameter");
                }
            } else {
                throw new InvalidPropertiesFormatException("Wrong Id");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void deleteInDir(int id) {
        try {
            final var path = Path.of(directory);
            if (Files.exists(path) && Files.isDirectory(path)) {
                String filePath = directory + id + ".json";
                File file = new File(filePath);
                if (file.exists() && file.isFile()) {
                    if (!file.delete()) {
                        throw new AccessDeniedException("File deleting error");
                    }
                } else {
                    throw new IOException("Wrong file parameters");
                }
            } else {
                throw new IOException("Wrong directory parameter");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void updateInDir(Person person, int id) {
        try {
            final var path = Path.of(directory);
            if (Files.exists(path) && Files.isDirectory(path)) {
                String filePath = directory + id + ".json";
                File file = new File(filePath);
                if (file.exists() && file.isFile()) {
                    if (!file.delete()) {
                        throw new AccessDeniedException("File deleting error");
                    }
                    map.replace(id, person);
                    createInDir(id);
                } else {
                    throw new IOException("Wrong file parameters");
                }
            } else {
                throw new IOException("Wrong directory parameter");
            }
        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public Person findInDir(int id) {
        try {
            final var path = Path.of(directory);
            if (Files.exists(path) && Files.isDirectory(path)) {
                try {
                    return objectMapper.readValue(new File(directory + id + ".json"), Student.class);
                } catch (IOException e) {
                    try {
                        return objectMapper.readValue(new File(directory + id + ".json"), Teacher.class);
                    } catch (IOException ex) {
                        throw new IOException("Wrong format file");
                    }
                }
            } else {
                throw new IOException("Wrong directory parameter");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

}
