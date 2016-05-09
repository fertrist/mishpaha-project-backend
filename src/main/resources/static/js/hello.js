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
      "2016-04-10",
      "2016-04-11",
      "2016-04-12",
      "2016-04-13",
      "2016-04-14",
      "2016-04-15",
      "2016-04-16",
      "2016-04-17",
      "2016-04-18",
      "2016-04-19",
      "2016-04-20",
      "2016-04-21",
      "2016-04-22",
      "2016-04-23",
      "2016-04-24",
      "2016-04-25",
      "2016-04-26",
      "2016-04-27",
      "2016-04-28",
      "2016-04-29",
      "2016-04-30",
      "2016-05-01",
      "2016-05-02",
      "2016-05-03",
      "2016-05-04",
      "2016-05-05",
      "2016-05-06",
      "2016-05-07",
      "2016-05-08",
      "2016-05-09",
      "2016-05-10"
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

    $scope.eventsObj = {
      "p_1" :
      [
        {"id":2144,"typeId":1, happened : [2016,4,1]},
        {"id":2145,"typeId":2, happened : [2016,4,10]}
      ],
      "p_2" : 
      [
        {"id":2146,"typeId":3,happened:[2016,4,11]},
        {"id":2147,"typeId":4,happened:[2016,4,13]},
        {"id":2155,"typeId":2,happened:[2016,4,13]},
        {"id":2156,"typeId":3,happened:[2016,4,13]}
      ],
      "p_3" : 
      [
        {"id":2148,"typeId":1,happened:[2016,4,13]},
        {"id":2149,"typeId":2,happened:[2016,4,15]}
      ],
      "p_8" : 
      [
        {"id":2150,"typeId":3,happened:[2016,4,10]}
      ],
      "p_5" : 
      [
        {"id":2151,"typeId":4,happened:[2016,4,18]}
      ],
      "p_6" : 
      [
        {"id":2152,"typeId":1,happened:[2016,4,20]}
      ],
      "p_7" : 
      [
        {"id":2153,"typeId":2,happened:[2016,4,30]}
      ],
      "p_9" : 
      [
        {"id":2154,"typeId":1,happened:[2016,5,4]}
      ]
    };
    
    //"Встреча", "Посещение", "Звонок", "Группа", "Клуб", "Шабат"
    $scope.eventTypes = ["meet", "visit", "call", "group", "club", "shabat", "clean"];
    $scope.eventStyles = ["#3DF36D", "#A64100", "#FFF040", "#FF6400", "#FF8373", "#3BCCEE"];
    $scope.eventsForDate;
    $scope.checkedType = 0;

    $scope.setCheckedType = function(index) {
      $scope.checkedType = index;
    }

    $scope.setEvent = function(personId, date) {
        var parsedDate = [parseInt(date.substring(0,4)), parseInt(date.substring(5,7)), parseInt(date.substring(8,10))];
        var event = {"id" : 1000, "typeId" : $scope.checkedType, happened: parsedDate};
        //check removal case
        if ($scope.checkedType==$scope.eventTypes.indexOf("clean")) {
            return;
        }
        //check duplicate case
        if (findItem($scope.eventsObj["p_" + personId], event) !== -1) {
          return;
        }
        //check addition case
        if ($scope.eventsObj["p_" + personId] == null) {
          $scope.eventsObj["p_" + personId]=[];
        }
        $scope.eventsObj["p_" + personId].push(event);
    }

    $scope.removeEvent = function(personId, event) {
      var index = findItem($scope.eventsObj["p_" + personId], event);
      if (index == -1) {
        return;
      }
      $scope.eventsObj["p_" + personId].splice(index, 1);
    }

    findItem = function(events, event) {
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

    $scope.getEventsForDate = function(personId, date) {
      var personEvents = $scope.eventsObj["p_" + personId];
      if (personEvents == null) {
          return;
      }
      $scope.eventsForDate = new Array();
      var happened;
      for (var i = 0; i < personEvents.length; i++) {
        happened = personEvents[i].happened[0] + "-";
        if("" + personEvents[i].happened[1].toString().length==1) {
          happened += "0";
        }
        happened += personEvents[i].happened[1] + "-";
        if(personEvents[i].happened[2].toString().length==1) {
          happened += "0";
        }
        happened += personEvents[i].happened[2];
        if (happened == date) {
          $scope.eventsForDate.push(personEvents[i]);
        }
      }  
      if ($scope.eventsForDate.length == 0) {return;} else {return $scope.eventsForDate;}
    };
});