package com.webank.test.oracle.oraclenew;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;

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
public class APIConsumer extends Contract {
    public static String BINARY = "608060405260018055670de0b6b3a764000060045534801561002057600080fd5b506040516108423803806108428339818101604052602081101561004357600080fd5b810190808051906020019092919050505080600360006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505061079d806100a56000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c8063338cdca114610051578063653721471461006f578063de2927891461008d578063e8d0a0d2146100ab575b600080fd5b6100596100e3565b6040518082815260200191505060405180910390f35b6100776101e2565b6040518082815260200191505060405180910390f35b6100956101e8565b6040518082815260200191505060405180910390f35b6100e1600480360360408110156100c157600080fd5b8101908080359060200190929190803590602001909291905050506101f2565b005b60006040518060800160405280606081526020016106e0606091396007908051906020019061011392919061063a565b506101dd600360009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1660078054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156101d05780601f106101a5576101008083540402835291602001916101d0565b820191906000526020600020905b8154815290600101906020018083116101b357829003601f168201915b5050505050600454610331565b905090565b60065481565b6000600654905090565b816002600082815260200190815260200160002060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146102aa576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260288152602001806107406028913960400191505060405180910390fd5b6002600082815260200190815260200160002060006101000a81549073ffffffffffffffffffffffffffffffffffffffff0219169055807f6930ee93026433301a14b1baa0b52ca0d4640cb1edaa703beeb9056e2352698460405160405180910390a281600560008581526020019081526020016000208190555081600681905550505050565b600030600154604051602001808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1660601b815260140182815260200192505050604051602081830303815290604052805190602001209050836002600083815260200190815260200160002060006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550807f9dc80e952f32e8aef7828795a4cc11bbe605c2ad3cc626df8115be4f78338f6960405160405180910390a2836000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663fab4cace3060015486866040518563ffffffff1660e01b8152600401808573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200184815260200180602001838152602001828103825284818151815260200191508051906020019080838360005b83811015610524578082015181840152602081019050610509565b50505050905090810190601f1680156105515780820380516001836020036101000a031916815260200191505b5095505050505050602060405180830381600087803b15801561057357600080fd5b505af1158015610587573d6000803e3d6000fd5b505050506040513d602081101561059d57600080fd5b8101908080519060200190929190505050610620576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f6f7261636c652d636f726520696e766f6b65206661696c65642100000000000081525060200191505060405180910390fd5b600180600082825401925050819055508090509392505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061067b57805160ff19168380011785556106a9565b828001600101855582156106a9579182015b828111156106a857825182559160200191906001019061068d565b5b5090506106b691906106ba565b5090565b6106dc91905b808211156106d85760008160009055506001016106c0565b5090565b9056fe706c61696e2868747470733a2f2f7777772e72616e646f6d2e6f72672f696e7465676572732f3f6e756d3d313030266d696e3d31266d61783d31303026636f6c3d3126626173653d313026666f726d61743d706c61696e26726e643d6e657729536f75726365206d75737420626520746865206f7261636c65206f66207468652072657175657374a2646970667358221220f8cd748e5bc9553855581c15e757b3ea28558788b58559523ca22a9a4571d40b64736f6c63430006060033";

    public static final String ABI = "[{\"inputs\": [{\"internalType\": \"address\", \"name\": \"oracleAddress\", \"type\": \"address\"}], \"stateMutability\": \"nonpayable\", \"type\": \"constructor\"}, {\"anonymous\": false, \"inputs\": [{\"indexed\": true, \"internalType\": \"bytes32\", \"name\": \"id\", \"type\": \"bytes32\"}], \"name\": \"Fulfilled\", \"type\": \"event\"}, {\"anonymous\": false, \"inputs\": [{\"indexed\": true, \"internalType\": \"bytes32\", \"name\": \"id\", \"type\": \"bytes32\"}], \"name\": \"Requested\", \"type\": \"event\"}, {\"inputs\": [{\"internalType\": \"bytes32\", \"name\": \"_requestId\", \"type\": \"bytes32\"}, {\"internalType\": \"int256\", \"name\": \"_result\", \"type\": \"int256\"}], \"name\": \"__callback\", \"outputs\": [], \"stateMutability\": \"nonpayable\", \"type\": \"function\"}, {\"inputs\": [], \"name\": \"getResult\", \"outputs\": [{\"internalType\": \"int256\", \"name\": \"\", \"type\": \"int256\"}], \"stateMutability\": \"view\", \"type\": \"function\"}, {\"inputs\": [], \"name\": \"request\", \"outputs\": [{\"internalType\": \"bytes32\", \"name\": \"requestId\", \"type\": \"bytes32\"}], \"stateMutability\": \"nonpayable\", \"type\": \"function\"}, {\"inputs\": [], \"name\": \"result\", \"outputs\": [{\"internalType\": \"int256\", \"name\": \"\", \"type\": \"int256\"}], \"stateMutability\": \"view\", \"type\": \"function\"}]";

