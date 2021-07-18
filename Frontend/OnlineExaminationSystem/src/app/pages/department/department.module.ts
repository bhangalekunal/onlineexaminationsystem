import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DepartmentRoutingModule } from './department-routing.module';
import { SharedModule } from '../../shared/shared.module';
import { DepartmentComponent } from './department.component';


@NgModule({
  declarations: [
    DepartmentComponent
  ],
  imports: [
    CommonModule,
    DepartmentRoutingModule,
    FormsModule,
    SharedModule
  ]
})
export class DepartmentModule { }

