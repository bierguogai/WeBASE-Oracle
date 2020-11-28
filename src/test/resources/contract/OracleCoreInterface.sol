pragma solidity ^0.6.10;



interface OracleCoreInterface  {

    function query(
    address _callbackAddress,
    uint256 _nonce,
    string calldata _url,
    uint256 _timesAmount
  ) external
   returns(bool) ;

}

