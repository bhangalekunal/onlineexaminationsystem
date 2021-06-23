import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FieldDetails } from 'src/app/basicmodels/FieldDetails';

@Component({
  selector: 'app-filter-fields',
  templateUrl: './filter-fields.component.html',
  styleUrls: ['./filter-fields.component.css']
})
export class FilterFieldsComponent implements OnInit {

  
  @Input('fieldNames') fieldNames: FieldDetails[]=[];
  @Output() filterItemEvent = new EventEmitter<object>();
  constructor() {
   }

  ngOnInit(): void {
  }

  onClickSubmit(data: any) 
  {
    const obj = {searchFieldName: data.selectbox, searchFieldValue: data.searchbox};
    this.filterItemEvent.emit(obj);
  }
}
