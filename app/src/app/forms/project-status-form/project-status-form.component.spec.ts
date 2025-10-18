import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectStatusFormComponent } from './project-status-form.component';

describe('ProjectStatusFormComponent', () => {
  let component: ProjectStatusFormComponent;
  let fixture: ComponentFixture<ProjectStatusFormComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProjectStatusFormComponent]
    });
    fixture = TestBed.createComponent(ProjectStatusFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
