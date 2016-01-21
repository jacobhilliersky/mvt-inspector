var app = angular.module('app', ['ngRoute', 'ngResource']);

app.config(function ($httpProvider, $routeProvider) {
    $routeProvider.when('/sports/:proposition', {
        templateUrl: 'sports.html',
        controller: 'SportsController',
        resolve: {
            sports: function ($route, $http, Sports) {
                var proposition = "NOWTV:UK";
                if ($route.current.params.proposition == "es") {
                    proposition = 'NOWTV:ESP';
                }
                $http.defaults.headers.common['Proposition'] = proposition;

                return Sports.get().$promise;
            }
        }
    });
    $routeProvider.when('/signup/:proposition', {
        templateUrl: 'signup.html',
        controller: 'SignupController',
        resolve: {
            signup: function ($route, $http, Signup) {
                var proposition = "NOWTV:UK";
                if ($route.current.params.proposition == "es") {
                    proposition = 'NOWTV:ESP';
                }
                $http.defaults.headers.common['Proposition'] = proposition;

                return Signup.get().$promise;
            }
        }
    });
});

app.controller('AppController', function ($scope) {
    $scope.navigation = {
        proposition: ''
    };
});

app.controller('SportsController', function ($scope, sports, $route) {
    $scope.imageUrl = sports.imageUrl;
    $scope.navigation.proposition = $route.current.params.proposition;
});

app.controller('SignupController', function ($scope, signup, $route) {
    $scope.buttonClass = signup.buttonClass;
    $scope.navigation.proposition = $route.current.params.proposition;
});

app.factory('Sports', function ($resource) {
    return $resource('/sports');
});

app.factory('Signup', function ($resource) {
    return $resource('/signup');
});
