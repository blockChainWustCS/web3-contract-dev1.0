package com.example.scoresystemv2.mapper;

import com.example.scoresystemv2.bean.Course;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CourseMapper {
    @Select("select * from course where courseID=#{courseID}")
    Course getOneId(@Param("courseID")Long cuurseid);

    @Insert("insert into course values(#{courseID},#{name},#{teacherAddress})")
    void addCourse(Course course);

    @Update("update course set teacheraddress=#{teacherAddress} where courseID = #{courseID}")
    void updataCourse(Course course);

    @Update("update course set name=#{name} where courseID = #{courseID}")
    void updataName(Course course);
}
