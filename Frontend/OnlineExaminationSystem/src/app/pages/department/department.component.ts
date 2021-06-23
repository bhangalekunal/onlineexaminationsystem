import { Component, OnInit } from '@angular/core';
import { FieldDetails } from 'src/app/basicmodels/FieldDetails';

@Component({
  selector: 'app-department',
  templateUrl: './department.component.html',
  styleUrls: ['./department.component.css']
})
export class DepartmentComponent implements OnInit {
  searchString: string = "";
  searchField: string = "";
 

  fields: FieldDetails[] = [
    {
      displayFieldName: 'Department Code',
      actualFieldName: 'departmentCode'
    },
    {
      displayFieldName: 'Department Name',
      actualFieldName: 'departmentName'
    },
    {
      displayFieldName: 'Status',
      actualFieldName: 'status'
    }
  ];

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

  isFilterChange(event:any) {
    this.searchField = event.searchFieldName;
    this.searchString = event.searchFieldValue;
  }
}
