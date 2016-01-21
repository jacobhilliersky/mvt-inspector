var app = angular.module('app', ['ngRoute', 'ngResource']);

app.config(function ($httpProvider, $routeProvider) {
    $httpProvider.interceptors.push('PropsitionHeaderInterceptor');

    $routeProvider.when('/sports', {
        templateUrl: 'sports.html',
        controller: 'SportsController',
        resolve: {
            sports: function ($route, Sports) {
                return Sports.get().$promise;
            }
        }
    });
    $routeProvider.when('/signup', {
        templateUrl: 'signup.html',
        controller: 'SignupController',
        resolve: {
            signup: function ($route, Signup) {
                return Signup.get().$promise;
            }
        }
    });
});

app.controller('AppController', function ($scope, $window, $route) {
    $scope.navigation = {
        propositions: ['NOWTV:ESP', 'NOWTV:UK'],
        selected: 'NOWTV:ESP'
    };

    $scope.storeSelectedProposition = function () {
        $window.localStorage.proposition = $scope.navigation.selected;
    };

    $scope.changeProposition = function () {
        $scope.storeSelectedProposition();
        $route.reload();
    };

    if ($window.localStorage.proposition) {
        $scope.navigation.selected = $window.localStorage.proposition;
    } else {
        $scope.storeSelectedProposition();
    }
});

app.controller('SportsController', function ($scope, sports) {
    $scope.imageUrl = sports.imageUrl;
});

app.controller('SignupController', function ($scope, signup) {
    $scope.buttonClass = signup.buttonClass;
});

app.factory('Sports', function ($resource) {
    return $resource('/sports');
});

app.factory('Signup', function ($resource) {
    return $resource('/signup');
});

app.factory('PropsitionHeaderInterceptor', function ($q, $window) {
    return {
        request: function (config) {
            config.headers = config.headers || {};
            if ($window.localStorage.proposition) {
                config.headers.Proposition = $window.localStorage.proposition;
            }
            return config;
        },
        response: function (response) {
            return response || $q.when(response);
        },
        responseError: function (response) {
            return response || $q.when(response);
        }
    };
});
