define([], function () {
    return {
        defaultRoutePath: '/gitHubApi',
        routes: {
            '/gitHubApi/week': {
                templateUrl: '/viewGit/week.html',
                dependencies: ['../viewGit/weekCtrl']
            },
            '/gitHubApi/month': {
                templateUrl: '/viewGit/month.html',
                dependencies: ['../viewGit/monthCtrl']
            },
            '/gitHubApi/quarter': {
                templateUrl: '/viewGit/quarter.html',
                dependencies: ['../viewGit/quarterCtrl']
            },
            '/gitHubApi/year': {
                templateUrl: '/viewGit/year.html',
                dependencies: ['../viewGit/yearCtrl']
            },
            '/gitHubApi/week/weekDetail/:username/:weekInYear': {
                templateUrl: '/viewGit/weekDetail.html',
                dependencies: ['../viewGit/weekDetailCtrl']
            },
            '/gitHubApi/detail': {
                templateUrl: '/viewGit/detail.html',
                dependencies: ['../viewGit/detailCtrl']
            },
            '/gitHubApi/highChart/:username': {
                templateUrl: '/viewGit/highChart.html',
                dependencies: ['../viewGit/highChartCtrl']
            },
            '/gitHubApi/label/addLabel': {
                templateUrl: '/viewGit/addLabel.html',
                dependencies: ['../viewGit/addLabelCtrl']
            },
            '/gitHubApi/labelList/:repository': {
                templateUrl: '/viewGit/labelList.html',
                dependencies: ['../viewGit/labelListCtrl']
            },
            '/gitHubApi/addRepository': {
                templateUrl: '/viewGit/addRepository.html',
                dependencies: ['../viewGit/addRepositoryCtrl']
            },
            '/gitHubApi/repositoryList': {
                templateUrl: '/viewGit/repositoryList.html',
                dependencies: ['../viewGit/repositoryListCtrl']
            },
            '/gitHubApi/repositoryDetail/:repository': {
                templateUrl: '/viewGit/RepositoryDetail.html',
                dependencies: ['../viewGit/RepositoryDetailCtrl']
            },
            '/gitHubApi/taskStat/:repository': {
                templateUrl: '/viewGit/taskStat.html',
                dependencies: ['../viewGit/taskStatCtrl']
            },
            '/gitHubApi/analysisView/:repository': {
                templateUrl: '/viewGit/analysisView.html',
                dependencies: ['../viewGit/analysisViewCtrl']
            },
            '/gitHubApi/projectStaff/:repository': {
                templateUrl: '/viewGit/projectStaff.html',
                dependencies: ['../viewGit/projectStaffCtrl']
            },
            '/gitHubApi/addMilestone': {
                templateUrl: '/viewGit/addMilestone.html',
                dependencies: ['../viewGit/addMilestoneCtrl']
            },
            '/gitHubApi/milestoneList/:repository': {
                templateUrl: '/viewGit/milestoneList.html',
                dependencies: ['../viewGit/milestoneListCtrl']
            },
            '/gitHubApi/issue/:repository': {
                templateUrl: '/viewGit/issue.html',
                dependencies: ['../viewGit/issueCtrl']
            },
            '/gitHubApi/addIssue': {
                templateUrl: '/viewGit/addIssue.html',
                dependencies: ['../viewGit/addIssueCtrl']
            },
        }
    };
});


