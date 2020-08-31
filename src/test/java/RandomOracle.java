import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
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
public class RandomOracle extends Contract {
    public static String BINARY = "608060405234801561001057600080fd5b506109ed806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff168063299f7f9d1461007257806385db4a70146101025780639bd058e114610119578063cc08e34b14610190578063fa7820dc146101c3575b600080fd5b34801561007e57600080fd5b5061008761021e565b6040518080602001828103825283818151815260200191508051906020019080838360005b838110156100c75780820151818401526020810190506100ac565b50505050905090810190601f1680156100f45780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561010e57600080fd5b506101176102c0565b005b34801561012557600080fd5b5061018e6004803603810190808035600019169060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610451565b005b34801561019c57600080fd5b506101a561062f565b60405180826000191660001916815260200191505060405180910390f35b3480156101cf57600080fd5b50610204600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610635565b604051808215151515815260200191505060405180910390f35b606060018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156102b65780601f1061028b576101008083540402835291602001916102b6565b820191906000526020600020905b81548152906001019060200180831161029957829003601f168201915b5050505050905090565b7fad6379f9ffadb633fd664bcdc4059e9c73b41881d48ef8b15c4dd59807d639616040518080602001828103825260348152602001807f4f7261636c65207175657279207761732073656e742c207374616e64696e672081526020017f627920666f722074686520616e737765722e2e2e00000000000000000000000081525060400191505060405180910390a161040f6040805190810160405280600381526020017f75726c0000000000000000000000000000000000000000000000000000000000815250608060405190810160405280606081526020017f706c61696e2868747470733a2f2f7777772e72616e646f6d2e6f72672f696e7481526020017f65676572732f3f6e756d3d313030266d696e3d31266d61783d31303026636f6c81526020017f3d3126626173653d313026666f726d61743d706c61696e26726e643d6e65772981525061069a565b600281600019169055506001600360006002546000191660001916815260200190815260200160002060006101000a81548160ff021916908315150217905550565b61045961084a565b73ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614151561049257600080fd5b60036000836000191660001916815260200190815260200160002060009054906101000a900460ff16151561052f576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260148152602001807f6964206d757374206265206e6f7420757365642100000000000000000000000081525060200191505060405180910390fd5b806001908051906020019061054592919061091c565b5060036000836000191660001916815260200190815260200160002060006101000a81549060ff02191690557f7732b6befd5e9fe96b3027da77730e803ded4086aa16db31c10935b938f30d886001604051808060200182810382528381815460018160011615610100020316600290048152602001915080546001816001161561010002031660029004801561061d5780601f106105f25761010080835404028352916020019161061d565b820191906000526020600020905b81548152906001019060200180831161060057829003601f168201915b50509250505060405180910390a15050565b60025481565b60008061064183610911565b111561069057816000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060019050610695565b600090505b919050565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663953e6567600085856040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808481526020018060200180602001838103835285818151815260200191508051906020019080838360005b83811015610752578082015181840152602081019050610737565b50505050905090810190601f16801561077f5780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b838110156107b857808201518184015260208101905061079d565b50505050905090810190601f1680156107e55780820380516001836020036101000a031916815260200191505b5095505050505050602060405180830381600087803b15801561080757600080fd5b505af115801561081b573d6000803e3d6000fd5b505050506040513d602081101561083157600080fd5b8101908080519060200190929190505050905092915050565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663dca666026040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b1580156108d157600080fd5b505af11580156108e5573d6000803e3d6000fd5b505050506040513d60208110156108fb57600080fd5b8101908080519060200190929190505050905090565b6000813b9050919050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061095d57805160ff191683800117855561098b565b8280016001018555821561098b579182015b8281111561098a57825182559160200191906001019061096f565b5b509050610998919061099c565b5090565b6109be91905b808211156109ba5760008160009055506001016109a2565b5090565b905600a165627a7a72305820fb2b3cbe8c9ae695a624301d6de4146362db902e9f57f51f18d23ada33f259f40029";

