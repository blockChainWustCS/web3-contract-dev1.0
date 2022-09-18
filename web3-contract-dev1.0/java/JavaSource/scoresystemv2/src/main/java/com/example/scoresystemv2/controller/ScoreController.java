package com.example.scoresystemv2.controller;

import com.example.scoresystemv2.bean.Course;
import com.example.scoresystemv2.bean.Score;
import com.example.scoresystemv2.bean.User;
import com.example.scoresystemv2.bean.UserScore;
import com.example.scoresystemv2.service.CourseService;
import com.example.scoresystemv2.service.ScoreService;
import com.example.scoresystemv2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/score")
public class ScoreController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public UserScore getScore(@RequestParam("courseid") Long courseid, @RequestParam("address")String address){
        Course course = courseService.getCourse(courseid);
        User user = userService.getFromAddress(address);
        Score score =scoreService.getOneScore(courseid,address);
        UserScore userScore = new UserScore(course,user,score);
        return userScore;
    }

    @PostMapping("")
    public void addScore(@RequestBody UserScore userscore){
        System.out.println(userscore.getCourseID());
        System.out.println(userscore.getUser().getAddress());
        System.out.println(userscore.getCourseID());
        Score score = new Score(userscore.getUser().getAddress(),userscore.getCourseID().getCourseID(),userscore.getScore());
        scoreService.addScore(score);
    }

    @PutMapping("")
    public void updataCourse(@RequestBody UserScore userscore){
        Score score = new Score(userscore.getUser().getAddress(),userscore.getCourseID().getCourseID(),userscore.getScore());
        scoreService.upadata(score);
    }
}
