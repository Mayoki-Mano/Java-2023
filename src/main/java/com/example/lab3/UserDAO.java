package com.example.lab3;

public interface UserDAO extends DAO{
    public static String convertToJson(Object object) {
        // Преобразование объекта в JSON
        // В реальном проекте лучше использовать Jackson или Gson
        return "{\"data\":" + object.toString() + "}";
    }
}
