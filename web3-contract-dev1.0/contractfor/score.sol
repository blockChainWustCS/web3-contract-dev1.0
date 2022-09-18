// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.8.7;
contract Foo {
    address public owner;
    mapping(address => bool) public isAdmin;
    mapping(address => bool) public isTeacher;
    mapping(uint256 => bool) public isCourseId;

    mapping(uint256 => address) public adminsId;
    mapping(uint256 => address) public teachersId;
    mapping(uint256 => address) public studentsId;

    mapping(uint256 => BasicInfo) public admins;
    mapping(uint256 => BasicInfo) public teachers;
    mapping(uint256 => BasicInfo) public students;
    mapping(uint256 => CourseInfo) public courses;

    mapping(uint256 => uint64[]) public coursesOfStudent;
    mapping(uint256 => uint64[]) public coursesOfTeacher;
    mapping(uint256 => mapping(uint256 => uint256)) scoreRecords;

    struct BasicInfo {
        string  name;
        string  url;
        string  sex;
        string  contractInfo;
    }

    struct CourseInfo {
        string name;
        bool isMainSubject;
    }

    constructor(address _owner) {
        if (_owner != address(0)) {
            owner = _owner;
        } else {
            owner = msg.sender;
        }
    }

    modifier onlyOwner() {
        require(msg.sender == owner, "Caller is not owner");
        _;
    }

    modifier onlyAdmin() {
        require(isAdmin[msg.sender], "Caller is not admin");
        _;
    }

    modifier onlyTeacher() {
        require(isTeacher[msg.sender], "Caller is not teacher");
        _;
    }

    function setAdmin(address _addr, bool flag) public onlyOwner {
        isAdmin[_addr] = flag;
    }

    function setTeacher(address _addr, bool flag) public onlyAdmin{
        isTeacher[_addr] = flag;
    }

    function setAdminId(uint256 _id, address _addr) public onlyAdmin {
        studentsId[_id] = _addr;
    }

    function setStudentId(uint256 _id, address _addr) public onlyAdmin {
        studentsId[_id] = _addr;
    }

    function setTeacherId(uint256 _id, address _addr) public onlyAdmin {
        teachersId[_id] = _addr;
    }

    function setAdminInfo(uint256 _id, string memory _name, string memory _url, string memory _sex, string memory _contractInfo) public {
        require(isAdminId(_id), "Admin ID does not exist");
        require(adminsId[_id] == msg.sender, "Wrong caller");
        admins[_id].url = _url;
        admins[_id].name = _name;
        admins[_id].sex = _sex;
        admins[_id].contractInfo = _contractInfo;
    }

    function setTeacherInfo(uint256 _id, string memory _name, string memory _url, string memory _sex, string memory _contractInfo) public {
        require(isTeacherId(_id), "Teacher ID does not exist");
        require(isAdmin[msg.sender] || teachersId[_id] == msg.sender, "Wrong caller");
        teachers[_id].url = _url;
        teachers[_id].name = _name;
        teachers[_id].sex = _sex;
        teachers[_id].contractInfo = _contractInfo;
    }

    function setStudentInfo(uint256 _id, string memory _name, string memory _url, string memory _sex, string memory _contractInfo) public {
        require(isStudentId(_id), "Student ID does not exist");
        require(isAdmin[msg.sender] || studentsId[_id] == msg.sender, "Wrong caller");
        students[_id].url = _url;
        students[_id].name = _name;
        students[_id].sex = _sex;
        students[_id].contractInfo = _contractInfo;
    }

    function setCourseInfo(uint256 _id, string memory _name, bool _isMainSubject) public onlyAdmin{
        courses[_id].name = _name;
        courses[_id].isMainSubject = _isMainSubject;
        isCourseId[_id] = true;
    }

    function setScoreRecord(uint256 _studentId, uint256 _courseId, uint256 score) public onlyTeacher{
        require(isStudentId(_studentId), "Student ID does not exist");
        require(isCourseId[_courseId], "Courses ID does not exist");
        // coursesOfStudent[_studentId][...x] == _courseId;
        scoreRecords[_studentId][_courseId] = score;
    }

    function setCourseForStudent(uint256 _studentId, uint64 _courseId) public onlyAdmin{
        require(isStudentId(_studentId), "Teacher IDdoes not exist");
        require(isCourseId[_courseId], "Course ID does not exist");
        coursesOfStudent[_studentId].push(_courseId);
    }

    function setCourseForTeacher(uint256 _teacherId, uint64 _courseId) public onlyAdmin{
        require(isTeacherId(_teacherId), "Student ID does not exist");
        require(isCourseId[_courseId], "Courses ID does not exist");
        coursesOfTeacher[_teacherId].push(_courseId);
    }

    function setCourseId(uint256 _id, bool flag) public onlyAdmin{
        isCourseId[_id] = flag;
    }

    function getAdminInfo(uint256 _id) public view returns(BasicInfo memory){
        return admins[_id];
    }

    function getTeacherInfo(uint256 _id) public view returns(BasicInfo memory){
        return teachers[_id];
    }

    function getStudentInfo(uint256 _id) public view returns(BasicInfo memory){
        return students[_id];
    }

    function getCourseInfo(uint256 _id) public view returns(CourseInfo memory){
        return courses[_id];
    }

    function getScoreRecord(uint256 _studentId, uint256 _courseId) public view returns(uint256) {
        return scoreRecords[_studentId][_courseId];
    }

    function isAdminId(uint256 _id) public view returns(bool){
        return adminsId[_id] != address(0);
    }

    function isStudentId(uint256 _id) public view returns(bool){
        return studentsId[_id] != address(0);
    }

    function isTeacherId(uint256 _id) public view returns(bool){
        return teachersId[_id] != address(0);
    }
}
