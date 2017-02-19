import { Component } from '@angular/core';

import { Hero, HEROES } from './hero';

@Component({
  selector: 'hero-root',
  templateUrl: './hero.component.html',
  styleUrls: ['./hero.component.styl'],
})

export class HeroComponent {
  title = 'Tour of Heroes';
}