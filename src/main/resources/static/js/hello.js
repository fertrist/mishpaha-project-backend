angular.module('hello', [ 'ngRoute' ])
  .config(function($routeProvider, $httpProvider) {
    $routeProvider.when('/', {
        templateUrl : 'home.html',
        controller : 'home'
    }).when('/login', {
        templateUrl : 'login.html',
        controller : 'navigation'
    }).when('/people',{
        templateUrl : 'people.html',
        controller : 'peopleCtrl'
    }).otherwise('/');

    //$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
  })
  .controller('home', function($scope, $http) {
    $scope.greeting = "Hello, World!";
    // $http.get('/resource/').success(function(data) {
    //     $scope.greeting = data;
    // })
  })
 .controller('navigation',
    function($rootScope, $scope, $http, $location) {
    var authenticate = function(credentials, callback) {

      var headers = credentials ? {authorization : "Basic "
          + btoa(credentials.username + ":" + credentials.password)
      } : {};

      // $http.get('user', {headers : headers}).success(function(data) {
      //   if (data.name) {
      //     $rootScope.authenticated = true;
      //   } else {
      //     $rootScope.authenticated = false;
      //   }
      //   callback && callback();
      // }).error(function() {
      //   $rootScope.authenticated = false;
      //   callback && callback();
      // });
       $rootScope.authenticated = true;
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
      // $http.post('logout', {}).success(function() {
      //   $rootScope.authenticated = false;
      //   $location.path("/");
      // }).error(function(data) {
      //   $rootScope.authenticated = false;
      // });
      $rootScope.authenticated = false;
      $location.path("/");
    }

  })
.controller('peopleCtrl', function($rootScope, $scope, $http, $location){
    $scope.dates = [
      "4-10",
      "4-11",
      "4-12",
      "4-13",
      "4-14",
      "4-15",
      "4-16",
      "4-17",
      "4-18",
      "4-19",
      "4-20",
      "4-21",
      "4-22",
      "4-23",
      "4-24",
      "4-25",
      "4-26",
      "4-27",
      "4-28",
      "4-29",
      "4-30",
      "5-1",
      "5-2",
      "5-3",
      "5-4",
      "5-5",
      "5-6",
      "5-7",
      "5-8",
      "5-9",
      "5-10"
    ];
    //white, blue, green, jewish
    $scope.categories = ["#FFC39F" /*leader*/, "#E5EEE0" /*white*/, "#5ED1BA" /*blue*/, "#7AEE3C" /*green*/, "#91B52D" /*jew*/];
    $scope.backGround = function(category) {
        return {"background-color" : $scope.categories[category-1]};
    }

    $scope.groups = [
          {id:1, regionId:1, leader:"Имя-3 Фамилия-3",
            persons:[            
            {id:1, firstName : "Name-1", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 3, isJew : true, givesTithe : true, comment : null},
            {id:2, firstName : "Name-2", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 2, isJew : true, givesTithe : true, comment : null},
            {id:3, firstName : "Name-3", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 4, isJew : true, givesTithe : true, comment : null},
            {id:4, firstName : "Name-5", midName : "Фамилия-3", lastName : "Отчество-3", categoryId : 1, isJew : false, givesTithe : false, comment : null},
            {id:5, firstName : "Name-4", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 5, isJew : true, givesTithe : true, comment : null},
            {id:6, firstName : "Name-6", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 4, isJew : true, givesTithe : true, comment : null},
            {id:7, firstName : "Name-7", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 3, isJew : true, givesTithe : true, comment : null},
            {id:8, firstName : "Name-8", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 2, isJew : true, givesTithe : true, comment : null},
            {id:9, firstName : "Name-9", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 3, isJew : true, givesTithe : true, comment : null}
            ]
          }
    ];

    $scope.events = [
      {personId : 1, events : [
        {"id":2144,"typeId":1,"happened":[2016,4,1]},
        {"id":2145,"typeId":2,"happened":[2016,4,10]}]
      },
      {personId : 2, events : [
        {"id":2146,"typeId":3,"happened":[2016,4,11]},
        {"id":2147,"typeId":4,"happened":[2016,4,13]},
        {"id":2155,"typeId":2,"happened":[2016,4,13]},
        {"id":2156,"typeId":3,"happened":[2016,4,13]}]
      },
      {personId : 3, events : [
        {"id":2148,"typeId":1,"happened":[2016,4,13]},
        {"id":2149,"typeId":2,"happened":[2016,4,15]}]
      },
      {personId : 8, events : [
        {"id":2150,"typeId":3,"happened":[2016,4,10]}]
      },
      {personId : 5, events : [
        {"id":2151,"typeId":4,"happened":[2016,4,18]}],
      },
      {personId : 6, events : [
        {"id":2152,"typeId":1,"happened":[2016,4,20]}]
      },
      {personId : 7, events : [
        {"id":2153,"typeId":2,"happened":[2016,4,30]}]
      },
      {personId : 9, events : [
        {"id":2154,"typeId":1,"happened":[2016,5,4]}]
      }
    ];

    
    //"Встреча", "Посещение", "Звонок", "Группа", "Клуб", "Шабат"
    $scope.eventTypes = ["meet", "visit", "call", "group", "club", "shabat"];
    $scope.eventStyles = ["#3DF36D", "#A64100", "#FFF040", "#FF6400", "#FF8373", "#3BCCEE"];
    $scope.eventsForDate;
    $scope.getEventStyle = function(eventType) {
      var height = 100 / $scope.eventsForDate.length + "%";
      var bg = $scope.eventStyles[eventType-1];
      var obj = {"background-color" : bg, "height" : height};
      return obj;
    }
    $scope.getEventsForDate = function(personId, date) {
      var personEvents;
      for (var i = 0; i < $scope.events.length; i++) {
          if ($scope.events[i].personId == personId) {
            personEvents = $scope.events[i].events;
            break;
          }
      }
      if (personEvents == null) {
          return;
      }
      $scope.eventsForDate = new Array();
      var happened;
      for (var i = 0; i < personEvents.length; i++) {
        happened = personEvents[i].happened[1]+"-"+personEvents[i].happened[2];
        if (happened == date) {
          $scope.eventsForDate.push(personEvents[i]);
        }
      }  
      if ($scope.eventsForDate.length == 0) {return;} else {return $scope.eventsForDate;}
    };
});