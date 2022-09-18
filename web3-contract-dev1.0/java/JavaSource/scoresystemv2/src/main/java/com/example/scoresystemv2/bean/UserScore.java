package com.example.scoresystemv2.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserScore {
    private Course courseID;
    private User user;
    private Long score;

    public UserScore(Course course,User user,Score score){
        this.courseID=course;
        this.user=user;
        this.score = score.getScore();
    }
}
