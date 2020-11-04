package com.webank.test.oracle.oraclenew;

import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.*;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.fisco.bcos.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version none.
 */
@SuppressWarnings("unchecked")
public class OracleCore extends Contract {
    public static String BINARY = "608060405263e8d0a0d260e01b600260006101000a81548163ffffffff021916908360e01c021790555034801561003557600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16600073ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a3610b6a806101016000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c80634b602282146100675780638da5cb5b146100855780638f32d59b146100cf578063f105f2a0146100f1578063f2fde38b1461016b578063fab4cace146101af575b600080fd5b61006f610274565b6040518082815260200191505060405180910390f35b61008d61027a565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6100d76102a3565b604051808215151515815260200191505060405180910390f35b6101516004803603608081101561010757600080fd5b8101908080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff16906020019092919080359060200190929190803590602001909291905050506102fa565b604051808215151515815260200191505060405180910390f35b6101ad6004803603602081101561018157600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610660565b005b61025a600480360360808110156101c557600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190803590602001909291908035906020019064010000000081111561020c57600080fd5b82018360208201111561021e57600080fd5b8035906020019184600183028401116401000000008311171561024057600080fd5b9091929391929390803590602001909291905050506106e6565b604051808215151515815260200191505060405180910390f35b61025881565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905090565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614905090565b600061030461027a565b73ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610387576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602a815260200180610b0b602a913960400191505060405180910390fd5b846000801b60016000838152602001908152602001600020541415610414576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601b8152602001807f4d757374206861766520612076616c696420726571756573744964000000000081525060200191505060405180910390fd5b60008585604051602001808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1660601b815260140182815260200192505050604051602081830303815290604052805190602001209050806001600089815260200190815260200160002054146104fe576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601e8152602001807f506172616d7320646f206e6f74206d617463682072657175657374204944000081525060200191505060405180910390fd5b600160008881526020019081526020016000206000905560008673ffffffffffffffffffffffffffffffffffffffff16600260009054906101000a900460e01b89876040516024018083815260200182815260200192505050604051602081830303815290604052907bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19166020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff83818316178352505050506040518082805190602001908083835b602083106105e757805182526020820191506020810190506020830392506105c4565b6001836020036101000a0380198251168184511680821785525050505050509050019150506000604051808303816000865af19150503d8060008114610649576040519150601f19603f3d011682016040523d82523d6000602084013e61064e565b606091505b50509050809350505050949350505050565b6106686102a3565b6106da576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260208152602001807f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e657281525060200191505060405180910390fd5b6106e381610918565b50565b6000808686604051602001808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1660601b8152601401828152602001925050506040516020818303038152906040528051906020012090506000801b6001600083815260200190815260200160002054146107d4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260148152602001807f4d75737420757365206120756e6971756520494400000000000000000000000081525060200191505060405180910390fd5b60006107eb61025842610a5c90919063ffffffff16565b90508781604051602001808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1660601b8152601401828152602001925050506040516020818303038152906040528051906020012060016000848152602001908152602001600020819055507f1ced7cb179518c5626be5b844708b97156f8697d776f7494929702a531d9a80e8883888888604051808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001858152602001806020018381526020018281038252858582818152602001925080828437600081840152601f19601f820116905080830192505050965050505050505060405180910390a160019250505095945050505050565b600073ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff16141561099e576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526026815260200180610ae56026913960400191505060405180910390fd5b8073ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e060405160405180910390a3806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b600080828401905083811015610ada576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601b8152602001807f536166654d6174683a206164646974696f6e206f766572666c6f77000000000081525060200191505060405180910390fd5b809150509291505056fe4f776e61626c653a206e6577206f776e657220697320746865207a65726f20616464726573734e6f7420616e20617574686f72697a6564206e6f646520746f2066756c66696c6c207265717565737473a264697066735822122031ab6663b6de44952a52a23c591d36e063e8702cbdaf5e659a45d2bfc4a044e064736f6c63430006060033";

