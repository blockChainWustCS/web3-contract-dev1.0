package com.example.scoresystemv2.service;

import com.example.scoresystemv2.bean.Course;
import com.example.scoresystemv2.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService implements ICourseService{
    @Autowired
    private CourseMapper courseMapper;

    @Override
    public void addCourse(Course course) {
        courseMapper.addCourse(course);
    }

    @Override
    public void updataAddress(Course course) {
        courseMapper.updataCourse(course);
    }

    @Override
    public void updataName(Course course) {
        courseMapper.updataName(course);
    }

    @Override
    public Course getCourse(Long courseid) {
        return courseMapper.getOneId(courseid);
    }

}