    public static final String FUNC___CALLBACK = "__callback";

    public static final String FUNC_GETRESULT = "getResult";

    public static final String FUNC_REQUEST = "request";

    public static final String FUNC_RESULT = "result";

    public static final Event FULFILLED_EVENT = new Event("Fulfilled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    public static final Event REQUESTED_EVENT = new Event("Requested", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>(true) {}));
    ;

    @Deprecated
    protected APIConsumer(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected APIConsumer(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected APIConsumer(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected APIConsumer(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<FulfilledEventResponse> getFulfilledEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(FULFILLED_EVENT, transactionReceipt);
        ArrayList<FulfilledEventResponse> responses = new ArrayList<FulfilledEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            FulfilledEventResponse typedResponse = new FulfilledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerFulfilledEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(FULFILLED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerFulfilledEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(FULFILLED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<RequestedEventResponse> getRequestedEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(REQUESTED_EVENT, transactionReceipt);
        ArrayList<RequestedEventResponse> responses = new ArrayList<RequestedEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            RequestedEventResponse typedResponse = new RequestedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.id = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerRequestedEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(REQUESTED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerRequestedEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(REQUESTED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public RemoteCall<TransactionReceipt> __callback(byte[] _requestId, BigInteger _result) {
        final Function function = new Function(
                FUNC___CALLBACK, 
                Arrays.<Type>asList(new Bytes32(_requestId),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(_result)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void __callback(byte[] _requestId, BigInteger _result, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC___CALLBACK, 
                Arrays.<Type>asList(new Bytes32(_requestId),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(_result)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String __callbackSeq(byte[] _requestId, BigInteger _result) {
        final Function function = new Function(
                FUNC___CALLBACK, 
                Arrays.<Type>asList(new Bytes32(_requestId),
                new org.fisco.bcos.web3j.abi.datatypes.generated.Int256(_result)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> getResult() {
        final Function function = new Function(
                FUNC_GETRESULT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void getResult(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_GETRESULT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getResultSeq() {
        final Function function = new Function(
                FUNC_GETRESULT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> request() {
        final Function function = new Function(
                FUNC_REQUEST, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void request(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_REQUEST, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String requestSeq() {
        final Function function = new Function(
                FUNC_REQUEST, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> result() {
        final Function function = new Function(
                FUNC_RESULT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void result(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_RESULT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String resultSeq() {
        final Function function = new Function(
                FUNC_RESULT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    @Deprecated
    public static APIConsumer load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new APIConsumer(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static APIConsumer load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new APIConsumer(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static APIConsumer load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new APIConsumer(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static APIConsumer load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new APIConsumer(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<APIConsumer> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String oracleAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(oracleAddress)));
        return deployRemoteCall(APIConsumer.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<APIConsumer> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String oracleAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(oracleAddress)));
        return deployRemoteCall(APIConsumer.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<APIConsumer> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String oracleAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(oracleAddress)));
        return deployRemoteCall(APIConsumer.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<APIConsumer> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String oracleAddress) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(oracleAddress)));
        return deployRemoteCall(APIConsumer.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class FulfilledEventResponse {
        public Log log;

        public byte[] id;
    }

    public static class RequestedEventResponse {
        public Log log;

        public byte[] id;
    }
}
