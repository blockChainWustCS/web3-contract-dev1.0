// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract CourseScore {
   mapping(address=>bool) isAdmin;
   mapping(address=>bool)  isStudent;
   mapping(address=>uint256)  studentScore;
   address teacher;

   event scoreChange(address indexed student,address indexed teacher,address indexed course,uint256 oldvalue,uint256 newvalue);
  
   constructor (){
       isAdmin[msg.sender]=true;
   }

   modifier onlyAdmin(){
       require(isAdmin[msg.sender],"No permission to modify!");
       _;
   }

   modifier onlyTeacher(){
       require(teacher==msg.sender,"No permission to modify!");
       _;
   }

   modifier onlyStudent(){
       require(isStudent[msg.sender],"No permission to modify!");
       _;
   }

   function getScoreForStudent() onlyStudent external view returns (uint256 score){
       return studentScore[msg.sender];
   }


   function getScoreForTeacher(address _student) onlyTeacher external view returns (uint256 score){
       return studentScore[_student];
   }

   function setScore(address _student,uint256 _score) onlyTeacher external {
        uint256 oldScore=studentScore[_student];
        studentScore[_student]=_score;
        emit scoreChange(_student,msg.sender,address(this),oldScore,_score);
   }

   function addStudent(address _student) onlyAdmin external {
         isStudent[_student]=true;
   }

   function deleteStudent(address _student) onlyAdmin external {
        delete isStudent[_student];
   }
   
   function setTeacher(address _teacher) onlyAdmin external{
         teacher=_teacher;
   }

   function kill() onlyAdmin external{
         selfdestruct(payable(msg.sender)); 
   }
}