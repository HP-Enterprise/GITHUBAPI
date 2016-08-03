var app = angular.module('addIssuesDetailApp',['ngResource'])

app.controller("addIssuesDetailCtrl", function($scope,$http,$rootScope, $location, $resource){
    $scope.engineer = {
        name: "Dani",
        currentActivity: {
            id: 3,
            type: "Work" ,
            name: "Fixing bugs"
        }
    };
    var abc=1111111111;
    $scope.activities =
        [
            { id: 1, type: "Work" , name: "Writing code" },
            { id: 2, type: "Work" , name: "Testing code" },
            { id: 3, type: "Work" , name: "Fixing bugs" },
            { id: 4, type: "Play" , name: "Dancing" }
        ];
    console.log($scope.engineer.currentActivity)

    $scope.master={};
    //$scope.show=function(fff){
    //    //console.log('owner:'+ $scope.repository.owner);
    //    //console.log('仓库名:',$scope.get.repository);
    //    //console.log('描述:',$scope.get.describe);
    //    $scope.master=angular.copy(fff);
    //
    //    console.log($scope.master);
    //};

    $scope.show=function(www){
        //console.log('owner:'+ $scope.repository.owner);
        //console.log('仓库名:',$scope.get.repository);
        //console.log('描述:',$scope.get.describe);
        //$scope.master=angular.copy(fff);
        console.log(444444);
    };


    $scope.put=function(eee){
        //console.log('owner:'+ $scope.repository.owner);
        //console.log('仓库名:',$scope.get.repository);
        //console.log('描述:',$scope.get.describe);
        //$scope.master=angular.copy(fff);

        console.log($scope.fff.aaa);
    };
});
