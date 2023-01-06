import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResolveQuestionComponent } from './resolve-question.component';

describe('ResolveQuestionComponent', () => {
  let component: ResolveQuestionComponent;
  let fixture: ComponentFixture<ResolveQuestionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResolveQuestionComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResolveQuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
