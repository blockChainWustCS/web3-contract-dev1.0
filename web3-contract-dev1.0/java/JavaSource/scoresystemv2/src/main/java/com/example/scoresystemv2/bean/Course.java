package com.example.scoresystemv2.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Course {
    private Long courseID;
    private String name;
    private String teacherAddress;

    public Course(Long courseID ,String teacheraddress,String name ){
        this.courseID=courseID;
        this.teacherAddress=teacheraddress;
        this.name=name;
    }
}
