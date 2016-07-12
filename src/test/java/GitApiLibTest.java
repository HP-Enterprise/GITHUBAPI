import com.incar.gitApi.Application;
import com.incar.gitApi.entity.GitResult;
import com.incar.gitApi.repository.GitResultRepository;
import com.incar.gitApi.util.GitRetUtil;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Milestone;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.MilestoneService;
import org.eclipse.egit.github.core.service.RepositoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/3/16 0016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(classes = Application.class)
public class GitApiLibTest {

    @Autowired
    private GitResultRepository gitResultRepository;

    @Test
    public void testGetAllIssue() throws IOException {

        GitHubClient gitHubClient = new GitHubClient("api.github.com");
        gitHubClient.setCredentials("travis4hpe", "travis4Java");
        Map<String,String> params = new HashMap<String,String>();
        params.put("state","all");
        IssueService issueService = new IssueService(gitHubClient);
        try {
            List<Issue> issueList = issueService.getIssues("HP-Enterprise", "BriAir", params);
            System.out.println("issueList.size"+issueList.size());
            for (Issue issue : issueList){
                System.out.println("issue string pullrequest:"+issue.getPullRequest()+", assignee:"+issue.getAssignee());
            }
            List<GitResult> gitResults = GitRetUtil.issuesToGitresults(issueList);
            System.out.println("gitResults.size"+gitResults.size());
//            gitResultRepository.save(gitResults);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
         public void testGetRepo() throws IOException {
        GitHubClient gitHubClient = new GitHubClient("api.github.com");
        gitHubClient.setCredentials("travis4hpe", "travis4Java");
        RepositoryService repositoryService = new RepositoryService(gitHubClient);
        List<Repository> repositories = repositoryService.getRepositories();
        for (Repository repository : repositories)
            System.out.println("giturl :"+repository.getGitUrl());
    }

    @Test
    public void testMileStone() throws IOException {
        GitHubClient gitHubClient = new GitHubClient("api.github.com");
        gitHubClient.setCredentials("travis4hpe", "travis4Java");
        String user = "HP-Enterprise";
        String rep = "Rental653";
        String number = "11";
        MilestoneService milestoneService = new MilestoneService(gitHubClient);
        Milestone milestone = milestoneService.getMilestone(user,rep,number);
        System.out.println(milestone.getDescription());
    }
}
