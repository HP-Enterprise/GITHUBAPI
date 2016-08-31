define(['../scripts/git','jquery'],function(module,$){
    module.controller("analysisViewCtrl",function($scope,$http,$routeParams){
        $scope.repository = $routeParams.repository;
    });
});