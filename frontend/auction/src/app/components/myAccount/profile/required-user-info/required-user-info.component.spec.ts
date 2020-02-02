import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RequiredUserInfoComponent } from './required-user-info.component';

describe('RequiredUserInfoComponent', () => {
  let component: RequiredUserInfoComponent;
  let fixture: ComponentFixture<RequiredUserInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RequiredUserInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RequiredUserInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
