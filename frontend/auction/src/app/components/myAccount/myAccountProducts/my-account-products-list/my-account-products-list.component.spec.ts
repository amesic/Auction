import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAccountProductsListComponent } from './my-account-products-list.component';

describe('MyAccountProductsListComponent', () => {
  let component: MyAccountProductsListComponent;
  let fixture: ComponentFixture<MyAccountProductsListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyAccountProductsListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyAccountProductsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
