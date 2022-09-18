package com.example.scoresystemv2.service;

import com.example.scoresystemv2.bean.Score;
import com.example.scoresystemv2.mapper.ScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService implements IScoreService{
    @Autowired
    private ScoreMapper scoreMapper;

    @Override
    public Score getOneScore(Long courseid, String address) {
        return scoreMapper.getScore(courseid, address);
    }

    @Override
    public void addScore(Score score) {
        scoreMapper.addScore(score);
    }

    @Override
    public void upadata(Score score) {
        scoreMapper.updata(score);
    }

}
