import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
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
import org.fisco.bcos.web3j.tuples.generated.Tuple1;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;

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
public class TemperatureOracle extends Contract {
    public static String BINARY = "608060405234801561001057600080fd5b506109c3806100206000396000f300608060405260043610610078576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806311d0df2e1461007d57806327dc297e146100d857806338bbfa501461014f578063a2e620451461020c578063adccea1214610223578063af640d0f146102b3575b600080fd5b34801561008957600080fd5b506100be600480360381019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506102e6565b604051808215151515815260200191505060405180910390f35b3480156100e457600080fd5b5061014d6004803603810190808035600019169060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610389565b005b34801561015b57600080fd5b5061020a6004803603810190808035600019169060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061049f565b005b34801561021857600080fd5b506102216104a4565b005b34801561022f57600080fd5b506102386105b2565b6040518080602001828103825283818151815260200191508051906020019080838360005b8381101561027857808201518184015260208101905061025d565b50505050905090810190601f1680156102a55780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156102bf57600080fd5b506102c8610650565b60405180826000191660001916815260200191505060405180910390f35b6000806102f283610656565b111561037f57816000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506103766040805190810160405280600581526020017f666973636f000000000000000000000000000000000000000000000000000000815250610661565b60019050610384565b600090505b919050565b61039161067b565b73ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff161415156103ca57600080fd5b80600290805190602001906103e09291906108f2565b507f4c7d0b87e189b9f866291da1eb75ea9852bfff2c58a3eace1299a5a1d448caad6002604051808060200182810382528381815460018160011615610100020316600290048152602001915080546001816001161561010002031660029004801561048d5780601f106104625761010080835404028352916020019161048d565b820191906000526020600020905b81548152906001019060200180831161047057829003601f168201915b50509250505060405180910390a15050565b505050565b7fc4dc360d0a9c0677a3379ae0a3d81e887f761e65fdf3d7e00859d1bcd3c473896040518080602001828103825260368152602001807f50726f7661626c65207175657279207761732073656e742c207374616e64696e81526020017f6720627920666f722074686520616e737765722e2e2e0000000000000000000081525060400191505060405180910390a16105a66040805190810160405280600381526020017f75726c00000000000000000000000000000000000000000000000000000000008152506040805190810160405280600581526020017f68656c6c6f000000000000000000000000000000000000000000000000000000815250610742565b60038160001916905550565b60028054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156106485780601f1061061d57610100808354040283529160200191610648565b820191906000526020600020905b81548152906001019060200180831161062b57829003601f168201915b505050505081565b60035481565b6000813b9050919050565b80600190805190602001906106779291906108f2565b5050565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663c281d19e6040518163ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401602060405180830381600087803b15801561070257600080fd5b505af1158015610716573d6000803e3d6000fd5b505050506040513d602081101561072c57600080fd5b8101908080519060200190929190505050905090565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1663adf59f99600085856040518463ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808481526020018060200180602001838103835285818151815260200191508051906020019080838360005b838110156107fa5780820151818401526020810190506107df565b50505050905090810190601f1680156108275780820380516001836020036101000a031916815260200191505b50838103825284818151815260200191508051906020019080838360005b83811015610860578082015181840152602081019050610845565b50505050905090810190601f16801561088d5780820380516001836020036101000a031916815260200191505b5095505050505050602060405180830381600087803b1580156108af57600080fd5b505af11580156108c3573d6000803e3d6000fd5b505050506040513d60208110156108d957600080fd5b8101908080519060200190929190505050905092915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061093357805160ff1916838001178555610961565b82800160010185558215610961579182015b82811115610960578251825591602001919060010190610945565b5b50905061096e9190610972565b5090565b61099491905b80821115610990576000816000905550600101610978565b5090565b905600a165627a7a723058202680d587cc155cad03f54dcf4d657d7b5d243edd8db06ee03ab177bc8c5a90170029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"oracleAddress\",\"type\":\"address\"}],\"name\":\"provable_setNetwork\",\"outputs\":[{\"indexed\":false,\"name\":\"_networkSet\",\"type\":\"bool\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_myid\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"_result\",\"type\":\"string\"}],\"name\":\"__callback\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_myid\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"_result\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_proof\",\"type\":\"bytes\"}],\"name\":\"__callback\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"name\":\"update\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"temperature\",\"outputs\":[{\"indexed\":false,\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"id\",\"outputs\":[{\"indexed\":false,\"name\":\"\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"description\",\"type\":\"string\"}],\"name\":\"LogNewProvableQuery\",\"payable\":false,\"type\":\"event\"},{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"temperature\",\"type\":\"string\"}],\"name\":\"LogNewTemperatureMeasure\",\"payable\":false,\"type\":\"event\"}]";

