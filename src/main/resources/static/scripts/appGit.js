requirejs.config({
    paths:{
        angular:"/lib/angular/angular.min",
        jquery:"/lib/jquery/dist/jquery.min",
        angularResource: "/lib/angular-resource/angular-resource.min",
        angularRoute:"/lib/angular-route/angular-route.min",
        bootstrap:"/lib/bootstrap/dist/js/bootstrap.min",

    },
    shim:{
        jquery: { exports: '$' },
        bootstrap: ['jquery'],
        angular: { exports: 'angular',deps:['jquery']},
        angularResource: ['angular'],
        angularRoute:['angular']
    }
});


require
(
    [
        'angular','git','../viewGit/indexGitCtrl'
    ],
    function(angular)
    {
        angular.bootstrap(document, ['git']);
    }
);

