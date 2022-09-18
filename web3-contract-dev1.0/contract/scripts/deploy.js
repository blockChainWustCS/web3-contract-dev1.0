// We require the Hardhat Runtime Environment explicitly here. This is optional
// but useful for running the script in a standalone fashion through `node <script>`.
//
// You can also run a script with `npx hardhat run <script>`. If you do that, Hardhat
// will compile your contracts, add the Hardhat Runtime Environment's members to the
// global scope, and execute the script.
const hre = require("hardhat");

const main = async() => {
    const CourseScoreService = await hre.ethers.getContractFactory("CourseScoreService")
    const courseScoreService = await CourseScoreService.deploy()
    await courseScoreService.deployed()

    console.log(`Contact deployed at ${courseScoreService.address}`)

    const creater = await courseScoreService.getOwner()
    console.log(`This contact was created by ${creater}`)

}


// We recommend this pattern to be able to use async/await everywhere
// and properly handle errors.
main()
    .then(() => {
        console.log(`successfully deployed`)
        process.exit(0)
    })
    .catch((e) => {
        console.log(`Failed to deploy`)
        console.log(e)
        process.exit(1)
    });