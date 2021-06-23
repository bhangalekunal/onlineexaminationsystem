import { Component } from '@angular/core';
declare var $: any;
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'OnlineExaminationSystem';
  constructor() {
    $('body').tooltip({selector: '[data-bs-toggle="tooltip"]'});
   }
}
