package com.webank.oracle.transaction.vrf;

import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.channel.event.filter.EventLogPushWithDecodeCallback;
import org.fisco.bcos.web3j.abi.EventEncoder;
import org.fisco.bcos.web3j.abi.FunctionReturnDecoder;
import org.fisco.bcos.web3j.abi.TypeReference;
import org.fisco.bcos.web3j.abi.datatypes.*;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray2;
import org.fisco.bcos.web3j.abi.datatypes.generated.StaticArray3;
import org.fisco.bcos.web3j.abi.datatypes.generated.Uint256;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.RemoteCall;
import org.fisco.bcos.web3j.protocol.core.methods.response.Log;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple1;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;

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
public class VRF extends Contract {
    public static final String[] BINARY_ARRAY = {"608060405234801561001057600080fd5b50611561806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c806361fd0d4f14610046578063e911439c14610115578063fa8fc6f114610133575b600080fd5b6100ff6004803603602081101561005c57600080fd5b810190808035906020019064010000000081111561007957600080fd5b82018360208201111561008b57600080fd5b803590602001918460018302840111640100000000831117156100ad57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610202565b6040518082815260200191505060405180910390f35b61011d6104da565b6040518082815260200191505060405180910390f35b6101ec6004803603602081101561014957600080fd5b810190808035906020019064010000000081111561016657600080fd5b82018360208201111561017857600080fd5b8035906020019184600183028401116401000000008311171561019a57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506104e0565b6040518082815260200191505060405180910390f35b60006101a082511461027c576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807f77726f6e672070726f6f66206c656e677468000000000000000000000000000081525060200191505060405180910390fd5b6102846114a3565b61028c6114a3565b6102946114c5565b600061029e6114a3565b6102a66114a3565b6000888060200190516101a08110156102be57600080fd5b810190809190826040019190826040019190826060018051906020019092919091908260400191908260400180519060200190929190505050839350809750819850829950839a50849b50859c50869d50505050505050507f9847978339c4b5dd01721f262e40d7a836902256b26004168fa4d40b69696cb0878787878787876040518088600260200280838360005b8381101561036957808201518184015260208101905061034e565b5050505090500187600260200280838360005b8381101561039757808201518184015260208101905061037c565b5050505090500186600360200280838360005b838110156103c55780820151818401526020810190506103aa565b505050509050018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200184600260200280838360005b8381101561042557808201518184015260208101905061040a565b5050505090500183600260200280838360005b83811015610453578082015181840152602081019050610438565b5050505090500182815260200197505050505050505060405180910390a16003866040516020018083815260200182600260200280838360005b838110156104a857808201518184015260208101905061048d565b50505050905001925050506040516020818303038152906040528051906020012060001c975050505050505050919050565b6101a081565b60006101a082511461055a576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807f77726f6e672070726f6f66206c656e677468000000000000000000000000000081525060200191505060405180910390fd5b6105626114a3565b61056a6114a3565b6105726114c5565b600061057c6114a3565b6105846114a3565b6000888060200190516101a081101561059c57600080fd5b810190809190826040019190826040019190826060018051906020019092919091908260400191908260400180519060200190929190505050839350809750819850829950839a50849b50859c50869d505050505050505061063887878760006003811061060657fe5b60200201518860016003811061061857fe5b60200201518960026003811061062a57fe5b6020020151898989896106a1565b6003866040516020018083815260200182600260200280838360005b8381101561066f578082015181840152602081019050610654565b50505050905001925050506040516020818303038152906040528051906020012060001c975050505050505050919050565b6106aa8961094e565b61071c576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f7075626c6963206b6579206973206e6f74206f6e20637572766500000000000081525060200191505060405180910390fd5b6107258861094e565b610797576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260158152602001807f67616d6d61206973206e6f74206f6e206375727665000000000000000000000081525060200191505060405180910390fd5b6107a08361094e565b610812576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f6347616d6d615769746e657373206973206e6f74206f6e20637572766500000081525060200191505060405180910390fd5b61081b8261094e565b61088d576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601c8152602001807f73486173685769746e657373206973206e6f74206f6e2063757276650000000081525060200191505060405180910390fd5b6108956114a3565b61089f8a876109bf565b90506108a96114a3565b6108b8898b878b868989610a7e565b905060006108c9838d8d8a86610b62565b9050808a14610940576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f696e76616c69642070726f6f660000000000000000000000000000000000000081525060200191505060405180910390fd5b505050505050505050505050565b60007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061097857fe5b8260016002811061098557fe5b60200201518360016002811061099757fe5b6020020151096109b7836000600281106109ad57fe5b6020020151610c91565b149050919050565b6109c76114a3565b610a28600184846040516020018084815260200183600260200280838360005b83811015610a025780820151818401526020810190506109e7565b505050509050018281526020019350505050604051602081830303815290604052610d1f565b90505b610a348161094e565b610a7857610a7181600060028110610a4857fe5b602002015160405160200180828152602001915050604051602081830303815290604052610d1f565b9050610a2b565b92915050565b610a866114a3565b60007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f84600060028110610ab657fe5b602002015188600060028110610ac857fe5b60200201510381610ad557fe5b061415610b4a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252601e8152602001807f706f696e747320696e2073756d206d7573742062652064697374696e6374000081525060200191505060405180910390fd5b610b55868484610df2565b9050979650505050505050565b6000600286868685876040516020018087815260200186600260200280838360005b83811015610b9f578082015181840152602081019050610b84565b5050505090500185600260200280838360005b83811015610bcd578082015181840152602081019050610bb2565b5050505090500184600260200280838360005b83811015610bfb578082015181840152602081019050610be0565b5050505090500183600260200280838360005b83811015610c29578082015181840152602081019050610c0e565b505050509050018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1660601b815260140196505050505050506040516020818303038152906040528051906020012060001c905095945050505050565b6000807ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80610cbc57fe5b7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80610ce457fe5b848509840990507ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80610d1357fe5b60078208915050919050565b610d276114a3565b610d3082610f70565b81600060028110610d3d57fe5b602002018181525050610d68610d6382600060028110610d5957fe5b6020020151610c91565b610fdd565b81600160028110610d7557fe5b6020020181815250506001600282600160028110610d8f57fe5b602002015181610d9b57fe5b061415610ded5780600160028110610daf57fe5b60200201517ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f0381600160028110610de357fe5b6020020181815250505b919050565b610dfa6114a3565b6000806000610e4f87600060028110610e0f57fe5b602002015188600160028110610e2157fe5b602002015188600060028110610e3357fe5b602002015189600160028110610e4557fe5b6020020151611017565b80935081945082955050505060017ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80610e8557fe5b86830914610efb576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260198152602001807f696e765a206d75737420626520696e7665727365206f66207a0000000000000081525060200191505060405180910390fd5b60405180604001604052807ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80610f2e57fe5b87860981526020017ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80610f5e57fe5b87850981525093505050509392505050565b6000818051906020012060001c90505b7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8110610fd85780604051602001808281526020019150506040516020818303038152906040528051906020012060001c9050610f80565b919050565b6000","61101082600260017ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f01901c6111eb565b9050919050565b60008060008060006001809150915060007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061105057fe5b897ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f038808905060007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f806110a157fe5b8b7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f038a08905060006110d68383858561134d565b80925081995050506110ea88828e886113b7565b80925081995050506110fe88828c876113b7565b809250819950505060006111148d878b856113b7565b80925081995050506111288882868661134d565b809250819950505061113c88828e896113b7565b80925081995050508082146111d7577ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061117357fe5b818a0998507ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f806111a057fe5b82890997507ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f806111cd57fe5b81830996506111db565b8196505b5050505050509450945094915050565b6000806111f66114e7565b60208160006006811061120557fe5b60200201818152505060208160016006811061121d57fe5b60200201818152505060208160026006811061123557fe5b602002018181525050848160036006811061124c57fe5b602002018181525050838160046006811061126357fe5b6020020181815250507ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8160056006811061129a57fe5b6020020181815250506112ab611509565b60208160c0846005600019fa92506000831415611330576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807f6269674d6f64457870206661696c75726521000000000000000000000000000081525060200191505060405180910390fd5b8060006001811061133d57fe5b6020020151935050505092915050565b6000807ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061137857fe5b8487097ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f806113a357fe5b848709809250819350505094509492505050565b60008060007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f806113e457fe5b878509905060007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061141357fe5b87877ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f030990507ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061146257fe5b8183087ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061148d57fe5b8689098094508195505050505094509492505050565b6040518060400160405280600290602082028036833780820191505090505090565b6040518060600160405280600390602082028036833780820191505090505090565b6040518060c00160405280600690602082028036833780820191505090505090565b604051806020016040528060019060208202803683378082019150509050509056fea26469706673582212209578f6ff1848e0d38136075c7d38be0fd1244db4d4eb0e9bc5168b6702dc991a64736f6c634300060a0033"};

