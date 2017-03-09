import { BrowserModule, Title } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

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
    InMemoryWebApiModule.forRoot(InMemoryDataService),
    NgbModule.forRoot(),
    HeroRoutingModule
  ],
  declarations: [
    HeroComponent,
    HeroDetailComponent,
    HeroListComponent,
    HeroDashboardComponent,
    HeroSearchComponent
  ],
  providers: [
    Title,
    HeroService
  ],
  bootstrap: [
    HeroComponent
  ]
})

export class HeroModule { }
