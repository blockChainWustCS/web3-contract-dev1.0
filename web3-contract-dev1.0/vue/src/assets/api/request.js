import axios from "axios"
import { contractManager } from "./ContractManager"
const basePath = 'http://localhost:8080'
class Request {
    constructor() {
        this.backend = {
            getUser: async(address) => {
                try {
                    const response = await axios.get(`${basePath}/user?address=${address}`);
                    return response.data
                } catch (error) {
                    console.error(error)
                }
            },
            getCourse: async(courseId) => {
                try {
                    const response = await axios.get(`${basePath}/course?courseid=${courseId}`);
                    return response.data
                } catch (error) {
                    console.error(error);
                }
            },
            getScore: async(courseId, address) => {
                if (courseId && address) {
                    try {
                        const response = await axios.get(`${basePath}/score?courseid=${courseId}&address=${address}`);
                        return response.data
                    } catch (error) {
                        console.error(error);
                    }
                } else if (courseId && !address) {
                    try {
                        const response = await axios.get(`${basePath}/score?courseid=${courseId}`);
                        return response.data
                    } catch (error) {
                        console.error(error);
                    }
                } else if (!courseId && address) {
                    try {
                        const response = await axios.get(`${basePath}/score?address=${address}`);
                        return response.data
                    } catch (error) {
                        console.error(error);
                    }
                }
            },
            //向后端添加课程包含的学生，post请求
            setStudentToCourse: async(body) => {
                const res = await axios.post(`${basePath}/score`, body)
                    .catch(function(error) {
                        console.log(error);
                    });
                return res
            },
            newUser: async(user) => {
                const res = await axios.post(`${basePath}/user`, user)
                    .catch(function(error) {
                        console.log(error);
                    });
                return res
            },
            newCourse: async(course) => {
                const res = await axios.post(`${basePath}/course`, course)
                    .catch(function(error) {
                        console.log(error);
                    });
                return res
            },
            updateUser: async(address, user) => {
                const res = await axios.put(`${basePath}/user?address=${address}`, user)
                    .catch(function(error) {
                        console.log(error)
                    })
                return res
            }


        }
        this.chain = function() {
            //利用闭包存储contractManager，方便调用
            const manager = contractManager
            return {
                getCreater: async() => {
                    const res = await manager.getCreater()
                    console.log(res)
                },
                setAdmin: async(address) => {
                    await manager.setAdmin(address)
                },
                setCourseTeacher: async(address, courseId) => {
                    await manager.setCourseTeacher(address, courseId)
                },
                //调用这个接口必须先要保证已经往数据库中存了对应的课程和address关系，这个只负责成绩存储
                setScore: async() => {
                    await manager.setScore(address, courseId, score)
                },
            }
        }
    }
}

export const request = new Request()