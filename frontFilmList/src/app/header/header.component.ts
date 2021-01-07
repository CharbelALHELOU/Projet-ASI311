import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  translate: TranslateService;
  language: string = 'fr';
  constructor(translate: TranslateService) {
    this.translate = translate;
  }

  ngOnInit(): void {
  }

  useLanguage(language: string) {
    this.translate.use(language)
    this.language = language
  }
}
