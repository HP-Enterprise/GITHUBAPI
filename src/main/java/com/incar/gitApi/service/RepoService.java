package com.incar.gitApi.service;

import com.google.gson.reflect.TypeToken;
import com.incar.gitApi.GithubClientConfig;
import org.eclipse.egit.github.core.*;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PagedRequest;
import org.eclipse.egit.github.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2016/7/19.
 */
@Service
public class RepoService {
    private Repository repository1;
    @Autowired
    private GithubClientConfig githubClientConfig;
    @Autowired
    private WorkService workService;


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
     *
     * @param organization 组织名
     * @return
     * @throws IOException
     */
    public List<Repository> getAllRepository(String organization, String token) throws IOException {
        RepositoryService repositoryService = new RepositoryService(githubClientConfig.getClient(token));
        List<Repository> repositoryList = repositoryService.getOrgRepositories(organization);
        return repositoryList;
    }

    /**
     * 添加组织仓库
     *
     * @param organization
     * @param repository
     * @return
     * @throws IOException
     */
    public Repository addOrgRepository(String organization, Repository repository, String token) throws IOException {
        RepositoryService repositoryService = new RepositoryService(githubClientConfig.getClient(token));
        repository.setHasIssues(true);
        Repository repository2 = repositoryService.createRepository(organization, repository);
        return repository2;
    }

    /**
     * 查询个人仓库
     *
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
     *
     * @param organization
     * @param repo
     * @param repository
     * @return
     * @throws IOException
     */
    public Repository editRepository(String organization, String repo, Repository repository, String token) throws IOException {
        GitHubService gitHubService = new GistService(githubClientConfig.getGitHubClient());
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        } else {
            StringBuilder uri = new StringBuilder("/repos");
            uri.append('/').append(organization).append('/').append(repo);
            return (Repository) gitHubService.getClient().post(uri.toString(), repository, Repository.class);
        }
    }

    /**
     * 删除仓库
     *
     * @param organization
     * @param repository
     * @throws IOException
     */
    public void deleteRepository(String organization, String repository, String token) throws IOException {
        GitHubService gitHubService = new GistService(githubClientConfig.getClient(token));
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        } else {
            StringBuilder uri = new StringBuilder("/repos");
            uri.append('/').append(organization).append('/').append(repository);
            gitHubService.getClient().delete(uri.toString());
        }
    }

    /**
     * 获取项目的贡献
     * @param org
     * @param repository
     * @param token
     * @return
     * @throws IOException
     */

    public List<Contributor> getcontributor(String org, String repository, String token) throws IOException {
        RepositoryService repositoryService = new RepositoryService(githubClientConfig.getClient(token));
        IRepositoryIdProvider iRepositoryIdProvider = new IRepositoryIdProvider() {
            @Override
            public String generateId() {
                return org + "/" + repository;
            }
        };
        List<Contributor> list = repositoryService.getContributors(iRepositoryIdProvider, false);
        List<Contributor> contributorList = new ArrayList<>();
        Properties properties = workService.getRealnameProperties();
        for (Contributor contributor : list) {
            if (contributor.getLogin() != null) {
                Object obj = properties.get(contributor.getLogin());
                if (obj != null) {
                    contributor.setName(obj.toString());
                }
            }
            contributorList.add(contributor);
        }
        return contributorList;
    }

    /**
     * 获取项目的分支
     * @param org
     * @param repository
     * @param token
     * @return
     * @throws IOException
     */
    public List<RepositoryBranch> getRepositoryBranch(String org, String repository, String token) throws IOException {
        RepositoryService repositoryService = new RepositoryService(githubClientConfig.getClient(token));
        IRepositoryIdProvider iRepositoryIdProvider = new IRepositoryIdProvider() {
            @Override
            public String generateId() {
                return org + "/" + repository;
            }
        };
        List<RepositoryBranch> list = repositoryService.getBranches(iRepositoryIdProvider);

        return list;
    }
    public List<RepositoryCommit> getCommits(String org, String repository, String token) throws IOException {
        CommitService commitService= new CommitService(githubClientConfig.getClient(token));
        IRepositoryIdProvider iRepositoryIdProvider = new IRepositoryIdProvider() {
            @Override
            public String generateId() {
                return org + "/" + repository;
            }
        };
        List<RepositoryCommit> list = commitService.getCommits(iRepositoryIdProvider);

        return list;
    }
}
