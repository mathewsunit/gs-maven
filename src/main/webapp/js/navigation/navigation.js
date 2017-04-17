angular.module('navigation', ['ngRoute', 'secure-rest-angular']).controller(
		'navigation',

		function($route, Login) {

			var self = this;

			self.credentials = {};

			self.tab = function(route) {
				return $route.current && route === $route.current.controller;
			};

			self.authenticated = function() {
				return Login.authenticated;
			}

			self.login = function() {
				Login.authenticate(self.credentials, function(authenticated) {
					if (authenticated) {
						console.log("Login succeeded")
						self.error = false;
					} else {
						console.log("Login failed")
						self.error = true;
					}
				})
			};
			self.logout = Login.clear;
});