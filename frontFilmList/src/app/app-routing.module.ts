import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FilmDetailComponent } from './film-detail/film-detail.component';
import { FilmsComponent } from './films/films.component';
import { DirectorDetailComponent } from './director-detail/director-detail.component';
import { DirectorsComponent } from './directors/directors.component';


const routes: Routes = [
  { path: '', component: DashboardComponent },
  { path: 'film', component: FilmsComponent },
  { path: 'film/:id', component: FilmDetailComponent },
  { path: 'director', component: DirectorsComponent },
  { path: 'director/:id', component: DirectorDetailComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
