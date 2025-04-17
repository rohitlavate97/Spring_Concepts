import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard } from './guards/auth.guard';

const routes: Routes = [ // localhost:4200/auth
  {
    path: 'auth',
    loadChildren: () => import('./auth/auth.module').then((authmodule) => authmodule.AuthModule)
  },
  {
   path: 'dashboard',
   loadChildren: () => import('./dashboard/dashboard.module').then((dashboardmodule) => dashboardmodule.DashboardModule),
   canActivate:[authGuard] 
  },
  {
    path: '',
    redirectTo: 'auth',
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
