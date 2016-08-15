define(['../scripts/git','jquery'],function(module,$){
    module.controller("indexGitCtrl1",function($rootScope,$http,$resource,$location){

        console.log("login page");

        this.return = function() {
            $location.path("/gitHubApi/indexGit");
        }


    });
});