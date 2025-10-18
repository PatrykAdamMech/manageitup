import { TestBed } from '@angular/core/testing';

import { ProjectStatusServiceService } from './project-status-service.service';

describe('ProjectStatusServiceService', () => {
  let service: ProjectStatusServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectStatusServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
