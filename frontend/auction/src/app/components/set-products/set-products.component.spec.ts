import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SetProductsComponent } from './set-products.component';

describe('SetProductsComponent', () => {
  let component: SetProductsComponent;
  let fixture: ComponentFixture<SetProductsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetProductsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SetProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
