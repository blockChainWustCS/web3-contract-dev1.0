package com.example.scoresystemv2.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddAdmin extends ContractData{
    private String adminaddress;
    private String signature;
    private String message;
    private String address;
}
