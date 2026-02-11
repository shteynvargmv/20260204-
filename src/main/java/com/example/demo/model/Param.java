package com.example.demo.model;

public class Param {
    public String Name;
    public String Value;

    public Param(String name, String value) {
        Name = name;
        Value = value;
    }

    public String getName() {
        return Name;
    }

    public String getValue() {
        return Value;
    }
}
