// SPDX-License-Identifier: MIT


pragma solidity >=0.7.0 <0.9.0;

contract CourseScoreService {
    address public owner;

    //分数
    mapping(address => mapping(uint256 => uint8)) scores;
    //课程老师
    mapping(uint256 => address) courseTeacher;

    modifier onlyOwner() {
        require(msg.sender == owner, "You are not the owner of this contract");
        _;
    }

    event NewCouresTeacher(address _address, uint256 _courseId);
    event NewCouresScore(address _address, uint256 _courseId, uint8 _score,address _teacherAddress);

    constructor() {
        owner = msg.sender;
    }

    //添加课程老师
    function setCourseTeacher(address _address, uint256 _courseId)
        external
        onlyOwner
    {
        courseTeacher[_courseId] = _address;
        emit NewCouresTeacher(_address, _courseId);
    }

    //添加学生分数
    function setStudentToCourse(
        address _address,
        uint256 _courseId,
        uint8 _score,
        address _teacherAddress
    ) external {
        require(courseTeacher[_courseId]==_teacherAddress,"you have no right to operate");
        scores[_address][_courseId] = _score;
        emit NewCouresScore(_address, _courseId, _score, _teacherAddress);
    }

}