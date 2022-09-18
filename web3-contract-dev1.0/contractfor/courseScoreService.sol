// SPDX-License-Identifier: MIT

pragma solidity >=0.7.0 <0.9.0;

contract CourseScoreService {
    address public owner;

    //分数
    mapping(address => mapping(uint256 => uint8)) scores;
    //权限
    mapping(address => bool) isAdmin;
    //课程老师
    mapping(uint256 => address) courseTeacher;

    modifier onlyOwner() {
        require(msg.sender == owner, "You are not the owner of this contract");
        _;
    }

    modifier onlyAdmin() {
        require(
            isAdmin[msg.sender],
            "You have not rights to this operation,onlyAdmin"
        );
        _;
    }


    event UpdataAdmin(address _address);
    event NewCouresTeacher(address _address, uint256 _courseId);
    event NewCouresScore(address _address, uint256 _courseId, uint8 _score);

    constructor() {
        owner = msg.sender;
    }

    //设置管理员
    function addAdmin(address _address) external onlyOwner {
        require(
            !isAdmin[_address],
            "This person has already been an administrator"
        );
        isAdmin[_address] = true;
        emit UpdataAdmin(_address);
    }

    //添加课程老师
    function setCourseTeacher(address _address, uint256 _courseId)
        external
        onlyAdmin
    {
        courseTeacher[_courseId] = _address;
        emit NewCouresTeacher(_address, _courseId);
    }

    //添加学生分数
    function setStudentToCourse(
        address _address,
        uint256 _courseId,
        uint8 _score
    ) external {
        require(courseTeacher[_courseId]==msg.sender,"you have no right to operate");
        scores[_address][_courseId] = _score;
        emit NewCouresScore(_address, _courseId, _score);
    }

    function getOwner() public view returns (address) {
        return owner;
    }

}
