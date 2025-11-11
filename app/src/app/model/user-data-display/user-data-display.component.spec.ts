import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserDataDisplayComponent } from './user-data-display.component';

describe('UserDataDisplayComponent', () => {
  let component: UserDataDisplayComponent;
  let fixture: ComponentFixture<UserDataDisplayComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UserDataDisplayComponent]
    });
    fixture = TestBed.createComponent(UserDataDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
