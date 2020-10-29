import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.temp.*;
import org.fisco.bcos.temp.BAC001;
import org.fisco.bcos.web3j.abi.datatypes.Address;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.Hash;
import org.fisco.bcos.web3j.crypto.Sign;
import org.fisco.bcos.web3j.crypto.gm.sm3.Util;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tuples.generated.Tuple3;
import org.fisco.bcos.web3j.tuples.generated.Tuple5;
import org.fisco.bcos.web3j.tuples.generated.Tuple7;
import org.fisco.bcos.web3j.tx.gas.ContractGasProvider;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.fisco.bcos.web3j.utils.Numeric;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigInteger;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.List;

import static org.fisco.bcos.web3j.crypto.Keys.getAddress;
import static org.fisco.bcos.web3j.crypto.Sign.signedMessageToKey;

public class VRFTest {

    BigInteger gasPrice = new BigInteger("1");
    BigInteger gasLimit = new BigInteger("2100000000");
    ContractGasProvider contractGasProvider = new StaticGasProvider(gasPrice, gasLimit);
    //根据私钥导入账户
    Credentials credentials = Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");
    Credentials credentialsBob = Credentials.create("2");
    // 生成随机私钥使用下面方法；
    // Credentials credentialsBob =Credentials.create(Keys.createEcKeyPair());
    String Bob = "0x2b5ad5c4795c026514f8317c7a215e218dccd6cf";
    String Owner = "0x148947262ec5e21739fe3a931c29e8b84ee34a0f";

    String Alice = "0x1abc9fd9845cd5a0acefa72e4f40bcfd4136f864";

    @Test
    public void testVRF() throws Exception {

        Web3j web3j = getWeb3j();
      //  System.out.println(Credentials.create("1").getAddress());
        //fist  secretRegistty
       VRF vrf = VRF.deploy(web3j, credentials, contractGasProvider).send();
     //  String sublime ="79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8220932710bb12fef86739686044ec7659904ab9992085b5f08eac3d80e610b7ec662bf88aca6461cecb8e49b221e087f5fb6a6cbcda0296ae99dc8c3b5cdb9d66ed60938b1c9584e331983c721d81e44366714a21ccb454d11a1b3753c180f1a9129f6c74e36a7b1cce67c38de27e1ba8447c844927d5aeeae30ab17941e322a00000000000000000000000000000000000000000000000000000000000000020000000000000000000000006813eb9362372eef6200f3b1dbc3f819671cba6915c8542f131c37b8af7de971945f2411785a3819323b9b8e021162158ba56036d61fc4144a90a645632a304d4a1756e9234570c2464e268358b0b058d028288b8f4ff1c1f41c33eac4000859601456a38ff38c0c601b9a9ed6abc01248141c17260456a8282ec82b3fb0e932d5b5ded0af3bb9d2edfb8621d43b81a96ab723b1a179a40a635c715d34699ce19f6774af703b13bb0efd13478d53be627e519a2d";
       String s ="0xb015f8044f5fcbdcf21ca26d6c34fb8197829205c7b7d2a7cb66418c157b112cab8c1e086d04e813744a655b2df8d5f83b3cdc6faa3088c1d3aea1454e3a1d5f0c7e532826ca771636f9430be430ff0905a97cdc5378aec06854e1bcc4cec42e65089603d0c65e5d84a295169b664ae2fff258220ca0a2c716ea05744792dee7ecf04e000751e54cee9372b7676bd5e3cdf38eb326ca556ebb241b672087a8f6594901f956764cfadbcc9712deda529710dd8195971f70a185a2d5f3124e498c0000000000000000000000000000000000000000000000000000000000c831f60000000000000000000000001cc0c65ca5dd6b767338946f2c44c02040744ef52bc36732922c89b38dc35f684e632c52174097c158d05a4dc28aa3dec591f4cb0cb6ec42358ee1b0f9d3c4a92eb74a5dffcabeccb7ed5d628ebbb57bb3876722ef94966c20b32ef87cbf1f50a767016dbbaa5cf234350738dfa9700ebe3fce19367866cf7241ee0ea1362578c01578ec2a9dcfbc0e96dbcf45945eda665ca9cc592cb7b588f4c2268024b8f1524768198d5e0904e45c9e5bb8e1718bb85b18f7";

        byte[] i= Numeric.hexStringToByteArray(s);
//        byte[] i1 = hexStringtoBytes(s);
////        TransactionReceipt ttt = vrf.valueFromVRFProof(i).send();
////        List<VRF.ValueFromVRFProofEventResponse> logs =  vrf.getValueFromVRFProofEvents(ttt);
        TransactionReceipt t = vrf.testRandomValueFromVRFProof(i).send();

        System.out.println(t.getStatus());
        System.out.println(t.getOutput());
        VRF.TestRandomEventResponse log =  vrf.getTestRandomEvents(t).get(0);
//        String pkx = log.pk.get(0).toString(16);
//        String pky = log.pk.get(1).toString(16);
//        String gammax = log.gamma.get(0).toString(16);
//        String gammay = log.gamma.get(1).toString(16);
//
//        String c = log.cSSeed.get(0).toString(16);
//        String S = log.gamma.get(1).toString(16);
//        String seed = log.gamma.get(2).toString(16);
//
//        String cGammaWitnessX =   log.cGammaWitness.get(0).toString(16);
//        String cGammaWitnessY =   log.cGammaWitness.get(1).toString(16);

        TransactionReceipt tt = vrf.randomValueFromVRFProof(i).send();

        System.out.println(tt.getStatus());
        System.out.println(tt.getOutput());
      //  System.out.println(DecodeOutputUtils.decodeOutputReturnString0x16(tt.getOutput()));


        //System.out.println(t);
    }

