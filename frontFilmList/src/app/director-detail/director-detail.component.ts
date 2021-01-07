import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Director } from '../directors/director.model';

@Component({
  selector: 'app-director-detail',
  templateUrl: './director-detail.component.html',
  styleUrls: ['./director-detail.component.css']
})
export class DirectorDetailComponent implements OnInit {

  id: number;
  director: Director = new Director();
  updated_director: Director = new Director();
  showForm: Boolean;

  constructor(private http: HttpClient, private activatedRoute: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(parameter => {
      this.id = +parameter.id;
    })
    this.getReal().subscribe(
      data => { this.director = data }
    )
    this.showForm = false;
  }

  /**
   * Recupere le realisateur de cette page à partir de l'id
   */
  getReal(): Observable<Director> {
    return this.http.get<Director>('http://localhost:8080/director/{id}?id=' + this.id.toString());
  }

  /**
   * Supprime le realisateur courant de la base de donnees
   */
  deleteDirector(): void {
    if (confirm("Assurez-vous d'avoir au prealable supprime les films de ce realisteur.\n \nÊtes-vous sur de vouloir supprimer ce director : " + this.director.firstName + " " + this.director.lastName)) {
      console.log(this.director);
      let params = new HttpParams();
      params = params.append('id', this.id.toString());
      this.http.delete<Director>('http://localhost:8080/director', { params: params }).subscribe()
      this.router.navigateByUrl('/director')
    }
  }

  /**
   * Montre le formulaire de mise à jour du realisateur
   */
  clickShowForm(): void {
    this.showForm = true;
  }

  /**
   * Cache le formulaire de mise à jour du realisateur
   */
  clickHideForm(): void {
    this.showForm = false;
  }

  /**
   * Bouton retour à la liste des realisateurs
   */
  btnClick(): void {
    this.router.navigateByUrl('/director')
  }

  /**
   * Mise à jour du realisateur avec l'API 
   * @param form le formulaire de mise à jour rempli
   */
  processUpdateForm(form): Observable<Director> {
    if (form.invalid) {
      alert("The form is incorrect")
      return;
    }
    this.updated_director.id = this.director.id;
    this.updated_director.firstName = form.value.firstName;
    this.updated_director.lastName = form.value.lastName;
    this.updated_director.date = form.value.date;
    this.http.put<Director>('http://localhost:8080/director', this.updated_director).subscribe();
    location.reload();
  }
}
