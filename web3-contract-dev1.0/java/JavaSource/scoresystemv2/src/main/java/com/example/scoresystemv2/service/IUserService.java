package com.example.scoresystemv2.service;

import com.example.scoresystemv2.bean.User;
import java.util.List;

public interface IUserService {
    User getFromAddress(String address);
    void register(User user);
    void updata(User user);
}
