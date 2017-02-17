import { PayUiPage } from './app.po';

describe('pay-ui App', function() {
  let page: PayUiPage;

  beforeEach(() => {
    page = new PayUiPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
