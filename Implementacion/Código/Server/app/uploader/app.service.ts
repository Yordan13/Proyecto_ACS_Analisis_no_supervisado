
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import {Observable} from 'rxjs/Rx';

@Injectable()


export class StateService{
  constructor(private http: Http) { }

  getState(i:any) {
  	var ext = i.file.name.substring(i.file.name.lastIndexOf('.') + 1);
  	if (ext == "mp4" || ext == "avi"){
	     return Observable.interval(1)
	      .switchMap(() => this.http.get('http://localhost:8000/'+i.file.name))
	      .map(res => res.json());
  	}else{
  		alert("Dirección del video es incorrecta.");
  		return null;
  	}
  }

  dowload(i:any) {
    i.downloadUrl = 'http://localhost:8000/dl/output.avi';
  }

}
