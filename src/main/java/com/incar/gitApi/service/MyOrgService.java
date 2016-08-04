package com.incar.gitApi.service;

import com.incar.gitApi.GithubClientConfig;
import org.eclipse.egit.github.core.User;
import org.eclipse.egit.github.core.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;


/**
 * Created by Administrator on 2016/8/1.
 */
@Service
public class MyOrgService {
    @Autowired
    private GithubClientConfig githubClientConfig;

    /**
     * 查询该组织的成员
     * @param organization
     * @return
     * @throws IOException
     */
    public List<User> getOrgMembers(String organization)throws IOException{
        OrganizationService organizationService=new OrganizationService(githubClientConfig.getGitHubClient());
      List<User> userList=  organizationService.getMembers(organization);
       return userList;
    }

    /**
     * 查询用户的所有组织名
     * @return
     * @throws IOException
     */
    public List<User> getOrganization()throws IOException{
        OrganizationService organizationService=new OrganizationService(githubClientConfig.getGitHubClient());
        List<User> users=  organizationService.getOrganizations(githubClientConfig.getUsername());
        return users;
    }
}
