package com.example.scoresystemv2.controller;

import com.example.scoresystemv2.bean.NewCouresScore;
import com.example.scoresystemv2.bean.NewCouresTeacher;
import com.example.scoresystemv2.bean.BaseConfig;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contract")
public class ContractController {

//    /**
//     * 测试方法
//     * @param data
//     * @throws Exception
//     */
//    @PostMapping("/helloword")
//    public void sendMessage(@RequestBody ContractData data) throws Exception {
//        System.out.println(data);
//        Boolean result = data.validate();
//        System.out.println(result);
//        Web3j web3j = Web3j.build(new HttpService("http://localhost:8545"));
//        HelloWorld helloWorld = HelloWorld.deploy(web3j, Credentials.create(BaseConfig.pricatekey), new DefaultGasProvider() ).send();
//        String name = helloWorld.get().send();
//        System.out.println(name);
//        //web3j.shutdown();
//    }

    static{
        BaseConfig.active();
    }

    /**
     * 返回合约地址
     * @return
     * @throws Exception
     */
    @GetMapping("")
    public String getaddress() throws Exception {
        System.out.println("ok");
        return BaseConfig.address;
    }

    /**
     * 课程上链
     * @param ncs
     * @throws Exception
     */
    @PostMapping("/newCouresTeacher")
    public void newCouresTeacher(@RequestBody NewCouresTeacher ncs) throws Exception {
        System.out.println(ncs);
        Boolean result = ncs.validate(ncs.getSignature(),ncs.getMessage(),ncs.getAddress());
        System.out.println(result);
        BaseConfig.CSService.setCourseTeacher(ncs.getTeacheraddress(),ncs.getCourseId()).send();
    }

    /**
     * 成绩上链
     * @param score
     * @throws Exception
     */
    @PostMapping("/newCouresScore")
    public void NewCouresScore(@RequestBody NewCouresScore score) throws Exception {
        System.out.println(score);
        Boolean result = score.validate(score.getSignature(),score.getMessage(),score.getAddress());
        System.out.println(result);
        BaseConfig.CSService.setStudentToCourse(score.getUseraddress(),score.getCourseid(),score.getScore(),score.getAddress(),score.getMessage(),score.getSignature()).send();
    }
}