    public static final TransactionDecoder transactionDecoder = new TransactionDecoder(ABI, BINARY);

    public static final String FUNC_PROVABLE_SETNETWORK = "provable_setNetwork";

    public static final String FUNC___CALLBACK = "__callback";

    public static final String FUNC_UPDATE = "update";

    public static final String FUNC_TEMPERATURE = "temperature";

    public static final String FUNC_ID = "id";

    public static final Event LOGNEWPROVABLEQUERY_EVENT = new Event("LogNewProvableQuery", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    public static final Event LOGNEWTEMPERATUREMEASURE_EVENT = new Event("LogNewTemperatureMeasure", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected TemperatureOracle(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TemperatureOracle(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected TemperatureOracle(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected TemperatureOracle(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static TransactionDecoder getTransactionDecoder() {
        return transactionDecoder;
    }

    public RemoteCall<TransactionReceipt> provable_setNetwork(String oracleAddress) {
        final Function function = new Function(
                FUNC_PROVABLE_SETNETWORK, 
                Arrays.<Type>asList(new Address(oracleAddress)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void provable_setNetwork(String oracleAddress, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_PROVABLE_SETNETWORK, 
                Arrays.<Type>asList(new Address(oracleAddress)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String provable_setNetworkSeq(String oracleAddress) {
        final Function function = new Function(
                FUNC_PROVABLE_SETNETWORK, 
                Arrays.<Type>asList(new Address(oracleAddress)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getProvable_setNetworkInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_PROVABLE_SETNETWORK, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public Tuple1<Boolean> getProvable_setNetworkOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_PROVABLE_SETNETWORK, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
                );
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

    public Tuple2<byte[], String> get__callbackBytes32StringInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC___CALLBACK, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<byte[], String>(

                (byte[]) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> __callback(byte[] _myid, String _result, byte[] _proof) {
        final Function function = new Function(
                FUNC___CALLBACK, 
                Arrays.<Type>asList(new Bytes32(_myid),
                new Utf8String(_result),
                new DynamicBytes(_proof)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void __callback(byte[] _myid, String _result, byte[] _proof, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC___CALLBACK, 
                Arrays.<Type>asList(new Bytes32(_myid),
                new Utf8String(_result),
                new DynamicBytes(_proof)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String __callbackSeq(byte[] _myid, String _result, byte[] _proof) {
        final Function function = new Function(
                FUNC___CALLBACK, 
                Arrays.<Type>asList(new Bytes32(_myid),
                new Utf8String(_result),
                new DynamicBytes(_proof)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple3<byte[], String, byte[]> get__callbackBytes32StringBytesInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC___CALLBACK, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<DynamicBytes>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple3<byte[], String, byte[]>(

                (byte[]) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (byte[]) results.get(2).getValue()
                );
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

    public RemoteCall<String> temperature() {
        final Function function = new Function(FUNC_TEMPERATURE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<byte[]> id() {
        final Function function = new Function(FUNC_ID, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public List<LogNewProvableQueryEventResponse> getLogNewProvableQueryEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGNEWPROVABLEQUERY_EVENT, transactionReceipt);
        ArrayList<LogNewProvableQueryEventResponse> responses = new ArrayList<LogNewProvableQueryEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogNewProvableQueryEventResponse typedResponse = new LogNewProvableQueryEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.description = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerLogNewProvableQueryEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOGNEWPROVABLEQUERY_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerLogNewProvableQueryEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOGNEWPROVABLEQUERY_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<LogNewTemperatureMeasureEventResponse> getLogNewTemperatureMeasureEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOGNEWTEMPERATUREMEASURE_EVENT, transactionReceipt);
        ArrayList<LogNewTemperatureMeasureEventResponse> responses = new ArrayList<LogNewTemperatureMeasureEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            LogNewTemperatureMeasureEventResponse typedResponse = new LogNewTemperatureMeasureEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.temperature = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerLogNewTemperatureMeasureEventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOGNEWTEMPERATUREMEASURE_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerLogNewTemperatureMeasureEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOGNEWTEMPERATUREMEASURE_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    @Deprecated
    public static TemperatureOracle load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TemperatureOracle(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static TemperatureOracle load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TemperatureOracle(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static TemperatureOracle load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new TemperatureOracle(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static TemperatureOracle load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new TemperatureOracle(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<TemperatureOracle> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TemperatureOracle.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<TemperatureOracle> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(TemperatureOracle.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TemperatureOracle> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TemperatureOracle.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<TemperatureOracle> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TemperatureOracle.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class LogNewProvableQueryEventResponse {
        public Log log;

        public String description;
    }

    public static class LogNewTemperatureMeasureEventResponse {
        public Log log;

        public String temperature;
    }
}
