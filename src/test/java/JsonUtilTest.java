import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.incar.gitApi.jsonObj.Issue;
import com.incar.gitApi.jsonObj.PullRequest;
import com.incar.gitApi.jsonObj.User;
import com.incar.gitApi.query.IssueQuery;
import com.incar.gitApi.util.JsonUtils;

/**
 * Created by Administrator on 2016/2/21 0021.
 */

import org.json.JSONArray;
import org.junit.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@SuppressWarnings("unchecked")
public class JsonUtilTest {

    @Test
    public void testJsonToObj(){
        String jsonRet = "{\n" +
                "    \"url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/issues/336\",\n" +
                "    \"repository_url\": \"https://api.github.com/repos/HP-Enterprise/Rental653\",\n" +
                "    \"labels_url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/issues/336/labels{/name}\",\n" +
                "    \"comments_url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/issues/336/comments\",\n" +
                "    \"events_url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/issues/336/events\",\n" +
                "    \"html_url\": \"https://github.com/HP-Enterprise/Rental653/pull/336\",\n" +
                "    \"id\": 134836425,\n" +
                "    \"number\": 336,\n" +
                "    \"title\": \"timeout settings\",\n" +
                "    \"user\": {\n" +
                "      \"login\": \"AceBear\",\n" +
                "      \"id\": 5030312,\n" +
                "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/5030312?v=3\",\n" +
                "      \"gravatar_id\": \"\",\n" +
                "      \"url\": \"https://api.github.com/users/AceBear\",\n" +
                "      \"html_url\": \"https://github.com/AceBear\",\n" +
                "      \"followers_url\": \"https://api.github.com/users/AceBear/followers\",\n" +
                "      \"following_url\": \"https://api.github.com/users/AceBear/following{/other_user}\",\n" +
                "      \"gists_url\": \"https://api.github.com/users/AceBear/gists{/gist_id}\",\n" +
                "      \"starred_url\": \"https://api.github.com/users/AceBear/starred{/owner}{/repo}\",\n" +
                "      \"subscriptions_url\": \"https://api.github.com/users/AceBear/subscriptions\",\n" +
                "      \"organizations_url\": \"https://api.github.com/users/AceBear/orgs\",\n" +
                "      \"repos_url\": \"https://api.github.com/users/AceBear/repos\",\n" +
                "      \"events_url\": \"https://api.github.com/users/AceBear/events{/privacy}\",\n" +
                "      \"received_events_url\": \"https://api.github.com/users/AceBear/received_events\",\n" +
                "      \"type\": \"User\",\n" +
                "      \"site_admin\": false\n" +
                "    },\n" +
                "    \"labels\": [\n" +
                "\n" +
                "    ],\n" +
                "    \"state\": \"closed\",\n" +
                "    \"locked\": false,\n" +
                "    \"assignee\": null,\n" +
                "    \"milestone\": null,\n" +
                "    \"comments\": 0,\n" +
                "    \"created_at\": \"2016-02-19T10:57:47Z\",\n" +
                "    \"updated_at\": \"2016-02-19T11:06:35Z\",\n" +
                "    \"closed_at\": \"2016-02-19T11:06:32Z\",\n" +
                "    \"pull_request\": {\n" +
                "      \"url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/pulls/336\",\n" +
                "      \"html_url\": \"https://github.com/HP-Enterprise/Rental653/pull/336\",\n" +
                "      \"diff_url\": \"https://github.com/HP-Enterprise/Rental653/pull/336.diff\",\n" +
                "      \"patch_url\": \"https://github.com/HP-Enterprise/Rental653/pull/336.patch\"\n" +
                "    },\n" +
                "    \"body\": \"\"\n" +
                "  }";

        String jsonRetPullRequest = "{\n" +
                "      \"url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/pulls/336\",\n" +
                "      \"html_url\": \"https://github.com/HP-Enterprise/Rental653/pull/336\",\n" +
                "      \"diff_url\": \"https://github.com/HP-Enterprise/Rental653/pull/336.diff\",\n" +
                "      \"patch_url\": \"https://github.com/HP-Enterprise/Rental653/pull/336.patch\"\n" +
                "    }";
        Issue issue = JsonUtils.toObject(jsonRet, Issue.class);

//        System.out.println(issue.toString());

        PullRequest pullRequest = JsonUtils.toObject(jsonRetPullRequest, PullRequest.class);

//        System.out.println(pullRequest.toString());

            String userJson = "{\n" +
                    "      \"login\": \"AceBear\",\n" +
                    "      \"id\": 5030312,\n" +
                    "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/5030312?v=3\",\n" +
                    "      \"gravatar_id\": \"\",\n" +
                    "      \"url\": \"https://api.github.com/users/AceBear\",\n" +
                    "      \"html_url\": \"https://github.com/AceBear\",\n" +
                    "      \"followers_url\": \"https://api.github.com/users/AceBear/followers\",\n" +
                    "      \"following_url\": \"https://api.github.com/users/AceBear/following{/other_user}\",\n" +
                    "      \"gists_url\": \"https://api.github.com/users/AceBear/gists{/gist_id}\",\n" +
                    "      \"starred_url\": \"https://api.github.com/users/AceBear/starred{/owner}{/repo}\",\n" +
                    "      \"subscriptions_url\": \"https://api.github.com/users/AceBear/subscriptions\",\n" +
                    "      \"organizations_url\": \"https://api.github.com/users/AceBear/orgs\",\n" +
                    "      \"repos_url\": \"https://api.github.com/users/AceBear/repos\",\n" +
                    "      \"events_url\": \"https://api.github.com/users/AceBear/events{/privacy}\",\n" +
                    "      \"received_events_url\": \"https://api.github.com/users/AceBear/received_events\",\n" +
                    "      \"type\": \"User\",\n" +
                    "      \"site_admin\": false\n" +
                    "    }";
            User user = JsonUtils.toObject(userJson,User.class);
//            System.out.println(user.toString());
    }
        @Test
        public void testJsonArray(){
                String jsonArrayStr ="[\n" +
                        "  {\n" +
                        "    \"url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/issues/336\",\n" +
                        "    \"repository_url\": \"https://api.github.com/repos/HP-Enterprise/Rental653\",\n" +
                        "    \"labels_url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/issues/336/labels{/name}\",\n" +
                        "    \"comments_url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/issues/336/comments\",\n" +
                        "    \"events_url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/issues/336/events\",\n" +
                        "    \"html_url\": \"https://github.com/HP-Enterprise/Rental653/pull/336\",\n" +
                        "    \"id\": 134836425,\n" +
                        "    \"number\": 336,\n" +
                        "    \"title\": \"timeout settings\",\n" +
                        "    \"user\": {\n" +
                        "      \"login\": \"AceBear\",\n" +
                        "      \"id\": 5030312,\n" +
                        "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/5030312?v=3\",\n" +
                        "      \"gravatar_id\": \"\",\n" +
                        "      \"url\": \"https://api.github.com/users/AceBear\",\n" +
                        "      \"html_url\": \"https://github.com/AceBear\",\n" +
                        "      \"followers_url\": \"https://api.github.com/users/AceBear/followers\",\n" +
                        "      \"following_url\": \"https://api.github.com/users/AceBear/following{/other_user}\",\n" +
                        "      \"gists_url\": \"https://api.github.com/users/AceBear/gists{/gist_id}\",\n" +
                        "      \"starred_url\": \"https://api.github.com/users/AceBear/starred{/owner}{/repo}\",\n" +
                        "      \"subscriptions_url\": \"https://api.github.com/users/AceBear/subscriptions\",\n" +
                        "      \"organizations_url\": \"https://api.github.com/users/AceBear/orgs\",\n" +
                        "      \"repos_url\": \"https://api.github.com/users/AceBear/repos\",\n" +
                        "      \"events_url\": \"https://api.github.com/users/AceBear/events{/privacy}\",\n" +
                        "      \"received_events_url\": \"https://api.github.com/users/AceBear/received_events\",\n" +
                        "      \"type\": \"User\",\n" +
                        "      \"site_admin\": false\n" +
                        "    },\n" +
                        "    \"labels\": [\n" +
                        "\n" +
                        "    ],\n" +
                        "    \"state\": \"closed\",\n" +
                        "    \"locked\": false,\n" +
                        "    \"assignee\": null,\n" +
                        "    \"milestone\": null,\n" +
                        "    \"comments\": 0,\n" +
                        "    \"created_at\": \"2016-02-19T10:57:47Z\",\n" +
                        "    \"updated_at\": \"2016-02-19T11:06:35Z\",\n" +
                        "    \"closed_at\": \"2016-02-19T11:06:32Z\",\n" +
                        "    \"pull_request\": {\n" +
                        "      \"url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/pulls/336\",\n" +
                        "      \"html_url\": \"https://github.com/HP-Enterprise/Rental653/pull/336\",\n" +
                        "      \"diff_url\": \"https://github.com/HP-Enterprise/Rental653/pull/336.diff\",\n" +
                        "      \"patch_url\": \"https://github.com/HP-Enterprise/Rental653/pull/336.patch\"\n" +
                        "    },\n" +
                        "    \"body\": \"\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/issues/335\",\n" +
                        "    \"repository_url\": \"https://api.github.com/repos/HP-Enterprise/Rental653\",\n" +
                        "    \"labels_url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/issues/335/labels{/name}\",\n" +
                        "    \"comments_url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/issues/335/comments\",\n" +
                        "    \"events_url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/issues/335/events\",\n" +
                        "    \"html_url\": \"https://github.com/HP-Enterprise/Rental653/pull/335\",\n" +
                        "    \"id\": 134821856,\n" +
                        "    \"number\": 335,\n" +
                        "    \"title\": \"修改测试数据临时表插入sql\",\n" +
                        "    \"user\": {\n" +
                        "      \"login\": \"ThomasChant\",\n" +
                        "      \"id\": 16570977,\n" +
                        "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/16570977?v=3\",\n" +
                        "      \"gravatar_id\": \"\",\n" +
                        "      \"url\": \"https://api.github.com/users/ThomasChant\",\n" +
                        "      \"html_url\": \"https://github.com/ThomasChant\",\n" +
                        "      \"followers_url\": \"https://api.github.com/users/ThomasChant/followers\",\n" +
                        "      \"following_url\": \"https://api.github.com/users/ThomasChant/following{/other_user}\",\n" +
                        "      \"gists_url\": \"https://api.github.com/users/ThomasChant/gists{/gist_id}\",\n" +
                        "      \"starred_url\": \"https://api.github.com/users/ThomasChant/starred{/owner}{/repo}\",\n" +
                        "      \"subscriptions_url\": \"https://api.github.com/users/ThomasChant/subscriptions\",\n" +
                        "      \"organizations_url\": \"https://api.github.com/users/ThomasChant/orgs\",\n" +
                        "      \"repos_url\": \"https://api.github.com/users/ThomasChant/repos\",\n" +
                        "      \"events_url\": \"https://api.github.com/users/ThomasChant/events{/privacy}\",\n" +
                        "      \"received_events_url\": \"https://api.github.com/users/ThomasChant/received_events\",\n" +
                        "      \"type\": \"User\",\n" +
                        "      \"site_admin\": false\n" +
                        "    },\n" +
                        "    \"labels\": [\n" +
                        "\n" +
                        "    ],\n" +
                        "    \"state\": \"closed\",\n" +
                        "    \"locked\": false,\n" +
                        "    \"assignee\": {\n" +
                        "      \"login\": \"AceBear\",\n" +
                        "      \"id\": 5030312,\n" +
                        "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/5030312?v=3\",\n" +
                        "      \"gravatar_id\": \"\",\n" +
                        "      \"url\": \"https://api.github.com/users/AceBear\",\n" +
                        "      \"html_url\": \"https://github.com/AceBear\",\n" +
                        "      \"followers_url\": \"https://api.github.com/users/AceBear/followers\",\n" +
                        "      \"following_url\": \"https://api.github.com/users/AceBear/following{/other_user}\",\n" +
                        "      \"gists_url\": \"https://api.github.com/users/AceBear/gists{/gist_id}\",\n" +
                        "      \"starred_url\": \"https://api.github.com/users/AceBear/starred{/owner}{/repo}\",\n" +
                        "      \"subscriptions_url\": \"https://api.github.com/users/AceBear/subscriptions\",\n" +
                        "      \"organizations_url\": \"https://api.github.com/users/AceBear/orgs\",\n" +
                        "      \"repos_url\": \"https://api.github.com/users/AceBear/repos\",\n" +
                        "      \"events_url\": \"https://api.github.com/users/AceBear/events{/privacy}\",\n" +
                        "      \"received_events_url\": \"https://api.github.com/users/AceBear/received_events\",\n" +
                        "      \"type\": \"User\",\n" +
                        "      \"site_admin\": false\n" +
                        "    },\n" +
                        "    \"milestone\": null,\n" +
                        "    \"comments\": 0,\n" +
                        "    \"created_at\": \"2016-02-19T09:58:07Z\",\n" +
                        "    \"updated_at\": \"2016-02-19T10:35:30Z\",\n" +
                        "    \"closed_at\": \"2016-02-19T10:35:28Z\",\n" +
                        "    \"pull_request\": {\n" +
                        "      \"url\": \"https://api.github.com/repos/HP-Enterprise/Rental653/pulls/335\",\n" +
                        "      \"html_url\": \"https://github.com/HP-Enterprise/Rental653/pull/335\",\n" +
                        "      \"diff_url\": \"https://github.com/HP-Enterprise/Rental653/pull/335.diff\",\n" +
                        "      \"patch_url\": \"https://github.com/HP-Enterprise/Rental653/pull/335.patch\"\n" +
                        "    },\n" +
                        "    \"body\": \"\"\n" +
                        "  },\n]";

                List<Issue> issueList = JsonUtils.toObjectList(new JSONArray(jsonArrayStr), Issue.class);
               System.out.println("issueSize:"+issueList.size());
        }

        @Test
        public void testJsonReader() throws IOException {
                IssueQuery issueQuery = new IssueQuery();
                Connection connection = issueQuery.conn("121.40.157.200", "deploy", "deploy");
                Session session = connection.openSession();
                InputStreamReader inputStreamReader = issueQuery.executeCmd("curl -u travis4hpe:travis4Java https://api.github.com/repos/HP-Enterprise/Rental653/issues?state=all", session);
                List<Issue> result =  (List<Issue>)JsonUtils.readValue(inputStreamReader, Object.class);
                System.out.println("result.size()" + result.size());
        }
}
