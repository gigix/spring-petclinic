pragma solidity >=0.4.21 <0.7.0;

contract Owners {
    struct Owner {
        uint id;
        string name;
        string addr;
        string city;
        string telephone;
    }

    mapping (uint => Owner) owners;

    function add(uint _id, string memory _name, string memory _addr, string memory _city, string memory _telephone) public {
        owners[_id] = Owner(_id, _name, _addr, _city, _telephone);
    }
}
