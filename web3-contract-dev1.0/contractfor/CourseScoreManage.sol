// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

import "./PersonalInfoManage.sol";

contract CourseScoreManage is PersonalInfoManage {
    //当分数发生变化时触发该事件 参数是课程号，教师号，学生学号，原来成绩和现在的成绩
    event scoreChange(
        uint256 indexed courseId,
        uint256 indexed teacherId,
        uint256 indexed studentsId,
        uint256 oldValue,
        uint256 newValue
    );
    //添加老师时触发该事件  参数是教师号，教师姓名
    event teacherAdd(uint256 indexed teacherId, string name);
    //删除老师时触发该事件  参数是教师号，教师姓名
    event teacherDelete(uint256 indexed teacherId, string name);
    //添加学生时触发该事件  参数是学生学号，学生姓名
    event studentAdd(uint256 indexed studentsId, string name);
    //删除学生时触发该事件   参数是学生学号，学生姓名
    event studentDelete(uint256 indexed studentsId, string name);
    //添加课程时触发该事件  参数是课程号和一开始的教师号，教师姓名
    event courseAdd(
        uint256 indexed courseId,
        string corseName,
        uint256 indexed teacherId,
        string teacherName
    );
    //删除课程时触发该事件   参数是课程号和删除时的教师号，教师姓名
    event courseDelete(
        uint256 indexed courseId,
        string corseName,
        uint256 indexed teacherId,
        string teacherName
    );
    //课程老师变化时触发该事件  参数是课程号，课程名，更改前的教师号，教师姓名和更改后的教师号，教师姓名
    event courseTeacherChange(
        uint256 indexed courseId,
        string corseName,
        uint256 indexed oldteacherId,
        string oldteacheName,
        uint256 indexed newteacherId,
        string newteacherName
    );
    //课程某个学生添加时触发该事件  参数是课程号，课程名和学生学号，学生名
    event courseStudentAdd(
        uint256 indexed courseId,
        string courseName,
        uint256 indexed studentsId,
        string studentName
    );
    //课程某个学生删除时触发该事件  参数是课程号，课程名和学生学号，学生名
    event courseStudentDelete(
        uint256 indexed courseId,
        string courseName,
        uint256 indexed studentsId,
        string studentName
    );

    //结构体定义，存储课程信息
    struct CourseInformation {
        uint256 id; //课程号
        string name; //课程名称
        uint256 teacherId; //教师号
        string teacherName; //教师名
        uint256 studentNum;
        bool isUsed; //判断该课程是否已经创建
    }

    //传入的参数是第一个管理员的id，姓名，url，性别和联系方式，同时设置id不能为0
    constructor(
        uint256 _id,
        string memory _name,
        string memory _url,
        string memory _sex,
        string memory _contractInformation
    ) {
        personId[address(0)] = 0;
        personalInformation[0] = PersonalInformation(
            0,
            0,
            0,
            "",
            "",
            "",
            "",
            false,
            false,
            false,
            true
        );
        require(_id != 0);
        personId[msg.sender] = _id;
        personalInformation[_id] = (
            PersonalInformation(
                _id,
                0,
                0,
                _name,
                _url,
                _sex,
                _contractInformation,
                false,
                false,
                true,
                true
            )
        );
        emit personAdd(_id, _name, false, false, true);
        emit adminAdd(_id, _name);
    }

    //课程id到课程信息数组的索引的映射
    mapping(uint256 => CourseInformation) courseInformation;

    //查询学生是否选修某门课的映射表，第一个uint256是课程编号，第二个uint256是学生学号
    mapping(uint256 => mapping(uint256 => bool)) courseOfStudent;
    //查询学生某门课成绩的映射表，第一个uint256是课程编号，第二个uint256是学生学号
    mapping(uint256 => mapping(uint256 => uint256)) courseScores;

    //修饰器，限制课程必须存在
    modifier Exists(uint256 _courseId) {
        require(_courseExists(_courseId), "No such course!");
        _;
    }

    //查询课程是否存在。参数是课程id
    function _courseExists(uint256 _courseId) private view returns (bool) {
        return courseInformation[_courseId].isUsed;
    }

    //查询学生是否选修了某门课，第一个参数是课程id，第二个参数是学生id
    function _isAStudentOf(uint256 _courseId, uint256 _studentId)
        private
        view
        Exists(_courseId)
        returns (bool)
    {
        return courseOfStudent[_courseId][_studentId];
    }

    //查询老师是否教了某门课，第一个参数是课程id，第二个参数是老师id
    function _isATeacherOf(uint256 _courseId, uint256 _teacherId)
        private
        view
        Exists(_courseId)
        returns (bool)
    {
        return _teacherId == courseInformation[_courseId].teacherId;
    }

    function _isAStudent(uint256 _studentId)
        private
        view
        isAUser(_studentId)
        returns (bool)
    {
        return personalInformation[_studentId].isStudent;
    }

    function _isATeacher(uint256 _teacherId)
        private
        view
        isAUser(_teacherId)
        returns (bool)
    {
        return personalInformation[_teacherId].isTeacher;
    }

    //设置一个用户是否为老师，参数是用户id
    function setTeacher(uint256 _teacherId, bool _isTeacher)
        public
        onlyAdmin
        isAUser(_teacherId)
    {
        if (_isTeacher) {
            require(
                !personalInformation[_teacherId].isTeacher,
                "This teacher already exists!"
            );
            personalInformation[_teacherId].isTeacher = _isTeacher;
            emit teacherAdd(_teacherId, personalInformation[_teacherId].name);
        } else {
            require(
                personalInformation[_teacherId].totalTeach == 0,
                "Please delete  the teacher from the course first!"
            );
            personalInformation[_teacherId].isTeacher = _isTeacher;
            emit teacherDelete(
                _teacherId,
                personalInformation[_teacherId].name
            );
        }
    }

    //设置一个用户是否为学生，参数是用户id
    function setStuent(uint256 _studentId, bool _isStudent)
        public
        onlyAdmin
        isAUser(_studentId)
    {
        if (_isStudent) {
            require(
                !personalInformation[_studentId].isStudent,
                "This student already exists!"
            );
            personalInformation[_studentId].isStudent = _isStudent;
            emit studentAdd(_studentId, personalInformation[_studentId].name);
        } else {
            require(
                personalInformation[_studentId].totalLearn == 0,
                "Please delete  the  student from the course first!"
            );
            personalInformation[_studentId].isStudent = _isStudent;
            emit studentDelete(
                _studentId,
                personalInformation[_studentId].name
            );
        }
    }

    //添加一门课程参数分别是课程号，课程名称，老师的id，老师的姓名
    function addCourse(
        uint256 _courseId,
        string memory _courseName,
        uint256 _teacherId,
        string memory _teacherName
    ) external onlyAdmin isAUser(_teacherId) {
        require(!_courseExists(_courseId), "This course already exists");
        require(_isATeacher(_teacherId), "No such teacher");
        courseInformation[_courseId] = CourseInformation(
            _courseId,
            _courseName,
            _teacherId,
            _teacherName,
            0,
            true
        );
        personalInformation[_teacherId].totalTeach++;
        emit courseAdd(
            _courseId,
            courseInformation[_courseId].name,
            _teacherId,
            personalInformation[_teacherId].name
        );
    }

    //设置课程的老师，参数分别是课程号，老师的id，老师的姓名
    function setTeacherTo(
        uint256 _courseId,
        uint256 _teacherId,
        string memory _teacherName
    ) public Exists(_courseId) onlyAdmin isAUser(_teacherId) {
        require(_isATeacher(_teacherId), "No such teacher");
        uint256 oldteacherId = courseInformation[_courseId].teacherId;
        string memory oldteacherName = courseInformation[_courseId].teacherName;
        courseInformation[_courseId].teacherId = _teacherId;
        courseInformation[_courseId].teacherName = _teacherName;
        personalInformation[oldteacherId].totalTeach--;
        personalInformation[_teacherId].totalTeach++;
        emit courseTeacherChange(
            _courseId,
            courseInformation[_courseId].name,
            oldteacherId,
            oldteacherName,
            _teacherId,
            _teacherName
        );
    }

    //给课程添加一个学生，参数是课程id和学生id
    function addStudentsToCourse(uint256 _courseId, uint256 _studentId)
        public
        Exists(_courseId)
        onlyAdmin
    {
        require(_isAStudent(_studentId), "No such student!");
        courseOfStudent[_courseId][_studentId] = true;
        courseInformation[_courseId].studentNum++;
        personalInformation[_studentId].totalLearn++;
        emit courseStudentAdd(
            _courseId,
            courseInformation[_courseId].name,
            _studentId,
            personalInformation[_studentId].name
        );
    }

    //从课程中删除一个学生，参数是课程id和学生id
    function deleteStudentsFromCourse(uint256 _courseId, uint256 _studentId)
        public
        Exists(_courseId)
        onlyAdmin
    {
        require(_isAStudentOf(_courseId, _studentId), "No such student!");
        delete courseOfStudent[_courseId][_studentId];
        delete courseScores[_courseId][_studentId];
        courseInformation[_courseId].studentNum--;
        personalInformation[_studentId].totalLearn--;
        emit courseStudentDelete(
            _courseId,
            courseInformation[_courseId].name,
            _studentId,
            personalInformation[_studentId].name
        );
    }

    /*  获取一个学生的某门课程信息，参数是课程id和学生id
     返回值分别是课程id，课程名称，老师id，老师姓名和课程分数*/
    function getCourseInformationForStudent(
        uint256 _courseId,
        uint256 _studentId
    )
        external
        view
        onlyOwner(_studentId)
        returns (
            uint256 courseId,
            string memory courseName,
            uint256 teacherId,
            string memory teacherName,
            uint256 score
        )
    {
        require(_isAStudentOf(_courseId, _studentId), "No permission to see!");
        return (
            _courseId,
            courseInformation[_courseId].name,
            courseInformation[_courseId].teacherId,
            courseInformation[_courseId].teacherName,
            courseScores[_courseId][_studentId]
        );
    }

    //删除一门课程，参数是课程id,必须先删除该课程的所有学生
    function deleteCourse(uint256 _courseId)
        external
        Exists(_courseId)
        onlyAdmin
    {
        require(
            courseInformation[_courseId].studentNum == 0,
            "Please delete all the students from the course first!"
        );
        string memory name = courseInformation[_courseId].name;
        uint256 teacherId = courseInformation[_courseId].teacherId;
        delete courseInformation[_courseId];
        emit courseDelete(
            _courseId,
            name,
            teacherId,
            personalInformation[teacherId].name
        );
    }

    //设置课程成绩，参数是课程id，学生id和分数
    function setScore(
        uint256 _courseId,
        uint256 _studentId,
        uint256 _score
    ) external Exists(_courseId) {
        require(
            _isATeacherOf(_courseId, personId[msg.sender]),
            "No permission to modify!"
        );
        require(_isAStudentOf(_courseId, _studentId), "No Such Student!");
        uint256 oldScore = courseScores[_courseId][_studentId];
        courseScores[_courseId][_studentId] = _score;
        emit scoreChange(
            _courseId,
            personId[msg.sender],
            _studentId,
            oldScore,
            _score
        );
    }

    //deletePersonalInformation方法，删除个人信息，参数是账户地址，必须要先删除这个用户所有的教授课程信息和选修课程信息
    function deletePersonalInformation(address _address)
        external
        onlyAdmin
        isAUser(personId[_address])
    {
        require(msg.sender != _address, "You can't delete yourself!");
        uint256 id = personId[_address];
        require(
            personalInformation[id].totalLearn == 0,
            "Please delete  the student from the course first!"
        );
        require(
            personalInformation[id].totalTeach == 0,
            "Please delete  the teacher from the course first!"
        );
        string memory name = personalInformation[id].name;
        if (_isAStudent(id)) {
            setStuent(id, false);
        }
        if (_isAStudent(id)) {
            setTeacher(id, false);
        }
        if (personalInformation[id].isAdmin) {
            setAdmin(id, false);
        }
        delete personalInformation[id];
        delete personId[_address];
        emit personDelete(id, name);
    }

    function getId() external view returns (uint256) {
        return personId[msg.sender];
    }

    //查看某人的身份，参数是id，三个返回值分别代表是否学生,是否是老师，是否是管理员
    function getStatus(uint256 _id)
        external
        view
        isAUser(_id)
        returns (
            bool isStudent,
            bool isTeacher,
            bool isAdmin
        )
    {
        return (
            _isAStudent(_id),
            _isATeacher(_id),
            personalInformation[_id].isAdmin
        );
    }

    //管理员获取某人信息
    function getUsersInformations(uint256 _id)
        external
        view
        onlyAdmin
        isAUser(_id)
        returns (PersonalInformation memory information)
    {
        return personalInformation[_id];
    }
}
