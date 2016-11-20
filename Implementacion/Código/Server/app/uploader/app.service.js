"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var http_1 = require('@angular/http');
var Rx_1 = require('rxjs/Rx');
var StateService = (function () {
    function StateService(http) {
        this.http = http;
    }
    StateService.prototype.getState = function (i) {
        var _this = this;
        var ext = i.file.name.substring(i.file.name.lastIndexOf('.') + 1);
        if (ext == "mp4" || ext == "avi") {
            return Rx_1.Observable.interval(1)
                .switchMap(function () { return _this.http.get('http://localhost:8000/' + i.file.name); })
                .map(function (res) { return res.json(); });
        }
        else {
            alert("Dirección del video es incorrecta.");
            return null;
        }
    };
    StateService.prototype.dowload = function (i) {
        i.downloadUrl = 'http://localhost:8000/dl/output.avi';
    };
    StateService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], StateService);
    return StateService;
}());
exports.StateService = StateService;
//# sourceMappingURL=app.service.js.map