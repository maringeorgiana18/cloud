import { Component, OnInit } from '@angular/core';
import {AlertService} from '../../_helpers/alert.service';
import {Router} from '@angular/router';
import {StartQuestionModel} from '../../_models/startQuestionModel';
import {fadeAnimation} from '../../_animation/fadeInOut';
import {AttemptService} from '../../_service/attempt/attempt.service';
import {FinishQuestionModel} from '../../_models/finishQuestionModel';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-resolve-question',
  templateUrl: './resolve-question.component.html',
  styleUrls: ['./resolve-question.component.scss'],
  animations: [fadeAnimation]
})
export class ResolveQuestionComponent implements OnInit {

  question: StartQuestionModel;
  checkList = [];
  sendVerify: boolean;

  constructor(
    private alertService: AlertService,
    private attemptService: AttemptService,
    private router: Router
  ) {
    if (this.router.getCurrentNavigation().extras.state === undefined) {
      this.router.navigate(['questions-updated']);
    }
    this.question = this.router.getCurrentNavigation().extras.state.question;

    this.sendVerify = false;
  }

  ngOnInit(): void {
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
