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
     * 通过userName 生成一个唯一的token
     * @param userName 用户名
     * @return 加密后的唯一码
     */
    public String generateToken(String userName) {

        String token = MD5Util.getMD5ofStr(userName + System.currentTimeMillis() + this.getRandom(4));
        return token;
    }

    /**
     * 通过token 解析 loginInfo
     * @param token 登陆令牌
     * @return loginInfo对象
     */

    public UserAccount loadToken(String token) {
        UserAccount userAccount = (UserAccount)sessionRedis.getSessionOfList(token);
        return userAccount;
    }

    /**
     * 保存token 及登陆信息
     * @param token         登陆令牌
     * @param expireSeconds 保存时间
     * @return 状态码 0 成功  1 失败
     */
    public int saveToken(String token, UserAccount userAccount, long expireSeconds) {
        sessionRedis.saveSessionOfList(token, userAccount, expireSeconds);
        return 0;
    }

    /**
     * 销毁token
     * @param token 登陆令牌
     * @return 状态码  0 成功  1 失败
     */
    public int eraseToken(String token) {
        boolean res = sessionRedis.delSessionAllOfList(token);
        if(res){
            return 0;
        }
        return 1;
    }


    /**
     * 生成随机数
     * @param length 随机数长度
     * @return 随机数
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