    public static final String BINARY = String.join("", BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"internalType\":\"uint256[2]\",\"name\":\"pk\",\"type\":\"uint256[2]\"},{\"indexed\":false,\"internalType\":\"uint256[2]\",\"name\":\"gamma\",\"type\":\"uint256[2]\"},{\"indexed\":false,\"internalType\":\"uint256[3]\",\"name\":\"cSSeed\",\"type\":\"uint256[3]\"},{\"indexed\":false,\"internalType\":\"address\",\"name\":\"uWitness\",\"type\":\"address\"},{\"indexed\":false,\"internalType\":\"uint256[2]\",\"name\":\"cGammaWitness\",\"type\":\"uint256[2]\"},{\"indexed\":false,\"internalType\":\"uint256[2]\",\"name\":\"sHashWitness\",\"type\":\"uint256[2]\"},{\"indexed\":false,\"internalType\":\"uint256\",\"name\":\"zInv\",\"type\":\"uint256\"}],\"name\":\"TestRandom\",\"type\":\"event\"},{\"inputs\":[],\"name\":\"PROOF_LENGTH\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes\",\"name\":\"proof\",\"type\":\"bytes\"}],\"name\":\"randomValueFromVRFProof\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"output\",\"type\":\"uint256\"}],\"stateMutability\":\"view\",\"type\":\"function\"},{\"inputs\":[{\"internalType\":\"bytes\",\"name\":\"proof\",\"type\":\"bytes\"}],\"name\":\"testRandomValueFromVRFProof\",\"outputs\":[{\"internalType\":\"uint256\",\"name\":\"output\",\"type\":\"uint256\"}],\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]"};

