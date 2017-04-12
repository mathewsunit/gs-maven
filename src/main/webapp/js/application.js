var jspAuction = angular.module('jspAuction', ['ngRoute','mainModule']);

jspAuction.config(['$routeProvider', function($routeProvider) {
    $routeProvider.
        when('/about', {
            templateUrl: 'templates/about.html'
        }).
        when('/user/create', {
            templateUrl: 'templates/create.html',
            controller: 'userCreateController'
        }).
        when('/user/panel', {
            templateUrl: 'templates/user-panel.html',
            controller: 'userHomeController'
        }).
        otherwise({
            redirectTo: '/user/panel'
        });
}]);
