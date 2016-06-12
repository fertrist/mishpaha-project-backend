angular.module('hello', [ 'ngRoute' ])
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
    controller : 'tableCtrl'
  }).when('/list',{
    templateUrl : 'list.html',
    controller : 'listCtrl'
  }).when('/report',{
    templateUrl : 'report.html',
    controller : 'reportCtrl'
  }).when('/users',{
        templateUrl : 'users.html',
        controller : 'usersCtrl'
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
.controller('tableCtrl', function($q, $rootScope, $scope, $http, $location, ENV){

    //get people list for people panel
    $scope.tribes;
    function getPeople() {
        var url = ENV.HOST + "/people/summary";
        $http.get(url).then(function(response){
          $scope.tribes = response.data;
        });
    }

    //get people categories and configure styles for people panel
    $scope.categories;
    $scope.categoryStyles;
    function setCategories() {
        var defer = $q.defer();
        var url = ENV.HOST + "/people/categories";
        $http.get(url).then(function(response){
          $scope.categories = response.data;
          $scope.categoryStyles = [];
          for(var i = 0; i < $scope.categories.length; i++) {
            var color;
            if ($scope.categories[i].name == "white") {
                color = "#E5EEE0";
            } else if ($scope.categories[i].name == "blue") {
                color = "#5ED1BA";
            } else if ($scope.categories[i].name == "green") {
                color = "#7AEE3C";
            } else if ($scope.categories[i].name == "brown") {
                color = "#91B52D";
            }
            if(color) {
                $scope.categoryStyles[$scope.categories[i].id] = color;
            }
          }
          $scope.categoryStyles["leader"] = "#FFC39F";
          defer.resolve(response.data);
        }, function(response) {
            defer.reject(response);
        });
        return defer.promise;
    }

    //default dates logic
    $scope.start;
    $scope.end;
    function setDefaultDates() {
        var days = new Date().getDate();
        $scope.start = new Date().setDate(days - 15);
        $scope.end = new Date().setDate(days + 15);
    }

    function dateToString(d) {
        var date = new Date(d);
        var day = date.getDate();
        var month = date.getMonth() + 1;
        var year = date.getFullYear()
        var result = year + "-";
        if (month < 10) {
            result += "0";
        }
        result += month + "-";
        if (day < 10) {
            result += "0";
        }
        return result += day;
    }

    function arrayDateToString(date) {
        var year = date[0];
        var month = date[1];
        var day = date[2];
        var result = year + "-";
        if (month < 10) {
            result += "0";
        }
        result += month + "-";
        if (day < 10) {
            result += "0";
        }
        return result += day;
    }

    $scope.getMiniDate = function(date) {
        var str = dateToString(date);
        return str.slice(5,date.length);
    }

    //get all events for a time range
    $scope.eventsObj;
    function getEvents() {
        var defer = $q.defer();
        var url = ENV.HOST + "/events";
        if ($scope.start) {
            url += "?start=" + dateToString($scope.start);
        }
        if ($scope.end) {
            url += "&end=" + dateToString($scope.end);
        }
        $http.get(url).then(function(response){
          $scope.eventsObj = response.data;
          defer.resolve(response.data);
        }, function(response) {
           defer.reject(response);
        });

        return defer.promise;
    }

    //get specific dates range for calendar header panel
    $scope.dates;
    function generateDates() {
        if ($scope.start && $scope.end) {
            //generate dates between them
            $scope.dates = [];
            var current = new Date($scope.start);
            while(current < $scope.end) {
                current.setDate(current.getDate() + 1);
                $scope.dates.push(new Date(current));
            }
        } else {
            //generate dates from events
        }
    }

    //get background style for a given category
    $scope.backGround = function(categoryId) {
        return {"background-color" : $scope.categoryStyles[categoryId]};
    }

    //get event types and configure their styles
    $scope.eventTypes;
    $scope.eventButtons;
    $scope.eventTypesStyles;
    function getEventTypes() {
        var url = ENV.HOST + "/events/types";
        $http.get(url).then(function(response){
          $scope.eventTypes = response.data;
          $scope.eventButtons = [];
          $scope.eventTypesStyles = [];
          for(var i = 0; i < $scope.eventTypes.length; i++) {
            $scope.eventButtons.push($scope.eventTypes[i].type);
            var color;
            if ($scope.eventTypes[i].type == "meeting") {
                color = "#3DF36D";
            } else if ($scope.eventTypes[i].type == "visit") {
                color = "#A64100";
            } else if ($scope.eventTypes[i].type == "call") {
                color = "#FFF040";
            } else if ($scope.eventTypes[i].type == "group") {
                color = "#FF6400";
            } else if ($scope.eventTypes[i].type == "club") {
                color = "#FF8373";
            } else if ($scope.eventTypes[i].type == "holiday") {
                color = "#3BCCEE";
            }
            if(color) {
                $scope.eventTypesStyles[$scope.eventTypes[i].id] = color;
            }
          }
          $scope.eventButtons.push("clean");
        });
    }

    $scope.eventsForDate;

    $scope.checkedType = 0;

    //which event we are currently working with
    $scope.setCheckedType = function(index) {
      $scope.checkedType = index;
    }

    $scope.saveEvent = function(personId, date) {
      var parsedDate = [parseInt(date.substring(0,4)), parseInt(date.substring(5,7)), parseInt(date.substring(8,10))];
      var event = {"typeId" : $scope.checkedType, happened: parsedDate};
        //check removal case
        if ($scope.eventTypes[$scope.checkedType] == "clean") {
          return;
        }
        //check duplicate case
        if (findEventInEvents($scope.eventsObj["p_" + personId], event) !== -1) {
          return;
        }
        //check addition case
        if ($scope.eventsObj["p_" + personId] == null) {
          $scope.eventsObj["p_" + personId]=[];
        }
        var url = ENV.HOST + "/events/event";
        event["id"] = 0;
        $http.post(url, event).success(function(response){
            event = response.data;
        });
        if (event.id != 0) {
            $scope.eventsObj["p_" + personId].push(event);
        }
      }

      $scope.removeEvent = function(personId, event) {
        if ($scope.eventTypes[$scope.checkedType] == "clean") {
            return;
        }
        var index = findEventInEvents($scope.eventsObj["p_" + personId], event);
        if (index == -1) {
          return;
        }
        var url = ENV.HOST + "/events/event/" + event.id;
        $http.delete(url).success(function(response){
            $scope.eventsObj["p_" + personId].splice(index, 1);
        });
      }

      findEventInEvents = function(events, event) {
        if (events == null) {
          return -1;
        }
        for (var i = 0; i < events.length; i++) {
          if (events[i].typeId == event.typeId
            && events[i].happened[0] == event.happened[0]
            && events[i].happened[1] == event.happened[1]
            && events[i].happened[2] == event.happened[2])
            return i;
        }
        return -1;
      }

      //from events get events for person for a given date thus for 1 cell
      $scope.getEventsForDate = function(personId, date) {
        var personEvents = $scope.eventsObj["p_" + personId];
        if (personEvents == null) {
          return;
        }
        $scope.eventsForDate = new Array();
        var happened;
        for (var i = 0; i < personEvents.length; i++) {
          happened = arrayDateToString(personEvents[i].happened);
          if (happened == dateToString(date)) {
            $scope.eventsForDate.push(personEvents[i]);
          }
        }
        if ($scope.eventsForDate.length == 0) {return;} else {return $scope.eventsForDate;}
      };

      setDefaultDates();
      getEvents();
      getPeople();
      setCategories();
      generateDates();
      getEventTypes();

    })
.controller('listCtrl', function($rootScope, $scope, $http, $location){

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
.controller('reportCtrl',
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

}).controller('usersCtrl',
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
