pragma solidity ^0.6.0;

import "./FiscoOracleClient.sol";

contract APISampleOracle is FiscoOracleClient {


    //指定处理的oracle
    address private oracleCoreAddress;

    // Multiply the result by 1000000000000000000 to remove decimals
    uint256 private timesAmount  = 10**18;

    mapping(bytes32=>int256) resultMap;

    int256 public result;
    string url;

    constructor(address oracleAddress) public {
        oracleCoreAddress = oracleAddress;
    }


    function update() public returns (bytes32 requestId)
    {

          // Set your URL
         url = "plain(https://www.random.org/integers/?num=100&min=1&max=100&col=1&base=10&format=plain&rnd=new)";

        return oracleQuery(oracleCoreAddress, url, timesAmount);
    }

    /**
     * Receive the response in the form of uint256
     */
    function __callback(bytes32 _requestId, int256 _result) public override onlyOracleCoreInvoke(_requestId)
    {
        resultMap[_requestId]= _result;
        result = _result ;
    }


      function getResult()  public view  returns(int256){
         return result;
      }
}