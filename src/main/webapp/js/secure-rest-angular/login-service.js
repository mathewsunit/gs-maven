angular.module('secure-rest-angular').factory('Login', function ($http, $resource, $rootScope, $location, Cookies) {

  enter = function() {
    if ($location.path() != auth.loginPath) {
      auth.path = $location.path();
      if (!auth.authenticated) {
        $location.path(auth.loginPath);
      }
    }
  }

  var loginResources = $resource('/login', {}, {
    options: {method: 'OPTIONS', cache: false}
  });

  var logoutResources = $resource('/logout', {}, {
    options: {method: 'OPTIONS', cache: false}
  });

  /**
	 * Tries to detect whether the response elements returned indicate an invalid or missing CSRF token...
	 */
  var isCSRFTokenInvalidOrMissing = function (data, status) {
    return (status === 403 && data.message && data.message.toLowerCase().indexOf('csrf') > -1)
    || (status === 0 && data === null);
  };

  var auth = {
    /**
		 * Service function that logs in the user with the specified username and password.
		 * To handle the returned promise we use a successHandler/errorHandler approach because we want to have
		 * access to the additional information received when the failure handler is invoked (status, etc.).
		 */
    authenticated : false,
    loginPath : '/login',
    logoutPath : '/logout',
    homePath : '/',
    path : $location.path(),


    authenticate: function(credentials, callback) {

      // Obtain a CSRF token
      loginResources.options().$promise.then(function (response) {
        console.log('Obtained a CSRF token in a cookie', response);

        // Extract the CSRF token
        var csrfToken = Cookies.getFromDocument($http.defaults.xsrfCookieName);
        console.log('Extracted the CSRF token from the cookie', csrfToken);

        // Prepare the headers
        var headers = {
          'Content-Type': 'application/x-www-form-urlencoded'
        };
        headers[$http.defaults.xsrfHeaderName] = csrfToken;

        // Post the credentials for logging in
        $http.post('/login', 'username=' + credentials.username + '&password=' + credentials.password, {
          headers: headers
        })
          .then(function(response) {
          if (response.status==200) {
            auth.authenticated = true;
          } else {
            auth.authenticated = false;
          }
          console.log('We\'re authenticated',response);
          callback && callback(auth.authenticated);
          $location.path(auth.path==auth.loginPath ? auth.homePath : auth.path);
        },function (data, status, headers, config) {

          if (isCSRFTokenInvalidOrMissing(data, status)) {
            console.error('The obtained CSRF token was either missing or invalid. Have you turned on your cookies?');

          } else {
            // Nope, the error is due to something else. Run the error handler...
            console.error('Something went wrong while trying to login... ', data, status, headers, config);
          }
          auth.authenticated = false;
          callback && callback(false);
        });

      }).catch(function(response) {
        console.error('Could not contact the server... is it online? Are we?', response);
      });
    },

    clear: function(successHandler, errorHandler) {

      // Obtain a CSRF token
      logoutResources.options().$promise.then(function (response) {
        console.log('Obtained a CSRF token in a cookie', response);

        // Extract the CSRF token
        var csrfToken = Cookies.getFromDocument($http.defaults.xsrfCookieName);
        console.log('Extracted the CSRF token from the cookie', csrfToken);

        // Prepare the headers
        var headers = {
          'Content-Type': 'application/x-www-form-urlencoded'
        };
        headers[$http.defaults.xsrfHeaderName] = csrfToken;

        // Post the credentials for logging out
        $http.post('/logout', '', {
          headers: headers
        })
          .then(function() {
          $rootScope.credentials = {username: '', password: ''};
          delete $cookies['JSESSIONID'];
          console.info('The user has been logged out!');

          $location.url('/');
        },function(data, status, headers, config) {

          if (isCSRFTokenInvalidOrMissing(data, status)) {
            console.error('The obtained CSRF token was either missing or invalid. Have you turned on your cookies?');

          } else {
            // Nope, the error is due to something else. Run the error handler...
            console.error('Something went wrong while trying to logout... ', data, status, headers, config);
          }
        });

      }).catch(function(response) {
        console.error('Could not contact the server... is it online? Are we?', response);
      });
    },

    init : function(homePath, loginPath, logoutPath) {

      auth.homePath = homePath;
      auth.loginPath = loginPath;
      auth.logoutPath = logoutPath;

      auth.authenticate({}, function(authenticated) {
        if (authenticated) {
          $location.path(auth.path);
        }
      })

      // Guard route changes and switch to login page if unauthenticated
      $rootScope.$on('$routeChangeStart', function() {
        enter();
      });
    }
  };
  return auth;
});