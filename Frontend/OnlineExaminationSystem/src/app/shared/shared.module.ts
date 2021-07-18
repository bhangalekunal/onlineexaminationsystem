import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FilterFieldsComponent } from './filter-fields/filter-fields.component';
import { FormsModule } from '@angular/forms';
import { FilterPipe } from './filterpipes/filter.pipe';
import { CreateProfileButtonComponent } from './create-profile-button/create-profile-button.component';
import { RefreshProfileButtonComponent } from './refresh-profile-button/refresh-profile-button.component';





@NgModule({
  declarations: [
    FilterFieldsComponent,
    FilterPipe,
    CreateProfileButtonComponent,
    RefreshProfileButtonComponent
  ],
  imports: [
    CommonModule,
    FormsModule
  ],
  providers: [

  ],
  exports: [
    FilterFieldsComponent,
    FilterPipe,
    CreateProfileButtonComponent,
    RefreshProfileButtonComponent
]
})
export class SharedModule { }
