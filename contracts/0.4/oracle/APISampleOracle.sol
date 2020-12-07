pragma solidity ^0.6.0;

import "./FiscoOracleClient.sol";

contract APISampleOracle is FiscoOracleClient {


    //指定处理的oracle
    address private oracleCoreAddress;

    // Multiply the result by 1000000000000000000 to remove decimals
    uint256 private timesAmount  = 10**18;

    mapping(bytes32=>int256) private resultMap;

    mapping(bytes32=>bool) private validIds;

    int256 public result;
    string url;

    constructor(address oracleAddress) public {
        oracleCoreAddress = oracleAddress;
    }


    function request() public returns (bytes32 requestId)
    {

          // Set your URL
         url = "plain(https://www.random.org/integers/?num=100&min=1&max=100&col=1&base=10&format=plain&rnd=new)";
         bytes32  requestId = oracleQuery(oracleCoreAddress, url, timesAmount);
         validIds[requestId] = true;
    }

    /**
     * Receive the response in the form of uint256
     */
    function __callback(bytes32 _requestId, int256 _result) public override onlyOracleCoreInvoke(_requestId)
    {
        require(validIds[_myid], "id must be not used!") ;
        resultMap[_requestId]= _result;
        delete validIds[_myid];
        result = _result ;
    }


      function get()  public view  returns(int256){
         return result;
      }
}