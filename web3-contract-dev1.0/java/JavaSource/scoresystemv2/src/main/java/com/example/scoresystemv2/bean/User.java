package com.example.scoresystemv2.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    public Long id;
    private String address;
    private Boolean isAdmin;
    private String name;
    private String num;
    private Long age;
    private String url;

    public User(Long userid,String address){
        this.id=userid;
        this.address =address;
        this.name = "无";
        this.url="待指定";
    }
}
