angular.module('people')
.factory('tableDataFactory', function($http, ENV) {
     var factoryResult = {
         getPeopleInfo: function() {
             var url = ENV.HOST + "/people/info";
             var promise = $http.get(url).then(function(response) {
                 return response.data;
             });
             return promise;
         },

         getPeopleSummary: function() {
             var url = ENV.HOST + "/people/summary";
             var promise = $http.get(url).then(function(response) {
                 return response.data;
             });
             return promise;
         },

         getEventTypes: function() {
            var url = ENV.HOST + "/events/types";
            var promise = $http.get(url).then(function(response){
                 return response.data;
            });
            return promise;
         },

         getCategories: function() {
            var url = ENV.HOST + "/people/categories";
            var promise = $http.get(url).then(function(response){
                 return response.data;
            });
            return promise;
         },

         getEvents: function() {
            var url = ENV.HOST + "/events";
            var promise = $http.get(url).then(function(response){
                return response.data;
            });
            return promise;
         }

     };
     return factoryResult;
 });