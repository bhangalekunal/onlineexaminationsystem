import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PagetwoComponent } from './pagetwo.component';

const routes: Routes = [
  {
    path: '',
    component: PagetwoComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PagetwoRoutingModule { }
