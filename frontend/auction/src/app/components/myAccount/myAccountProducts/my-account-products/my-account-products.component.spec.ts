import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyAccountProductsComponent } from './my-account-products.component';

describe('MyAccountProductsComponent', () => {
  let component: MyAccountProductsComponent;
  let fixture: ComponentFixture<MyAccountProductsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyAccountProductsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyAccountProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
