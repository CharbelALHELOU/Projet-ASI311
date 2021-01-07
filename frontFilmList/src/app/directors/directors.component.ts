import { Component, OnInit } from '@angular/core';
import { Director } from './director.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-directors',
  templateUrl: './directors.component.html',
  styleUrls: ['./directors.component.css']
})
export class DirectorsComponent implements OnInit {

  directors: Director[];
  added_director: Director;

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.getReals().subscribe(
      data => { this.directors = data; }
    )
  }

  /**
   * Recupere la liste des realisateurs depuis l'API
   */
  getReals(): Observable<Director[]> {
    return this.http.get<Director[]>('http://localhost:8080/director');
  }

  /**
   * Ajoute un realisateur à la base de donnees à partir du formulaire d'ajout rempli par l'utilisateur
   * @param form le formulaire rempli
   */
  processAddingForm(form): Observable<Director> {
    if (form.invalid) {
      alert("The form is incorrect")
      return;
    }
    this.added_director = new Director(form.value);
    this.http.post<Director>('http://localhost:8080/director', this.added_director).subscribe();
    location.reload();
  }

  /**
   * Bouton details d'un realisateur
   * @param id l'id du realisateur
   */
  btnClick(id: number): void {
    this.router.navigateByUrl('/director/' + id.toString());
  }
}
