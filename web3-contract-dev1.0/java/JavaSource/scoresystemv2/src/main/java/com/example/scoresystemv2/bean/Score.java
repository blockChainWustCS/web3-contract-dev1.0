package com.example.scoresystemv2.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Score {
    private Long id;
    private Long courseid;
    private String address;
    private Long score;

    public Score(String address, Long courseid, Long scores){
        this.address = address;
        this.courseid= courseid;
        this.score = scores;
    }
}
