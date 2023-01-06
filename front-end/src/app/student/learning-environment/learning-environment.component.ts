import {Component, OnDestroy, OnInit} from '@angular/core';
import {AlertService} from '../../_helpers/alert.service';
import {fadeAnimation} from '../../_animation/fadeInOut';
import {StartQuestionModel} from '../../_models/startQuestionModel';
import {first} from 'rxjs/operators';
import {FormBuilder, FormGroup} from '@angular/forms';
import {FinishQuestionModel} from '../../_models/finishQuestionModel';
import {AttemptService} from '../../_service/attempt/attempt.service';

@Component({
  selector: 'app-learning-environment',
  templateUrl: './learning-environment.component.html',
  styleUrls: ['./learning-environment.component.scss'],
  animations: [fadeAnimation]
})
export class LearningEnvironmentComponent implements OnInit, OnDestroy {

  allProfiles = ['PHYSICS', 'INFORMATICS', 'MATHS'];
  checkList = [];
  question: StartQuestionModel;
  numberOfQuestion: number;
  intervalId: number;
  formPrincipal: FormGroup;
  sendMenu: boolean;
  sendVerify: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private attemptService: AttemptService,
    private alertService: AlertService
  ) {
    this.numberOfQuestion = 0;

    this.formPrincipal = this.formBuilder.group({
      type: ['PHYSICS'],
      option: ['true'],
    }, {
    });
    this.attemptService.get()
      .pipe(first())
      .subscribe(
        data => {
          this.question = data;
          if (data !== null) {
            data.answers.forEach(answer => {this.checkList.push(false); });
          }
        },
        error => {
          this.alertService.error('Get started question error');
        }
      );

    this.intervalId = setInterval(() => {
      this.attemptService.getNumberOfQuestion(this.formPrincipalFields.type.value, this.formPrincipalFields.option.value)
        .pipe(first())
        .subscribe(
          data => {
              this.numberOfQuestion = data;
          },
          error => {
            this.alertService.error('Get number of question fail');
          }
        );
    }, 500);

    this.question = null;
    this.sendMenu = false;
    this.sendVerify = false;
  }

  ngOnInit(): void {
  }

  ngOnDestroy() {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  private get formPrincipalFields() { return this.formPrincipal.controls; }

  skipQuestion() {
    this.sendMenu = true;
    this.question = null;
    this.checkList = [];
    setTimeout(() => {
      this.attemptService.skip()
        .pipe(first())
        .subscribe(
          data => {
            this.question = null;
          },
          error => {
            this.alertService.error('Skip question error');
          }
        );
      this.sendMenu = false;
    }, 1000);
  }


  startQuestion() {
    this.sendMenu = true;
    setTimeout(() => {
      this.attemptService.start(this.formPrincipalFields.type.value, this.formPrincipalFields.option.value)
        .pipe(first())
        .subscribe(
          data => {
            this.question = data;
            this.checkList = [];
            if (data !== null) {
              data.answers.forEach(answer => {this.checkList.push(false); });
            }
          },
          error => {
            this.alertService.error('Start question error');
          }
        );
      this.sendMenu = false;
    }, 1000);
  }

  verify() {
    const finishQuestion = new FinishQuestionModel();
    const answers = [];
    this.checkList.forEach((answer, index) => {
      if (answer === true) {
        answers.push(this.question.answers[index].id);
      }
    });
    finishQuestion.attemptId = this.question.id;
    finishQuestion.answerList = answers;
    this.sendVerify = true;
    setTimeout(() => {
      this.attemptService.finishQuestion(finishQuestion)
        .pipe(first())
        .subscribe(
          data => {
            data === true ?
            this.alertService.success('Correct answer') :
            this.alertService.error('Wrong answer');
            this.question = null;
          },
          error => {
            this.alertService.error('Finish question error');
          }
        );
      this.sendVerify = false;
    }, 1000);
  }

}
