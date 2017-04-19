angular.module('secure-rest-angular').controller('Controller', function ($cookies, $http, $location, $q, $resource, $scope, Cookies, Csrf, Login) {

	$scope.greetings = {
		open: {
			getResult: '',
			postValue: 'some value'
		},
		secure: {
			getResult: '',
			postValue: 'some secure value'
		}
	};

	$scope.credentials = {
		username: '',
		password: ''
	};

	var secureResources = function (headers) {
		if (headers !== undefined) {
			return $resource('http://localhost:8081/rest/secure', {}, {
				post: {method: 'POST', headers: headers, isArray: false}
			});
		} else {
			return $resource('http://localhost:8081/rest/secure', {}, {
				get: {method: 'GET', cache: false, isArray: false},
				options: {method: 'OPTIONS', cache: false}
			});
		}
	};

	$scope.getSecureGreetings = function() {
		$scope.greetings.secure.getResult = '';

		secureResources().get().$promise.then(function (response) {
			console.log('GET /rest/secure returned: ', response);
			$scope.greetings.secure.getResult = response.greetings;

		}).catch(function(response) {
			handleError(response);
		});
	};

	$scope.postSecureGreetings = function () {
		Csrf.addResourcesCsrfToHeaders(secureResources().options, $http.defaults.headers.post).then(function (headers) {
			secureResources(headers).post({greetings: $scope.greetings.secure.postValue}).$promise.then(function (response) {
				console.log('POST /rest/secure returned: ', response);
				console.info('You might want to check the server logs to see that the POST has been handled!');

			}).catch(function(response) {
				handleError(response);
			});
		});
	};

	var handleError = function(response) {

		if (response.status === 401) {
			console.error('You need to login first!');

		} else {
			console.error('Something went wrong...', response);
		}
	};
});