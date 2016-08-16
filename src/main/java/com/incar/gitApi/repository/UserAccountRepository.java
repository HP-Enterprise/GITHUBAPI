package com.incar.gitApi.repository;

import com.incar.gitApi.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2016/8/15.
 */
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
   UserAccount findByUsername(String username);
}