    public byte[]  hexStringtoBytes(String s) {
        byte[] val = new byte[s.length() / 2];
        for (int i = 0; i < val.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(s.substring(index, index + 2), 16);
            val[i] = (byte) j;
        }
        return val;
    }

    @Test
    public void testVRFCoordinator() throws Exception {

        Web3j web3j = getWeb3j();
        System.out.println(Credentials.create("1").getAddress());
        //fist  secretRegistty
        VRFCoordinator vrfCoordinator = VRFCoordinator.deploy(web3j, credentials, contractGasProvider).send();
      //  String s ="0x79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8220932710bb12fef86739686044ec7659904ab9992085b5f08eac3d80e610b7ec662bf88aca6461cecb8e49b221e087f5fb6a6cbcda0296ae99dc8c3b5cdb9d69b3c9054e20e8dd14e82c7d37de1b3a9aad5449854825d3cce6475d2148aced764c36fab1df1722eb17d382c821e4c550fd9984e5ac642fef16de8babbab726b00000000000000000000000000000000000000000000000000000000000000020000000000000000000000007e5f4552091a69125d5dfcb7b8c2659029395bdfaa9881ee471e42fc5327067e85bf914f4384aa36954c831dc48b96b6b38a7d07c38f5bfc7eccbb81f71ff0dfab91a09fa4725d4f26f60e7ff206c6383aad575b6cea027120ad41374b9720edf0e9bc8a8e09a9ffddda6d925054ae71dc6468ff4bcf24d770067e334e2c7f9722bbe09ea6031aa63be8311557296986dd4dccf03fbe9e7d30509914af77cffa148ee040c85ace282707527eb07f8db31ced4b0a";
          String s ="0xb015f8044f5fcbdcf21ca26d6c34fb8197829205c7b7d2a7cb66418c157b112cab8c1e086d04e813744a655b2df8d5f83b3cdc6faa3088c1d3aea1454e3a1d5f0c7e532826ca771636f9430be430ff0905a97cdc5378aec06854e1bcc4cec42e65089603d0c65e5d84a295169b664ae2fff258220ca0a2c716ea05744792dee7ecf04e000751e54cee9372b7676bd5e3cdf38eb326ca556ebb241b672087a8f6594901f956764cfadbcc9712deda529710dd8195971f70a185a2d5f3124e498c0000000000000000000000000000000000000000000000000000000000c831f60000000000000000000000001cc0c65ca5dd6b767338946f2c44c02040744ef52bc36732922c89b38dc35f684e632c52174097c158d05a4dc28aa3dec591f4cb0cb6ec42358ee1b0f9d3c4a92eb74a5dffcabeccb7ed5d628ebbb57bb3876722ef94966c20b32ef87cbf1f50a767016dbbaa5cf234350738dfa9700ebe3fce19367866cf7241ee0ea1362578c01578ec2a9dcfbc0e96dbcf45945eda665ca9cc592cb7b588f4c2268024b8f1524768198d5e0904e45c9e5bb8e1718bb85b18f7";
        String blockNumber = "0000000000000000000000000000000000000000000000000000000000000005";
        byte[] i= Numeric.hexStringToByteArray(s+blockNumber);
        TransactionReceipt t  = vrfCoordinator.fulfillRandomnessRequest(i).send();
        System.out.println("address:" + vrfCoordinator.getContractAddress());

//        TransactionReceipt ttt = vrf.valueFromVRFProof(i).send();
//        List<VRF.ValueFromVRFProofEventResponse> logs =  vrf.getValueFromVRFProofEvents(ttt);
//        TransactionReceipt t = vrf.randomValueFromVRFProof(i).send();
       // TransactionReceipt t = vrf.c(i).send();


        System.out.println(t.getStatus());
        System.out.println(t.getOutput());
//
        System.out.println(DecodeOutputUtils.decodeOutputReturnString0x16(t.getOutput()));
    }


