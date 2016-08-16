package com.incar.gitApi.service;

import com.incar.gitApi.entity.UserAccount;
import com.incar.gitApi.repository.UserAccountRepository;
import com.incar.gitApi.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/8/15.
 */
@Service
public class UserAccountService {
    @Autowired
    private UserAccountRepository userAccountRepository;

    /**
     * 验证账号密码
     *
     * @param username 用户名
     * @param password 密码
     * @return 0：验证成功   1：用户名不存在    2：密码错误
     */
    public int login(String username, String password) {
        UserAccount userAccount = userAccountRepository.findByUsername(username);
        if (userAccount == null) {
            return 1;
        }
        String Md5password = MD5Util.getMD5ofStr(password);
        if (userAccount.getPassword().equals(Md5password)) {
            return 0;
        }
        return 2;
    }

    /**
     * 添加用户
     * @param userAccount
     * @return 0：成功  1：失败  -1：用户名已存在
     */
    public int addUserAccount(UserAccount userAccount) {
        try {
            UserAccount userAccount1 = userAccountRepository.findByUsername(userAccount.getUsername());
            if (userAccount != null) {
                return -1;
            }
            UserAccount userAccount2 = new UserAccount();
            userAccount2.setPassword(MD5Util.getMD5ofStr(userAccount.getPassword()));
            userAccount2.setUsername(userAccount.getUsername());
            userAccountRepository.save(userAccount2);
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

    /**
     * 删除用户
     * @param username
     * @return
     */
    public int removeUserAccount(String username){
         UserAccount userAccount=userAccountRepository.findByUsername(username);
        userAccountRepository.delete(userAccount);
        return 0;
    }

    /**
     * 修改密码
     * @param username
     * @param password
     * @return
     */
    public int modifyPassword(String username,String password){
       UserAccount userAccount= userAccountRepository.findByUsername(username);
        if(userAccount==null){
            return 1;
        }
        userAccount.setPassword(MD5Util.getMD5ofStr(password));
        userAccountRepository.save(userAccount);
        return 0;

    }
}
