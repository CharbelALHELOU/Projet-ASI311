import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Film } from './film.model';
import { Director } from '../directors/director.model';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { FilmPage } from './filmpage.modele';

@Component({
  selector: 'app-films',
  templateUrl: './films.component.html',
  styleUrls: ['./films.component.css']
})
export class FilmsComponent implements OnInit {

  films: Film[];
  directors: Director[];
  newFilm: Film = new Film();
  selected_real: Director;
  order: string = 'id';
  reverse: boolean = false;
  number: number = 1;
  size: number = 10;
  total: number = 1;
  realFilter: string = '';
  titleFilter: string = '';

  constructor(private http: HttpClient, private router: Router) {
  }

  ngOnInit(): void {
    this.getFilms().subscribe(
      page => { this.films = page.data; this.number = page.number; this.size = page.size; this.total = page.total }
    )
    this.getDirectors().subscribe(
      dir => { this.directors = dir; }
    )
  }

  btnClick(id: number): void {
    this.router.navigateByUrl('/film/' + id.toString());
  }

  getFilms(): Observable<FilmPage> {
    return this.http.get<FilmPage>('http://localhost:8080/film?number=' + this.number + '&size=' + this.size + '&order=' + this.order +"&sens=" + this.reverse);
  }

  getFilmsFiltered(): Observable<FilmPage> {
    return this.http.get<FilmPage>('http://localhost:8080/film/{titre}/{director}?titre=' + this.titleFilter + '&director=' + this.realFilter + '&number=' + this.number + '&size=' + this.size + '&order=' + this.order + '&reverse=' + this.reverse);
  }

  getDirectors(): Observable<Director[]> {
    return this.http.get<Director[]>('http://localhost:8080/director');
  }

  processAddingForm(form): Observable<Film> {
    if (form.invalid) {
      alert("The form is incorrect")
      return;
    }
    this.newFilm.directorID = +this.selected_real.id;
    this.newFilm.titre = form.value.title;
    this.newFilm.directorName = this.selected_real.firstName.concat(" ", this.selected_real.lastName.toString());
    this.newFilm.duration = +form.value.duration;
    this.http.post<Film>('http://localhost:8080/film', this.newFilm).subscribe(data => (this.films.push(data)));
    this.showAllFilms();
  }

  processFilterForm(form): Observable<Film[]> {
    if (form.invalid) {
      alert("The form is incorrect")
      return;
    }
    this.reverse = false;
    this.number = 1;
    this.realFilter = form.value.realFilter;
    this.titleFilter = form.value.titleFilter;
    this.showFilms();
    console.log(this.films.length);
  }


  showFilms(): void {
    if (this.realFilter == '' && this.titleFilter == '') {
      this.getFilms().subscribe(
        data => { this.films = data.data; this.number = data.number; this.size = data.size; this.total = data.total }
      )
    console.log(this.films);
    }
    else {
      this.getFilmsFiltered().subscribe(
        data => { this.films = data.data; this.number = data.number; this.size = data.size; this.total = data.total }
      )
    }
  }


  setNumber(number: number) {
    this.number = number;
    this.showFilms();
  }

  nextPage() {
    if (this.number < this.total) {
      this.number += 1;
      this.showFilms();
    }
  }

  prevPage() {
    if (this.number > 1) {
      this.number -= 1;
      this.showFilms();
    }
  }

  showAllFilms(): void {
    this.realFilter = '';
    this.titleFilter = '';
    this.setNumber(1);
    this.showFilms();
  }

  rev(){
    this.reverse = !this.reverse;
    this.number = 1;
    this.showFilms();
  }

  setOrder(value: string) {
    this.order = value;
    this.number = 1;
    this.showFilms();
  }

  setSize(size: number) {
    this.size = size
    this.number = 1;
    this.showFilms;
  }

}
