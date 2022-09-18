package com.example.scoresystemv2.service;

import com.example.scoresystemv2.bean.Course;

import java.util.List;

public interface ICourseService {
    void addCourse(Course course);
    void updataAddress(Course course);
    void updataName(Course course);
    Course getCourse(Long courseid);
}
