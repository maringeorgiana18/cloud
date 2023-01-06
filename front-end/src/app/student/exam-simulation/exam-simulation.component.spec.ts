import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExamSimulationComponent } from './exam-simulation.component';

describe('ExamSimulationComponent', () => {
  let component: ExamSimulationComponent;
  let fixture: ComponentFixture<ExamSimulationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ExamSimulationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ExamSimulationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
