import Web3 from 'web3/dist/web3.min.js';
import axios from "axios";
const basePath = 'http://localhost:8080/contract'

export class ContractManager {


    constructor() {

    }

    //每次执行都需要获取当前最新签名
    // async getContractSigner() {
    //     this.provider = new ethers.providers.Web3Provider(window.ethereum)
    //     const singer = this.provider.getSigner()
    //     this.CourseContract = new ethers.Contract(this.contractAddress, this.contractABI, singer)
    // }

    // //每次调用结束都需要调用,清除上次调用签名
    // initManagerProvider() {
    //     this.provider = null
    //     this.CourseContract = null
    // 
    

    async getWeb3() {
        if (window.ethereum) {
            // use MetaMask's provider
            this.web3 = new Web3(window.ethereum);
            window.ethereum.enable(); // get permission to access accounts
        } else {
            console.warn(
                "No web3 detected. Falling back to http://127.0.0.1:8545. You should remove this fallback when you deploy live",
            );
            // fallback - use your fallback strategy (local node / hosted node + in-dapp id mgmt / fail)
            this.web3 = new Web3(
                new Web3.providers.HttpProvider("http://127.0.0.1:8545"),
            );
        }
    }

    // async setAdmin(address) {
    //     this.getContractSigner()
    //     if (!this.CourseContract) return
    //
    //     const tx = await this.CourseContract.addAdmin(address)
    //     await tx.wait()
    //
    // }

    async setCourseTeacher(address, courseId) {
        await this.getWeb3()
        let message = "setCourseTeacher";
		let accounts = await this.web3.eth.getAccounts();
		let account = accounts[0];
        let signdMessage = await this.web3.eth.personal.sign(message, account);
        const param = {
            'teacheraddress': address,
            'courseId': courseId,
            'signature': signdMessage,
            'message': message,
            'address': account
        }
        try {
            const response = await axios.post(`${basePath}/newCouresTeacher`, param);
            return response.data
        } catch (error) {
            console.error(error);
        }
    }

    async setScore(address, courseId, score) {
        await this.getWeb3()
        let message = "setScore";
		let accounts = await this.web3.eth.getAccounts();
        let hashMassage= "0xfdb4bb21b16fe324dd4ba4297aa6c4f0cb81a5437234263f30271162ebdb6fe4";
        let account = accounts[0];
        let signdMessage = await this.web3.eth.personal.sign(hashMassage, account);
        const param = { 
            'courseid': courseId,
            'score': score,
            'useraddress': address,
            'signature': signdMessage,
            'message': message,
            'address': account
        }
        try {
            const response = await axios.post(`${basePath}/newCouresScore`, param)
            return response.data
        } catch (error) {
            console.error(error);
        }
    }
}
//明文地址,部署前需要测试
// const contractAddress = '0x5fbdb2315678afecb367f032d93f642f64180aa3'
// const contractABI = abi.abi
export const contractManager = new ContractManager()