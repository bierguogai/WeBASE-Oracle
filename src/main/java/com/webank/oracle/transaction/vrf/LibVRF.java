package com.webank.oracle.transaction.vrf;

import org.springframework.core.io.ClassPathResource;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface LibVRF extends Library {

    /**
     *
     * @param sk
     * @param preseed
     * @return
     */
    public String VRFProoFGenerate(String sk, String preseed);

    public class InstanceHolder {
        private static LibVRF instance = null;

        static {
            String os = System.getProperty("os.name").toLowerCase();
            String libExtension;
            if (os.contains("mac os")) {
                libExtension = "dylib";
            } else if (os.contains("windows")) {
                libExtension = "dll";
            } else {
                libExtension = "so";
            }

            String lib = "./libvrf." + libExtension;

            instance = Native.loadLibrary(new ClassPathResource(lib).getPath(), LibVRF.class);
        }

        public static LibVRF getInstance() {
            return instance;
        }
    }
}