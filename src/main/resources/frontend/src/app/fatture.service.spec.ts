import { TestBed } from '@angular/core/testing';

import { FattureService } from './services/fatture.service';

describe('FattureService', () => {
  let service: FattureService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FattureService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
