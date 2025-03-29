import { TestBed } from '@angular/core/testing';

import { BonosYdescuentosService } from './bonos-descuentos.service';

describe('BonosYdescuentosService', () => {
  let service: BonosYdescuentosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BonosYdescuentosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
