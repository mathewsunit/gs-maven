angular.module('home', ['secure-rest-angular']).controller('home', function($cookies, $http, $location, $q, $resource, $scope, Cookies, Csrf, Login) {
	var self = this;

	var secureResources = function (headers) {
    		if (headers !== undefined) {
    			return $resource('/user', {}, {
    				post: {method: 'POST', headers: headers, isArray: false}
    			});
    		} else {
    			return $resource('/user', {}, {
    				get: {method: 'GET', cache: false, isArray: false},
    				options: {method: 'OPTIONS', cache: false}
    			});
    		}
    	};

	secureResources().get().$promise.then(function(response) {
        console.log('GET returned: ', response);
		self.user = response.name;
	});
});