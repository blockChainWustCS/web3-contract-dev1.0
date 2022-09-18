// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

import "./ownable.sol";

contract PersonalInfoManage is Ownable {
    //结构体定义，用来存储个人信息
    struct PersonalInformation {
        uint256 id; //可以是学号，教师编号
        uint256 totalLearn; //选修课程总数
        uint256 totalTeach; //教授课程总数
        string name; //姓名
        string url; //头像的URL
        string sex; //出生日期
        string contractInformation; //联系方式
        bool isStudent; //判断是否是学生，true代表是管理员
        bool isTeacher; //判断是否是老师，true代表是管理员
        bool isAdmin; //判断是否是管理员，true代表是管理员
        bool isUsed; //判断人员信息是否被创建
    }

    //添加人员时触发该事件 参数是人员id,姓名,添加时是否是学生，是否是老师，是否是管理员
    event personAdd(
        uint256 indexed _id,
        string name,
        bool isStudent,
        bool isTeacher,
        bool isAdmin
    );
    //人员信息变动时触发该事件  参数是人员id,姓名
    event personalInformationChange(uint256 indexed _id, string name);
    //删除人员时触发该事件  参数是人员id,姓名
    event personDelete(uint256 indexed _id, string name);
    //添加管理员触发该事件  参数是人员id,姓名
    event adminAdd(uint256 indexed _id, string name);
    //删除管理员触发该事件  参数是人员id,姓名
    event adminDelete(uint256 indexed _id, string name);

    //地址到Id的映射，用来找每个Id的账户地址
    mapping(address => uint256) public personId;
    //Id到个人信息的映射，用来快速寻找对应个人信息
    mapping(uint256 => PersonalInformation) public personalInformation;

    //修饰器，限制只有传入的_id对应的账户可以调用
    modifier onlyOwner(uint256 _id) {
        require(personId[msg.sender] == _id, "No permission to see!");
        _;
    }

    //修饰器，限制只有管理员账户可以调用
    modifier onlyAdmin() {
        require(
            personalInformation[personId[msg.sender]].isAdmin,
            "No permission to modify!"
        );
        _;
    }

    //修饰器，限制id必须属于某个用户
    modifier isAUser(uint256 _id) {
        require(personalInformation[_id].isUsed, "No such person!");
        _;
    }

    //获取个人信息，参数是要查询的人的id ，返回值是这个id对应的个人信息，返回值类型是一个PersonalInformation的结构体
    function getPersonalInformation(uint256 _id)
        external
        view
        onlyOwner(_id)
        returns (PersonalInformation memory information)
    {
        return personalInformation[_id];
    }

    /*添加一个用户，只有管理员才能操作，参数依次是要添加的用户的账户地址，姓名，头像的URL，出生日期，是否设为管理员
    管理员不能添加自己，不能添加已经存在的ID*/
    function addOnePerson(
        address _address,
        uint256 _id,
        string memory _name,
        string memory _url,
        string memory _sex,
        string memory _contractInformation,
        bool _isAdmin
    ) external onlyAdmin {
        require(_id != 0, "zero id!");
        require(!personalInformation[_id].isUsed, "This Id already exists!");
        require(msg.sender != _address, "You can't add yourself");
        personId[_address] = _id;
        personalInformation[_id] = PersonalInformation(
            _id,
            0,
            0,
            _name,
            _url,
            _sex,
            _contractInformation,
            false,
            false,
            _isAdmin,
            true
        );
        emit personAdd(_id, _name, false, false, _isAdmin);
        if (_isAdmin) {
            emit adminAdd(_id, _name);
        }
    }

    /*管理员修改一个用户信息，参数依次是账户地址，姓名，url，性别，联系方式，
 不能修改一个已经存在的账户地址和ID的关系，只能删除用户后重新添加*/
    function setPersonalInformation(
        address _address,
        uint256 _id,
        string memory _name,
        string memory _url,
        string memory _sex,
        string memory _contractInformation
    ) external onlyAdmin isAUser(_id) {
        require(
            personId[_address] == _id,
            "Please delete the old user and create a new user!"
        );
        personalInformation[_id].name = _name;
        personalInformation[_id].url = _url;
        personalInformation[_id].sex = _sex;
        personalInformation[_id].contractInformation = _contractInformation;
        emit personalInformationChange(_id, _name);
    }

    /*用户修改自己的信息，参数依次是账户地址，姓名，url，联系方式，
  不能修改一个已经存在的账户地址和ID的关系，只能删除用户后重新添加*/
    function setOwnInformation(
        address _address,
        uint256 _id,
        string memory _url,
        string memory _contractInformation
    ) external isAUser(_id) onlyOwner(_id) {
        require(
            personId[_address] == _id,
            "Please delete the old user and create a new user!"
        );
        personalInformation[_id].url = _url;
        personalInformation[_id].contractInformation = _contractInformation;
        emit personalInformationChange(_id, personalInformation[_id].name);
    }

    /*设置一个用户是否为管理员，第一个参数是用户id，第二个参数是是否设为管理员，
  只有管理员才能操作，管理员不能把自己设为非管理员*/
    function setAdmin(uint256 _id, bool _isAdmin)
        public
        onlyAdmin
        isAUser(_id)
    {
        require(personId[msg.sender] != _id, "You can't operate yourself!");
        personalInformation[_id].isAdmin = _isAdmin;
        if (_isAdmin) {
            emit adminAdd(_id, personalInformation[_id].name);
        } else {
            emit adminDelete(_id, personalInformation[_id].name);
        }
    }
}
