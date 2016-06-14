angular.module('people')
.controller('tableController', function($q, $rootScope, $scope, $http, $location,
 ENV, peopleData, eventTypesData, categoriesData, eventsData){

    $scope.tribes = peopleData;
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
        }
        if(color) {
            $scope.categoryStyles[$scope.categories[i].id] = color;
        }
      }
      $scope.categoryStyles["leader"] = "#FFC39F";
    }
    getCategories();

    //default dates logic
    $scope.start;
    $scope.end;
    function setDefaultDates() {
        var days = new Date().getDate();
        $scope.start = new Date().setDate(days - 15);
        $scope.end = new Date().setDate(days + 15);
    }
    setDefaultDates();

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

    //get event types and configure their styles
    $scope.eventTypes = eventTypesData;
    $scope.eventButtons;
    $scope.eventTypesStyles;
    function getEventTypes() {
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
        } else if ($scope.eventTypes[i].type == "common") {
            color = "#3BCCEE";
        }
        if(color) {
            $scope.eventTypesStyles[$scope.eventTypes[i].id] = color;
        }
      }
      $scope.eventButtons.push("clean");
    }
    getEventTypes();

    //get all events for a time range
    $scope.events = eventsData;
    function getEvents() {
        var url = ENV.HOST + "/events";
        if ($scope.start) {
            url += "?start=" + dateToString($scope.start);
        }
        if ($scope.end) {
            url += "&end=" + dateToString($scope.end);
        }
        $http.get(url).then(function(response){
          $scope.events = response.data;
        });
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
                $scope.dates.push(dateToString(current));
            }
        } else {
            //generate dates from events
        }
    }
    generateDates();

    //get background style for a given category
    $scope.backGround = function(categoryId) {
        return {"background-color" : $scope.categoryStyles[categoryId]};
    }

    $scope.eventsForDate;

    $scope.checkedType = 1;

    //which event we are currently working with
    $scope.setCheckedType = function(index) {
        var type = $scope.eventButtons[index];
        if (type == "clean") {
            $scope.checkedType = -1;
        }
        for(var i = 0; i < $scope.eventTypes.length; i++) {
            if ($scope.eventTypes[i].type == type) {
                $scope.checkedType = $scope.eventTypes[i].id;
            }
        }
    }

    $scope.saveEvent = function(personId, groupId, date) {
        var parsedDate = [parseInt(date.substring(0,4)), parseInt(date.substring(5,7)), parseInt(date.substring(8,10))];
        var event = {"id" : 0, "personId" : personId, "groupId" : groupId,
         "typeId" : $scope.checkedType, happened: parsedDate};
        //check removal case
        if ($scope.checkedType == -1) {
          return;
        }
        //check duplicate case
        if (findEventInEvents($scope.events["p_" + personId], event) !== -1) {
          return;
        }
        //check addition case
        if ($scope.events["p_" + personId] == null) {
          $scope.events["p_" + personId]=[];
        }
        var url = ENV.HOST + "/events/event";
        event["id"] = 0;
        $http.post(url, event).then(function(response){
            event = response.data;
            $scope.events["p_" + personId].push(event);
        });
    }

      $scope.removeEvent = function(personId, event) {
        if ($scope.checkedType != -1) {
            return;
        }
        var index = findEventInEvents($scope.events["p_" + personId], event);
        if (index == -1) {
          return;
        }
        var url = ENV.HOST + "/events/event/" + event.id;
        $http.delete(url).success(function(response){
            $scope.events["p_" + personId].splice(index, 1);
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

        var personEvents = $scope.events["p_" + personId];
        if (personEvents == null) {
          return;
        }
        $scope.eventsForDate = new Array();
        for (var i = 0; i < personEvents.length; i++) {
          var happened = arrayDateToString(personEvents[i].happened);
          if (happened == date) {
            $scope.eventsForDate.push(personEvents[i]);
          }
        }
        if ($scope.eventsForDate.length == 0) {return;} else {return $scope.eventsForDate;}
      };

});