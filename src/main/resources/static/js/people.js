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
            return tableDataFactory.getPeople();
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
    controller : 'listController'
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
.controller('listController', function($rootScope, $scope, $http, $location){

  $scope.people;
  function getPeople() {
    $scope.people = [];
  }
 
  $scope.copy;

  $scope.selectPerson = function(person) {
      $scope.copy = {};
      $scope.copy.id = person.id;
      $scope.copy.firstName = person.firstName;
      $scope.copy.midName = person.midName;
      $scope.copy.lastName = person.lastName;
      $scope.copy.categoryId = person.categoryId;
      $scope.copy.isJew = person.isJew;
      $scope.copy.givesTithe = person.givesTithe;
      $scope.copy.comment = person.comment;
      $scope.copy.sex = person.sex;
      $scope.copy.birthDay = person.birthDay;
      $scope.copy.emails = [];
      $scope.copy.phones = [];
      for (var i = 0; i < person.emails.length; i++) {
        $scope.copy.emails[i] = person.emails[i];
      }
      for (var i = 0; i < person.phones.length; i++) {
        $scope.copy.phones[i] = person.phones[i];
      }
      $scope.copy.address = person.address;
  };

    getPersonCopy = function(person){
      var copy = {};
      copy.id = person.id;
      copy.firstName = person.firstName;
      copy.midName = person.midName;
      copy.lastName = person.lastName;
      copy.categoryId = person.categoryId;
      copy.isJew = person.isJew;
      copy.givesTithe = person.givesTithe;
      copy.comment = person.comment;
      copy.sex = person.sex;
      copy.birthDay = person.birthDay;
      copy.emails = [];
      copy.phones = [];
      for (var i = 0; i < person.emails.length; i++) {
        copy.emails[i] = person.emails[i];
      }
      for (var i = 0; i < person.phones.length; i++) {
        copy.phones[i] = person.phones[i];
      }
      copy.address = person.address;
      return copy;
    }

    $scope.categories = ["#FFC39F" /*leader*/, "#E5EEE0" /*white*/, "#5ED1BA" /*blue*/,
    "#7AEE3C" /*green*/, "#91B52D" /*specific*/];

    $scope.backGround = function(category) {
      return {"background-color" : $scope.categories[category-1]};
    };

    $scope.arrayToString = function(string){
      return string.join(", ");
    };

    $scope.arrayToDate = function(array){
      return array.join("-");
    };

    $scope.submitUpdate = function(groupId) {
      for (var i = 0; i < $scope.groups.length; i++) {
        for (var j = 0; j < $scope.groups[i].persons.length; j++) {
          if ($scope.groups[i].persons[j].id == $scope.copy.id) {
            $scope.groups[i].persons[j] = getPersonCopy($scope.copy);
            $scope.copy = {};
            return;
          }
        }
      }
    };

    $scope.newPhone;
    $scope.newEmail;
    $scope.addPhone = function() {
      //check duplicate case
      if (!$scope.newPhone || findItem($scope.copy.phones, $scope.newPhone) !== -1) {
        return;
      }
      $scope.copy.phones.push($scope.newPhone);
      $scope.newPhone = "";
    }

    $scope.addEmail = function() {
      //check duplicate case
      if (!$scope.newEmail || findItem($scope.copy.emails, $scope.newEmail) !== -1) {
        return;
      }
      $scope.copy.emails.push($scope.newEmail);
      $scope.newEmail = "";
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
