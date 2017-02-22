import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { enableProdMode } from '@angular/core';
import { environment } from './environments/environment';

import { HeroModule } from './app/hero/hero.module';

if (environment.production) {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(HeroModule);
