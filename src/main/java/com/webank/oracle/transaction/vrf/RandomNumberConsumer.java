package com.webank.oracle.transaction.vrf;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
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
public class RandomNumberConsumer extends Contract {
    public static String BINARY = "60a060405234801561001057600080fd5b506040516105ec3803806105ec8339818101604052604081101561003357600080fd5b810190808051906020019092919080519060200190929190505050818073ffffffffffffffffffffffffffffffffffffffff1660808173ffffffffffffffffffffffffffffffffffffffff1660601b815250505080600181905550505060805160601c61053a6100b26000398061018652806102c4525061053a6000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c806310835dbb1461005c57806342619f66146100a857806394985ddd146100c65780639e317f12146100fe578063b37217a414610140575b600080fd5b6100926004803603604081101561007257600080fd5b810190808035906020019092919080359060200190929190505050610182565b6040518082815260200191505060405180910390f35b6100b06102bc565b6040518082815260200191505060405180910390f35b6100fc600480360360408110156100dc57600080fd5b8101908080359060200190929190803590602001909291905050506102c2565b005b61012a6004803603602081101561011457600080fd5b8101908080359060200190929190505050610391565b6040518082815260200191505060405180910390f35b61016c6004803603602081101561015657600080fd5b81019080803590602001909291905050506103a9565b6040518082815260200191505060405180910390f35b60007f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff1663ac917f848484306040518463ffffffff1660e01b8152600401808481526020018381526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019350505050600060405180830381600087803b15801561023357600080fd5b505af1158015610247573d6000803e3d6000fd5b50505050600061026b848430600080898152602001908152602001600020546103be565b905061029360016000808781526020019081526020016000205461043890919063ffffffff16565b600080868152602001908152602001600020819055506102b384826104c0565b91505092915050565b60025481565b7f000000000000000000000000000000000000000000000000000000000000000073ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610383576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601f8152602001807f4f6e6c7920565246436f6f7264696e61746f722063616e2066756c66696c6c0081525060200191505060405180910390fd5b61038d82826104f9565b5050565b60006020528060005260406000206000915090505481565b60006103b760015483610182565b9050919050565b600084848484604051602001808581526020018481526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019450505050506040516020818303038152906040528051906020012060001c9050949350505050565b6000808284019050838110156104b6576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601b8152602001807f536166654d6174683a206164646974696f6e206f766572666c6f77000000000081525060200191505060405180910390fd5b8091505092915050565b60008282604051602001808381526020018281526020019250505060405160208183030381529060405280519060200120905092915050565b80600281905550505056fea2646970667358221220183ad7e174d13d79c146c70eb08094aea4028a951d504f1d17056b996d8f4aba64736f6c63430006060033";

    public static final String ABI = "[{\"inputs\": [{\"internalType\": \"address\", \"name\": \"_coordinator\", \"type\": \"address\"}, {\"internalType\": \"bytes32\", \"name\": \"_keyHash\", \"type\": \"bytes32\"}], \"stateMutability\": \"nonpayable\", \"type\": \"constructor\"}, {\"inputs\": [{\"internalType\": \"uint256\", \"name\": \"userProvidedSeed\", \"type\": \"uint256\"}], \"name\": \"getRandomNumber\", \"outputs\": [{\"internalType\": \"bytes32\", \"name\": \"requestId\", \"type\": \"bytes32\"}], \"stateMutability\": \"nonpayable\", \"type\": \"function\"}, {\"inputs\": [{\"internalType\": \"bytes32\", \"name\": \"\", \"type\": \"bytes32\"}], \"name\": \"nonces\", \"outputs\": [{\"internalType\": \"uint256\", \"name\": \"\", \"type\": \"uint256\"}], \"stateMutability\": \"view\", \"type\": \"function\"}, { \"constant\":true, \"inputs\": [], \"name\": \"randomResult\", \"outputs\": [{\"internalType\": \"uint256\", \"name\": \"\", \"type\": \"uint256\"}], \"stateMutability\": \"view\", \"type\": \"function\"}, {\"inputs\": [{\"internalType\": \"bytes32\", \"name\": \"requestId\", \"type\": \"bytes32\"}, {\"internalType\": \"uint256\", \"name\": \"randomness\", \"type\": \"uint256\"}], \"name\": \"rawFulfillRandomness\", \"outputs\": [], \"stateMutability\": \"nonpayable\", \"type\": \"function\"}, {\"inputs\": [{\"internalType\": \"bytes32\", \"name\": \"_keyHash\", \"type\": \"bytes32\"}, {\"internalType\": \"uint256\", \"name\": \"_seed\", \"type\": \"uint256\"}], \"name\": \"requestRandomness\", \"outputs\": [{\"internalType\": \"bytes32\", \"name\": \"requestId\", \"type\": \"bytes32\"}], \"stateMutability\": \"nonpayable\", \"type\": \"function\"}]";

