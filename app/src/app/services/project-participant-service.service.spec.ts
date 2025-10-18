import { TestBed } from '@angular/core/testing';

import { ProjectParticipantServiceService } from './project-participant-service.service';

describe('ProjectParticipantServiceService', () => {
  let service: ProjectParticipantServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectParticipantServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
