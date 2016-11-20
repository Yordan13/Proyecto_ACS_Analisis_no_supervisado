import {Component} from '@angular/core';
import {CORE_DIRECTIVES, FORM_DIRECTIVES, NgClass, NgStyle} from '@angular/common';
import {FILE_UPLOAD_DIRECTIVES, FileUploader} from 'ng2-file-upload/ng2-file-upload';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';
import {StateService} from './app.service'


// const URL = '/api/';
const URL = 'http://localhost:8000/';

@Component({
  selector: 'simple-demo',
  templateUrl:'/app/uploader/template.html' ,
  directives: [FILE_UPLOAD_DIRECTIVES, NgClass, NgStyle, CORE_DIRECTIVES, FORM_DIRECTIVES],
  providers: [StateService]

})
export class SimpleDemoComponent {
  stateService: StateService;
  constructor(stateService: StateService) {
    this.stateService = stateService;
  }
  public uploader:FileUploader = new FileUploader({url: URL});
  public hasBaseDropZoneOver:boolean = false;
  public hasAnotherDropZoneOver:boolean = false;

  public fileOverBase(e:any):void {
    this.hasBaseDropZoneOver = e;
  }

  public fileOverAnother(e:any):void {
    this.hasAnotherDropZoneOver = e;
  }


  upload (i:any){
    i.upload();

    let obs = this.stateService.getState (i);
    let sucription = obs.subscribe(
        data => {i.state = data.state  ;
                  console.log (data);
                  if (i.state == 100){
                    sucription.unsubscribe();
                    this.stateService.dowload (i);
                  } },
        err => console.log(err.toString())
      );;


  }

}
