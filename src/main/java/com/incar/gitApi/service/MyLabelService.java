package com.incar.gitApi.service;

import com.incar.gitApi.GithubClientConfig;
import org.eclipse.egit.github.core.Label;
import org.eclipse.egit.github.core.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/20.
 */
@Service
public class MyLabelService {
    @Autowired
    private GithubClientConfig githubClientConfig;

    /**
     * 添加一个标签
     *
     * @param repository
     * @param label
     * @return
     * @throws IOException
     */
    public Label addLabel(String user, String repository, Label label) throws IOException {
        LabelService labelService = new LabelService(githubClientConfig.getGitHubClient());
        Label label1 = labelService.createLabel(user, repository, label);
        return label1;
    }

    /**
     * 查询某个仓库的所有标签
     *
     * @param repository
     * @return
     * @throws IOException
     */
    public List<Label> getAllLabel(String user, String repository) throws IOException {
        LabelService labelService = new LabelService(githubClientConfig.getGitHubClient());
        List<Label> labelList = labelService.getLabels(user, repository);
        return labelList;
    }

    /**
     * 生成常用的label
     *
     * @param user
     * @param repository
     * @return
     * @throws IOException
     */
    public List<Label> addAllLabel(String user, String repository) throws IOException {
        Object[] str1 = new Object[]{"H1", "H2", "H4", "H6", "H8", "D1", "D2", "D3", "D4", "D5", "Medium", "High", "Fixed", "Backlog", "Feature", "wontfix", "low"};
        Object[] str2 = new Object[]{"EEE9E9", "E0E0E0", "FFBBFF", "FF0000", "FF00FF", "D1EEEE", "32CD32", "282828", "1C86EE", "00F5FF", "008B00", "CD0000", "CAE1FF", "CCCCCC", "ADFF2F", "A8A8A8", "FFFFE0"};
        List<Label> labels = new ArrayList<>();
        List<Label> list = this.getAllLabel(user, repository);
        for (int i = 0; i < str1.length; i++) {
            if (!list.toString().contains(str1[i].toString())) {
                Label label = new Label();
                label.setName(str1[i].toString());
                label.setColor(str2[i].toString());
                Label label1 = this.addLabel(user, repository, label);
                labels.add(label1);
            }
        }
        return labels;
    }
}
