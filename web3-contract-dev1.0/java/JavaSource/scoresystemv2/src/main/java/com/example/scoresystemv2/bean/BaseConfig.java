package com.example.scoresystemv2.bean;

import com.example.scoresystemv2.contract.CourseScoreService;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

@Data
@NoArgsConstructor
public class BaseConfig {
    public static String privatekey;
    public static Web3j web3j;
    public static CourseScoreService CSService;
    public static String address;


    public void distory(){
        address =  CSService.getContractAddress();
        web3j.shutdown();
    }

    //初始化合约服务
    public static void active(){

        try {
            privatekey = "0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80";
            web3j = Web3j.build(new HttpService("http://localhost:8545/"));
            CSService = CourseScoreService.deploy(web3j, Credentials.create(BaseConfig.privatekey), new DefaultGasProvider()).send();
            address =  CSService.getContractAddress();
            //System.out.println("aaaa");
            System.out.println(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
