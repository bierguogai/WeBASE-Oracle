package com.webank.oracle.test.temp;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.abi.datatypes.Bool;
import org.fisco.bcos.web3j.abi.datatypes.DynamicArray;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.Utf8String;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple1;
import org.fisco.bcos.web3j.tuples.generated.Tuple10;
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
public class OracleRegisterCenter extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550611ce9806100606000396000f3fe608060405234801561001057600080fd5b506004361061009e5760003560e01c8063a65436c211610066578063a65436c214610342578063a6a64047146103a1578063b1662ad6146103bf578063c47f9c5a1461056a578063d80ce1ac146107235761009e565b806316cad12a146100a357806327c5c4d8146100e757806334d30ad3146101435780635089e2c8146102ee57806384052a5e14610338575b600080fd5b6100e5600480360360208110156100b957600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610791565b005b610129600480360360208110156100fd57600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610912565b604051808215151515815260200191505060405180910390f35b6102d46004803603608081101561015957600080fd5b8101908080604001906002806020026040519081016040528092919082600260200280828437600081840152601f19601f8201169050808301925050505050509192919290803590602001906401000000008111156101b757600080fd5b8201836020820111156101c957600080fd5b803590602001918460018302840111640100000000831117156101eb57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561024e57600080fd5b82018360208201111561026057600080fd5b8035906020019184600183028401116401000000008311171561028257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506109c3565b604051808215151515815260200191505060405180910390f35b6102f661102d565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b610340611052565b005b61034a61121a565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b8381101561038d578082015181840152602081019050610372565b505050509050019250505060405180910390f35b6103a96112a8565b6040518082815260200191505060405180910390f35b610550600480360360808110156103d557600080fd5b81019080803590602001906401000000008111156103f257600080fd5b82018360208201111561040457600080fd5b8035906020019184600183028401116401000000008311171561042657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192908035906020019064010000000081111561048957600080fd5b82018360208201111561049b57600080fd5b803590602001918460018302840111640100000000831117156104bd57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f82011690508083019250505050505050919291929080604001906002806020026040519081016040528092919082600260200280828437600081840152601f19601f82011690508083019250505050505091929192905050506112b5565b604051808215151515815260200191505060405180910390f35b6105ac6004803603602081101561058057600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff16906020019092919050505061170a565b604051808b81526020018a73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200189600260200280838360005b8381101561060f5780820151818401526020810190506105f4565b50505050905001888152602001806020018060200187815260200186815260200185151515158152602001848152602001838103835289818151815260200191508051906020019080838360005b8381101561067857808201518184015260208101905061065d565b50505050905090810190601f1680156106a55780820380516001836020036101000a031916815260200191505b50838103825288818151815260200191508051906020019080838360005b838110156106de5780820151818401526020810190506106c3565b50505050905090810190601f16801561070b5780820380516001836020036101000a031916815260200191505b509c5050505050505050505050505060405180910390f35b61074f6004803603602081101561073957600080fd5b8101908080359060200190929190505050611a72565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610853576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4f6e6c792063616c6c61626c65206279206f776e65720000000000000000000081525060200191505060405180910390fd5b80600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508073ffffffffffffffffffffffffffffffffffffffff166000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff167f21743d91d76b657487b4f3aafe9bb0582564de06fec24f57ca7780d2ddd0342c60405160405180910390a350565b600080600380549050141561092a57600090506109be565b8173ffffffffffffffffffffffffffffffffffffffff16600260008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161490505b919050565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610a87576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4f6e6c792063616c6c61626c65206279206f776e65720000000000000000000081525060200191505060405180910390fd5b6000600260003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002090503373ffffffffffffffffffffffffffffffffffffffff168160010160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614610b8f576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f4f7261636c652073657276696365206e6f74206578697374732e00000000000081525060200191505060405180910390fd5b610b97611b38565b81600201600280602002604051908101604052809291908260028015610bd2576020028201915b815481526020019060010190808311610bbe575b505050505090506060826005018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610c735780601f10610c4857610100808354040283529160200191610c73565b820191906000526020600020905b815481529060010190602001808311610c5657829003601f168201915b505050505090506060836006018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610d145780601f10610ce957610100808354040283529160200191610d14565b820191906000526020600020905b815481529060010190602001808311610cf757829003601f168201915b505050505090506000846004015490508885600201906002610d37929190611b5a565b5087856005019080519060200190610d50929190611b9a565b5086856006019080519060200190610d69929190611b9a565b50886040516020018082600260200280838360005b83811015610d99578082015181840152602081019050610d7e565b5050505090500191505060405160208183030381529060405280519060200120856004018190555084600001547fe61d8880be74f41c359cf3917c82bb03711c4523e8235298883332485fd634cb85838c8960040154888e898f6040518089600260200280838360005b83811015610e1e578082015181840152602081019050610e03565b5050505090500188815260200187600260200280838360005b83811015610e52578082015181840152602081019050610e37565b5050505090500186815260200180602001806020018060200180602001858103855289818151815260200191508051906020019080838360005b83811015610ea7578082015181840152602081019050610e8c565b50505050905090810190601f168015610ed45780820380516001836020036101000a031916815260200191505b50858103845288818151815260200191508051906020019080838360005b83811015610f0d578082015181840152602081019050610ef2565b50505050905090810190601f168015610f3a5780820380516001836020036101000a031916815260200191505b50858103835287818151815260200191508051906020019080838360005b83811015610f73578082015181840152602081019050610f58565b50505050905090810190601f168015610fa05780820380516001836020036101000a03191681526020019150","5b50858103825286818151815260200191508051906020019080838360005b83811015610fd9578082015181840152602081019050610fbe565b50505050905090810190601f1680156110065780820380516001836020036101000a031916815260200191505b509c5050505050505050505050505060405180910390a26001955050505050509392505050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b600160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614611115576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260168152602001807f4d7573742062652070726f706f736564206f776e65720000000000000000000081525060200191505060405180910390fd5b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050336000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055506000600160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055503373ffffffffffffffffffffffffffffffffffffffff168173ffffffffffffffffffffffffffffffffffffffff167f5c7c30d4a0f08950cb23be4132957b357fa5dfdb0fcf218f81b86a1c036e47d060405160405180910390a350565b6060600380548060200260200160405190810160405280929190818152602001828054801561129e57602002820191906000526020600020905b8160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019060010190808311611254575b5050505050905090565b6000600380549050905090565b60006112c033610912565b15611333576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252601c8152602001807f4f7261636c65536572766963652068617320726567697374657264210000000081525060200191505060405180910390fd5b60003390506003819080600181540180825580915050600190039060005260206000200160009091909190916101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550600060016003805490500390506000846040516020018082600260200280838360005b838110156113d95780820151818401526020810190506113be565b50505050905001915050604051602081830303815290604052805190602001209050611403611c1a565b6040518061014001604052808481526020018573ffffffffffffffffffffffffffffffffffffffff168152602001878152602001838152602001898152602001888152602001428152602001600081526020016001151581526020016000815250905080600260008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000820151816000015560208201518160010160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff16021790555060408201518160020190600261150e929190611b5a565b50606082015181600401556080820151816005019080519060200190611535929190611b9a565b5060a0820151816006019080519060200190611552929190611b9a565b5060c0820151816007015560e082015181600801556101008201518160090160006101000a81548160ff02191690831515021790555061012082015181600a01559050508373ffffffffffffffffffffffffffffffffffffffff16837f5da68fab04a10103e6bb33d03511100ec1822fd70f9920242a44701d67027cc688858c8c8760c001516040518086600260200280838360005b838110156116035780820151818401526020810190506115e8565b505050509050018581526020018060200180602001848152602001838103835286818151815260200191508051906020019080838360005b8381101561165657808201518184015260208101905061163b565b50505050905090810190601f1680156116835780820380516001836020036101000a031916815260200191505b50838103825285818151815260200191508051906020019080838360005b838110156116bc5780820151818401526020810190506116a1565b50505050905090810190601f1680156116e95780820380516001836020036101000a031916815260200191505b5097505050505050505060405180910390a360019450505050509392505050565b600080611715611b38565b60006060806000806000806117298b610912565b61179b576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f4f7261636c652073657276696365206e6f74206578697374732e00000000000081525060200191505060405180910390fd5b6117a3611c1a565b600260008d73ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060405180610140016040529081600082015481526020016001820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016002820160028060200260405190810160405280929190826002801561188a576020028201915b815481526020019060010190808311611876575b5050505050815260200160048201548152602001600582018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156119365780601f1061190b57610100808354040283529160200191611936565b820191906000526020600020905b81548152906001019060200180831161191957829003601f168201915b50505050508152602001600682018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156119d85780601f106119ad576101008083540402835291602001916119d8565b820191906000526020600020905b8154815290600101906020018083116119bb57829003601f168201915b5050505050815260200160078201548152602001600882015481526020016009820160009054906101000a900460ff16151515158152602001600a820154815250509050806000015181602001518260400151836060015184608001518560a001518660c001518760e001518861010001518961012001519a509a509a509a509a509a509a509a509a509a50509193959799509193959799565b600080821080611a8757506003805490508210155b611af9576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252600f8152602001807f696e6465782069732077726f6e6721000000000000000000000000000000000081525060200191505060405180910390fd5b60038281548110611b0657fe5b9060005260206000200160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff169050919050565b6040518060400160405280600290602082028036833780820191505090505090565b8260028101928215611b89579160200282015b82811115611b88578251825591602001919060010190611b6d565b5b509050611b969190611c8e565b5090565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10611bdb57805160ff1916838001178555611c09565b82800160010185558215611c09579182015b82811115611c08578251825591602001919060010190611bed565b5b509050611c169190611c8e565b5090565b60405180610140016040528060008152602001600073ffffffffffffffffffffffffffffffffffffffff168152602001611c52611b38565b81526020016000801916815260200160608152602001606081526020016000815260200160008152602001600015158152602001600081525090565b611cb091905b80821115611cac576000816000905550600101611c94565b5090565b9056fea2646970667358221220f174d51b80314d844f6f6408e9220ad35154a7925b9e5d1afac1c7711f0ad14164736f6c634300060a0033"};

    public static final String BINARY = String.join("", BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"oracleServiceAddress\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256[2]\",\"name\":\"publicKey\",\"type\":\"uint256[2]\"},{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"keyhash\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"operator\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"url\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"createTime\",\"type\":\"uint256\"}],\"name\":\"LogNewOracleService\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256[2]\",\"name\":\"oldPublicKey\",\"type\":\"uint256[2]\"},{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"oldKeyhash\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"uint256[2]\",\"name\":\"publicKey\",\"type\":\"uint256[2]\"},{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"keyhash\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"oldOperator\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"operator\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"oldUrl\",\"type\":\"string\"},{\"indexed\":false,\"internalType\":\"string\",\"name\":\"url\",\"type\":\"string\"}],\"name\":\"LogUpdateOracleService\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"}],\"name\":\"OwnershipTransferRequested\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":true,\"internalType\":\"address\",\"name\":\"from\",\"type\":\"address\"},{\"indexed\":true,\"internalType\":\"address\",\"name\":\"to\",\"type\":\"address\"}],\"name\":\"OwnershipTransferred\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"acceptOwnership\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getAllOracleServiceInfo\",\"outputs\":[{\"internalType\":\"address[]\",\"name\":\"serviceList\",\"type\":\"address[]\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256\",\"name\":\"index\",\"type\":\"uint256\"}],\"name\":\"getOracleServiceAtIndex\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"oracleAddress\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"getOracleServiceCount\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"count\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"oracleAddress\",\"type\":\"address\"}],\"name\":\"getOracleServiceInfo\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"},{\"internalType\":\"uint256[2]\",\"name\":\"\",\"type\":\"uint256[2]\"},{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"\",\"type\":\"string\"},{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"},{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"},{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"oracleAddress\",\"type\":\"address\"}],\"name\":\"isOracleExist\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"exists\",\"type\":\"bool\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"string\",\"name\":\"_operatorInfo\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"_url\",\"type\":\"string\"},{\"internalType\":\"uint256[2]\",\"name\":\"_publicKey\",\"type\":\"uint256[2]\"}],\"name\":\"oracleRegister\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"owner\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"\",\"type\":\"address\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"address\",\"name\":\"_to\",\"type\":\"address\"}],\"name\":\"transferOwnership\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256[2]\",\"name\":\"_publicKey\",\"type\":\"uint256[2]\"},{\"internalType\":\"string\",\"name\":\"_operator\",\"type\":\"string\"},{\"internalType\":\"string\",\"name\":\"_url\",\"type\":\"string\"}],\"name\":\"updateOracleInfo\",\"outputs\":[{\"internalType\":\"bool\",\"name\":\"success\",\"type\":\"bool\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = String.join("", ABI_ARRAY);

    public static final TransactionDecoder transactionDecoder = new TransactionDecoder(ABI, BINARY);

    public static final String FUNC_ACCEPTOWNERSHIP = "acceptOwnership";

    public static final String FUNC_GETALLORACLESERVICEINFO = "getAllOracleServiceInfo";

    public static final String FUNC_GETORACLESERVICEATINDEX = "getOracleServiceAtIndex";

    public static final String FUNC_GETORACLESERVICECOUNT = "getOracleServiceCount";

    public static final String FUNC_GETORACLESERVICEINFO = "getOracleServiceInfo";

    public static final String FUNC_ISORACLEEXIST = "isOracleExist";

    public static final String FUNC_ORACLEREGISTER = "oracleRegister";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_UPDATEORACLEINFO = "updateOracleInfo";

    public static final Event LOGNEWORACLESERVICE_EVENT = new Event("LogNewOracleService", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<StaticArray2<Uint256>>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event LOGUPDATEORACLESERVICE_EVENT = new Event("LogUpdateOracleService", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>(true) {}, new TypeReference<StaticArray2<Uint256>>() {}, new TypeReference<Bytes32>() {}, new TypeReference<StaticArray2<Uint256>>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERREQUESTED_EVENT = new Event("OwnershipTransferRequested", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected OracleRegisterCenter(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(getBinary(), contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected OracleRegisterCenter(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(getBinary(), contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected OracleRegisterCenter(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(getBinary(), contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected OracleRegisterCenter(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(getBinary(), contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static String getBinary() {
        return BINARY;
    }

    public static TransactionDecoder getTransactionDecoder() {
        return transactionDecoder;
    }

    public List<LogNewOracleServiceEventResponse> getLogNewOracleServiceEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOGNEWORACLESERVICE_EVENT, transactionReceipt);
        ArrayList<LogNewOracleServiceEventResponse> responses = new ArrayList<LogNewOracleServiceEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogNewOracleServiceEventResponse typedResponse = new LogNewOracleServiceEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.index = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.oracleServiceAddress = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.publicKey = (List<BigInteger>) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.keyhash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.operator = (String) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.url = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.createTime = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerLogNewOracleServiceEventLogFilter(String fromBlock, String toBlock, List<String> otherTopics, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOGNEWORACLESERVICE_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void registerLogNewOracleServiceEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOGNEWORACLESERVICE_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<LogUpdateOracleServiceEventResponse> getLogUpdateOracleServiceEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOGUPDATEORACLESERVICE_EVENT, transactionReceipt);
        ArrayList<LogUpdateOracleServiceEventResponse> responses = new ArrayList<LogUpdateOracleServiceEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogUpdateOracleServiceEventResponse typedResponse = new LogUpdateOracleServiceEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.index = (BigInteger) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.oldPublicKey = (List<BigInteger>) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.oldKeyhash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.publicKey = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.keyhash = (byte[]) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.oldOperator = (String) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.operator = (String) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.oldUrl = (String) eventValues.getNonIndexedValues().get(6).getValue();
            typedResponse.url = (String) eventValues.getNonIndexedValues().get(7).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerLogUpdateOracleServiceEventLogFilter(String fromBlock, String toBlock, List<String> otherTopics, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOGUPDATEORACLESERVICE_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void registerLogUpdateOracleServiceEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(LOGUPDATEORACLESERVICE_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<OwnershipTransferRequestedEventResponse> getOwnershipTransferRequestedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERREQUESTED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferRequestedEventResponse> responses = new ArrayList<OwnershipTransferRequestedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferRequestedEventResponse typedResponse = new OwnershipTransferRequestedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerOwnershipTransferRequestedEventLogFilter(String fromBlock, String toBlock, List<String> otherTopics, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(OWNERSHIPTRANSFERREQUESTED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void registerOwnershipTransferRequestedEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(OWNERSHIPTRANSFERREQUESTED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerOwnershipTransferredEventLogFilter(String fromBlock, String toBlock, List<String> otherTopics, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void registerOwnershipTransferredEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public RemoteCall<TransactionReceipt> acceptOwnership() {
        final Function function = new Function(
                FUNC_ACCEPTOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void acceptOwnership(TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ACCEPTOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String acceptOwnershipSeq() {
        final Function function = new Function(
                FUNC_ACCEPTOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public RemoteCall<List> getAllOracleServiceInfo() {
        final Function function = new Function(FUNC_GETALLORACLESERVICEINFO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Address>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<String> getOracleServiceAtIndex(BigInteger index) {
        final Function function = new Function(FUNC_GETORACLESERVICEATINDEX, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> getOracleServiceCount() {
        final Function function = new Function(FUNC_GETORACLESERVICECOUNT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple10<BigInteger, String, List<BigInteger>, byte[], String, String, BigInteger, BigInteger, Boolean, BigInteger>> getOracleServiceInfo(String oracleAddress) {
        final Function function = new Function(FUNC_GETORACLESERVICEINFO, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(oracleAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<StaticArray2<Uint256>>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Bool>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple10<BigInteger, String, List<BigInteger>, byte[], String, String, BigInteger, BigInteger, Boolean, BigInteger>>(
                new Callable<Tuple10<BigInteger, String, List<BigInteger>, byte[], String, String, BigInteger, BigInteger, Boolean, BigInteger>>() {
                    @Override
                    public Tuple10<BigInteger, String, List<BigInteger>, byte[], String, String, BigInteger, BigInteger, Boolean, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple10<BigInteger, String, List<BigInteger>, byte[], String, String, BigInteger, BigInteger, Boolean, BigInteger>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                convertToNative((List<Uint256>) results.get(2).getValue()), 
                                (byte[]) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (String) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (Boolean) results.get(8).getValue(), 
                                (BigInteger) results.get(9).getValue());
                    }
                });
    }

    public RemoteCall<Boolean> isOracleExist(String oracleAddress) {
        final Function function = new Function(FUNC_ISORACLEEXIST, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(oracleAddress)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<TransactionReceipt> oracleRegister(String _operatorInfo, String _url, List<BigInteger> _publicKey) {
        final Function function = new Function(
                FUNC_ORACLEREGISTER, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_operatorInfo), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_url), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(_publicKey, org.fisco.bcos.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void oracleRegister(String _operatorInfo, String _url, List<BigInteger> _publicKey, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_ORACLEREGISTER, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_operatorInfo), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_url), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(_publicKey, org.fisco.bcos.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String oracleRegisterSeq(String _operatorInfo, String _url, List<BigInteger> _publicKey) {
        final Function function = new Function(
                FUNC_ORACLEREGISTER, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_operatorInfo), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_url), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(_publicKey, org.fisco.bcos.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple3<String, String, List<BigInteger>> getOracleRegisterInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_ORACLEREGISTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<StaticArray2<Uint256>>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple3<String, String, List<BigInteger>>(

                (String) results.get(0).getValue(), 
                (String) results.get(1).getValue(), 
                convertToNative((List<Uint256>) results.get(2).getValue())
                );
    }

    public Tuple1<Boolean> getOracleRegisterOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_ORACLEREGISTER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
                );
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> transferOwnership(String _to) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_to)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void transferOwnership(String _to, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_to)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String transferOwnershipSeq(String _to) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.Address(_to)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<String> getTransferOwnershipInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<String>(

                (String) results.get(0).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> updateOracleInfo(List<BigInteger> _publicKey, String _operator, String _url) {
        final Function function = new Function(
                FUNC_UPDATEORACLEINFO, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(_publicKey, org.fisco.bcos.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_operator), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_url)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void updateOracleInfo(List<BigInteger> _publicKey, String _operator, String _url, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_UPDATEORACLEINFO, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(_publicKey, org.fisco.bcos.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_operator), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_url)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String updateOracleInfoSeq(List<BigInteger> _publicKey, String _operator, String _url) {
        final Function function = new Function(
                FUNC_UPDATEORACLEINFO, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(_publicKey, org.fisco.bcos.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_operator), 
                new org.fisco.bcos.web3j.abi.datatypes.Utf8String(_url)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple3<List<BigInteger>, String, String> getUpdateOracleInfoInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_UPDATEORACLEINFO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray2<Uint256>>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple3<List<BigInteger>, String, String>(

                convertToNative((List<Uint256>) results.get(0).getValue()), 
                (String) results.get(1).getValue(), 
                (String) results.get(2).getValue()
                );
    }

    public Tuple1<Boolean> getUpdateOracleInfoOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_UPDATEORACLEINFO, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<Boolean>(

                (Boolean) results.get(0).getValue()
                );
    }

    @Deprecated
    public static OracleRegisterCenter load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new OracleRegisterCenter(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static OracleRegisterCenter load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new OracleRegisterCenter(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static OracleRegisterCenter load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new OracleRegisterCenter(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static OracleRegisterCenter load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new OracleRegisterCenter(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<OracleRegisterCenter> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(OracleRegisterCenter.class, web3j, credentials, contractGasProvider, getBinary(), "");
    }

    @Deprecated
    public static RemoteCall<OracleRegisterCenter> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(OracleRegisterCenter.class, web3j, credentials, gasPrice, gasLimit, getBinary(), "");
    }

    public static RemoteCall<OracleRegisterCenter> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(OracleRegisterCenter.class, web3j, transactionManager, contractGasProvider, getBinary(), "");
    }

    @Deprecated
    public static RemoteCall<OracleRegisterCenter> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(OracleRegisterCenter.class, web3j, transactionManager, gasPrice, gasLimit, getBinary(), "");
    }

    public static class LogNewOracleServiceEventResponse {
        public Log log;

        public BigInteger index;

        public String oracleServiceAddress;

        public List<BigInteger> publicKey;

        public byte[] keyhash;

        public String operator;

        public String url;

        public BigInteger createTime;
    }

    public static class LogUpdateOracleServiceEventResponse {
        public Log log;

        public BigInteger index;

        public List<BigInteger> oldPublicKey;

        public byte[] oldKeyhash;

        public List<BigInteger> publicKey;

        public byte[] keyhash;

        public String oldOperator;

        public String operator;

        public String oldUrl;

        public String url;
    }

    public static class OwnershipTransferRequestedEventResponse {
        public Log log;

        public String from;

        public String to;
    }

    public static class OwnershipTransferredEventResponse {
        public Log log;

        public String from;

        public String to;
    }
}
