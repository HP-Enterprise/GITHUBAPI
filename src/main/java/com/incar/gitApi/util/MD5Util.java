package com.incar.gitApi.util;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by Administrator on 2016/8/15.
 */
public class MD5Util {
    public MD5Util(){}
    public static String getMD5ofStr(String inbuf) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(inbuf.getBytes());
            byte[] buf = md5.digest();
            return DatatypeConverter.printHexBinary(buf);
        }
        catch(Exception ex){
            return null;
        }
    }

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16).toUpperCase();
    }


}
