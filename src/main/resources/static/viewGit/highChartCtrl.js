define(['../scripts/git','jquery'],function(module,$){
    module.controller("highChartCtrl",function($scope,$http,$routeParams){
        function cloneJSON(para){
            var rePara = null;
            var type = Object.prototype.toString.call(para);
            if(type.indexOf("Object") > -1){
                rePara = jQuery.extend(true, {}, para);
            }else if(type.indexOf("Array") > 0){
                rePara = [];
                jQuery.each(para, function(index, obj){
                    rePara.push(jQuery.cloneJSON(obj));
                });
            }else{
                rePara = para;
            }
            return rePara;
        }
        var config ={
            title: {
                text: '个人工作时间',
                x: -20 //center
            },
            subtitle: {
                text: '预期时间与实际工作时间',
                x: -20
            },
            xAxis: {
                categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                    'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
            },
            yAxis: {
                title: {
                    text: '时间（h）'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                valueSuffix: 'h'
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },

            series: [{
                name: 'Tokyo',
                data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
            }, {
                name: 'New York',
                data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
            }, {
                name: 'Berlin',
                data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
            }, {
                name: 'London',
                data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
            }]
        }

        function showChart(config)
        {
            $("#container").highcharts(config);
        }


        $scope.str = $routeParams.username;

        $scope.workSearch={
            params:{
                userName:$scope.str
            }
        };

        $http.get("/api/json",$scope.workSearch).success(function(data,status,headers){
            $scope.fff = data.message;
            $scope.actual = data.message.actual_data;
            $scope.expect = data.message.expect_data;
            $scope.cate = data.message.category;

            console.log($scope.fff);

            var newConfig=cloneJSON(config);
            newConfig.xAxis={
                categories: $scope.fff.category
            };
            newConfig.series=[{name:"预期工作时长",data:$scope.fff.expect_data,color:'red'},{name:"实际工作时长",data:$scope.fff.actual_data,color:'blue'}];
            newConfig.title = {text:"个人工作时长"};
            showChart(newConfig);
        }).error(function(err){
            console.log(err);
        })
    });
});