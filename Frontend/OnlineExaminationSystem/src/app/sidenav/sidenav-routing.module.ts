import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SidenavComponent } from './sidenav.component';

const routes: Routes = [
  {
    path: '',
    component: SidenavComponent,
    children: [
      {
        path: 'pageone',
        loadChildren: () => import('./pageone/pageone.module')
          .then(m => m.PageoneModule),
      },
      {
        path: 'pagetwo',
        loadChildren: () => import('./pagetwo/pagetwo.module')
          .then(m => m.PagetwoModule),
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SidenavRoutingModule { }
