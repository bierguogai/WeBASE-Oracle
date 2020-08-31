package com.webank.oracle.transaction;

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
    public static String BINARY = "608060405234801561001057600080fd5b5033600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555033600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555061081d806100a26000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680635690ae6214610132578063953e656714610207578063d4443b40146102dc578063dca666021461031f578063f2048c9814610376575b34801561007957600080fd5b50600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141580156101265750600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614155b1561013057600080fd5b005b34801561013e57600080fd5b506101e960048036038101908080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610441565b60405180826000191660001916815260200191505060405180910390f35b34801561021357600080fd5b506102be60048036038101908080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506106de565b60405180826000191660001916815260200191505060405180910390f35b3480156102e857600080fd5b5061031d600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506106f4565b005b34801561032b57600080fd5b506103346107b5565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b34801561038257600080fd5b50610423600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192905050506107db565b60405180826000191660001916815260200191505060405180910390f35b6000624f1a00420184111561045557600080fd5b30336000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018281526020019350505050604051809103902090506000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600081548092919060010191905055507fe098e7c3ee5d2bc2ef9cdfa9074073f2e7732ceaf137be82b88eb848f8b6e6c53382868686604051808673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200185600019166000191681526020018481526020018060200180602001838103835285818151815260200191508051906020019080838360005b8381101561062f578082015181840152602081019050610614565b50505050905090810190601f16801561065c5780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b8381101561069557808201518184015260208101905061067a565b50505050905090810190601f1680156106c25780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390a18090509392505050565b60006106eb848484610441565b90509392505050565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141580610767575060008173ffffffffffffffffffffffffffffffffffffffff16145b1561077157600080fd5b80600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60006107e960008484610441565b9050929150505600a165627a7a72305820d9c7210a90e533f7abac86ae49cbd63c7286fd683a585262bc0fa019e5fb54eb0029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"name\":\"_timestamp\",\"type\":\"uint256\"},{\"name\":\"_datasource\",\"type\":\"string\"},{\"name\":\"_arg\",\"type\":\"string\"}],\"name\":\"query1\",\"outputs\":[{\"name\":\"_id\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_timestamp\",\"type\":\"uint256\"},{\"name\":\"_datasource\",\"type\":\"string\"},{\"name\":\"_arg\",\"type\":\"string\"}],\"name\":\"query\",\"outputs\":[{\"name\":\"_id\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"newCbaddress\",\"type\":\"address\"}],\"name\":\"setCBaddress\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"cbAddress\",\"outputs\":[{\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_datasource\",\"type\":\"string\"},{\"name\":\"_arg\",\"type\":\"string\"}],\"name\":\"query\",\"outputs\":[{\"name\":\"_id\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"fallback\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"sender\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"cid\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"timestamp\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"datasource\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"arg\",\"type\":\"string\"}],\"name\":\"Log1\",\"type\":\"event\"}]";

    public static final String FUNC_QUERY1 = "query1";

    public static final String FUNC_QUERY = "query";

    public static final String FUNC_SETCBADDRESS = "setCBaddress";

    public static final String FUNC_CBADDRESS = "cbAddress";

    public static final Event LOG1_EVENT = new Event("Log1",
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
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

    public RemoteCall<TransactionReceipt> query1(BigInteger _timestamp, String _datasource, String _arg) {
        final Function function = new Function(
                FUNC_QUERY1, 
                Arrays.<Type>asList(new Uint256(_timestamp),
                new Utf8String(_datasource),
                new Utf8String(_arg)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void query1(BigInteger _timestamp, String _datasource, String _arg, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_QUERY1, 
                Arrays.<Type>asList(new Uint256(_timestamp),
                new Utf8String(_datasource),
                new Utf8String(_arg)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String query1Seq(BigInteger _timestamp, String _datasource, String _arg) {
        final Function function = new Function(
                FUNC_QUERY1, 
                Arrays.<Type>asList(new Uint256(_timestamp),
                new Utf8String(_datasource),
                new Utf8String(_arg)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> query(BigInteger _timestamp, String _datasource, String _arg) {
        final Function function = new Function(
                FUNC_QUERY, 
                Arrays.<Type>asList(new Uint256(_timestamp),
                new Utf8String(_datasource),
                new Utf8String(_arg)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void query(BigInteger _timestamp, String _datasource, String _arg, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_QUERY, 
                Arrays.<Type>asList(new Uint256(_timestamp),
                new Utf8String(_datasource),
                new Utf8String(_arg)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String querySeq(BigInteger _timestamp, String _datasource, String _arg) {
        final Function function = new Function(
                FUNC_QUERY, 
                Arrays.<Type>asList(new Uint256(_timestamp),
                new Utf8String(_datasource),
                new Utf8String(_arg)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> setCBaddress(String newCbaddress) {
        final Function function = new Function(
                FUNC_SETCBADDRESS, 
                Arrays.<Type>asList(new Address(newCbaddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void setCBaddress(String newCbaddress, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_SETCBADDRESS, 
                Arrays.<Type>asList(new Address(newCbaddress)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String setCBaddressSeq(String newCbaddress) {
        final Function function = new Function(
                FUNC_SETCBADDRESS, 
                Arrays.<Type>asList(new Address(newCbaddress)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<String> cbAddress() {
        final Function function = new Function(FUNC_CBADDRESS,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> query(String _datasource, String _arg) {
        final Function function = new Function(
                FUNC_QUERY, 
                Arrays.<Type>asList(new Utf8String(_datasource),
                new Utf8String(_arg)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void query(String _datasource, String _arg, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_QUERY, 
                Arrays.<Type>asList(new Utf8String(_datasource),
                new Utf8String(_arg)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String querySeq(String _datasource, String _arg) {
        final Function function = new Function(
                FUNC_QUERY, 
                Arrays.<Type>asList(new Utf8String(_datasource),
                new Utf8String(_arg)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public List<Log1EventResponse> getLog1Events(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOG1_EVENT, transactionReceipt);
        ArrayList<Log1EventResponse> responses = new ArrayList<Log1EventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            Log1EventResponse typedResponse = new Log1EventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.cid = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.datasource = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.arg = (String) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerLog1EventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOG1_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerLog1EventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOG1_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
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

    public static class Log1EventResponse {
        public Log log;

        public String sender;

        public byte[] cid;

        public BigInteger timestamp;

        public String datasource;

        public String arg;
    }
}
