import { Component, OnInit } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { MENU_ITEMS } from './pages-menu';

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.css']
})
export class PagesComponent implements OnInit {
  menu = MENU_ITEMS;
  isOpened: boolean = false;
  private titleSubject = new BehaviorSubject<any>({headingTitle:'demoheading',childTitle:'demochild'});
  public titleAction$ = this.titleSubject.asObservable();
  constructor() {
   
   }

  ngOnInit(): void {
  }

  toggleSidebar() {
    this.isOpened = !this.isOpened;
  }

  public changeTitle(heading: string, child: string): void
  {
    const titleObj = {headingTitle: heading,childTitle: child};
      this.titleSubject.next(titleObj);
  }


}
