import {Component, OnInit} from '@angular/core';
import {fadeAnimation} from '../../_animation/fadeInOut';
import {first} from 'rxjs/operators';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AlertService} from '../../_helpers/alert.service';
import {StartExamModel} from '../../_models/startExamModel';
import {ExamService} from '../../_service/exam/exam.service';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';
import {MatVerticalStepper} from '@angular/material/stepper';
import {FinishExamModel} from '../../_models/finishExamModel';
import {FinishQuestionModel} from '../../_models/finishQuestionModel';

@Component({
  selector: 'app-exam-simulation',
  templateUrl: './exam-simulation.component.html',
  styleUrls: ['./exam-simulation.component.scss'],
  animations: [fadeAnimation],
  providers: [{
    provide: STEPPER_GLOBAL_OPTIONS, useValue: {displayDefaultIndicatorType: false}
  }]
})
export class ExamSimulationComponent implements OnInit {

  allProfiles = ['PHYSICS', 'INFORMATICS', 'MATHS'];
  checkList = [];
  formPrincipal: FormGroup;
  sendMenu: boolean;
  sendVerify: boolean;

  exam: StartExamModel;
  questionsFormGroup = [];
  completedList = [];

  constructor(
    private formBuilder: FormBuilder,
    private examService: ExamService,
    private alertService: AlertService
  ) {

    this.exam = undefined;

    for (let i = 0; i < 15 ; i++) {
      this.questionsFormGroup.push(this.formBuilder.group({
      }));
      this.completedList.push(false);
    }

    this.formPrincipal = this.formBuilder.group({
      type: ['PHYSICS']
    }, {
    });

    this.examService.getStarted()
      .pipe(first())
      .subscribe(
        data => {
          this.exam = data;
          if (data !== null) {
            this.initializeCheckList();
          }
        },
        error => {
          this.alertService.error('Get started exam error');
        }
      );

    this.sendMenu = false;
    this.sendVerify = false;
  }

  ngOnInit(): void {
  }

  private get formPrincipalFields() { return this.formPrincipal.controls; }

  startExam() {
    this.sendMenu = true;
    setTimeout(() => {
      this.examService.start(this.formPrincipalFields.type.value)
        .pipe(first())
        .subscribe(
          data => {
            this.exam = data;
            this.checkList = [];
            if (data !== null) {
              this.initializeCheckList();
            }
          },
          error => {
            this.alertService.error('Start exam error');
          }
        );
      this.sendMenu = false;
    }, 1000);
  }

  private initializeCheckList() {
    this.exam.attempts.forEach((attempt, index) => {
      const auxlist = [];
      attempt.answers.forEach(answer => auxlist.push(false));
      this.checkList.push(auxlist);
    });
  }

  checkCompleted(index: number) {
    let auxCheck = false;
    this.checkList[index].forEach(check => {if (check === true) {
      auxCheck = true;
    }});
    return auxCheck;
  }

  reset(stepper: MatVerticalStepper) {
    stepper.reset();
    this.checkList = [];
    this.initializeCheckList();
  }

  finish() {
    const finishExamModel = new FinishExamModel();
    finishExamModel.id = this.exam.id;
    finishExamModel.attempts = [];
    this.exam.attempts.forEach((attempt, index) => {
      const finishAttemptModel = new FinishQuestionModel();
      finishAttemptModel.attemptId = attempt.id;
      const answers = [];
      this.checkList[index].forEach((answer, index2) => {
          if (answer === true) {
            answers.push(attempt.answers[index2].id);
          }
        }
      );
      finishAttemptModel.answerList = answers;
      finishExamModel.attempts.push(finishAttemptModel);
    });
    this.sendVerify = true;
    setTimeout(() => {
      this.examService.finish(finishExamModel)
        .pipe(first())
        .subscribe(
          data => {
            this.alertService.success(data + ' / 15');
            this.exam = null;
          },
          error => {
            this.alertService.error('Finish exam error');
          }
        );
      this.sendVerify = false;
    }, 1000);
  }
}
