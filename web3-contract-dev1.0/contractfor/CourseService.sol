// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract CourseService {
    address public owner;

    mapping(address => mapping(uint256 => uint8)) courseStudentScore;
    mapping(address => bool) Students;
    mapping(address => bool) Teachers;
    mapping(address => bool) Admins;
    mapping(address => string) img;

    constructor() {
        owner = msg.sender;
    }

    modifier isTeacher() {
        require(Teachers[msg.sender]);
        _;
    }

    function addStudent(address _address) public {
        require(Admins[msg.sender]);
        Students[_address] = true;
    }
}
