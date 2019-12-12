import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAccountBidsComponent } from './my-account-bids.component';

describe('MyAccountBidsComponent', () => {
  let component: MyAccountBidsComponent;
  let fixture: ComponentFixture<MyAccountBidsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyAccountBidsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyAccountBidsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
