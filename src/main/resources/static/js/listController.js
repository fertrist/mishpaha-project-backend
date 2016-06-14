angular.module('people')
.controller('listController', function($rootScope, $scope, $http, $location, peopleData, categoriesData, ENV){

    $scope.arrayToString = function(string){
        return string.join(", ");
    };

    $scope.arrayToDate = function(date) {
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

  $scope.people = peopleData;

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
      $scope.copy.birthDay = $scope.arrayToDate(person.birthDay);
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

    $scope.categories = categoriesData;
    //configure styles for people panel
    $scope.categoryStyles;
    function getCategories() {
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
        } else if ($scope.categories[i].name == "guest") {
            color = "#FFF633";
        }
        if(color) {
            $scope.categoryStyles[$scope.categories[i].id] = color;
        }
      }
      //$scope.categoryStyles["leader"] = "#FFC39F";
    }
    getCategories();

    $scope.backGround = function(categoryId) {
      return {"background-color" : $scope.categoryStyles[categoryId]};
    };

    $scope.submitUpdate = function(groupId) {
        for (var t = 0; t < $scope.people.length; t++) {
            var tribe = $scope.people[t];
            for (var r = 0; r < tribe.regions.length; r++) {
                var region = tribe.regions[r];
                for (var g = 0; g < region.groups.length; g++) {
                    var group = region.groups[g];
                    for(var p = 0; p < group.persons.length; p++){
                        if (group.persons[p].id == $scope.copy.id) {
                            var url = ENV.HOST + "/people/person"
                            $http.put(url, $scope.copy).then(function(response){
                                var person = response.data;
                                group.persons[p] = person;
                                $scope.copy = {};
                            });
                            return;
                        }
                    }
                }
            }
        }
    }

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
});