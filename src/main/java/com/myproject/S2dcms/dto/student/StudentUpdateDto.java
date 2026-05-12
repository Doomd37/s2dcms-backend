package com.myproject.S2dcms.dto.student;

public class StudentUpdateDto {
    private String name;


    public StudentUpdateDto() {
    }

    public StudentUpdateDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
