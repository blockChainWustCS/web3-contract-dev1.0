const axios =require ('axios');
const ethers = require('ethers');
const { getAddress } = require('ethers/lib/utils');
const basePath = 'http://localhost:8080'
const http = require('http')
const abi = require('./assets/contract/CourseScoreService.json')
const contractAddress = ''
const provider = new ethers.providers.JsonRpcProvider("http://localhost:8545/");
const contract = new ethers.Contract(contractAddress, abi.abi, provider);
//console.log(contract)
var privkey = "0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80";
let wallet = new ethers.Wallet(privkey, provider);
let contractSign = contract.connect(wallet);
async function loadAddress() {
  const contractAddress = await axios.get(`${basePath}/contract`);
}

loadAddress();

contractSign.on('NewCouresScore', async (_address, courseId, score, teacherAddress ,event) =>  {
  console.log("更改课程分数");
  console.log("学生地址 ：");
  console.log(_address);

  console.log("课程号 ：");
  console.log(courseId.toNumber());

  console.log("分数 ：");
  console.log(score);

  console.log("老师地址 ：");
  console.log(teacherAddress);

  let param={
    "courseID": {
      "courseID": courseId.toNumber(),
      "name": "noting",
      "teacherAddress": teacherAddress
    },
    "user": {
      "address": _address,
      "isAdmin": false,
      "name": "noting",
      "num": "noting",
      "age": 19,
      "url": "noting"
    },
    "score": score
  }

  const response = await axios.get(`${basePath}/score?courseid=${courseId.toNumber()}&address=${_address}`).catch(function(error) {
   
  });
  

  if (!response){
    axios.post(`${basePath}/score?courseid=${courseId.toNumber()}&address=${_address}`, param)
    .catch(function(error) {
      console.log(error)
    })
  }
  else{
    axios.put(`${basePath}/score?courseid=${courseId.toNumber()}&address=${_address}`, param)
    .catch(function(error) {
      console.log(error)
    })
  }


  console.log("=============================");

  // let req = http.request(options, res => {
  //   console.log(`状态码: ${res.statusCode}`)
  //   if (res.statusCode == 200) {
  //     let post_data = {
  //       "courseID": {
  //         "courseID": courseId,
  //         "name": "nothing",
  //         "teacherAddress": "nothing"
  //       },
  //       "user": {
  //         "address": address,
  //         "isAdmin": false,
  //         "name": "nothing",
  //         "num": "nothing",
  //         "age": 0,
  //         "url": "nothing"
  //       },
  //       "score": 70
  //     }
  //     let content = JSON.stringify(post_data);
  //     options = {
  //       hostname: 'localhost',
  //       port: 8080,
  //       path: '/score',
  //       method: 'PUT',
  //       headers: {
  //         'Content-Type': 'application/json',
  //         'Content-Length': content.length
  //       }
  //     }
  //     var req = http.request(options, function (res) {
  //       console.log("statusCode: ", res.statusCode);
  //       console.log("headers: ", res.headers);
  //     });
  //     req.write(content);
  //     req.end();
  //   } else {
  //     let post_data = {
  //       "courseID": {
  //         "courseID": courseId,
  //         "name": "nothing",
  //         "teacherAddress": "nothing"
  //       },
  //       "user": {
  //         "address": address,
  //         "isAdmin": false,
  //         "name": "nothing",
  //         "num": "nothing",
  //         "age": 0,
  //         "url": "nothing"
  //       },
  //       "score": score
  //     }
  //     let content = JSON.stringify(post_data);
  //     let options = {
  //       hostname: 'localhost',
  //       port: 8080,
  //       path: '/score',
  //       method: 'POST',
  //       headers: {
  //         'Content-Type': 'application/json',
  //         'Content-Length': content.length
  //       }
  //     }
  //     var reqs = http.request(options, function (res) {
  //       console.log("statusCode: ", res.statusCode);
  //       console.log("headers: ", res.headers);
  //     });
  //     reqs.write(content);
  //     reqs.end();
  //   }
  // })
  // req.end()
})

contractSign.on('NewCouresTeacher', async (_address, courseId, event) => {
  

  console.log("更改课程老师");
  console.log("老师地址 ：");
  console.log(_address);
  
  console.log("课程号 ：");
  console.log(courseId.toNumber());


  let param={
    "courseID": courseId.toNumber(),
    "name": "",
    "teacherAddress": _address
  }

  
  const response = await axios.get(`${basePath}/course?courseid=${courseId.toNumber()}`).catch(function(error) {
    // console.log(error)
  });
   

   if (!response.data){
   axios.post(`${basePath}/course`, param)
   .catch(function(error) {
        // console.log(error)
   })
   }
   else {
    axios.put(`${basePath}/course/address`, param)
   .catch(function(error) {
        console.log(error)
   })
   }
  
   console.log("=============================");

  // let options = {
  //   hostname: 'localhost',
  //   port: 8080,
  //   path: '/score?courseid=' +courseId,
  //   method: 'GET'
  // }
  // let req = http.request(options, res => {
  //   if (res.statusCode === 200) {
  //     let post_data = {
  //       "courseID": courseId,
  //       "name": "nothing",
  //       "teacherAddress": address
  //     }//这是需要提交的数据
  //     let content = JSON.stringify(post_data);
  //     let options = {
  //       hostname: 'localhost',
  //       port: 8080,
  //       path: '/course',
  //       method: 'PUT',
  //       headers: {
  //         'Content-Type': 'application/json',
  //         'Content-Length': content.length
  //       }
  //     }
  //     var req = http.request(options, function (res) {
  //       console.log("statusCode: ", res.statusCode);
  //       console.log("headers: ", res.headers);
  //       var _data = '';
  //       res.on('data', function (chunk) {
  //         _data += chunk;
  //       });
  //       res.on('end', function () {
  //         console.log("\n--->>\nresult:", _data)
  //       });
  //     });
  //     req.write(content);
  //     req.end();
  //   }else{
  //     let post_data = {
  //       "courseID": courseId,
  //       "name": "nothing",
  //       "teacherAddress": address
  //     }//这是需要提交的数据
  //     let content = JSON.stringify(post_data);
  //     let options = {
  //       hostname: 'localhost',
  //       port: 8080,
  //       path: '/course',
  //       method: 'POST',
  //       headers: {
  //         'Content-Type': 'application/json',
  //         'Content-Length': content.length
  //       }
  //     }
  //     var req = http.request(options, function (res) {
  //       console.log("statusCode: ", res.statusCode);
  //       console.log("headers: ", res.headers);
  //       var _data = '';
  //       res.on('data', function (chunk) {
  //         _data += chunk;
  //       });
  //       res.on('end', function () {
  //         console.log("\n--->>\nresult:", _data)
  //       });
  //     });
  //     req.write(content);
  //     req.end();
  //   }
  // })
  // req.end()
  // console.log(event.blockNumber);
})
