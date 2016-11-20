import { Component } from '@angular/core';
import { SimpleDemoComponent } from './uploader/simple-demo';


@Component({
  selector: 'my-app',
  template: '<div class="triangle">  <h1><img src="/app/corona.png" id="logo" alt="logo" > </h1> <h1>FutAnalyzer</h1>  </div> <simple-demo> </simple-demo>',
  directives: [SimpleDemoComponent]
})
export class AppComponent { 



}