    public static final String ABI = "[{\"constant\":true,\"inputs\":[],\"name\":\"get\",\"outputs\":[{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"update\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"_myid\",\"type\":\"bytes32\"},{\"name\":\"_result\",\"type\":\"string\"}],\"name\":\"__callback\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"id\",\"outputs\":[{\"name\":\"\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"oracleAddress\",\"type\":\"address\"}],\"name\":\"oracle_setNetwork\",\"outputs\":[{\"name\":\"_networkSet\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"description\",\"type\":\"string\"}],\"name\":\"LogNewQuery\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"temp\",\"type\":\"string\"}],\"name\":\"LogNewTempMeasure\",\"type\":\"event\"}]";

    public static final String FUNC_GET = "get";

    public static final String FUNC_UPDATE = "update";

    public static final String FUNC___CALLBACK = "__callback";

    public static final String FUNC_ID = "id";

    public static final String FUNC_ORACLE_SETNETWORK = "oracle_setNetwork";

    public static final Event LOGNEWQUERY_EVENT = new Event("LogNewQuery", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event LOGNEWTEMPMEASURE_EVENT = new Event("LogNewTempMeasure", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected RandomOracle(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected RandomOracle(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected RandomOracle(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected RandomOracle(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<String> get() {
        final Function function = new Function(FUNC_GET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> update() {
        final Function function = new Function(
                FUNC_UPDATE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void update(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_UPDATE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String updateSeq() {
        final Function function = new Function(
                FUNC_UPDATE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> __callback(byte[] _myid, String _result) {
        final Function function = new Function(
                FUNC___CALLBACK, 
                Arrays.<Type>asList(new Bytes32(_myid),
                new Utf8String(_result)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void __callback(byte[] _myid, String _result, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC___CALLBACK, 
                Arrays.<Type>asList(new Bytes32(_myid),
                new Utf8String(_result)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String __callbackSeq(byte[] _myid, String _result) {
        final Function function = new Function(
                FUNC___CALLBACK, 
                Arrays.<Type>asList(new Bytes32(_myid),
                new Utf8String(_result)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<byte[]> id() {
        final Function function = new Function(FUNC_ID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteCall<TransactionReceipt> oracle_setNetwork(String oracleAddress) {
        final Function function = new Function(
                FUNC_ORACLE_SETNETWORK, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(oracleAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void oracle_setNetwork(String oracleAddress, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ORACLE_SETNETWORK, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(oracleAddress)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String oracle_setNetworkSeq(String oracleAddress) {
        final Function function = new Function(
                FUNC_ORACLE_SETNETWORK, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(oracleAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public List<LogNewQueryEventResponse> getLogNewQueryEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGNEWQUERY_EVENT, transactionReceipt);
        ArrayList<LogNewQueryEventResponse> responses = new ArrayList<LogNewQueryEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogNewQueryEventResponse typedResponse = new LogNewQueryEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.description = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerLogNewQueryEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOGNEWQUERY_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerLogNewQueryEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOGNEWQUERY_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<LogNewTempMeasureEventResponse> getLogNewTempMeasureEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGNEWTEMPMEASURE_EVENT, transactionReceipt);
        ArrayList<LogNewTempMeasureEventResponse> responses = new ArrayList<LogNewTempMeasureEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogNewTempMeasureEventResponse typedResponse = new LogNewTempMeasureEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.temp = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerLogNewTempMeasureEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOGNEWTEMPMEASURE_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerLogNewTempMeasureEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOGNEWTEMPMEASURE_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    @Deprecated
    public static RandomOracle load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new RandomOracle(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static RandomOracle load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new RandomOracle(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static RandomOracle load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new RandomOracle(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static RandomOracle load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new RandomOracle(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<RandomOracle> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(RandomOracle.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<RandomOracle> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(RandomOracle.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<RandomOracle> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RandomOracle.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<RandomOracle> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(RandomOracle.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class LogNewQueryEventResponse {
        public Log log;

        public String description;
    }

    public static class LogNewTempMeasureEventResponse {
        public Log log;

        public String temp;
    }
}
