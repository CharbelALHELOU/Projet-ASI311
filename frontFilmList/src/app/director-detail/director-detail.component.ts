import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Director } from '../directors/director.model';
import { Film } from '../films/film.model';
import { FilmPage } from '../films/filmpage.modele';

@Component({
  selector: 'app-director-detail',
  templateUrl: './director-detail.component.html',
  styleUrls: ['./director-detail.component.css']
})
export class DirectorDetailComponent implements OnInit {

  id: number;
  director: Director = new Director();
  updatedDirector: Director = new Director();
  showForm: Boolean;
  films: Film[];
  number: number = 1;
  size: number = 10;
  total: number = 1;
  show : boolean = false;

  constructor(private http: HttpClient, private activatedRoute: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(parameter => {
      this.id = +parameter.id;
    })
    this.getDirector().subscribe(
      data => { this.director = data }
    )
    this.showForm = false;
  }

  getFilmsFiltered(): Observable<FilmPage> {
    return this.http.get<FilmPage>('http://localhost:8080/film/{titre}/{director}?titre=&director=' + this.director.firstName + ' ' + this.director.lastName + '&number=1&size=50&order=id&reverse=false');
  }

  getDirector(): Observable<Director> {
    return this.http.get<Director>('http://localhost:8080/director/{id}?id=' + this.id.toString());
  }

  deleteDirector(): void {
    if (confirm("Assurez-vous d'avoir au prealable supprime les films de ce realisteur.\n \nÊtes-vous sur de vouloir supprimer ce director : " + this.director.firstName + " " + this.director.lastName)) {
      console.log(this.director);
      let params = new HttpParams();
      params = params.append('id', this.id.toString());
      this.http.delete<Director>('http://localhost:8080/director', { params: params }).subscribe()
      this.router.navigateByUrl('/director')
    }
  }

  showFilms(){
    this.show = true;
    this.getFilmsFiltered().subscribe(
      page => {this.films = page.data; this.number = page.number; this.size = page.size; this.total = page.total;}
    )
  }

  processUpdateForm(form): Observable<Director> {
    if (form.invalid) {
      alert("The form is incorrect")
      return;
    }
    this.updatedDirector.id = this.director.id;
    this.updatedDirector.firstName = form.value.firstName;
    this.updatedDirector.lastName = form.value.lastName;
    this.updatedDirector.date = form.value.date;
    this.http.put<Director>('http://localhost:8080/director', this.updatedDirector).subscribe();
    location.reload();
  }
}
