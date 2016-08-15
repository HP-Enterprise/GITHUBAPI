define([], function () {
    return {
        //defaultRoutePath: '/gitHubApi/',
        routes: {
            '/gitHubApi/indexGit1': {
                templateUrl: '/viewGit/indexGit1.html',
                dependencies: ['../viewGit/indexGit1Ctrl']
            },
            '/gitHubApi/week': {
                templateUrl: '/viewGit/week.html',
                dependencies: ['../viewGit/weekCtrl']
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
            '/gitHubApi/addMilestone': {
                templateUrl: '/viewGit/addMilestone.html',
                dependencies: ['../viewGit/addMilestoneCtrl']
            },
            '/gitHubApi/milestoneList/:repository': {
                templateUrl: '/viewGit/milestoneList.html',
                dependencies: ['../viewGit/milestoneListCtrl']
            },
            '/gitHubApi/addIssue': {
                templateUrl: '/viewGit/addIssue.html',
                dependencies: ['../viewGit/addIssueCtrl']
            },
        }
    };
});


