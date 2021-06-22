import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DepartmentRoutingModule } from './department-routing.module';
import { FilterPipe } from '../filterpipes/filter-pipe.pipe'
import { DepartmentComponent } from './department.component';
import { FilterFieldsComponent } from '../filter-fields/filter-fields.component';

@NgModule({
  declarations: [
    DepartmentComponent,
    FilterPipe,
    FilterFieldsComponent
  ],
  imports: [
    CommonModule,
    DepartmentRoutingModule,
    FormsModule
  ]
})
export class DepartmentModule { }

