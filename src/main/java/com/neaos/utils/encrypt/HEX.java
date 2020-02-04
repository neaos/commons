package com.neaos.utils.encrypt;

@SuppressWarnings("WeakerAccess")
public class HEX {
    public static String encrypt(byte[] b) {
        StringBuilder sb = new StringBuilder();
        int i;
        for (byte b1 : b) {
            i = b1;
            if (i < 0)
                i += 256;
            if (i < 16)
                sb.append("0");
            sb.append(Integer.toHexString(i));
        }
        return sb.toString();
    }
}
