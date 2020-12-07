pragma solidity ^0.6.6;

import "./Ownable.sol";
import "./SafeMath.sol";

/**
 * @title The  contract  for oracle service listening
 */
contract OracleCore is  Ownable {
  using SafeMath for uint256;

  mapping(bytes32 => bytes32) private commitments;
  mapping(bytes32 => uint256) timeoutMap;

  bytes4 private callbackFunctionId = bytes4(keccak256("__callback(bytes32,int256)"));

  event OracleRequest(
    address callbackAddr,
    bytes32 requestId,
    string url,
    uint256  expiration,
    uint256 _timesAmount
  );

  constructor()
    public
    Ownable()
  {

  }

  function query(
    address _callbackAddress,
    uint256 _nonce,
    string calldata _url,
    uint256 _timesAmount,
    uint256 _expiryTime
  )
    external
  returns(bool)
  {
    bytes32 requestId = keccak256(abi.encodePacked(_callbackAddress, _nonce));
    require(commitments[requestId] == 0, "Must use a unique ID");
    uint256 expiration = now.add(_expiryTime);
    timeoutMap[requestId] = expiration;
    commitments[requestId] = keccak256(
      abi.encodePacked(
        _callbackAddress,
        expiration
      )
    );

    emit OracleRequest(
      _callbackAddress,
      requestId,
      _url,
      expiration,
     _timesAmount);
    return true;
  }


  function fulfillRequest(
    bytes32 _requestId,
    address _callbackAddress,
    uint256 _expiration,
    uint256 _result
  )
    external
    onlyOwner
    isValidRequest(_requestId)
    returns (bool)
  {
    bytes32 paramsHash = keccak256(
      abi.encodePacked(
        _callbackAddress,
        _expiration
      )
    );
    require(commitments[_requestId] == paramsHash, "Params do not match request ID");
    delete commitments[_requestId];
    delete timeoutMap[_requestId];
    (bool success, ) = _callbackAddress.call(abi.encodeWithSelector(callbackFunctionId, _requestId, _result)); // solhint-disable-line avoid-low-level-calls

    return success;
  }



  /**
   * @dev Reverts if request ID does not exist or time out.
   * @param _requestId The given request ID to check in stored `commitments`
   */
  modifier isValidRequest(bytes32 _requestId) {
    require(commitments[_requestId] != 0, "Must have a valid requestId");
    require(timeoutMap[_requestId] > now, "fulfill request time out");
    _;
  }

  /**
   * @dev Reverts if `msg.sender` is not authorized to fulfill requests
   */
  modifier onlyOwner() {
    require(msg.sender == owner(), "Not an authorized node to fulfill requests");
    _;
  }

}
