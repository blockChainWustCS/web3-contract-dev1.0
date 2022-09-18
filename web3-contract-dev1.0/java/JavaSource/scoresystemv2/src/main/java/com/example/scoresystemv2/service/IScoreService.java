package com.example.scoresystemv2.service;

import com.example.scoresystemv2.bean.Score;

import java.util.List;

public interface IScoreService {
    Score getOneScore(Long courseid, String address);
    void addScore(Score score);
    void upadata(Score score);
}
