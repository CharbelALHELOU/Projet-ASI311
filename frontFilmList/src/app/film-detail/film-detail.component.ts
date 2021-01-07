import { Component, OnInit } from '@angular/core';
import { Film } from '../films/film.model';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Director } from '../directors/director.model';


@Component({
  selector: 'app-film-detail',
  templateUrl: './film-detail.component.html',
  styleUrls: ['./film-detail.component.css']
})
export class FilmDetailComponent implements OnInit {
  id: number;
  film: Film = new Film();
  updated_film: Film = new Film();
  showForm: Boolean;
  directors: Director[];
  selected_real: Director;

  constructor(private http: HttpClient, private activatedRoute: ActivatedRoute, private router: Router) {
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(parameter => {
      this.id = +parameter.id;
    })
    this.getFilm().subscribe(
      data => { this.film = data; }
    )
    this.getDirectors().subscribe(
      data => { this.directors = data; }
    )
    this.showForm = false;
  }

  /**
   * Recupere le film corrspondant à l'id de cette page
   */
  getFilm(): Observable<Film> {
    return this.http.get<Film>('http://localhost:8080/film/{id}?id=' + this.id.toString());
  }

  /**
   * Supprime le film 
   */
  deleteFilm(): void {
    if (confirm("Are you sure?")) {
      console.log(this.film);
      let params = new HttpParams();
      params = params.append('id', this.id.toString());
      this.http.delete<Film>('http://localhost:8080/film', { params: params }).subscribe()
      this.router.navigateByUrl('/film')
    }
  }

  btnClick(): void {
    this.router.navigateByUrl('/film');
  }

  getDirectors(): Observable<Director[]> {
    return this.http.get<Director[]>('http://localhost:8080/director');
  }

  /**
   * Met à jour le film à partir du formulaire de mise à jour rempli par l'utilisateur
   * @param form le formulaire recupere au submit
   */
  processUpdateForm(form): Observable<Film> {
    if (form.invalid) {
      alert("The form is incorrect")
      return;
    }
    this.updated_film.id = this.film.id;
    this.updated_film.directorID = +this.selected_real.id;
    this.updated_film.titre = form.value.titre;
    this.updated_film.directorName = this.selected_real.firstName.concat(" ", this.selected_real.lastName.toString());
    this.updated_film.duration = +form.value.duration;
    this.http.put<Film>('http://localhost:8080/film', this.updated_film).subscribe();
    location.reload();
  }
}
