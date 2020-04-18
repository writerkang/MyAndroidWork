package com.lec.a008_practice;

public class Person {
    String name;
    int age;
    String adr;

    public Person(String name, int age, String adr) {
        this.name = name;
        this.age = age;
        this.adr = adr;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String email) {
        this.adr = adr;
    }
}
