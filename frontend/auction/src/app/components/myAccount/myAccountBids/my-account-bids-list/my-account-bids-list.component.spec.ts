import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAccountBidsListComponent } from './my-account-bids-list.component';

describe('MyAccountBidsListComponent', () => {
  let component: MyAccountBidsListComponent;
  let fixture: ComponentFixture<MyAccountBidsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyAccountBidsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyAccountBidsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
