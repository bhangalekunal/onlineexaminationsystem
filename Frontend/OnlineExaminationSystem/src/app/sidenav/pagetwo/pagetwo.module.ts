import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PagetwoRoutingModule } from './pagetwo-routing.module';
import { PagetwoComponent } from './pagetwo.component';


@NgModule({
  declarations: [
    PagetwoComponent
  ],
  imports: [
    CommonModule,
    PagetwoRoutingModule
  ]
})
export class PagetwoModule { }
