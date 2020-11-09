


### 获取链下API数据
用户合约开发只需继承FiscoOracleClient合约即可。必须实现__callback方法，以便oracle-servixce将结果回写。

```

pragma solidity ^0.6.0;

import "./FiscoOracleClient.sol";

contract APIConsumer is FiscoOracleClient {


    //指定处理请求的oracle
    address private oracleCoreAddress;
  

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
```

### VRF获取可验证随机数
  
  用户合约开发只需继承VRFConsumerBase合约即可。必须实现fulfillRandomness方法，以便oracle-servixce将结果回写。

```
pragma solidity 0.6.6;

import "./VRFConsumerBase.sol";

contract RandomNumberConsumer is VRFConsumerBase {

    // hash of VRF Coordinator'pk
    bytes32 internal keyHash;
    uint256 public randomResult;

    constructor(address _coordinator, bytes32 _keyHash)
        VRFConsumerBase(
            _coordinator // VRF Coordinator
        ) public
    {
         // keyHash = 0xeedf1a9c68b3f4a8b1a1032b2b5ad5c4795c026514f8317c7a215e218dccd6cf;
          keyHash = _keyHash;
    }

    /**
     * Requests randomness from a user-provided seed
     */
    function getRandomNumber(uint256 userProvidedSeed) public returns (bytes32 requestId) {
        return requestRandomness(keyHash, userProvidedSeed);
    }

    /**
     * Callback function used by VRF Coordinator
     */
    function fulfillRandomness(bytes32 requestId, uint256 randomness) internal override {
        randomResult = randomness;
    }
}
  ```
  
  
### 去中心化部署获取聚合结果



  latestAnswer()最新的聚合结果
  
  latestTimestamp() 最新一次聚合的时间戳
  
  latestRound()最新一次聚合的轮次号
  
  getAnswer(uint256 roundId) 通过轮次号获取历史结果
  
  getTimestamp(uint256 roundId)通过轮次号获取历史时间戳