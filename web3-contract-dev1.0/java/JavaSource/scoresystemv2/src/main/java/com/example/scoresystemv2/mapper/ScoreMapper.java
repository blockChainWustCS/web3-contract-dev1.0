package com.example.scoresystemv2.mapper;

import com.example.scoresystemv2.bean.Score;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ScoreMapper {

    @Select("select * from score where courseID=#{courseID} and address = #{address}")
    Score getScore(@Param("courseID")Long courseid, @Param("address") String address);

    @Insert("insert into score(courseID,address,score) values(#{courseid},#{address},#{score})")
    void addScore(Score score);

    @Options(useGeneratedKeys = false)
    @Update("update score SET score = #{score} where address = #{address} and courseid = #{courseid}")
    int updata(Score score);

}
