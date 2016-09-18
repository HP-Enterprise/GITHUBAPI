package com.incar.gitApi.service;
import com.google.gson.reflect.TypeToken;
import org.eclipse.egit.github.core.CommitStats;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.client.PagedRequest;
import org.eclipse.egit.github.core.service.GitHubService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/8/31.
 */

public class StatisticsService extends GitHubService {
    public StatisticsService(GitHubClient client){super(client);}

    public List<CommitStats> getPunchCard(String org, String repository) throws IOException {
        StringBuilder uri = new StringBuilder("/repos");
        uri.append('/').append(org);
        uri.append('/').append(repository);
        uri.append("/stats");
        uri.append("/punch_card");
        PagedRequest<CommitStats> request = this.createPagedRequest();
        request.setType((new TypeToken() {
        }).getType());
        request.setUri(uri);
        return (List<CommitStats>)this.client.get(request).getBody();

    }

}
