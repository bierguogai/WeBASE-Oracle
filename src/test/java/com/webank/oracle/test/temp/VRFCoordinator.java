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
import org.fisco.bcos.web3j.abi.datatypes.DynamicBytes;
import org.fisco.bcos.web3j.abi.datatypes.Event;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.abi.datatypes.Type;
import org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple1;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.tuples.generated.Tuple5;
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
public class VRFCoordinator extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b5061247c806100206000396000f3fe608060405234801561001057600080fd5b506004361061009e5760003560e01c80636355d206116100665780636355d206146102bf578063883053661461037a5780639b64b73914610398578063bca5ea2514610467578063de2ec502146104e05761009e565b80631290c07c146100a357806325140153146100fb5780633a73167f146101195780633f7086bc1461018e57806352796007146102a1575b600080fd5b6100f9600480360360608110156100b957600080fd5b810190808035906020019092919080359060200190929190803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506105af565b005b610103610888565b6040518082815260200191505060405180910390f35b6101456004803603602081101561012f57600080fd5b810190808035906020019092919050505061088e565b604051808373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019250505060405180910390f35b610247600480360360208110156101a457600080fd5b81019080803590602001906401000000008111156101c157600080fd5b8201836020820111156101d357600080fd5b803590602001918460018302840111640100000000831117156101f557600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506108d2565b6040518086815260200185815260200184815260200183600260200280838360005b83811015610284578082015181840152602081019050610269565b505050509050018281526020019550505050505060405180910390f35b6102a9610a6d565b6040518082815260200191505060405180910390f35b610378600480360360208110156102d557600080fd5b81019080803590602001906401000000008111156102f257600080fd5b82018360208201111561030457600080fd5b8035906020019184600183028401116401000000008311171561032657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610a72565b005b610382610b29565b6040518082815260200191505060405180910390f35b610451600480360360208110156103ae57600080fd5b81019080803590602001906401000000008111156103cb57600080fd5b8201836020820111156103dd57600080fd5b803590602001918460018302840111640100000000831117156103ff57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610b2e565b6040518082815260200191505060405180910390f35b6104ca6004803603604081101561047d57600080fd5b8101908080604001906002806020026040519081016040528092919082600260200280828437600081840152601f19601f8201169050808301925050505050509192919290505050610e06565b6040518082815260200191505060405180910390f35b610599600480360360208110156104f657600080fd5b810190808035906020019064010000000081111561051357600080fd5b82018360208201111561052557600080fd5b8035906020019184600183028401116401000000008311171561054757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610e5e565b6040518082815260200191505060405180910390f35b60006001600085815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905060006106128585858561101f565b905060006106208683611099565b9050600073ffffffffffffffffffffffffffffffffffffffff1660008083815260200190815260200160002060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161461068d57fe5b8360008083815260200190815260200160002060000160006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055508143604051602001808381526020018281526020019250505060405160208183030381529060405280519060200120600080838152602001908152602001600020600101819055507f788ec7c181e7cf2573f7bc2c953656bbe26a0dc509ad67ab99a5476529f1bf68868343878560008088815260200190815260200160002060010154604051808781526020018681526020018581526020018473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001838152602001828152602001965050505050505060405180910390a161082c600180600089815260200190815260200160002060008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020546110d290919063ffffffff16565b6001600088815260200190815260200160002060008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002081905550505050505050565b6101a081565b60006020528060005260406000206000915090508060000160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff16908060010154905082565b60008060006108df61238b565b6000806101a0602001905080875114610960576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807f77726f6e672070726f6f66206c656e677468000000000000000000000000000081525060200191505060405180910390fd5b600060208801935060e0880151925081880151905061097e84610e06565b965061098a8784611099565b95506109946123ad565b6000808881526020019081526020016000206040518060400160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200160018201548152505090506000824090506000858260405160200180838152602001828152602001925050506040516020818303038152906040528051906020012060001c90506101a08b52610a5d8b610e5e565b9750505050505091939590929450565b60e081565b6000610a7c6123ad565b600080610a888561115a565b9350935093509350600080838152602001908152602001600020600080820160006101000a81549073ffffffffffffffffffffffffffffffffffffffff021916905560018201600090555050610ae382828560000151611442565b7fddbc44155b0a0c66d2c0f2912cffaf4f6392cf074058f06945ccbd2333e0e6e78282604051808381526020018281526020019250505060405180910390a15050505050565b602081565b60006101a0825114610ba8576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807f77726f6e672070726f6f66206c656e677468000000000000000000000000000081525060200191505060405180910390fd5b610bb061238b565b610bb861238b565b610bc06123e0565b6000610bca61238b565b610bd261238b565b6000888060200190516101a0811015610bea57600080fd5b810190809190826040019190826040019190826060018051906020019092919091908260400191908260400180519060200190929190505050839350809750819850829950839a50849b50859c50869d50505050505050507fa6ee1a1c55599b18693eb2f2004ebf4036c56226ddf2e7d27186609e5e4905b1878787878787876040518088600260200280838360005b83811015610c95578082015181840152602081019050610c7a565b5050505090500187600260200280838360005b83811015610cc3578082015181840152602081019050610ca8565b5050505090500186600360200280838360005b83811015610cf1578082015181840152602081019050610cd6565b505050509050018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200184600260200280838360005b83811015610d51578082015181840152602081019050610d36565b5050505090500183600260200280838360005b83811015610d7f578082015181840152602081019050610d64565b5050505090500182815260200197505050505050505060405180910390a16003866040516020018083815260200182600260200280838360005b83811015610dd4578082015181840152602081019050610db9565b50505050905001925050506040516020818303038152906040528051906020012060001c975050505050505050919050565b6000816040516020018082600260200280838360005b83811015610e37578082015181840152602081019050610e1c565b50505050905001915050604051602081830303815290604052805190602001209050919050565b60006101a0825114610ed8576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807f77726f6e672070726f6f66206c656e677468000000000000000000000000000081525060200191505060405180910390fd5b610ee061238b565b610ee861238b565b610ef06123e0565b6000610efa61238b565b610f0261238b565b6000888060200190516101a0811015610f1a57600080fd5b810190809190826040019190826040019190826060018051906020019092919091908260400191908260400180519060200190929190505050839350809750819850829950839a50849b50859c50869d5050505050505050610fb6878787600060038110610f8457fe5b602002015188600160038110610f9657fe5b602002015189600260038110610fa857fe5b602002015189898989611589565b6003866040516020018083815260200182600260200280838360005b83811015610fed578082015181","840152602081019050610fd2565b50505050905001925050506040516020818303038152906040528051906020012060001c975050505050505050919050565b600084848484604051602001808581526020018481526020018373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020018281526020019450505050506040516020818303038152906040528051906020012060001c9050949350505050565b60008282604051602001808381526020018281526020019250505060405160208183030381529060405280519060200120905092915050565b600080828401905083811015611150576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252601b8152602001807f536166654d6174683a206164646974696f6e206f766572666c6f77000000000081525060200191505060405180910390fd5b8091505092915050565b60006111646123ad565b60008060006101a06020019050808651146111e7576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807f77726f6e672070726f6f66206c656e677468000000000000000000000000000081525060200191505060405180910390fd5b6111ef61238b565b60008060208901925060e0890151915083890151905061120e83610e06565b975061121a8883611099565b95506000808781526020019081526020016000206040518060400160405290816000820160009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020016001820154815250509650600073ffffffffffffffffffffffffffffffffffffffff16876000015173ffffffffffffffffffffffffffffffffffffffff161415611342576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260188152602001807f6e6f20636f72726573706f6e64696e672072657175657374000000000000000081525060200191505060405180910390fd5b81816040516020018083815260200182815260200192505050604051602081830303815290604052805190602001208760200151146113e9576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f77726f6e672070726553656564206f7220626c6f636b206e756d00000000000081525060200191505060405180910390fd5b6000814090506000838260405160200180838152602001828152602001925050506040516020818303038152906040528051906020012060001c90506101a08b526114338b610e5e565b96505050505050509193509193565b60006394985ddd60e01b905060608185856040516024018083815260200182815260200192505050604051602081830303815290604052907bffffffffffffffffffffffffffffffffffffffffffffffffffffffff19166020820180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff8381831617835250505050905060008373ffffffffffffffffffffffffffffffffffffffff16826040518082805190602001908083835b6020831061151657805182526020820191506020810190506020830392506114f3565b6001836020036101000a0380198251168184511680821785525050505050509050019150506000604051808303816000865af19150503d8060008114611578576040519150601f19603f3d011682016040523d82523d6000602084013e61157d565b606091505b50509050505050505050565b61159289611836565b611604576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f7075626c6963206b6579206973206e6f74206f6e20637572766500000000000081525060200191505060405180910390fd5b61160d88611836565b61167f576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260158152602001807f67616d6d61206973206e6f74206f6e206375727665000000000000000000000081525060200191505060405180910390fd5b61168883611836565b6116fa576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f6347616d6d615769746e657373206973206e6f74206f6e20637572766500000081525060200191505060405180910390fd5b61170382611836565b611775576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252601c8152602001807f73486173685769746e657373206973206e6f74206f6e2063757276650000000081525060200191505060405180910390fd5b61177d61238b565b6117878a876118a7565b905061179161238b565b6117a0898b878b868989611966565b905060006117b1838d8d8a86611a4a565b9050808a14611828576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f696e76616c69642070726f6f660000000000000000000000000000000000000081525060200191505060405180910390fd5b505050505050505050505050565b60007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061186057fe5b8260016002811061186d57fe5b60200201518360016002811061187f57fe5b60200201510961189f8360006002811061189557fe5b6020020151611b79565b149050919050565b6118af61238b565b611910600184846040516020018084815260200183600260200280838360005b838110156118ea5780820151818401526020810190506118cf565b505050509050018281526020019350505050604051602081830303815290604052611c07565b90505b61191c81611836565b611960576119598160006002811061193057fe5b602002015160405160200180828152602001915050604051602081830303815290604052611c07565b9050611913565b92915050565b61196e61238b565b60007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8460006002811061199e57fe5b6020020151886000600281106119b057fe5b602002015103816119bd57fe5b061415611a32576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252601e8152602001807f706f696e747320696e2073756d206d7573742062652064697374696e6374000081525060200191505060405180910390fd5b611a3d868484611cda565b9050979650505050505050565b6000600286868685876040516020018087815260200186600260200280838360005b83811015611a87578082015181840152602081019050611a6c565b5050505090500185600260200280838360005b83811015611ab5578082015181840152602081019050611a9a565b5050505090500184600260200280838360005b83811015611ae3578082015181840152602081019050611ac8565b5050505090500183600260200280838360005b83811015611b11578082015181840152602081019050611af6565b505050509050018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1660601b815260140196505050505050506040516020818303038152906040528051906020012060001c905095945050505050565b6000807ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80611ba457fe5b7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80611bcc57fe5b848509840990507ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80611bfb57fe5b60078208915050919050565b611c0f61238b565b611c1882611e58565b81600060028110611c2557fe5b602002018181525050611c50611c4b82600060028110611c4157fe5b6020020151611b79565b611ec5565b81600160028110611c5d57fe5b6020020181815250506001600282600160028110611c7757fe5b602002015181611c8357fe5b061415611cd55780600160028110611c9757fe5b60200201517ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f0381600160028110611ccb57fe5b6020020181815250505b919050565b611ce261238b565b6000806000611d3787600060028110611cf757fe5b602002015188600160028110611d0957fe5b602002015188600060028110611d1b57fe5b602002015189600160028110611d2d57fe5b6020020151611eff565b80935081945082955050505060017ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80611d6d57fe5b86830914611de3576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260198152602001807f696e765a206d75737420626520696e7665727365206f66207a0000000000000081525060200191505060405180910390fd5b60405180604001604052807ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80611e1657fe5b87860981526020017ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80611e4657fe5b87850981525093505050509392505050565b6000818051906020012060001c90505b7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8110611ec05780604051602001808281526020019150506040516020818303038152906040528051906020012060001c9050611e68565b919050565b6000611ef882600260017ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f01901c6120d3565b9050919050565b60008060008060006001809150915060007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80611f3857fe5b897ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f038808905060007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80611f8957fe5b8b7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f038a0890506000611fbe83838585612235565b8092508199505050611fd288828e8861229f565b8092508199505050611fe68882","8c8761229f565b80925081995050506000611ffc8d878b8561229f565b809250819950505061201088828686612235565b809250819950505061202488828e8961229f565b80925081995050508082146120bf577ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061205b57fe5b818a0998507ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061208857fe5b82890997507ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f806120b557fe5b81830996506120c3565b8196505b5050505050509450945094915050565b6000806120de612402565b6020816000600681106120ed57fe5b60200201818152505060208160016006811061210557fe5b60200201818152505060208160026006811061211d57fe5b602002018181525050848160036006811061213457fe5b602002018181525050838160046006811061214b57fe5b6020020181815250507ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8160056006811061218257fe5b602002018181525050612193612424565b60208160c0846005600019fa92506000831415612218576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807f6269674d6f64457870206661696c75726521000000000000000000000000000081525060200191505060405180910390fd5b8060006001811061222557fe5b6020020151935050505092915050565b6000807ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061226057fe5b8487097ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061228b57fe5b848709809250819350505094509492505050565b60008060007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f806122cc57fe5b878509905060007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f806122fb57fe5b87877ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f030990507ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061234a57fe5b8183087ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061237557fe5b8689098094508195505050505094509492505050565b6040518060400160405280600290602082028036833780820191505090505090565b6040518060400160405280600073ffffffffffffffffffffffffffffffffffffffff168152602001600080191681525090565b6040518060600160405280600390602082028036833780820191505090505090565b6040518060c00160405280600690602082028036833780820191505090505090565b604051806020016040528060019060208202803683378082019150509050509056fea2646970667358221220bc685af88cf9d4dc6de3b6779fb2dd4635668bc5cd1cfaa6f02cac7bd0403e7764736f6c634300060a0033"};

    public static final String BINARY = String.join("", BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"keyHash\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"seed\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"blockNumber\",\"type\":\"uint256\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"sender\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"requestID\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"seedAndBlockNum\",\"type\":\"bytes32\"}],\"name\":\"RandomnessRequest\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"bytes32\",\"name\":\"requestId\",\"type\":\"bytes32\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"output\",\"type\":\"uint256\"}],\"name\":\"RandomnessRequestFulfilled\",\"type\":\"event\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256[2]\",\"name\":\"pk\",\"type\":\"uint256[2]\"},{\"indexed\":false,\"internalType\":\"uint256[2]\",\"name\":\"gamma\",\"type\":\"uint256[2]\"},{\"indexed\":false,\"internalType\":\"uint256[3]\",\"name\":\"cSSeed\",\"type\":\"uint256[3]\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"uWitness\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256[2]\",\"name\":\"cGammaWitness\",\"type\":\"uint256[2]\"},{\"indexed\":false,\"internalType\":\"uint256[2]\",\"name\":\"sHashWitness\",\"type\":\"uint256[2]\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"zInv\",\"type\":\"uint256\"}],\"name\":\"TestRandom\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"PRESEED_OFFSET\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"PROOF_LENGTH\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[],\"name\":\"PUBLIC_KEY_OFFSET\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"name\":\"callbacks\",\"outputs\":[{\"internalType\":\"address\",\"name\":\"callbackContract\",\"type\":\"address\"},{\"internalType\":\"bytes32\",\"name\":\"seedAndBlockNum\",\"type\":\"bytes32\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes\",\"name\":\"_proof\",\"type\":\"bytes\"}],\"name\":\"fulfillRandomnessRequest\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes\",\"name\":\"_proof\",\"type\":\"bytes\"}],\"name\":\"getRandomnessFromProof1\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"currentKeyHash\",\"type\":\"bytes32\"},{\"internalType\":\"bytes32\",\"name\":\"requestId\",\"type\":\"bytes32\"},{\"internalType\":\"uint256\",\"name\":\"randomness\",\"type\":\"uint256\"},{\"internalType\":\"uint256[2]\",\"name\":\"publicKey\",\"type\":\"uint256[2]\"},{\"internalType\":\"uint256\",\"name\":\"preSeed\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"uint256[2]\",\"name\":\"_publicKey\",\"type\":\"uint256[2]\"}],\"name\":\"hashOfKey\",\"outputs\":[{\"internalType\":\"bytes32\",\"name\":\"\",\"type\":\"bytes32\"}],\"stateMutability\":\"pure\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes\",\"name\":\"proof\",\"type\":\"bytes\"}],\"name\":\"randomValueFromVRFProof\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"output\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes32\",\"name\":\"_keyHash\",\"type\":\"bytes32\"},{\"internalType\":\"uint256\",\"name\":\"_consumerSeed\",\"type\":\"uint256\"},{\"internalType\":\"address\",\"name\":\"_sender\",\"type\":\"address\"}],\"name\":\"randomnessRequest\",\"outputs\":[],\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes\",\"name\":\"proof\",\"type\":\"bytes\"}],\"name\":\"testRandomValueFromVRFProof\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"output\",\"type\":\"uint256\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = String.join("", ABI_ARRAY);

    public static final TransactionDecoder transactionDecoder = new TransactionDecoder(ABI, BINARY);

    public static final String FUNC_PRESEED_OFFSET = "PRESEED_OFFSET";

    public static final String FUNC_PROOF_LENGTH = "PROOF_LENGTH";

    public static final String FUNC_PUBLIC_KEY_OFFSET = "PUBLIC_KEY_OFFSET";

    public static final String FUNC_CALLBACKS = "callbacks";

    public static final String FUNC_FULFILLRANDOMNESSREQUEST = "fulfillRandomnessRequest";

    public static final String FUNC_GETRANDOMNESSFROMPROOF1 = "getRandomnessFromProof1";

    public static final String FUNC_HASHOFKEY = "hashOfKey";

    public static final String FUNC_RANDOMVALUEFROMVRFPROOF = "randomValueFromVRFProof";

    public static final String FUNC_RANDOMNESSREQUEST = "randomnessRequest";

    public static final String FUNC_TESTRANDOMVALUEFROMVRFPROOF = "testRandomValueFromVRFProof";

    public static final Event RANDOMNESSREQUEST_EVENT = new Event("RandomnessRequest", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}));
    ;

    public static final Event RANDOMNESSREQUESTFULFILLED_EVENT = new Event("RandomnessRequestFulfilled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TESTRANDOM_EVENT = new Event("TestRandom", 
            Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray2<Uint256>>() {}, new TypeReference<StaticArray2<Uint256>>() {}, new TypeReference<StaticArray3<Uint256>>() {}, new TypeReference<Address>() {}, new TypeReference<StaticArray2<Uint256>>() {}, new TypeReference<StaticArray2<Uint256>>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected VRFCoordinator(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(getBinary(), contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected VRFCoordinator(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(getBinary(), contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected VRFCoordinator(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(getBinary(), contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected VRFCoordinator(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(getBinary(), contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static String getBinary() {
        return BINARY;
    }

    public static TransactionDecoder getTransactionDecoder() {
        return transactionDecoder;
    }

    public List<RandomnessRequestEventResponse> getRandomnessRequestEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RANDOMNESSREQUEST_EVENT, transactionReceipt);
        ArrayList<RandomnessRequestEventResponse> responses = new ArrayList<RandomnessRequestEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RandomnessRequestEventResponse typedResponse = new RandomnessRequestEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.keyHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.seed = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.blockNumber = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.sender = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.requestID = (byte[]) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.seedAndBlockNum = (byte[]) eventValues.getNonIndexedValues().get(5).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerRandomnessRequestEventLogFilter(String fromBlock, String toBlock, List<String> otherTopics, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(RANDOMNESSREQUEST_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void registerRandomnessRequestEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(RANDOMNESSREQUEST_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<RandomnessRequestFulfilledEventResponse> getRandomnessRequestFulfilledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(RANDOMNESSREQUESTFULFILLED_EVENT, transactionReceipt);
        ArrayList<RandomnessRequestFulfilledEventResponse> responses = new ArrayList<RandomnessRequestFulfilledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RandomnessRequestFulfilledEventResponse typedResponse = new RandomnessRequestFulfilledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.requestId = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.output = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerRandomnessRequestFulfilledEventLogFilter(String fromBlock, String toBlock, List<String> otherTopics, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(RANDOMNESSREQUESTFULFILLED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void registerRandomnessRequestFulfilledEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(RANDOMNESSREQUESTFULFILLED_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public List<TestRandomEventResponse> getTestRandomEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TESTRANDOM_EVENT, transactionReceipt);
        ArrayList<TestRandomEventResponse> responses = new ArrayList<TestRandomEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TestRandomEventResponse typedResponse = new TestRandomEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.pk = (List<BigInteger>) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.gamma = (List<BigInteger>) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.cSSeed = (List<BigInteger>) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.uWitness = (String) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.cGammaWitness = (List<BigInteger>) eventValues.getNonIndexedValues().get(4).getValue();
            typedResponse.sHashWitness = (List<BigInteger>) eventValues.getNonIndexedValues().get(5).getValue();
            typedResponse.zInv = (BigInteger) eventValues.getNonIndexedValues().get(6).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void registerTestRandomEventLogFilter(String fromBlock, String toBlock, List<String> otherTopics, EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(TESTRANDOM_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void registerTestRandomEventLogFilter(EventLogPushWithDecodeCallback callback) {
        String topic0 = EventEncoder.encode(TESTRANDOM_EVENT);
        registerEventLogPushFilter(ABI,BINARY,topic0,callback);
    }

    public RemoteCall<BigInteger> PRESEED_OFFSET() {
        final Function function = new Function(FUNC_PRESEED_OFFSET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> PROOF_LENGTH() {
        final Function function = new Function(FUNC_PROOF_LENGTH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> PUBLIC_KEY_OFFSET() {
        final Function function = new Function(FUNC_PUBLIC_KEY_OFFSET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple2<String, byte[]>> callbacks(byte[] param0) {
        final Function function = new Function(FUNC_CALLBACKS, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Bytes32>() {}));
        return new RemoteCall<Tuple2<String, byte[]>>(
                new Callable<Tuple2<String, byte[]>>() {
                    @Override
                    public Tuple2<String, byte[]> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple2<String, byte[]>(
                                (String) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> fulfillRandomnessRequest(byte[] _proof) {
        final Function function = new Function(
                FUNC_FULFILLRANDOMNESSREQUEST, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(_proof)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void fulfillRandomnessRequest(byte[] _proof, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_FULFILLRANDOMNESSREQUEST, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(_proof)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String fulfillRandomnessRequestSeq(byte[] _proof) {
        final Function function = new Function(
                FUNC_FULFILLRANDOMNESSREQUEST, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(_proof)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<byte[]> getFulfillRandomnessRequestInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_FULFILLRANDOMNESSREQUEST, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public RemoteCall<Tuple5<byte[], byte[], BigInteger, List<BigInteger>, BigInteger>> getRandomnessFromProof1(byte[] _proof) {
        final Function function = new Function(FUNC_GETRANDOMNESSFROMPROOF1, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(_proof)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<StaticArray2<Uint256>>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple5<byte[], byte[], BigInteger, List<BigInteger>, BigInteger>>(
                new Callable<Tuple5<byte[], byte[], BigInteger, List<BigInteger>, BigInteger>>() {
                    @Override
                    public Tuple5<byte[], byte[], BigInteger, List<BigInteger>, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<byte[], byte[], BigInteger, List<BigInteger>, BigInteger>(
                                (byte[]) results.get(0).getValue(), 
                                (byte[]) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                convertToNative((List<Uint256>) results.get(3).getValue()), 
                                (BigInteger) results.get(4).getValue());
                    }
                });
    }

    public RemoteCall<TransactionReceipt> hashOfKey(List<BigInteger> _publicKey) {
        final Function function = new Function(
                FUNC_HASHOFKEY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(_publicKey, org.fisco.bcos.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void hashOfKey(List<BigInteger> _publicKey, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_HASHOFKEY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(_publicKey, org.fisco.bcos.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String hashOfKeySeq(List<BigInteger> _publicKey) {
        final Function function = new Function(
                FUNC_HASHOFKEY, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2<org.fisco.bcos.web3j.abi.datatypes.generated.Uint256>(
                        org.fisco.bcos.web3j.abi.Utils.typeMap(_publicKey, org.fisco.bcos.web3j.abi.datatypes.generated.Uint256.class))), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<List<BigInteger>> getHashOfKeyInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_HASHOFKEY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray2<Uint256>>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<List<BigInteger>>(

                convertToNative((List<Uint256>) results.get(0).getValue())
                );
    }

    public Tuple1<byte[]> getHashOfKeyOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_HASHOFKEY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public RemoteCall<BigInteger> randomValueFromVRFProof(byte[] proof) {
        final Function function = new Function(FUNC_RANDOMVALUEFROMVRFPROOF, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(proof)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> randomnessRequest(byte[] _keyHash, BigInteger _consumerSeed, String _sender) {
        final Function function = new Function(
                FUNC_RANDOMNESSREQUEST, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_keyHash), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_consumerSeed), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(_sender)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void randomnessRequest(byte[] _keyHash, BigInteger _consumerSeed, String _sender, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_RANDOMNESSREQUEST, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_keyHash), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_consumerSeed), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(_sender)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String randomnessRequestSeq(byte[] _keyHash, BigInteger _consumerSeed, String _sender) {
        final Function function = new Function(
                FUNC_RANDOMNESSREQUEST, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.generated.Bytes32(_keyHash), 
                new org.fisco.bcos.web3j.abi.datatypes.generated.Uint256(_consumerSeed), 
                new org.fisco.bcos.web3j.abi.datatypes.Address(_sender)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple3<byte[], BigInteger, String> getRandomnessRequestInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_RANDOMNESSREQUEST, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple3<byte[], BigInteger, String>(

                (byte[]) results.get(0).getValue(), 
                (BigInteger) results.get(1).getValue(), 
                (String) results.get(2).getValue()
                );
    }

    public RemoteCall<TransactionReceipt> testRandomValueFromVRFProof(byte[] proof) {
        final Function function = new Function(
                FUNC_TESTRANDOMVALUEFROMVRFPROOF, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(proof)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void testRandomValueFromVRFProof(byte[] proof, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_TESTRANDOMVALUEFROMVRFPROOF, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(proof)), 
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String testRandomValueFromVRFProofSeq(byte[] proof) {
        final Function function = new Function(
                FUNC_TESTRANDOMVALUEFROMVRFPROOF, 
                Arrays.<Type>asList(new org.fisco.bcos.web3j.abi.datatypes.DynamicBytes(proof)), 
                Collections.<TypeReference<?>>emptyList());
        return createTransactionSeq(function);
    }

    public Tuple1<byte[]> getTestRandomValueFromVRFProofInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_TESTRANDOMVALUEFROMVRFPROOF, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<byte[]>(

                (byte[]) results.get(0).getValue()
                );
    }

    public Tuple1<BigInteger> getTestRandomValueFromVRFProofOutput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getOutput();
        final Function function = new Function(FUNC_TESTRANDOMVALUEFROMVRFPROOF, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());;
        return new Tuple1<BigInteger>(

                (BigInteger) results.get(0).getValue()
                );
    }

    @Deprecated
    public static VRFCoordinator load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new VRFCoordinator(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static VRFCoordinator load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new VRFCoordinator(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static VRFCoordinator load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new VRFCoordinator(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static VRFCoordinator load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new VRFCoordinator(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<VRFCoordinator> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VRFCoordinator.class, web3j, credentials, contractGasProvider, getBinary(), "");
    }

    @Deprecated
    public static RemoteCall<VRFCoordinator> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VRFCoordinator.class, web3j, credentials, gasPrice, gasLimit, getBinary(), "");
    }

    public static RemoteCall<VRFCoordinator> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VRFCoordinator.class, web3j, transactionManager, contractGasProvider, getBinary(), "");
    }

    @Deprecated
    public static RemoteCall<VRFCoordinator> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VRFCoordinator.class, web3j, transactionManager, gasPrice, gasLimit, getBinary(), "");
    }

    public static class RandomnessRequestEventResponse {
        public Log log;

        public byte[] keyHash;

        public BigInteger seed;

        public BigInteger blockNumber;

        public String sender;

        public byte[] requestID;

        public byte[] seedAndBlockNum;
    }

    public static class RandomnessRequestFulfilledEventResponse {
        public Log log;

        public byte[] requestId;

        public BigInteger output;
    }

    public static class TestRandomEventResponse {
        public Log log;

        public List<BigInteger> pk;

        public List<BigInteger> gamma;

        public List<BigInteger> cSSeed;

        public String uWitness;

        public List<BigInteger> cGammaWitness;

        public List<BigInteger> sHashWitness;

        public BigInteger zInv;
    }
}
