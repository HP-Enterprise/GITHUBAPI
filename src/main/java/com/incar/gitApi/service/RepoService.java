package com.incar.gitApi.service;

import com.incar.gitApi.GithubClientConfig;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.GistService;
import org.eclipse.egit.github.core.service.GitHubService;
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

    /**
     * 查询个人仓库
     * @return
     * @throws IOException
     */
    public List<Repository> getRepository() throws IOException {
        RepositoryService repositoryService = new RepositoryService(githubClientConfig.getGitHubClient());
        List<Repository> repositoryList = repositoryService.getRepositories(githubClientConfig.getUsername());
        return repositoryList;
    }

    /**
     * 根据仓库名称更改仓库信息
     * @param organization
     * @param repo
     * @param repository
     * @return
     * @throws IOException
     */
    public Repository editRepository(String organization,String repo,Repository repository) throws IOException {
        GitHubService gitHubService=new GistService(githubClientConfig.getGitHubClient());
        if(repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        } else {
            StringBuilder uri = new StringBuilder("/repos");
            uri.append('/').append(organization).append('/').append(repo);
            return (Repository)gitHubService.getClient().post(uri.toString(),repository,Repository.class);
        }
    }

    /**
     * 删除仓库
     * @param organization
     * @param repository
     * @throws IOException
     */
    public void  deleteRepository(String organization,String  repository) throws IOException {
        GitHubService gitHubService=new GistService(githubClientConfig.getGitHubClient());
        if(repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        } else {
            StringBuilder uri = new StringBuilder("/repos");
            uri.append('/').append(organization).append('/').append(repository);
            gitHubService.getClient().delete(uri.toString());
        }
    }

}
