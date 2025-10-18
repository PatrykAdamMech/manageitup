import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatusDisplayComponent } from './status-display.component';

describe('StatusDisplayComponent', () => {
  let component: StatusDisplayComponent;
  let fixture: ComponentFixture<StatusDisplayComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [StatusDisplayComponent]
    });
    fixture = TestBed.createComponent(StatusDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
