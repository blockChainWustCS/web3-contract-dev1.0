package com.example.scoresystemv2.service;

import com.example.scoresystemv2.bean.User;
import com.example.scoresystemv2.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getFromAddress(String address) {
        return userMapper.getfromAddress(address);
    }

    @Override
    public void register(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updata(User user) {
        userMapper.updata(user);
    }
}
