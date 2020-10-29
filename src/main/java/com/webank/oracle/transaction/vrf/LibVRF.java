package com.webank.oracle.transaction.vrf;

import com.sun.jna.Library;
import com.sun.jna.Native;
import org.springframework.core.io.ClassPathResource;

public interface LibVRF extends Library {
    LibVRF INSTANCE = (LibVRF) Native.loadLibrary(new ClassPathResource("./libvrf.dylib").getPath(), LibVRF.class);

    String VRFProoFGenerate(String sk,String preseed);
}