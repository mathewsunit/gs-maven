angular
		.module('jspAuction', [ 'ngRoute','ngCookies', 'secure-rest-angular', 'home', 'message', 'navigation' ])
		.config(

				function($routeProvider, $httpProvider, $locationProvider) {
					$routeProvider.when('/', {
						templateUrl : 'js/home/home.html',
						controller : 'home',
						controllerAs : 'controller'
					}).when('/message', {
						templateUrl : 'js/message/message.html',
						controller : 'message',
						controllerAs : 'controller'
					}).when('/login', {
						templateUrl : 'js/navigation/login.html',
						controller : 'navigation',
						controllerAs : 'controller'
					}).otherwise('/login');

					$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

                }).run(function(Login) {

                    // Initialize auth module with the home page and login/logout path
                    // respectively
                    Login.init('/', '/login', '/logout');

  });