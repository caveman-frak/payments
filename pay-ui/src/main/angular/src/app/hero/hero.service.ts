import { Injectable } from '@angular/core';

import { Hero, HEROES } from '../hero/hero';

@Injectable()
export class HeroService {

  constructor() { }

  getHeroes(): Promise<Hero[]> {
    return Promise.resolve(HEROES);
  }
}
