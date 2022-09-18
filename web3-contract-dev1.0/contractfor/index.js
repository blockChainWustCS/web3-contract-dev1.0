const ethers = require('ethers');
const abi = require('./assets/contract/CourseScoreService.json')

const contractAddress = '0x5fbdb2315678afecb367f032d93f642f64180aa3'
//const provider = ethers.getDefaultProvider()
const provider = new ethers.providers.JsonRpcProvider("http://localhost:8545/");

const contract = new ethers.Contract(contractAddress, abi.abi, provider);

console.log(contract)
//async function listenEvent() {
var privkey = "0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80";
let wallet = new ethers.Wallet(privkey, provider);
let contractSign = contract.connect(wallet);
//let owner = await contractSign.getOwner();
//console.log(owner);
console.log('aaaaaaa');
contractSign.on("UpdataAdmin", (address, event) => {
  // 在值变化的时候被调用
  console.log('bbbbbb');
  console.log(address);
  // "0x14791697260E4c9A71f18484C9f997B308e59325"

  // console.log(oldValue);
  // // "Hello World"

  // console.log(newValue);
  // // "Ilike turtles."

  // 查看后面的事件触发器  Event Emitter 了解事件对象的属性
  console.log(event.blockNumber);
  // 4115004
});

contractSign.on('UpdataTeacher', (address, event) => {
  console.log(1)
  // 在值变化的时候被调用

  console.log(address);
  // "0x14791697260E4c9A71f18484C9f997B308e59325"

  // console.log(oldValue);
  // // "Hello World"

  // console.log(newValue);
  // // "Ilike turtles."

  // 查看后面的事件触发器  Event Emitter 了解事件对象的属性
  console.log(event.blockNumber);
  // 4115004
})

//}
//listenEvent();

