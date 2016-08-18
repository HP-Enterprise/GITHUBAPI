package com.incar.gitApi.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/8/17.
 */
@Component
public class SessionRedis {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedisTemplate<String, Object> objectRedisTemplate;

    private String[] preStr = {"session:","code:"};
    private String sessionKey = "";

    ValueOperations<String,String> valOpts = null;
    ValueOperations<String,Object> valObjOpts = null;

    private void setRedisTemplatePro(){
        this.objectRedisTemplate.setKeySerializer(this.objectRedisTemplate.getStringSerializer());
        this.objectRedisTemplate.setValueSerializer(this.objectRedisTemplate.getDefaultSerializer());
        this.objectRedisTemplate.afterPropertiesSet();
    }

    public String setPreOfKey(int index,String sessionId){
        return this.preStr[index] + sessionId;
    }

    private long defaultExpireSeconds(int hours){
        long expire = 0;
        expire = hours * 3600;
        return expire;
    }

    public void saveSessionOfVal(String sessionId,String sessionValue,long ... expireSeconds){

        sessionKey = setPreOfKey(1, sessionId);
        this.valOpts = this.stringRedisTemplate.opsForValue();

        if(!this.stringRedisTemplate.hasKey(sessionKey)){
            valOpts.set(sessionKey, sessionValue);
            if(expireSeconds.length != 0) {
                this.stringRedisTemplate.expire(sessionKey, expireSeconds[0], TimeUnit.SECONDS);
            }
            else {
                this.stringRedisTemplate.expire(sessionKey, this.defaultExpireSeconds(24), TimeUnit.SECONDS);
            }
        }

    }

    public String getSessionOfVal(String sessionId){
        sessionKey = setPreOfKey(1,sessionId);
        this.valOpts = this.stringRedisTemplate.opsForValue();
        if(!this.stringRedisTemplate.hasKey(sessionKey)){
            return null;
        }
        return valOpts.get(sessionKey);

    }

    public void updateSessionOfVal(String sessionId,String sessionValue){

        sessionKey = setPreOfKey(1, sessionId);
        long expireSeconds = this.stringRedisTemplate.getExpire(sessionKey);
        this.delSessionOfVal(sessionId);
        this.saveSessionOfVal(sessionId,sessionValue,expireSeconds);

    }

    public boolean delSessionOfVal(String sessionId){
        sessionKey = setPreOfKey(1, sessionId);
        boolean ret = true;
        if(this.stringRedisTemplate.hasKey(sessionKey)){
            this.stringRedisTemplate.delete(sessionKey);
        }
        else {
            return false;
        }

        return ret;
    }


    public void saveSessionOfList(String sessionId,Object sessionValue,long ... expireSeconds){

        sessionKey = setPreOfKey(0,sessionId);
        this.setRedisTemplatePro();
        this.valObjOpts = this.objectRedisTemplate.opsForValue();

        if(!this.objectRedisTemplate.hasKey(sessionKey)){
            this.valObjOpts.set(sessionKey, sessionValue);

            if(expireSeconds.length != 0) {
                this.objectRedisTemplate.expire(sessionKey,expireSeconds[0],TimeUnit.SECONDS);
            }
            else {
                this.objectRedisTemplate.expire(sessionKey,this.defaultExpireSeconds(24),TimeUnit.SECONDS);
            }
        }

    }

    public Object getSessionOfList(String sessionId){
        sessionKey = setPreOfKey(0,sessionId);
        this.setRedisTemplatePro();
        this.valObjOpts = this.objectRedisTemplate.opsForValue();
        if(!this.objectRedisTemplate.hasKey(sessionKey)){
            return null;
        }
        return this.valObjOpts.get(sessionKey);
    }

    public List<Object> getSessionOfList(){

        this.setRedisTemplatePro();
        this.valObjOpts = this.objectRedisTemplate.opsForValue();

        Set<String> setKey = this.objectRedisTemplate.keys("session*");

        return this.valObjOpts.multiGet(setKey);
    }

    public void updateSessionOfList(String sessionId,Object sessionValue){
        sessionKey = setPreOfKey(0,sessionId);
        long expireSeconds = this.objectRedisTemplate.getExpire(sessionKey);
        this.delSessionAllOfList(sessionId);
        this.saveSessionOfList(sessionId,sessionValue,expireSeconds);
    }

    public boolean delSessionAllOfList(String sessionId){
        sessionKey = setPreOfKey(0,sessionId);
        boolean ret = true;
        if(this.objectRedisTemplate.hasKey(sessionKey)){
            this.objectRedisTemplate.delete(sessionKey);
        }
        else {
            return false;
        }

        return ret;

    }

}
