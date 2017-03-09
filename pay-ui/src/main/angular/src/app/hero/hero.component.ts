import { Component, OnInit } from '@angular/core';
import { Title }     from '@angular/platform-browser';

import { Hero } from './hero';

@Component({
  selector: 'hero-root',
  templateUrl: './hero.component.html',
  styleUrls: ['./hero.component.styl'],
})

export class HeroComponent implements OnInit {

  public constructor(private titleService: Title ) { }

  ngOnInit(): void {
    this.setTitle("Heroes");
  }

  public setTitle( newTitle: string) {
    this.titleService.setTitle( newTitle );
  }

}