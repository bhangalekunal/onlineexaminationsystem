import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageoneComponent } from './pageone.component';

const routes: Routes = [
  {
    path: '',
    component: PageoneComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PageoneRoutingModule { }