    public static final String ABI = "[{\"inputs\": [], \"stateMutability\": \"nonpayable\", \"type\": \"constructor\"}, {\"anonymous\": false, \"inputs\": [{\"indexed\": false, \"internalType\": \"address\", \"name\": \"callbackAddr\", \"type\": \"address\"}, {\"indexed\": false, \"internalType\": \"bytes32\", \"name\": \"requestId\", \"type\": \"bytes32\"}, {\"indexed\": false, \"internalType\": \"string\", \"name\": \"url\", \"type\": \"string\"}, {\"indexed\": false, \"internalType\": \"uint256\", \"name\": \"_timesAmount\", \"type\": \"uint256\"}], \"name\": \"OracleRequest\", \"type\": \"event\"}, {\"anonymous\": false, \"inputs\": [{\"indexed\": true, \"internalType\": \"address\", \"name\": \"previousOwner\", \"type\": \"address\"}, {\"indexed\": true, \"internalType\": \"address\", \"name\": \"newOwner\", \"type\": \"address\"}], \"name\": \"OwnershipTransferred\", \"type\": \"event\"}, {\"inputs\": [], \"name\": \"EXPIRY_TIME\", \"outputs\": [{\"internalType\": \"uint256\", \"name\": \"\", \"type\": \"uint256\"}], \"stateMutability\": \"view\", \"type\": \"function\"}, {\"inputs\": [{\"internalType\": \"bytes32\", \"name\": \"_requestId\", \"type\": \"bytes32\"}, {\"internalType\": \"address\", \"name\": \"_callbackAddress\", \"type\": \"address\"}, {\"internalType\": \"uint256\", \"name\": \"_expiration\", \"type\": \"uint256\"}, {\"internalType\": \"uint256\", \"name\": \"_data\", \"type\": \"uint256\"}], \"name\": \"fulfillRequest\", \"outputs\": [{\"internalType\": \"bool\", \"name\": \"\", \"type\": \"bool\"}], \"stateMutability\": \"nonpayable\", \"type\": \"function\"}, {\"inputs\": [], \"name\": \"isOwner\", \"outputs\": [{\"internalType\": \"bool\", \"name\": \"\", \"type\": \"bool\"}], \"stateMutability\": \"view\", \"type\": \"function\"}, {\"inputs\": [], \"name\": \"owner\", \"outputs\": [{\"internalType\": \"address\", \"name\": \"\", \"type\": \"address\"}], \"stateMutability\": \"view\", \"type\": \"function\"}, {\"inputs\": [{\"internalType\": \"address\", \"name\": \"_callbackAddress\", \"type\": \"address\"}, {\"internalType\": \"uint256\", \"name\": \"_nonce\", \"type\": \"uint256\"}, {\"internalType\": \"string\", \"name\": \"_url\", \"type\": \"string\"}, {\"internalType\": \"uint256\", \"name\": \"_timesAmount\", \"type\": \"uint256\"}], \"name\": \"query\", \"outputs\": [{\"internalType\": \"bool\", \"name\": \"\", \"type\": \"bool\"}], \"stateMutability\": \"nonpayable\", \"type\": \"function\"}, {\"inputs\": [{\"internalType\": \"address\", \"name\": \"newOwner\", \"type\": \"address\"}], \"name\": \"transferOwnership\", \"outputs\": [], \"stateMutability\": \"nonpayable\", \"type\": \"function\"}]";

    public static final String FUNC_EXPIRY_TIME = "EXPIRY_TIME";

