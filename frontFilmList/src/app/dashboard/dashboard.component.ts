import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  filmsCount: number = 3;
  realsCount: number = 3;
  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.getFilmsCount().subscribe(
      data => { this.filmsCount = data }
    )
    this.getDirectorCount().subscribe(
      data => { this.realsCount = data }
    )
  }

  getFilmsCount(): Observable<number> {
    return this.http.get<number>('http://localhost:8080/film/count')
  }

  getDirectorCount(): Observable<number> {
    return this.http.get<number>('http://localhost:8080/director/count')
  }

  clickFilms(): void {
    this.router.navigateByUrl('/film');
  }
  clickDirectors(): void {
    this.router.navigateByUrl('/director');
  }
}
