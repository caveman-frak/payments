import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { HeroRoutingModule } from './hero-routing.module';
import { HeroComponent } from './hero.component';
import { HeroDetailComponent } from '../hero-detail/hero-detail.component';
import { HeroListComponent } from '../hero-list/hero-list.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    HeroRoutingModule
  ],
  declarations: [
    HeroComponent,
    HeroDetailComponent,
    HeroListComponent
  ],
  providers: [],
  bootstrap: [
    HeroComponent]
})

export class HeroModule { }
