pragma solidity 0.6.10;

import "./Ownable.sol";
import "./SafeMath.sol";

/**
 * @title The Chainlink Oracle contract
 * @notice Node operators can deploy this contract to fulfill requests sent to them
 */
contract OracleCore is  Ownable {
  using SafeMath for uint256;

  uint256 constant public EXPIRY_TIME = 10 minutes;
  // We initialize fields to 1 instead of 0 so that the first invocation
  // does not cost more gas.

  mapping(bytes32 => bytes32) private commitments;

  //__callback(bytes32,int256)
  //standard bytes4 private callbackFunctionId = 0xe8d0a0d2;
   bytes4 private callbackFunctionId = 0xa6e0c7dc;


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
    uint256 _timesAmount
  )
    external
  returns(bool)
  {
    bytes32 requestId = keccak256(abi.encodePacked(_callbackAddress, _nonce));
    require(commitments[requestId] == 0, "Must use a unique ID");
    // solhint-disable-next-line not-rely-on-time
    uint256 expiration = now.add(EXPIRY_TIME);

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
    uint256 _data
  )
    external
    onlyAuthorized
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
   // require(gasleft() >= MINIMUM_CONSUMExyR_GAS_LIMIT, "Must provide consumer enough gas");
    // All updates to the oracle's fulfillment should come before calling the
    // callback(addr+functionId) as it is untrusted.
    // See: https://solidity.readthedocs.io/en/develop/security-considerations.html#use-the-checks-effects-interactions-pattern
    // todo!!!
    (bool success, ) = _callbackAddress.call(abi.encodeWithSelector(callbackFunctionId, _requestId, _data)); // solhint-disable-line avoid-low-level-calls

    return success;
  }



  /**
   * @dev Reverts if request ID does not exist
   * @param _requestId The given request ID to check in stored `commitments`
   */
  modifier isValidRequest(bytes32 _requestId) {
    require(commitments[_requestId] != 0, "Must have a valid requestId");
    _;
  }

  /**
   * @dev Reverts if `msg.sender` is not authorized to fulfill requests
   */
  modifier onlyAuthorized() {
    require(msg.sender == owner(), "Not an authorized node to fulfill requests");
    _;
  }

}
