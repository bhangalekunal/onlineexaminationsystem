import { Component, OnInit } from '@angular/core';
import { MENU_ITEMS } from './pages-menu';

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.css']
})
export class PagesComponent implements OnInit {
  menu = MENU_ITEMS;
  isOpened: boolean = false;
  constructor() { }

  ngOnInit(): void {
  }

  toggleSidebar() {
    this.isOpened = !this.isOpened;
  }


}
