package com.webank.oracle.test.oracle.old;

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
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes1;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple1;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.tuples.generated.Tuple4;
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
public class OraclizeCore extends Contract {
    public static String BINARY = "60806040526404a817c80060035534801561001957600080fd5b5033600260006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555033600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506110dd806100ab6000396000f3006080604052600436106100af576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806323dc42e7146101745780634536297814610249578063480a434d146103145780635c242c591461033f5780636c0f7ee71461041e57806381ade307146104c75780639bb5148714610592578063a2ec191a146105d5578063adf59f9914610648578063b5bfdd731461071d578063c281d19e146107bc575b3480156100bb57600080fd5b50600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141580156101685750600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614155b1561017257600080fd5b005b34801561018057600080fd5b5061022b60048036038101908080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610813565b60405180826000191660001916815260200191505060405180910390f35b34801561025557600080fd5b506102f6600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061082d565b60405180826000191660001916815260200191505060405180910390f35b34801561032057600080fd5b50610329610847565b6040518082815260200191505060405180910390f35b34801561034b57600080fd5b5061040060048036038101908080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035906020019092919050505061084d565b60405180826000191660001916815260200191505060405180910390f35b34801561042a57600080fd5b506104c56004803603810190808035906020019082018035906020019080806020026020016040519081016040528093929190818152602001838360200280828437820191505050505050919291929080359060200190820180359060200190808060200260200160405190810160405280939291908181526020018383602002808284378201915050505050509192919290505050610bdf565b005b3480156104d357600080fd5b50610574600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610d50565b60405180826000191660001916815260200191505060405180910390f35b34801561059e57600080fd5b506105d3600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610d6a565b005b3480156105e157600080fd5b50610646600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080359060200190929190505050610e2b565b005b34801561065457600080fd5b506106ff60048036038101908080359060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610e5d565b60405180826000191660001916815260200191505060405180910390f35b34801561072957600080fd5b506107ba600480360381019080803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929080357effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1916906020019092919080359060200190929190505050610e77565b005b3480156107c857600080fd5b506107d161103a565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b600061082484848462030d4061084d565b90509392505050565b600061083f6000848462030d4061084d565b905092915050565b60065481565b600030336000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054604051808473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff166c010000000000000000000000000281526014018281526020019350505050604051809103902090506000803373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600081548092919060010191905055507fb76d0edd90c6a07aa3ff7a222d7f5933e29c6acc660c059c97837f05c4ca1a84338287878787600460003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a90047f010000000000000000000000000000000000000000000000000000000000000002600560003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054604051808973ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200188600019166000191681526020018781526020018060200180602001868152602001857effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff19168152602001848152602001838103835288818151815260200191508051906020019080838360005b83811015610b2c578082015181840152602081019050610b11565b50505050905090810190601f168015610b595780820380516001836020036101000a031916815260200191505b50838103825287818151815260200191508051906020019080838360005b83811015610b92578082015181840152602081019050610b77565b50505050905090810190601f168015610bbf5780820380516001836020036101000a031916815260200191505b509a505050505050505050505060405180910390a1809050949350505050565b6000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614158015610c8d5750600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614155b15610c9757600080fd5b600090505b8251811015610d4b578281815181101515610cb357fe5b906020019060200201516009808054809190600101610cd29190611060565b815481101515610cde57fe5b9060005260206000200181600019169055508181815181101515610cfe57fe5b90602001906020020151600860008584815181101515610d1a57fe5b9060200190602002015160001916600019168152602001908152602001600020819055508080600101915050610c9c565b505050565b6000610d626000848462030d4061084d565b905092915050565b600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16141580610ddd575060008173ffffffffffffffffffffffffffffffffffffffff16145b15610de757600080fd5b80600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555050565b610e598260007f01000000000000000000000000000000000000000000000000000000000000000283610e77565b5050565b6000610e6e84848462030d4061084d565b90509392505050565b6000600260009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614158015610f255750600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614155b15610f2f57600080fd5b83836040518083805190602001908083835b602083101515610f665780518252602082019150602081019050602083039250610f41565b6001836020036101000a038019825116818451168082178552505050505050905001827effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff19167effffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff191681526001019250505060405180910390209050806009808054809190600101610ff69190611060565b81548110151561100257fe5b906000526020600020018160001916905550816008600083600019166000191681526020019081526020016000208190555050505050565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b81548183558181111561108757818360005260206000209182019101611086919061108c565b5b505050565b6110ae91905b808211156110aa576000816000905550600101611092565b5090565b905600a165627a7a723058205f16255132764980c61634c9954f95e9af317eb1e774f2802dabf6f15c27ef0a0029";

