package com.example.scoresystemv2.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class NewCouresTeacher extends ContractData{
    private String teacheraddress;
    private BigInteger courseId;
    private String signature;
    private String message;
    private String address;
}
