import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WorlflowDialogComponent } from './worlflow-dialog.component';

describe('WorlflowDialogComponent', () => {
  let component: WorlflowDialogComponent;
  let fixture: ComponentFixture<WorlflowDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [WorlflowDialogComponent]
    });
    fixture = TestBed.createComponent(WorlflowDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
