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
    $scope.number = 30;
    $scope.getNumber = function(num) {
        return new Array(num);   
    }
    //white, blue, green, jewish
    $scope.categories = ["#E5EEE0", "#5ED1BA", "#7AEE3C", "#91B52D"];
    $scope.backGround = function(category) {
        return categories[category];
    }

    $scope.groups = [
          {id:1, regionId:1, leader:"Имя-3 Фамилия-3",
            persons:[
            {id:7, firstName : "Имя-3", midName : "Фамилия-3", lastName : "Отчество-3", categoryId : 4, isJew : false, givesTithe : false, comment : null},
            {id:8, firstName : "Name-4", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 2, isJew : true, givesTithe : true, comment : null},
            {id:8, firstName : "Name-4", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 1, isJew : true, givesTithe : true, comment : null},
            {id:8, firstName : "Name-4", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 3, isJew : true, givesTithe : true, comment : null},
            {id:8, firstName : "Name-4", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 4, isJew : true, givesTithe : true, comment : null},
            {id:8, firstName : "Name-4", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 3, isJew : true, givesTithe : true, comment : null},
            {id:8, firstName : "Name-4", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 2, isJew : true, givesTithe : true, comment : null},
            {id:8, firstName : "Name-4", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 1, isJew : true, givesTithe : true, comment : null},
            {id:8, firstName : "Name-4", midName : "Фамилия-4", lastName : "Отчество-4", categoryId : 2, isJew : true, givesTithe : true, comment : null}
            ]
          }
    ];
});