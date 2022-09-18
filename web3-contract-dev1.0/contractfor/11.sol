// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract CoinS {
    struct Coin {
        string name;
        uint256 num;
    }

    Coin[] coins;

    mapping(uint256 => address) ownderOfCoin;
    mapping(address => uint256) counts;
}
