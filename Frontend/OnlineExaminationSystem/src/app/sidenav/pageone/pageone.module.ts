import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PageoneRoutingModule } from './pageone-routing.module';
import { PageoneComponent } from './pageone.component';


@NgModule({
  declarations: [
    PageoneComponent
  ],
  imports: [
    CommonModule,
    PageoneRoutingModule
  ]
})
export class PageoneModule { }