    @Test
    public void testRandomNumberConsumer() throws Exception {


        System.out.println("deploy vrf coordinator");
        Web3j web3j = getWeb3j();
       // System.out.println(Credentials.create("1").getAddress());
        //fist  secretRegistty
       VRFCoordinator vrfCoordinator = VRFCoordinator.deploy(web3j, credentials, contractGasProvider).send();
      // interface VRFCoordinator vrfCoordinator = VRFCoordinator.load("0xcfdaa4a02061d0ccb4357dcf1a607b8fac9b57c0",web3j, credentials, contractGasProvider);
     //call
       // VRFCoordinator vrfCoordinator = VRFCoordinator.load("0xb75538117d2bb216af46b9cb8928f3b0d5963f14",web3j, credentials, contractGasProvider);
        //  String s ="0x79be667ef9dcbbac55a06295ce870b07029bfcdb2dce28d959f2815b16f81798483ada7726a3c4655da4fbfc0e1108a8fd17b448a68554199c47d08ffb10d4b8220932710bb12fef86739686044ec7659904ab9992085b5f08eac3d80e610b7ec662bf88aca6461cecb8e49b221e087f5fb6a6cbcda0296ae99dc8c3b5cdb9d69b3c9054e20e8dd14e82c7d37de1b3a9aad5449854825d3cce6475d2148aced764c36fab1df1722eb17d382c821e4c550fd9984e5ac642fef16de8babbab726b00000000000000000000000000000000000000000000000000000000000000020000000000000000000000007e5f4552091a69125d5dfcb7b8c2659029395bdfaa9881ee471e42fc5327067e85bf914f4384aa36954c831dc48b96b6b38a7d07c38f5bfc7eccbb81f71ff0dfab91a09fa4725d4f26f60e7ff206c6383aad575b6cea027120ad41374b9720edf0e9bc8a8e09a9ffddda6d925054ae71dc6468ff4bcf24d770067e334e2c7f9722bbe09ea6031aa63be8311557296986dd4dccf03fbe9e7d30509914af77cffa148ee040c85ace282707527eb07f8db31ced4b0a";
        System.out.println("coordinate address : " + vrfCoordinator.getContractAddress());
       String keyhash = "eedf1a9c68b3f4a8b1a1032b2b5ad5c4795c026514f8317c7a215e218dccd6cf";
        byte[] keyhashbyte = hexStringtoBytes(keyhash);
        System.out.println("deploy consumer  contract");

        RandomNumberConsumer randomNumberConsumer = RandomNumberConsumer.deploy(web3j,credentials,contractGasProvider,vrfCoordinator.getContractAddress(),keyhashbyte).send();
      // RandomNumberConsumer randomNumberConsumer = RandomNumberConsumer.load("0x0382f73a5924aa2dbf51429421275a8541eb53c3", web3j,credentials,contractGasProvider);
        System.out.println("consumer address: " + randomNumberConsumer.getContractAddress() );
        TransactionReceipt randomT =  randomNumberConsumer.getRandomNumber(new BigInteger("1")).send();
        System.out.println(randomT.getStatus());
      //  System.out.println(randomT.getOutput());
        System.out.println("consumer reqId: "+randomT.getOutput());

        System.out.println("coordinate listen to the event");
        VRFCoordinator.RandomnessRequestEventResponse randomevent =vrfCoordinator.getRandomnessRequestEvents(randomT).get(0);
       // vrfCoordinator.callbacks(randomevent.requestID);

        System.out.println("hash:" + bytesToHex(randomevent.keyHash));
        System.out.println("preseed " + randomevent.seed);
        System.out.println("blocknumber: " + randomevent.blockNumber);

        System.out.println("sender: "+ randomevent.sender);
        System.out.println("requestId: " +bytesToHex(randomevent.requestID));
        System.out.println("seedAndBlock:        " +bytesToHex(randomevent.seedAndBlockNum));

//        Tuple2<String, byte[]> callback =  vrfCoordinator.callbacks(randomevent.requestID).send();
//        System.out.println("callback add: "+ callback.getValue1());
//        System.out.println("callback seedandblock: "+ bytesToHex(callback.getValue2()));
//        byte[] seedAndBlock  = CryptoUtil.soliditySha3( randomevent.seed, randomevent.blockNumber);
//        System.out.println("seedAndBlockoffline: " + Numeric.toHexString(seedAndBlock));

//        byte[] vrfseed  = CryptoUtil.soliditySha3( randomevent.keyHash, new BigInteger("1"),new Address(randomNumberConsumer.getContractAddress()),new BigInteger("0"));
//        System.out.println("vrfseed: " + Numeric.toHexString(vrfseed));
//        byte[] requestId1 =  CryptoUtil.soliditySha3(randomevent.keyHash,new BigInteger(Numeric.toHexString(vrfseed).substring(2),16));
//        System.out.println("requestId1: "+ bytesToHex(requestId1));

        BigInteger preseed = randomevent.seed;
        BigInteger blockNumber = randomevent.blockNumber;
        byte[] bnbytes =Numeric.toBytesPadded(blockNumber, 32);
//        String blockHash = bytesToHex(CryptoUtil.soliditySha3(blockNumber));
//
//         String actualSeed = bytesToHex(CryptoUtil.soliditySha3(randomevent.seed, CryptoUtil.solidityBytes(blockNumber)));

     //   System.out.println("credential: " + credentialsBob.getEcKeyPair().getPrivateKey().toString());

        String proof =  LibVRF.INSTANCE.VRFProoFGenerate(credentialsBob.getEcKeyPair().getPrivateKey().toString(),preseed.toString(16));

        Thread.sleep(10);
        byte[] i= Numeric.hexStringToByteArray(proof);
        byte[] destination = new byte[i.length + 32];
        System.arraycopy(i, 0, destination, 0, i.length);
        System.arraycopy(bnbytes, 0, destination, i.length, 32);

        TransactionReceipt t  = vrfCoordinator.fulfillRandomnessRequest(destination).send();
//        Tuple5<byte[], byte[], BigInteger, List<BigInteger>, BigInteger> result = vrfCoordinator.getRandomnessFromProof1(destination).send();
//        System.out.println("hash: " + bytesToHex(result.getValue1()));
//        System.out.println("requestId: " + bytesToHex(result.getValue2()));
//        System.out.println("random: " + result.getValue3());
//        System.out.println("preseed: " + result.getValue5());

//         List ilist = new ArrayList<BigInteger>();
//         ilist.add(new BigInteger("89565891926547004231252920425935692360644145829622209833684329913297188986597"));
//         ilist.add(new BigInteger("12158399299693830322967808612713398636155367887041628176798871954788371653930"));
//
//        TransactionReceipt r1 = (TransactionReceipt) vrfCoordinator.hashOfKey(ilist).send();
//        System.out.println("hash of key: "+ r1.getOutput());

        System.out.println(t.getStatus());
        System.out.println(t.getOutput());
      //  System.out.println(DecodeOutputUtils.decodeOutputReturnString0x16(t.getOutput()));

         VRFCoordinator.RandomnessRequestFulfilledEventResponse res =vrfCoordinator.getRandomnessRequestFulfilledEvents(t).get(0);

        System.out.println("ramdom result: " +res.output);
        System.out.println("requestId: " +bytesToHex(res.requestId));

        System.out.println("check consumer contract result");
        BigInteger ram = randomNumberConsumer.randomResult().send();
        System.out.println(" ram: "+  ram.toString(16));
     //   System.out.println(DecodeOutputUtils.decodeOutputReturnString0x16(t.getOutput()));
    }