    public static final String ABI = String.join("", ABI_ARRAY);

    public static final TransactionDecoder transactionDecoder = new TransactionDecoder(ABI, BINARY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50611561806100206000396000f3fe608060405234801561001057600080fd5b50600436106100415760003560e01c806325140153146100465780639b64b73914610064578063de2ec50214610133575b600080fd5b61004e610202565b6040518082815260200191505060405180910390f35b61011d6004803603602081101561007a57600080fd5b810190808035906020019064010000000081111561009757600080fd5b8201836020820111156100a957600080fd5b803590602001918460018302840111640100000000831117156100cb57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f820116905080830192505050505050509192919290505050610208565b6040518082815260200191505060405180910390f35b6101ec6004803603602081101561014957600080fd5b810190808035906020019064010000000081111561016657600080fd5b82018360208201111561017857600080fd5b8035906020019184600183028401116401000000008311171561019a57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600081840152601f19601f8201169050808301925050505050505091929192905050506104e0565b6040518082815260200191505060405180910390f35b6101a081565b60006101a0825114610282576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807f77726f6e672070726f6f66206c656e677468000000000000000000000000000081525060200191505060405180910390fd5b61028a6114a3565b6102926114a3565b61029a6114c5565b60006102a46114a3565b6102ac6114a3565b6000888060200190516101a08110156102c457600080fd5b810190809190826040019190826040019190826060018051906020019092919091908260400191908260400180519060200190929190505050839350809750819850829950839a50849b50859c50869d50505050505050507fa6ee1a1c55599b18693eb2f2004ebf4036c56226ddf2e7d27186609e5e4905b1878787878787876040518088600260200280838360005b8381101561036f578082015181840152602081019050610354565b5050505090500187600260200280838360005b8381101561039d578082015181840152602081019050610382565b5050505090500186600360200280838360005b838110156103cb5780820151818401526020810190506103b0565b505050509050018573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200184600260200280838360005b8381101561042b578082015181840152602081019050610410565b5050505090500183600260200280838360005b8381101561045957808201518184015260208101905061043e565b5050505090500182815260200197505050505050505060405180910390a16003866040516020018083815260200182600260200280838360005b838110156104ae578082015181840152602081019050610493565b50505050905001925050506040516020818303038152906040528051906020012060001c975050505050505050919050565b60006101a082511461055a576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807f77726f6e672070726f6f66206c656e677468000000000000000000000000000081525060200191505060405180910390fd5b6105626114a3565b61056a6114a3565b6105726114c5565b600061057c6114a3565b6105846114a3565b6000888060200190516101a081101561059c57600080fd5b810190809190826040019190826040019190826060018051906020019092919091908260400191908260400180519060200190929190505050839350809750819850829950839a50849b50859c50869d505050505050505061063887878760006003811061060657fe5b60200201518860016003811061061857fe5b60200201518960026003811061062a57fe5b6020020151898989896106a1565b6003866040516020018083815260200182600260200280838360005b8381101561066f578082015181840152602081019050610654565b50505050905001925050506040516020818303038152906040528051906020012060001c975050505050505050919050565b6106aa8961094e565b61071c576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252601a8152602001807f7075626c6963206b6579206973206e6f74206f6e20637572766500000000000081525060200191505060405180910390fd5b6107258861094e565b610797576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260158152602001807f67616d6d61206973206e6f74206f6e206375727665000000000000000000000081525060200191505060405180910390fd5b6107a08361094e565b610812576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252601d8152602001807f6347616d6d615769746e657373206973206e6f74206f6e20637572766500000081525060200191505060405180910390fd5b61081b8261094e565b61088d576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252601c8152602001807f73486173685769746e657373206973206e6f74206f6e2063757276650000000081525060200191505060405180910390fd5b6108956114a3565b61089f8a876109bf565b90506108a96114a3565b6108b8898b878b868989610a7e565b905060006108c9838d8d8a86610b62565b9050808a14610940576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252600d8152602001807f696e76616c69642070726f6f660000000000000000000000000000000000000081525060200191505060405180910390fd5b505050505050505050505050565b60007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061097857fe5b8260016002811061098557fe5b60200201518360016002811061099757fe5b6020020151096109b7836000600281106109ad57fe5b6020020151610c91565b149050919050565b6109c76114a3565b610a28600184846040516020018084815260200183600260200280838360005b83811015610a025780820151818401526020810190506109e7565b505050509050018281526020019350505050604051602081830303815290604052610d1f565b90505b610a348161094e565b610a7857610a7181600060028110610a4857fe5b602002015160405160200180828152602001915050604051602081830303815290604052610d1f565b9050610a2b565b92915050565b610a866114a3565b60007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f84600060028110610ab657fe5b602002015188600060028110610ac857fe5b60200201510381610ad557fe5b061415610b4a576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252601e8152602001807f706f696e747320696e2073756d206d7573742062652064697374696e6374000081525060200191505060405180910390fd5b610b55868484610df2565b9050979650505050505050565b6000600286868685876040516020018087815260200186600260200280838360005b83811015610b9f578082015181840152602081019050610b84565b5050505090500185600260200280838360005b83811015610bcd578082015181840152602081019050610bb2565b5050505090500184600260200280838360005b83811015610bfb578082015181840152602081019050610be0565b5050505090500183600260200280838360005b83811015610c29578082015181840152602081019050610c0e565b505050509050018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1660601b815260140196505050505050506040516020818303038152906040528051906020012060001c905095945050505050565b6000807ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80610cbc57fe5b7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80610ce457fe5b848509840990507ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80610d1357fe5b60078208915050919050565b610d276114a3565b610d3082610f70565b81600060028110610d3d57fe5b602002018181525050610d68610d6382600060028110610d5957fe5b6020020151610c91565b610fdd565b81600160028110610d7557fe5b6020020181815250506001600282600160028110610d8f57fe5b602002015181610d9b57fe5b061415610ded5780600160028110610daf57fe5b60200201517ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f0381600160028110610de357fe5b6020020181815250505b919050565b610dfa6114a3565b6000806000610e4f87600060028110610e0f57fe5b602002015188600160028110610e2157fe5b602002015188600060028110610e3357fe5b602002015189600160028110610e4557fe5b6020020151611017565b80935081945082955050505060017ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80610e8557fe5b86830914610efb576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260198152602001807f696e765a206d75737420626520696e7665727365206f66207a0000000000000081525060200191505060405180910390fd5b60405180604001604052807ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80610f2e57fe5b87860981526020017ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f80610f5e57fe5b87850981525093505050509392505050565b6000818051906020012060001c90505b7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8110610fd85780604051602001808281526020019150506040516020818303038152906040528051906020012060001c9050610f80565b919050565b6000","61101082600260017ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f01901c6111eb565b9050919050565b60008060008060006001809150915060007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061105057fe5b897ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f038808905060007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f806110a157fe5b8b7ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f038a08905060006110d68383858561134d565b80925081995050506110ea88828e886113b7565b80925081995050506110fe88828c876113b7565b809250819950505060006111148d878b856113b7565b80925081995050506111288882868661134d565b809250819950505061113c88828e896113b7565b80925081995050508082146111d7577ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061117357fe5b818a0998507ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f806111a057fe5b82890997507ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f806111cd57fe5b81830996506111db565b8196505b5050505050509450945094915050565b6000806111f66114e7565b60208160006006811061120557fe5b60200201818152505060208160016006811061121d57fe5b60200201818152505060208160026006811061123557fe5b602002018181525050848160036006811061124c57fe5b602002018181525050838160046006811061126357fe5b6020020181815250507ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8160056006811061129a57fe5b6020020181815250506112ab611509565b60208160c0846005600019fa92506000831415611330576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807f6269674d6f64457870206661696c75726521000000000000000000000000000081525060200191505060405180910390fd5b8060006001811061133d57fe5b6020020151935050505092915050565b6000807ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061137857fe5b8487097ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f806113a357fe5b848709809250819350505094509492505050565b60008060007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f806113e457fe5b878509905060007ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061141357fe5b87877ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f030990507ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061146257fe5b8183087ffffffffffffffffffffffffffffffffffffffffffffffffffffffffefffffc2f8061148d57fe5b8689098094508195505050505094509492505050565b6040518060400160405280600290602082028036833780820191505090505090565b6040518060600160405280600390602082028036833780820191505090505090565b6040518060c00160405280600690602082028036833780820191505090505090565b604051806020016040528060019060208202803683378082019150509050509056fea26469706673582212207280568e34fa291943a03e8e3ebba8b09a843007f347dc6629d3ba08d25b453264736f6c634300060a0033"};

    public static final String SM_BINARY = String.join("", SM_BINARY_ARRAY);

    public static final String FUNC_PROOF_LENGTH = "PROOF_LENGTH";

    public static final String FUNC_RANDOMVALUEFROMVRFPROOF = "randomValueFromVRFProof";

    public static final String FUNC_TESTRANDOMVALUEFROMVRFPROOF = "testRandomValueFromVRFProof";

    public static final Event TESTRANDOM_EVENT = new Event("TestRandom", 
            Arrays.<TypeReference<?>>asList(new TypeReference<StaticArray2<Uint256>>() {}, new TypeReference<StaticArray2<Uint256>>() {}, new TypeReference<StaticArray3<Uint256>>() {}, new TypeReference<Address>() {}, new TypeReference<StaticArray2<Uint256>>() {}, new TypeReference<StaticArray2<Uint256>>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected VRF(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(getBinary(), contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected VRF(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(getBinary(), contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected VRF(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(getBinary(), contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected VRF(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(getBinary(), contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static String getBinary() {
        return (EncryptType.encryptType == EncryptType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public static TransactionDecoder getTransactionDecoder() {
        return transactionDecoder;
    }

    public List<TestRandomEventResponse> getTestRandomEvents(TransactionReceipt transactionReceipt) {
        List<EventValuesWithLog> valueList = extractEventParametersWithLog(TESTRANDOM_EVENT, transactionReceipt);
        ArrayList<TestRandomEventResponse> responses = new ArrayList<TestRandomEventResponse>(valueList.size());
        for (EventValuesWithLog eventValues : valueList) {
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

    public RemoteCall<BigInteger> PROOF_LENGTH() {
        final Function function = new Function(FUNC_PROOF_LENGTH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> randomValueFromVRFProof(byte[] proof) {
        final Function function = new Function(FUNC_RANDOMVALUEFROMVRFPROOF, 
                Arrays.<Type>asList(new DynamicBytes(proof)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> testRandomValueFromVRFProof(byte[] proof) {
        final Function function = new Function(
                FUNC_TESTRANDOMVALUEFROMVRFPROOF,
                Arrays.<Type>asList(new DynamicBytes(proof)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public void testRandomValueFromVRFProof(byte[] proof, TransactionSucCallback callback) {
        final Function function = new Function(
                FUNC_TESTRANDOMVALUEFROMVRFPROOF,
                Arrays.<Type>asList(new DynamicBytes(proof)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String testRandomValueFromVRFProofSeq(byte[] proof) {
        final Function function = new Function(
                FUNC_TESTRANDOMVALUEFROMVRFPROOF,
                Arrays.<Type>asList(new DynamicBytes(proof)),
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
    public static VRF load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new VRF(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static VRF load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new VRF(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static VRF load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new VRF(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static VRF load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new VRF(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<VRF> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VRF.class, web3j, credentials, contractGasProvider, getBinary(), "");
    }

    @Deprecated
    public static RemoteCall<VRF> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VRF.class, web3j, credentials, gasPrice, gasLimit, getBinary(), "");
    }

    public static RemoteCall<VRF> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(VRF.class, web3j, transactionManager, contractGasProvider, getBinary(), "");
    }

    @Deprecated
    public static RemoteCall<VRF> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(VRF.class, web3j, transactionManager, gasPrice, gasLimit, getBinary(), "");
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
