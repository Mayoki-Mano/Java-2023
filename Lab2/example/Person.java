package org.example;

import java.util.InvalidPropertiesFormatException;

abstract public  class Person {
    private final String fullName;
    private final int birthYear;
    private final String phoneNumber;
    public Person(String fullName, int birthYear, String phoneNumber) {
        this.fullName = fullName;
        this.birthYear = birthYear;
        this.phoneNumber = phoneNumber;
    }


    public String getFullName() throws InvalidPropertiesFormatException {
        try{
            if (fullName==null){
                throw new InvalidPropertiesFormatException("Wrong FullName (==null)");
            }
            return fullName;
        }catch (InvalidPropertiesFormatException e){
            System.out.println(e);
        }
        return null;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

