import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionsUpdatedComponent } from './questions-updated.component';

describe('QuestionsUpdatedComponent', () => {
  let component: QuestionsUpdatedComponent;
  let fixture: ComponentFixture<QuestionsUpdatedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ QuestionsUpdatedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(QuestionsUpdatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
