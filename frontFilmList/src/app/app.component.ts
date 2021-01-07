import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { faSortAmountUpAlt} from '@fortawesome/free-solid-svg-icons';
import { faSortAmountDownAlt} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontFilmList';
  faSortAmountDownAlt = faSortAmountDownAlt;
  faSortAmountUpAlt = faSortAmountUpAlt; 
  constructor(translate: TranslateService) {
    translate.setDefaultLang('fr');
    translate.use('fr');
  }
}
