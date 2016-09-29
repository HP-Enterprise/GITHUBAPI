requirejs.config({
    paths:{
        angular:"/lib/angular/angular.min",
        jquery:"/lib/jquery/dist/jquery.min",
        angularResource: "/lib/angular-resource/angular-resource.min",
        angularRoute:"/lib/angular-route/angular-route.min",
        bootstrap:"/lib/bootstrap/dist/js/bootstrap.min",
        highcharts:"/lib/Highcharts/js/highcharts",
        highchartsMore:"/lib/Highcharts/js/highcharts-more",
        angularMulti:"/lib/angular-multi-select-master/isteven-multi-select",
    },
    shim:{
        jquery: { exports: '$' },
        bootstrap: ['jquery'],
        angular: { exports: 'angular',deps:['jquery']},
        angularResource: ['angular'],
        angularRoute:['angular'],
        highcharts: ['jquery'],
        highchartsMore: ['jquery'],
        angularMulti: ['jquery']
    }
});


require
(
    ['angular','git','../viewGit/indexGitCtrl'],
    function(angular) {
        angular.bootstrap(document, ['git']);
    }
);

