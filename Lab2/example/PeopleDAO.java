package org.example;

public class PeopleDAO extends CachePeopleDAO implements DAO{
    public PeopleDAO(String directory) {
        super(directory);
    }
}
