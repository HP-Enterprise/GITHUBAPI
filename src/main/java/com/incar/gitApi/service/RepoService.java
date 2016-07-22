package com.incar.gitApi.service;

import com.incar.gitApi.GithubClientConfig;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/7/19.
 */
@Service
public class RepoService {
    private Repository repository1;
    @Autowired
    private GithubClientConfig githubClientConfig;

    /**
     * 添加个人新仓库
     *
     * @param repository
     * @return
     */
    public Repository addRepository(Repository repository) {
        RepositoryService repositoryService = new RepositoryService(githubClientConfig.getGitHubClient());
        try {
            repository1 = repositoryService.createRepository(repository);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return repository1;
    }

    /**
     * 根据organization查询组织所有的仓库
     * @param organization 组织名
     * @return
     * @throws IOException
     */
    public List<Repository> getAllRepository(String organization) throws IOException {
        RepositoryService repositoryService = new RepositoryService(githubClientConfig.getGitHubClient());
        List<Repository> repositoryList = repositoryService.getOrgRepositories(organization);
        return repositoryList;
    }

    /**
     * 添加组织仓库
     * @param organization
     * @param repository
     * @return
     * @throws IOException
     */
    public Repository addOrgRepository(String organization, Repository repository) throws IOException {
        RepositoryService repositoryService = new RepositoryService(githubClientConfig.getGitHubClient());
        Repository repository2 = repositoryService.createRepository(organization, repository);
        return repository2;
    }

}