    public static final String ABI = "[{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_timestamp\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"_datasource\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_arg\",\"type\":\"string\"}],\"name\":\"query1\",\"outputs\":[{\"indexed\":false,\"name\":\"_id\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_datasource\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_arg\",\"type\":\"string\"}],\"name\":\"query1\",\"outputs\":[{\"indexed\":false,\"name\":\"_id\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"baseprice\",\"outputs\":[{\"indexed\":false,\"name\":\"\",\"type\":\"uint256\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_timestamp\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"_datasource\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_arg\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_gaslimit\",\"type\":\"uint256\"}],\"name\":\"query1\",\"outputs\":[{\"indexed\":false,\"name\":\"_id\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"dsHash\",\"type\":\"bytes32[]\"},{\"indexed\":false,\"name\":\"multiplier\",\"type\":\"uint256[]\"}],\"name\":\"multiAddDSource\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_datasource\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_arg\",\"type\":\"string\"}],\"name\":\"query\",\"outputs\":[{\"indexed\":false,\"name\":\"_id\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"newCbaddress\",\"type\":\"address\"}],\"name\":\"setCBaddress\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"dsname\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"multiplier\",\"type\":\"uint256\"}],\"name\":\"addDSource\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"_timestamp\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"_datasource\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"_arg\",\"type\":\"string\"}],\"name\":\"query\",\"outputs\":[{\"indexed\":false,\"name\":\"_id\",\"type\":\"bytes32\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"dsname\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"proofType\",\"type\":\"bytes1\"},{\"indexed\":false,\"name\":\"multiplier\",\"type\":\"uint256\"}],\"name\":\"addDSource\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[],\"name\":\"cbAddress\",\"outputs\":[{\"indexed\":false,\"name\":\"\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"constructor\"},{\"constant\":false,\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"fallback\"},{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"sender\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"cid\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"timestamp\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"datasource\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"arg\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"gaslimit\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"proofType\",\"type\":\"bytes1\"},{\"indexed\":false,\"name\":\"gasPrice\",\"type\":\"uint256\"}],\"name\":\"Log1\",\"payable\":false,\"type\":\"event\"},{\"constant\":false,\"inputs\":[{\"indexed\":false,\"name\":\"sender\",\"type\":\"address\"},{\"indexed\":false,\"name\":\"cid\",\"type\":\"bytes32\"},{\"indexed\":false,\"name\":\"timestamp\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"datasource\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"arg1\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"arg2\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"gaslimit\",\"type\":\"uint256\"},{\"indexed\":false,\"name\":\"proofType\",\"type\":\"bytes1\"},{\"indexed\":false,\"name\":\"gasPrice\",\"type\":\"uint256\"}],\"name\":\"Log2\",\"payable\":false,\"type\":\"event\"}]";

    public static final TransactionDecoder transactionDecoder = new TransactionDecoder(ABI, BINARY);

    public static final String FUNC_QUERY1 = "query1";

    public static final String FUNC_BASEPRICE = "baseprice";

    public static final String FUNC_MULTIADDDSOURCE = "multiAddDSource";

    public static final String FUNC_QUERY = "query";

    public static final String FUNC_SETCBADDRESS = "setCBaddress";

    public static final String FUNC_ADDDSOURCE = "addDSource";

    public static final String FUNC_CBADDRESS = "cbAddress";

