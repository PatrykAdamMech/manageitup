import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParticipantsListDialogComponent } from './participants-list-dialog.component';

describe('ParticipantsListDialogComponent', () => {
  let component: ParticipantsListDialogComponent;
  let fixture: ComponentFixture<ParticipantsListDialogComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ParticipantsListDialogComponent]
    });
    fixture = TestBed.createComponent(ParticipantsListDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
