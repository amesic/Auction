import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAccountWatchlistListComponent } from './my-account-watchlist-list.component';

describe('MyAccountWatchlistListComponent', () => {
  let component: MyAccountWatchlistListComponent;
  let fixture: ComponentFixture<MyAccountWatchlistListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyAccountWatchlistListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyAccountWatchlistListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
