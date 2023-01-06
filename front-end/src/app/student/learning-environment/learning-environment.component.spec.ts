import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LearningEnvironmentComponent } from './learning-environment.component';

describe('LearningEnvironmentComponent', () => {
  let component: LearningEnvironmentComponent;
  let fixture: ComponentFixture<LearningEnvironmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LearningEnvironmentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LearningEnvironmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