    public static final String FUNC_GETRANDOMNUMBER = "getRandomNumber";

    public static final String FUNC_NONCES = "nonces";

    public static final String FUNC_RANDOMRESULT = "randomResult";

    public static final String FUNC_RAWFULFILLRANDOMNESS = "rawFulfillRandomness";

    public static final String FUNC_REQUESTRANDOMNESS = "requestRandomness";

    @Deprecated
    protected RandomNumberConsumer(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected RandomNumberConsumer(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected RandomNumberConsumer(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected RandomNumberConsumer(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> getRandomNumber(BigInteger userProvidedSeed) {
        final Function function = new Function(
                FUNC_GETRANDOMNUMBER, 
                Arrays.<Type>asList(new Uint256(userProvidedSeed)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void getRandomNumber(BigInteger userProvidedSeed, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_GETRANDOMNUMBER, 
                Arrays.<Type>asList(new Uint256(userProvidedSeed)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getRandomNumberSeq(BigInteger userProvidedSeed) {
        final Function function = new Function(
                FUNC_GETRANDOMNUMBER, 
                Arrays.<Type>asList(new Uint256(userProvidedSeed)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> nonces(byte[] param0) {
        final Function function = new Function(
                FUNC_NONCES, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void nonces(byte[] param0, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_NONCES, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String noncesSeq(byte[] param0) {
        final Function function = new Function(
                FUNC_NONCES, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<BigInteger> randomResult() {
        final Function function = new Function(FUNC_RANDOMRESULT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> rawFulfillRandomness(byte[] requestId, BigInteger randomness) {
        final Function function = new Function(
                FUNC_RAWFULFILLRANDOMNESS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(requestId), 
                new Uint256(randomness)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void rawFulfillRandomness(byte[] requestId, BigInteger randomness, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_RAWFULFILLRANDOMNESS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(requestId), 
                new Uint256(randomness)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String rawFulfillRandomnessSeq(byte[] requestId, BigInteger randomness) {
        final Function function = new Function(
                FUNC_RAWFULFILLRANDOMNESS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(requestId), 
                new Uint256(randomness)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<TransactionReceipt> requestRandomness(byte[] _keyHash, BigInteger _seed) {
        final Function function = new Function(
                FUNC_REQUESTRANDOMNESS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_keyHash), 
                new Uint256(_seed)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void requestRandomness(byte[] _keyHash, BigInteger _seed, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_REQUESTRANDOMNESS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_keyHash), 
                new Uint256(_seed)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String requestRandomnessSeq(byte[] _keyHash, BigInteger _seed) {
        final Function function = new Function(
                FUNC_REQUESTRANDOMNESS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_keyHash), 
                new Uint256(_seed)),
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    @Deprecated
    public static RandomNumberConsumer load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new RandomNumberConsumer(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static RandomNumberConsumer load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new RandomNumberConsumer(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static RandomNumberConsumer load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new RandomNumberConsumer(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static RandomNumberConsumer load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new RandomNumberConsumer(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<RandomNumberConsumer> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _coordinator, byte[] _keyHash) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_coordinator), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_keyHash)));
        return deployRemoteCall(RandomNumberConsumer.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<RandomNumberConsumer> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _coordinator, byte[] _keyHash) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_coordinator), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_keyHash)));
        return deployRemoteCall(RandomNumberConsumer.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<RandomNumberConsumer> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _coordinator, byte[] _keyHash) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_coordinator), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_keyHash)));
        return deployRemoteCall(RandomNumberConsumer.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<RandomNumberConsumer> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _coordinator, byte[] _keyHash) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_coordinator), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_keyHash)));
        return deployRemoteCall(RandomNumberConsumer.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }
}
