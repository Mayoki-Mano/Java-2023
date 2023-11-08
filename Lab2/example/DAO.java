package org.example;

import java.io.IOException;

public interface DAO {
    void createInDir(int id) throws IOException;
    void deleteInDir(int id) throws IOException;
    void updateInDir(Person person,int id) throws IOException ;
    Person findInDir(int id) throws IOException;
}
