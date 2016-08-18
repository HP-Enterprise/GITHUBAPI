package com.incar.gitApi.service;

import com.incar.gitApi.entity.UserAccount;
import com.incar.gitApi.redis.SessionRedis;
import com.incar.gitApi.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by Administrator on 2016/8/17.
 */
@Service
public class TokenService {
    @Autowired
    private SessionRedis sessionRedis = null;

    /**
     * ͨ��userName ����һ��Ψһ��token
     * @param userName �û���
     * @return ���ܺ��Ψһ��
     */
    public String generateToken(String userName) {

        String token = MD5Util.getMD5ofStr(userName + System.currentTimeMillis() + this.getRandom(4));
        return token;
    }

    /**
     * ͨ��token ���� loginInfo
     * @param token ��½����
     * @return loginInfo����
     */

    public UserAccount loadToken(String token) {
        UserAccount userAccount = (UserAccount)sessionRedis.getSessionOfList(token);
        return userAccount;
    }

    /**
     * ����token ����½��Ϣ
     * @param token         ��½����
     * @param expireSeconds ����ʱ��
     * @return ״̬�� 0 �ɹ�  1 ʧ��
     */
    public int saveToken(String token, UserAccount userAccount, long expireSeconds) {
        sessionRedis.saveSessionOfList(token, userAccount, expireSeconds);
        return 0;
    }

    /**
     * ����token
     * @param token ��½����
     * @return ״̬��  0 �ɹ�  1 ʧ��
     */
    public int eraseToken(String token) {
        boolean res = sessionRedis.delSessionAllOfList(token);
        if(res){
            return 0;
        }
        return 1;
    }


    /**
     * ���������
     * @param length ���������
     * @return �����
     */
    public String getRandom(int length){

        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer saltStr = new StringBuffer();
        Random random = new Random();
        int range = buffer.length();

        for(int i = 0;i < length;i++){
            saltStr.append(buffer.charAt(random.nextInt(range)));
        }

        return saltStr.toString();
    }
}
