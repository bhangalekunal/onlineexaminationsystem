import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.css']
})
export class DepartmentComponent implements OnInit {

  departments: any[]= [
    {
      departmentCode: 'CS',
      departmentName: 'Computer Engineering',
      status: 'ACTIVE'
    },
    {
      departmentCode: 'ME',
      departmentName: 'Mecanical Engineering',
      status: 'ACTIVE'
    },
    {
      departmentCode: 'CE',
      departmentName: 'Civil Engineering',
      status: 'ACTIVE'
    }
  ];
  constructor() { }

  ngOnInit(): void {
  }

}