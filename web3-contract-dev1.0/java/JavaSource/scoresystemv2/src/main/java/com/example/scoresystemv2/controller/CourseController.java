package com.example.scoresystemv2.controller;

import com.example.scoresystemv2.bean.Course;
import com.example.scoresystemv2.bean.User;
import com.example.scoresystemv2.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("")
    public Course courseMseeage(@RequestParam("courseid") Long courseid){
        return courseService.getCourse(courseid);
    }

    @PostMapping("")
    public void addCourse(@RequestBody Course course){
        courseService.addCourse(course);
    }

    @PutMapping("/address")
    public void updataCourse(@RequestBody Course course){
        courseService.updataAddress(course);
    }

    @PutMapping("/name")
    public void updataName(@RequestBody Course course){
        courseService.updataName(course);
    }
}
