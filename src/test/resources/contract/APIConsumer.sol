pragma solidity ^0.6.10;

import "./FiscoOracleClient.sol";

contract APIConsumer is FiscoOracleClient {


    //指定处理的oracle
    address private oracleCoreAddress;
    // bytes32 private jobId;

    // Multiply the result by 1000000000000000000 to remove decimals
    uint256 private timesAmount  = 10**18;

    mapping(bytes32=>int256) resultMap;

    int256 public result;
    string url;

    constructor(address oracleAddress) public {
        oracleCoreAddress = oracleAddress;
    }


    function request() public returns (bytes32 requestId)
    {

          // Set the URL to perform the GET request on
         url = "plain(https://www.random.org/integers/?num=100&min=1&max=100&col=1&base=10&format=plain&rnd=new)";

        // Sends the request
        return sendRequestTo(oracleCoreAddress, url, timesAmount);
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