    public static final String FUNC_FULFILLREQUEST = "fulfillRequest";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_QUERY = "query";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final Event ORACLEREQUEST_EVENT = new Event("OracleRequest", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected OracleCore(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected OracleCore(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected OracleCore(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected OracleCore(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<OracleRequestEventResponse> getOracleRequestEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(ORACLEREQUEST_EVENT, transactionReceipt);
        ArrayList<OracleRequestEventResponse> responses = new ArrayList<OracleRequestEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OracleRequestEventResponse typedResponse = new OracleRequestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.callbackAddr = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.requestId = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.url = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse._timesAmount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerOracleRequestEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(ORACLEREQUEST_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerOracleRequestEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(ORACLEREQUEST_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerOwnershipTransferredEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerOwnershipTransferredEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public RemoteCall<TransactionReceipt> EXPIRY_TIME() {
        final Function function = new Function(
                FUNC_EXPIRY_TIME, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void EXPIRY_TIME(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_EXPIRY_TIME, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String EXPIRY_TIMESeq() {
        final Function function = new Function(
                FUNC_EXPIRY_TIME, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> fulfillRequest(byte[] _requestId, String _callbackAddress, BigInteger _expiration, BigInteger _data) {
        final Function function = new Function(
                FUNC_FULFILLREQUEST, 
                Arrays.<Type>asList(new Bytes32(_requestId),
                new Address(_callbackAddress),
                new Uint256(_expiration),
                new Uint256(_data)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void fulfillRequest(byte[] _requestId, String _callbackAddress, BigInteger _expiration, BigInteger _data, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_FULFILLREQUEST, 
                Arrays.<Type>asList(new Bytes32(_requestId),
                new Address(_callbackAddress),
                new Uint256(_expiration),
                new Uint256(_data)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String fulfillRequestSeq(byte[] _requestId, String _callbackAddress, BigInteger _expiration, BigInteger _data) {
        final Function function = new Function(
                FUNC_FULFILLREQUEST, 
                Arrays.<Type>asList(new Bytes32(_requestId),
                new Address(_callbackAddress),
                new Uint256(_expiration),
                new Uint256(_data)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> isOwner() {
        final Function function = new Function(
                FUNC_ISOWNER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void isOwner(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ISOWNER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String isOwnerSeq() {
        final Function function = new Function(
                FUNC_ISOWNER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> owner() {
        final Function function = new Function(
                FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void owner(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String ownerSeq() {
        final Function function = new Function(
                FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> query(String _callbackAddress, BigInteger _nonce, String _url, BigInteger _timesAmount) {
        final Function function = new Function(
                FUNC_QUERY, 
                Arrays.<Type>asList(new Address(_callbackAddress),
                new Uint256(_nonce),
                new Utf8String(_url),
                new Uint256(_timesAmount)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void query(String _callbackAddress, BigInteger _nonce, String _url, BigInteger _timesAmount, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_QUERY, 
                Arrays.<Type>asList(new Address(_callbackAddress),
                new Uint256(_nonce),
                new Utf8String(_url),
                new Uint256(_timesAmount)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String querySeq(String _callbackAddress, BigInteger _nonce, String _url, BigInteger _timesAmount) {
        final Function function = new Function(
                FUNC_QUERY, 
                Arrays.<Type>asList(new Address(_callbackAddress),
                new Uint256(_nonce),
                new Utf8String(_url),
                new Uint256(_timesAmount)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new Address(newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void transferOwnership(String newOwner, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new Address(newOwner)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String transferOwnershipSeq(String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new Address(newOwner)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    @Deprecated
    public static OracleCore load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new OracleCore(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static OracleCore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new OracleCore(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static OracleCore load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new OracleCore(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static OracleCore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new OracleCore(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<OracleCore> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(OracleCore.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<OracleCore> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(OracleCore.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<OracleCore> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(OracleCore.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<OracleCore> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(OracleCore.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class OracleRequestEventResponse {
        public Log log;

        public String callbackAddr;

        public byte[] requestId;

        public String url;

        public BigInteger _timesAmount;
    }

    public static class OwnershipTransferredEventResponse {
        public Log log;

        public String previousOwner;

        public String newOwner;
    }
}