    public static final Event LOG1_EVENT = new Event("Log1", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes1>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOG2_EVENT = new Event("Log2", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bytes1>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected OraclizeCore(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected OraclizeCore(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected OraclizeCore(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected OraclizeCore(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static TransactionDecoder getTransactionDecoder() {
        return transactionDecoder;
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

    public Tuple3<BigInteger, String, String> getQuery1Uint256StringStringInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_QUERY1, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple3<BigInteger, String, String>(

                (BigInteger) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue()
                );
    }

//    public Tuple1<byte[]> getQuery1Bytes32Output(TransactionReceipt transactionReceipt) {
//        String data = transactionReceipt.getOutput();
//        final Function function = new Function(FUNC_QUERY1,
//                Arrays.<Type>asList(),
//                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
//        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
//        return new Tuple1<byte[]>(
//
//                (byte[]) results.get(0).getValue()
//                );
//    }

    public RemoteCall<TransactionReceipt> query1(String _datasource, String _arg) {
        final Function function = new Function(
                FUNC_QUERY1, 
                Arrays.<Type>asList(new Utf8String(_datasource),
                new Utf8String(_arg)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void query1(String _datasource, String _arg, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_QUERY1, 
                Arrays.<Type>asList(new Utf8String(_datasource),
                new Utf8String(_arg)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String query1Seq(String _datasource, String _arg) {
        final Function function = new Function(
                FUNC_QUERY1, 
                Arrays.<Type>asList(new Utf8String(_datasource),
                new Utf8String(_arg)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, String> getQuery1StringStringInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_QUERY1, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

//    public Tuple1<byte[]> getQuery1Bytes32Output(TransactionReceipt transactionReceipt) {
//        String data = transactionReceipt.getOutput();
//        final Function function = new Function(FUNC_QUERY1,
//                Arrays.<Type>asList(),
//                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
//        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
//        return new Tuple1<byte[]>(
//
//                (byte[]) results.get(0).getValue()
//                );
//    }

    public RemoteCall<BigInteger> baseprice() {
        final Function function = new Function(FUNC_BASEPRICE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> query1(BigInteger _timestamp, String _datasource, String _arg, BigInteger _gaslimit) {
        final Function function = new Function(
                FUNC_QUERY1, 
                Arrays.<Type>asList(new Uint256(_timestamp),
                new Utf8String(_datasource),
                new Utf8String(_arg),
                new Uint256(_gaslimit)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void query1(BigInteger _timestamp, String _datasource, String _arg, BigInteger _gaslimit, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_QUERY1, 
                Arrays.<Type>asList(new Uint256(_timestamp),
                new Utf8String(_datasource),
                new Utf8String(_arg),
                new Uint256(_gaslimit)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String query1Seq(BigInteger _timestamp, String _datasource, String _arg, BigInteger _gaslimit) {
        final Function function = new Function(
                FUNC_QUERY1, 
                Arrays.<Type>asList(new Uint256(_timestamp),
                new Utf8String(_datasource),
                new Utf8String(_arg),
                new Uint256(_gaslimit)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple4<BigInteger, String, String, BigInteger> getQuery1Uint256StringStringUint256Input(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_QUERY1, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple4<BigInteger, String, String, BigInteger>(

                (BigInteger) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue(), 
                (BigInteger) results.get(3).getValue()
                );
    }

    public Tuple1<byte[]> getQuery1Bytes32Output(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_QUERY1, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> multiAddDSource(List<byte[]> dsHash, List<BigInteger> multiplier) {
        final Function function = new Function(
                FUNC_MULTIADDDSOURCE, 
                Arrays.<Type>asList(dsHash.isEmpty()? DynamicArray.empty("bytes32[]"):new DynamicArray<Bytes32>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(dsHash, Bytes32.class)),
                multiplier.isEmpty()? DynamicArray.empty("uint256[]"):new DynamicArray<Uint256>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(multiplier, Uint256.class))),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void multiAddDSource(List<byte[]> dsHash, List<BigInteger> multiplier, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_MULTIADDDSOURCE, 
                Arrays.<Type>asList(dsHash.isEmpty()? DynamicArray.empty("bytes32[]"):new DynamicArray<Bytes32>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(dsHash, Bytes32.class)),
                multiplier.isEmpty()? DynamicArray.empty("uint256[]"):new DynamicArray<Uint256>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(multiplier, Uint256.class))),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String multiAddDSourceSeq(List<byte[]> dsHash, List<BigInteger> multiplier) {
        final Function function = new Function(
                FUNC_MULTIADDDSOURCE, 
                Arrays.<Type>asList(dsHash.isEmpty()? DynamicArray.empty("bytes32[]"):new DynamicArray<Bytes32>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(dsHash, Bytes32.class)),
                multiplier.isEmpty()? DynamicArray.empty("uint256[]"):new DynamicArray<Uint256>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(multiplier, Uint256.class))),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<List<byte[]>, List<BigInteger>> getMultiAddDSourceInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_MULTIADDDSOURCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Bytes32>>() {}, new TypeReference<DynamicArray<Uint256>>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<List<byte[]>, List<BigInteger>>(

                convertToNative((List<Bytes32>) results.get(0).getValue()), 
                convertToNative((List<Uint256>) results.get(1).getValue())
                );
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

    public Tuple2<String, String> getQueryStringStringInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_QUERY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue()
                );
    }

    public Tuple1<byte[]> getQueryBytes32Output(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_QUERY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
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

    public Tuple1<String> getSetCBaddressInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SETCBADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> addDSource(String dsname, BigInteger multiplier) {
        final Function function = new Function(
                FUNC_ADDDSOURCE, 
                Arrays.<Type>asList(new Utf8String(dsname),
                new Uint256(multiplier)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void addDSource(String dsname, BigInteger multiplier, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ADDDSOURCE, 
                Arrays.<Type>asList(new Utf8String(dsname),
                new Uint256(multiplier)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String addDSourceSeq(String dsname, BigInteger multiplier) {
        final Function function = new Function(
                FUNC_ADDDSOURCE, 
                Arrays.<Type>asList(new Utf8String(dsname),
                new Uint256(multiplier)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple2<String, BigInteger> getAddDSourceStringUint256Input(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ADDDSOURCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple2<String, BigInteger>(

                (String) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue()
                );
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

    public Tuple3<BigInteger, String, String> getQueryUint256StringStringInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_QUERY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple3<BigInteger, String, String>(

                (BigInteger) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue()
                );
    }

//    public Tuple1<byte[]> getQueryBytes32Output(TransactionReceipt transactionReceipt) {
//        String data = transactionReceipt.getOutput();
//        final Function function = new Function(FUNC_QUERY,
//                Arrays.<Type>asList(),
//                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
//        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
//        return new Tuple1<byte[]>(
//
//                (byte[]) results.get(0).getValue()
//                );
//    }

    public RemoteCall<TransactionReceipt> addDSource(String dsname, byte[] proofType, BigInteger multiplier) {
        final Function function = new Function(
                FUNC_ADDDSOURCE, 
                Arrays.<Type>asList(new Utf8String(dsname),
                new Bytes1(proofType),
                new Uint256(multiplier)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void addDSource(String dsname, byte[] proofType, BigInteger multiplier, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ADDDSOURCE, 
                Arrays.<Type>asList(new Utf8String(dsname),
                new Bytes1(proofType),
                new Uint256(multiplier)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String addDSourceSeq(String dsname, byte[] proofType, BigInteger multiplier) {
        final Function function = new Function(
                FUNC_ADDDSOURCE, 
                Arrays.<Type>asList(new Utf8String(dsname),
                new Bytes1(proofType),
                new Uint256(multiplier)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple3<String, byte[], BigInteger> getAddDSourceStringBytes1Uint256Input(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ADDDSOURCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bytes1>() {}, new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple3<String, byte[], BigInteger>(

                (String) results.get(0).getValue(), 
                (byte[]) results.get(1).getValue(), 
                (BigInteger) results.get(2).getValue()
                );
    }

    public RemoteCall<String> cbAddress() {
        final Function function = new Function(FUNC_CBADDRESS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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
            typedResponse.gaslimit = (BigInteger) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.proofType = (byte[]) eventValues.getNonIndexedValues().get(6).getValue();
            typedResponse.gasPrice = (BigInteger) eventValues.getNonIndexedValues().get(7).getValue();
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

    public List<Log2EventResponse> getLog2Events(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(LOG2_EVENT, transactionReceipt);
        ArrayList<Log2EventResponse> responses = new ArrayList<Log2EventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
            Log2EventResponse typedResponse = new Log2EventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.cid = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.datasource = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.arg1 = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.arg2 = (String) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.gaslimit = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
            typedResponse.proofType = (byte[]) eventValues.getNonIndexedValues().get(7).getValue();
            typedResponse.gasPrice = (BigInteger) eventValues.getNonIndexedValues().get(8).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerLog2EventLogFilter(String fromBlock, String toBlock, List<String> otherTopcs, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOG2_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopcs,callback);
    }

    public void registerLog2EventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOG2_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    @Deprecated
    public static OraclizeCore load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new OraclizeCore(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static OraclizeCore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new OraclizeCore(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static OraclizeCore load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new OraclizeCore(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static OraclizeCore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new OraclizeCore(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<OraclizeCore> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(OraclizeCore.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<OraclizeCore> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(OraclizeCore.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<OraclizeCore> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(OraclizeCore.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<OraclizeCore> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(OraclizeCore.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class Log1EventResponse {
        public Log log;

        public String sender;

        public byte[] cid;

        public BigInteger timestamp;

        public String datasource;

        public String arg;

        public BigInteger gaslimit;

        public byte[] proofType;

        public BigInteger gasPrice;
    }

    public static class Log2EventResponse {
        public Log log;

        public String sender;

        public byte[] cid;

        public BigInteger timestamp;

        public String datasource;

        public String arg1;

        public String arg2;

        public BigInteger gaslimit;

        public byte[] proofType;

        public BigInteger gasPrice;
    }
}
