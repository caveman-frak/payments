import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { MaterialModule,  } from '@angular/material';
import  'hammerjs';

//Imports for loading & configuring the in-memory web api
import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService }  from '../in-memory-data.service';

import { HeroRoutingModule } from './hero-routing.module';
import { HeroComponent } from './hero.component';
import { HeroService } from './hero.service';
import { HeroDetailComponent } from './detail/hero-detail.component';
import { HeroListComponent } from './list/hero-list.component';
import { HeroDashboardComponent } from './dashboard/hero-dashboard.component';
import { HeroSearchComponent } from './search/hero-search.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    MaterialModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService),
    HeroRoutingModule
  ],
  declarations: [
    HeroComponent,
    HeroDetailComponent,
    HeroListComponent,
    HeroDashboardComponent,
    HeroSearchComponent
  ],
  providers: [HeroService],
  bootstrap: [
    HeroComponent]
})

export class HeroModule { }
