package com.example.scoresystemv2.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class NewCouresScore extends ContractData{
    private String useraddress;
    private BigInteger courseid;
    private BigInteger score;
    private String signature;
    private String message;
    private String address;
}
