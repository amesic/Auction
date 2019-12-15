import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAccountWatchlistComponent } from './my-account-watchlist.component';

describe('MyAccountWatchlistComponent', () => {
  let component: MyAccountWatchlistComponent;
  let fixture: ComponentFixture<MyAccountWatchlistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyAccountWatchlistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyAccountWatchlistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
