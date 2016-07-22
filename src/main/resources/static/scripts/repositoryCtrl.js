var app = angular.module('repositoryApp',['ngResource'])

app.controller("repositoryCtrl", function($scope,$http,$rootScope, $location, $resource){
    $scope.master={};
    $scope.show=function(repository){
        //console.log('owner:'+ $scope.repository.owner);
        //console.log('仓库名:',$scope.get.repository);
        //console.log('描述:',$scope.get.describe);
        $scope.master=angular.copy(repository);

        console.log($scope.master);

        $http.post("http://localhost:8080/api/addRepository",$scope.master).success(function(data,status,headers){
                console.log(data);
        }).error(function(data){
            alert("失败" );
        })
    };

    //$scope.EstablishRepository = function(flag){
    //
    //
    //    //$scope.workPost={
    //    //    params:{
    //    //        owner:$scope.get.owner,
    //    //        repository:$scope.get.repository,
    //    //        describe:$scope.get.describe,
    //    //        fuzzy:1
    //    //    }
    //    //};
    //    //console.log("11111" + aaa);
    //
    //    $http.post("",$scope.workPost).success(function(data,status,headers){
    //        //$scope.workPageObject.totalPage = headers('Page-Count'); //总页数
    //        //$scope.allWork = data.message;
    //        //console.log(get.owner);
    //    }).error(function(err){
    //        console.log(err);
    //    })
    //};


});