    @Test
    public void testJNA() {
//        Pointer p1 = new Memory(15);
//        p1.setS();

        String hexString = "6ed60938b1c9584e331983c721d81e44366714a21ccb454d11a1b3753c180f1a";
//        String string = new BigInteger("8f4ff1c1f41c33eac4000859601456a38ff38c0c601b9a9ed6abc01248141c17",16).toString(10);
//        System.out.println(string);
//        String string1 = new BigInteger("260456a8282ec82b3fb0e932d5b5ded0af3bb9d2edfb8621d43b81a96ab723b1",16).toString(10);
//        System.out.println(string1);
        String sk= "1a";
        String seed= "1b";


       String result =  LibVRF.INSTANCE.VRFProoFGenerate(sk,seed);
        System.out.println(result);
    }



    @Test
    public void testSha() {
        String keyhash = "6c3699283bda56ad74f6b855546325b68d482e983852a7a82979cc4807b641f4";
        System.out.println("credential: " + credentialsBob.getEcKeyPair().getPublicKey().toString());

         String requestId = "0x3B8588274FD75969C29B0DB8C63D7E5716DFDEA31F8FDF229E041896C5FAF745";
         String keyhash1 = "0x3B8588274FD75969C29B0DB8C63D7E5716DFDEA31F8FDF229E041896C5FAF745";
         String vrfInput = "";
         byte[] vrfseed  = CryptoUtil.soliditySha3( hexStringtoBytes(keyhash), new BigInteger("1"),new Address("0xe5fd2eb6f001ea1d2675103f3047563bb4c0ab48"),new BigInteger("0"));
        System.out.println(new BigInteger(bytesToHex(vrfseed),16));
    }
    private Web3j getWeb3j() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("start --------------------- ");
        Service service = context.getBean(Service.class);
        service.run();
        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);


        return Web3j.build(channelEthereumService, service.getGroupId());
    }


    public  String  signatureDataToString(Sign.SignatureData signatureData)
    {
        byte[] byte_3 = new byte[1+signatureData.getR().length+signatureData.getS().length];

        System.arraycopy(signatureData.getR(), 0, byte_3, 0, signatureData.getR().length);
        System.arraycopy(signatureData.getS(), 0, byte_3, signatureData.getR().length, signatureData.getS().length);
        byte_3[signatureData.getR().length+signatureData.getS().length] = signatureData.getV();
        return  Numeric.toHexString(byte_3,0,byte_3.length,false);
    }
    static public  byte[]  signatureDataToBytes(Sign.SignatureData signatureData)
    {
        byte[] byte_3 = new byte[1+signatureData.getR().length+signatureData.getS().length];

        System.arraycopy(signatureData.getR(), 0, byte_3, 0, signatureData.getR().length);
        System.arraycopy(signatureData.getS(), 0, byte_3, signatureData.getR().length, signatureData.getS().length);
        byte_3[signatureData.getR().length+signatureData.getS().length] = signatureData.getV();
        return  byte_3;
    }


    public static void dealWithReceipt(TransactionReceipt transactionReceipt) {
        if ("0x16".equals(transactionReceipt.getStatus()) && transactionReceipt.getOutput().startsWith("0x08c379a0")) {
            System.out.println("0x16");
            System.out.println(DecodeOutputUtils.decodeOutputReturnString0x16(transactionReceipt.getOutput()));
        }
    }


















    // 存钱
    public Sign.SignatureData testHash(byte[] bytes) throws SignatureException {

        // String message = "\\x19Ethereum Signed Message:\n" + hash1.length() + hash1;

        //    byte[] data = message.getBytes();
        //   System.out.println("0x" + bytesToHex(Hash.sha3(bytes)));
        Credentials c = Credentials.create("b83261efa42895c38c6c2364ca878f43e77f3cddbc922bf57d0d48070f79feb6");

        System.out.println("address " + c.getAddress());
        Sign.SignatureData sigData = Sign.getSignInterface().signMessage(bytes, c.getEcKeyPair());


        System.out.println("Signature v value: " + (int) sigData.getV());
        System.out.println("Signature r value: 0x" + bytesToHex(sigData.getR()));
        System.out.println("Signature s value: 0x" + bytesToHex(sigData.getS()));

        System.out.println("Signature: 0x" + HashTest.signatureDataToString(sigData));
        System.out.println("address " + "0x" + getAddress(signedMessageToKey(Hash.sha3(bytes), sigData)));
        return sigData;
    }



    private static String bytesToHex(byte[] bytes)
    {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ )
        {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        String finalHex = new String(hexChars);
        return finalHex;
    }


}
