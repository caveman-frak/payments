import { Component, Input } from '@angular/core';

import { Hero } from '../hero/hero';

@Component({
  selector: 'hero-detail',
  templateUrl: './hero-detail.component.html',
  styleUrls: ['./hero-detail.component.styl', '../hero/hero.component.styl']
})
export class HeroDetailComponent {

  constructor() { }

  @Input()
  hero: Hero;

}
