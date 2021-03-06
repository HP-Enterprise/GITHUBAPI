define(['../scripts/git','jquery','highcharts','highchartsMore'],function(module,$){
    module.controller("analysisViewCtrl",function($scope,$http,$routeParams,$timeout){
        $scope.repository = $routeParams.repository;
        $scope.organization = $routeParams.organization;
        $(document).ready(function() {
            var chart = {
                inverted :true,
                type: 'bubble',
                height:600,
                zoomType: 'xy'
            };
            var title = {
                text: 'Punch card'
            };
            var yAxis = {
                labels: {
                    step:1,
                    formatter:function(){
                        switch (this.value){
                            case 0:return "am("+this.value+")";
                                break;
                            case 1:return "am("+this.value+")";
                                break;
                            case 2:return "am("+this.value+")";
                                break;
                            case 3:return "am("+this.value+")";
                                break;
                            case 4:return "am("+this.value+")";
                                break;
                            case 5:return "am("+this.value+")";
                                break;
                            case 6:return "am("+this.value+")";
                                break;
                            case 7:return "am("+this.value+")";
                                break;
                            case 8:return "am("+this.value+")";
                                break;
                            case 9:return "am("+this.value+")";
                                break;
                            case 10:return "am("+this.value+")";
                                break;
                            case 11:return "am("+this.value+")";
                                break;
                            case 12:return "am("+this.value+")";
                                break;
                            case 13:return "pm("+this.value+")";
                                break;
                            case 14:return "pm("+this.value+")";
                                break;
                            case 16:return "pm("+this.value+")";
                                break;
                            case 18:return "pm("+this.value+")";
                                break;
                            case 20:return "pm("+this.value+")";
                                break;
                            case 22:return "pm("+this.value+")";
                                break;
                            case 23:return "pm("+this.value+")";
                                break;
                        }
                    }
                },
                max:24,
                min:-1,
                startOnTick: false,
                endOnTick: false
            };
            var xAxis = {
                    labels: {
                    step:1,
                        formatter:function(){
                        switch (this.value){
                            case 0:return "Sunday("+this.value+")";
                            break;
                                break;
                            case 2:return "Tuesday("+this.value+")";
                                break;
                            case 3:return "Wednesday("+this.value+")";
                                break;
                            case 4:return "Thurday ("+this.value+")";
                                break;
                            case 5:return "Friday("+this.value+")";
                                break;
                            case 6:return "Saturday ("+this.value+")";
                                break;
                        }
                    }
                },
                max:7,
                min:-1,
                gridLineWidth: 1
            };



            var series= [{
                data:[
                    [
                        0,
                        0,
                        1
                    ],
                    [
                        0,
                        1,
                        0
                    ],
                    [
                        0,
                        2,
                        0
                    ],
                    [
                        0,
                        3,
                        0
                    ],
                    [
                        0,
                        4,
                        0
                    ],
                    [
                        0,
                        5,
                        0
                    ],
                    [
                        0,
                        6,
                        0
                    ],
                    [
                        0,
                        7,
                        0
                    ],
                    [
                        0,
                        8,
                        0
                    ],
                    [
                        0,
                        9,
                        0
                    ],
                    [
                        0,
                        10,
                        3
                    ],
                    [
                        0,
                        11,
                        6
                    ],
                    [
                        0,
                        12,
                        1
                    ],
                    [
                        0,
                        13,
                        2
                    ],
                    [
                        0,
                        14,
                        4
                    ],
                    [
                        0,
                        15,
                        3
                    ],
                    [
                        0,
                        16,
                        7
                    ],
                    [
                        0,
                        17,
                        8
                    ],
                    [
                        0,
                        18,
                        7
                    ],
                    [
                        0,
                        19,
                        2
                    ],
                    [
                        0,
                        20,
                        0
                    ],
                    [
                        0,
                        21,
                        2
                    ],
                    [
                        0,
                        22,
                        3
                    ],
                    [
                        0,
                        23,
                        3
                    ],
                    [
                        1,
                        0,
                        2
                    ],
                    [
                        1,
                        1,
                        0
                    ],
                    [
                        1,
                        2,
                        0
                    ],
                    [
                        1,
                        3,
                        1
                    ],
                    [
                        1,
                        4,
                        0
                    ],
                    [
                        1,
                        5,
                        0
                    ],
                    [
                        1,
                        6,
                        0
                    ],
                    [
                        1,
                        7,
                        1
                    ],
                    [
                        1,
                        8,
                        1
                    ],
                    [
                        1,
                        9,
                        35
                    ],
                    [
                        1,
                        10,
                        92
                    ],
                    [
                        1,
                        11,
                        104
                    ],
                    [
                        1,
                        12,
                        20
                    ],
                    [
                        1,
                        13,
                        63
                    ],
                    [
                        1,
                        14,
                        100
                    ],
                    [
                        1,
                        15,
                        133
                    ],
                    [
                        1,
                        16,
                        152
                    ],
                    [
                        1,
                        17,
                        122
                    ],
                    [
                        1,
                        18,
                        121
                    ],
                    [
                        1,
                        19,
                        44
                    ],
                    [
                        1,
                        20,
                        22
                    ],
                    [
                        1,
                        21,
                        7
                    ],
                    [
                        1,
                        22,
                        10
                    ],
                    [
                        1,
                        23,
                        2
                    ],
                    [
                        2,
                        0,
                        0
                    ],
                    [
                        2,
                        1,
                        2
                    ],
                    [
                        2,
                        2,
                        0
                    ],
                    [
                        2,
                        3,
                        1
                    ],
                    [
                        2,
                        4,
                        0
                    ],
                    [
                        2,
                        5,
                        0
                    ],
                    [
                        2,
                        6,
                        0
                    ],
                    [
                        2,
                        7,
                        0
                    ],
                    [
                        2,
                        8,
                        1
                    ],
                    [
                        2,
                        9,
                        61
                    ],
                    [
                        2,
                        10,
                        130
                    ],
                    [
                        2,
                        11,
                        147
                    ],
                    [
                        2,
                        12,
                        38
                    ],
                    [
                        2,
                        13,
                        70
                    ],
                    [
                        2,
                        14,
                        135
                    ],
                    [
                        2,
                        15,
                        160
                    ],
                    [
                        2,
                        16,
                        171
                    ],
                    [
                        2,
                        17,
                        154
                    ],
                    [
                        2,
                        18,
                        107
                    ],
                    [
                        2,
                        19,
                        35
                    ],
                    [
                        2,
                        20,
                        10
                    ],
                    [
                        2,
                        21,
                        4
                    ],
                    [
                        2,
                        22,
                        10
                    ],
                    [
                        2,
                        23,
                        4
                    ],
                    [
                        3,
                        0,
                        2
                    ],
                    [
                        3,
                        1,
                        2
                    ],
                    [
                        3,
                        2,
                        0
                    ],
                    [
                        3,
                        3,
                        0
                    ],
                    [
                        3,
                        4,
                        0
                    ],
                    [
                        3,
                        5,
                        0
                    ],
                    [
                        3,
                        6,
                        0
                    ],
                    [
                        3,
                        7,
                        0
                    ],
                    [
                        3,
                        8,
                        0
                    ],
                    [
                        3,
                        9,
                        73
                    ],
                    [
                        3,
                        10,
                        140
                    ],
                    [
                        3,
                        11,
                        157
                    ],
                    [
                        3,
                        12,
                        35
                    ],
                    [
                        3,
                        13,
                        67
                    ],
                    [
                        3,
                        14,
                        140
                    ],
                    [
                        3,
                        15,
                        187
                    ],
                    [
                        3,
                        16,
                        194
                    ],
                    [
                        3,
                        17,
                        185
                    ],
                    [
                        3,
                        18,
                        114
                    ],
                    [
                        3,
                        19,
                        27
                    ],
                    [
                        3,
                        20,
                        21
                    ],
                    [
                        3,
                        21,
                        20
                    ],
                    [
                        3,
                        22,
                        6
                    ],
                    [
                        3,
                        23,
                        3
                    ],
                    [
                        4,
                        0,
                        3
                    ],
                    [
                        4,
                        1,
                        0
                    ],
                    [
                        4,
                        2,
                        0
                    ],
                    [
                        4,
                        3,
                        0
                    ],
                    [
                        4,
                        4,
                        0
                    ],
                    [
                        4,
                        5,
                        0
                    ],
                    [
                        4,
                        6,
                        0
                    ],
                    [
                        4,
                        7,
                        0
                    ],
                    [
                        4,
                        8,
                        2
                    ],
                    [
                        4,
                        9,
                        61
                    ],
                    [
                        4,
                        10,
                        120
                    ],
                    [
                        4,
                        11,
                        144
                    ],
                    [
                        4,
                        12,
                        24
                    ],
                    [
                        4,
                        13,
                        43
                    ],
                    [
                        4,
                        14,
                        140
                    ],
                    [
                        4,
                        15,
                        148
                    ],
                    [
                        4,
                        16,
                        146
                    ],
                    [
                        4,
                        17,
                        170
                    ],
                    [
                        4,
                        18,
                        113
                    ],
                    [
                        4,
                        19,
                        32
                    ],
                    [
                        4,
                        20,
                        18
                    ],
                    [
                        4,
                        21,
                        11
                    ],
                    [
                        4,
                        22,
                        13
                    ],
                    [
                        4,
                        23,
                        12
                    ],
                    [
                        5,
                        0,
                        6
                    ],
                    [
                        5,
                        1,
                        4
                    ],
                    [
                        5,
                        2,
                        1
                    ],
                    [
                        5,
                        3,
                        0
                    ],
                    [
                        5,
                        4,
                        0
                    ],
                    [
                        5,
                        5,
                        0
                    ],
                    [
                        5,
                        6,
                        0
                    ],
                    [
                        5,
                        7,
                        1
                    ],
                    [
                        5,
                        8,
                        0
                    ],
                    [
                        5,
                        9,
                        55
                    ],
                    [
                        5,
                        10,
                        114
                    ],
                    [
                        5,
                        11,
                        110
                    ],
                    [
                        5,
                        12,
                        27
                    ],
                    [
                        5,
                        13,
                        49
                    ],
                    [
                        5,
                        14,
                        118
                    ],
                    [
                        5,
                        15,
                        143
                    ],
                    [
                        5,
                        16,
                        141
                    ],
                    [
                        5,
                        17,
                        139
                    ],
                    [
                        5,
                        18,
                        91
                    ],
                    [
                        5,
                        19,
                        29
                    ],
                    [
                        5,
                        20,
                        13
                    ],
                    [
                        5,
                        21,
                        13
                    ],
                    [
                        5,
                        22,
                        2
                    ],
                    [
                        5,
                        23,
                        3
                    ],
                    [
                        6,
                        0,
                        0
                    ],
                    [
                        6,
                        1,
                        0
                    ],
                    [
                        6,
                        2,
                        0
                    ],
                    [
                        6,
                        3,
                        0
                    ],
                    [
                        6,
                        4,
                        0
                    ],
                    [
                        6,
                        5,
                        0
                    ],
                    [
                        6,
                        6,
                        0
                    ],
                    [
                        6,
                        7,
                        0
                    ],
                    [
                        6,
                        8,
                        0
                    ],
                    [
                        6,
                        9,
                        2
                    ],
                    [
                        6,
                        10,
                        8
                    ],
                    [
                        6,
                        11,
                        14
                    ],
                    [
                        6,
                        12,
                        12
                    ],
                    [
                        6,
                        13,
                        12
                    ],
                    [
                        6,
                        14,
                        13
                    ],
                    [
                        6,
                        15,
                        19
                    ],
                    [
                        6,
                        16,
                        21
                    ],
                    [
                        6,
                        17,
                        29
                    ],
                    [
                        6,
                        18,
                        38
                    ],
                    [
                        6,
                        19,
                        19
                    ],
                    [
                        6,
                        20,
                        10
                    ],
                    [
                        6,
                        21,
                        2
                    ],
                    [
                        6,
                        22,
                        2
                    ],
                    [
                        6,
                        23,
                        0
                    ]
                ]
            }]


            var json = {};
            json.chart = chart;
            json.title = title;
            json.xAxis = xAxis;
            json.yAxis = yAxis;
            json.series = series;
            $timeout(function(){
                $('#container').highcharts(json);
            },500);


        });
    });
});