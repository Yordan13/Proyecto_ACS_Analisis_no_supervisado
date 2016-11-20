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
var common_1 = require('@angular/common');
var ng2_file_upload_1 = require('ng2-file-upload/ng2-file-upload');
require('rxjs/add/operator/map');
var app_service_1 = require('./app.service');
// const URL = '/api/';
var URL = 'http://localhost:8000/';
var SimpleDemoComponent = (function () {
    function SimpleDemoComponent(stateService) {
        this.uploader = new ng2_file_upload_1.FileUploader({ url: URL });
        this.hasBaseDropZoneOver = false;
        this.hasAnotherDropZoneOver = false;
        this.stateService = stateService;
    }
    SimpleDemoComponent.prototype.fileOverBase = function (e) {
        this.hasBaseDropZoneOver = e;
    };
    SimpleDemoComponent.prototype.fileOverAnother = function (e) {
        this.hasAnotherDropZoneOver = e;
    };
    SimpleDemoComponent.prototype.upload = function (i) {
        var _this = this;
        i.upload();
        var obs = this.stateService.getState(i);
        var sucription = obs.subscribe(function (data) {
            i.state = data.state;
            console.log(data);
            if (i.state == 100) {
                sucription.unsubscribe();
                _this.stateService.dowload(i);
            }
        }, function (err) { return console.log(err.toString()); });
        ;
    };
    SimpleDemoComponent = __decorate([
        core_1.Component({
            selector: 'simple-demo',
            templateUrl: '/app/uploader/template.html',
            directives: [ng2_file_upload_1.FILE_UPLOAD_DIRECTIVES, common_1.NgClass, common_1.NgStyle, common_1.CORE_DIRECTIVES, common_1.FORM_DIRECTIVES],
            providers: [app_service_1.StateService]
        }), 
        __metadata('design:paramtypes', [app_service_1.StateService])
    ], SimpleDemoComponent);
    return SimpleDemoComponent;
}());
exports.SimpleDemoComponent = SimpleDemoComponent;
//# sourceMappingURL=simple-demo.js.map