import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MontosComponent } from './montos.component';

describe('MontosComponent', () => {
  let component: MontosComponent;
  let fixture: ComponentFixture<MontosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MontosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MontosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
