package com.incar.gitApi.service;
import com.incar.gitApi.entity.Status;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PagedRequest;
import org.eclipse.egit.github.core.service.GitHubService;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 */
public class StatisticsService extends GitHubService {
    public StatisticsService(GitHubClient client){super(client);}

    public List<Status> getPunchCard(String org, String repository) throws IOException {
        StringBuilder uri = new StringBuilder("/repos");
        uri.append('/').append(org);
        uri.append('/').append(repository);
        uri.append("/stats");
        uri.append("/punch_card");
        PagedRequest<Status> request = this.createPagedRequest();
        request.setUri(uri);
        return this.getAll(request);

    }

}
