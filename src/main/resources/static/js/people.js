angular.module('people', [ 'ngRoute' ])
.constant('ENV', {HOST:'http://localhost:8080'})
.config(function($routeProvider, $httpProvider) {
  $routeProvider.when('/', {
    templateUrl : 'home.html',
    controller : 'home'
  }).when('/login', {
    templateUrl : 'login.html',
    controller : 'navigation'
  }).when('/table',{
    templateUrl : 'table.html',
    controller : 'tableController',
    resolve: {
        peopleData: function(tableDataFactory) {
            return tableDataFactory.getPeopleSummary();
        },
        eventTypesData: function(tableDataFactory){
            return tableDataFactory.getEventTypes();
        },
        categoriesData: function(tableDataFactory){
            return tableDataFactory.getCategories();
        },
        eventsData : function(tableDataFactory){
            return tableDataFactory.getEvents();
        }
    }
  }).when('/list',{
    templateUrl : 'list.html',
    controller : 'listController',
    resolve: {
        peopleData: function(tableDataFactory) {
            return tableDataFactory.getPeopleInfo();
        },
        categoriesData: function(tableDataFactory){
            return tableDataFactory.getCategories();
        }
    }
  }).when('/report',{
    templateUrl : 'report.html',
    controller : 'reportController'
  }).when('/users',{
        templateUrl : 'users.html',
        controller : 'usersController'
   }).otherwise('/');
   $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
  })
.controller('home', function($scope, $http, ENV) {
     var url = ENV.HOST + "/security/resource";
     $http.get(url).success(function(data) {
         $scope.greeting = data;
     })
  })
.controller('navigation',
  function($rootScope, $scope, $http, $location, ENV) {
    var url = ENV.HOST + "/security/authenticated";
    var authenticate = function(credentials, callback) {

      var headers = credentials ? {authorization : "Basic "
      + btoa(credentials.username + ":" + credentials.password)
    } : {};

       $http.get(url, {headers : headers}).success(function(data) {
         if (data.name) {
           $rootScope.authenticated = true;
           $rootScope.admin = false;
           for (var i = 0; i < data.authorities.length; i++) {
              if (data.authorities[i].authority == 'ROLE_ADMIN') {
                $rootScope.admin = true;
                break;
              }
           }
         } else {
           $rootScope.admin = false;
           $rootScope.authenticated = false;
         }
         callback && callback();
       }).error(function() {
         $rootScope.authenticated = false;
         callback && callback();
       });
    }

    authenticate();
    $scope.credentials = {};
    $scope.login = function() {
      authenticate($scope.credentials, function() {
        if ($rootScope.authenticated) {
          $location.path("/");
          $scope.error = false;
        } else {
          $location.path("/login");
          $scope.error = true;
        }
      });
    };

    $scope.logout = function() {
       $http.post(ENV.HOST + '/logout', {}).success(function() {
         $rootScope.authenticated = false;
         $location.path("/");
       }).error(function(data) {
         $rootScope.authenticated = false;
       });
    }

})
.controller('reportController',
  function($rootScope, $scope, $http, $location, ENV){

  $scope.reports;
  $scope.start;
  $scope.end;
  function getReport() {
    var url = ENV.HOST + "/reports";
    if ($scope.start) {
        url += "?start=" + $scope.start;
    }
    if ($scope.end) {
        url += "&end=" + $scope.end;
    }
    $http.get(url).then(function(response){
      $scope.reports = response.data;
    });
  }
  getReport();

  $scope.getWeekDates = function(week) {
    var date = "";
    if (week.weekStart[1] < 10) {
        date += "0";
    }
    date += week.weekStart[1] + ".";
    if (week.weekStart[2] < 10) {
        date += "0";
    }
    date += week.weekStart[2] + "-";
    if (week.weekEnd[1] < 10) {
        date += "0";
    }
    date += week.weekEnd[1] + ".";
    if (week.weekEnd[2] < 10) {
        date += "0";
    }
    date += week.weekEnd[2];
    return date;
  }

$scope.listedSize = function(listed){
    return Object.keys(listed).length;
}
$scope.presentListed = function(listed) {
    var size = 0, key;
    for (name in listed) {
        if (listed.hasOwnProperty(name) && listed[name]) {
            size++;
        };
    }
    return size;
}

}).controller('usersController',
  function($rootScope, $scope, $http, $location, ENV){

    $scope.users = null;
    var getUsers = function() {
        $http.get(ENV.HOST + "/security/users").then(function(response){
              $scope.users = response.data;
            });
    }
    getUsers();

    $scope.copy;
    $scope.password;
    $scope.changePassword;

    $scope.selectUser = function(user) {
      $scope.changePassword = false;
      $scope.copy = {};
      $scope.copy.username = user.username;
      $scope.copy.password = null;
      $scope.copy.roles = [];
      for (var i = 0; i < user.roles.length; i++) {
        $scope.copy.roles[i] = user.roles[i];
      }
    };

    $scope.changePasswordFlag = function () {
        $scope.changePassword = !$scope.changePassword;
    }

    getUserCopy = function(user){
      var copy = {};
      $scope.copy.username = user.username;
      $scope.copy.roles = [];
      for (var i = 0; i < user.roles.length; i++) {
        $scope.copy.roles[i] = user.roles[i];
      }
      return copy;
    }

    $scope.arrayToString = function(array){
      return array.join(", ");
    };

    $scope.submitUpdate = function() {
      if ($scope.changePassword == true) {
        $scope.copy.password = $scope.password;
      }
      $http.put(ENV.HOST + "/security/users/user", $scope.copy).then(function(response){
        $scope.copy = {};
        getUsers();
      }, function(error) {
        console.log(error)
      });
    };

    $scope.newRole = null;
    $scope.removeRole = function(role) {
       if (!role || $scope.copy.roles.indexOf(role) == -1) {
         return;
       }
       $scope.copy.roles.splice($scope.copy.roles.indexOf(role), 1);
    }

    $scope.addRole = function() {
      //check duplicate case
      if (!$scope.newRole || findItem($scope.copy.roles, $scope.newRole) !== -1) {
        return;
      }
      $scope.copy.roles.push($scope.newRole);
      $scope.newRole = null;
    }

    findItem = function(array, item) {
      if (array == null) {
        return -1;
      }
      for (var i = 0; i < array.length; i++) {
        if (array[i] == item){
          return i;
        }
      }
      return -1;
    }
});
