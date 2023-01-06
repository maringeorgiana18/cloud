import { Component, OnInit } from '@angular/core';
import {UpdateAnswerModel} from '../_models/updateAnswerModel';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UpdateQuestionModel} from '../_models/updateQuestionModel';
import {CheckAnswerStatusTrue} from '../_helpers/checkAnswerStatusTrue';
import {CheckAnswerStatusFalse} from '../_helpers/checkAnswerStatusFalse';
import {AlertService} from '../_helpers/alert.service';
import {first} from 'rxjs/operators';
import {fadeAnimation} from '../_animation/fadeInOut';
import {Router} from '@angular/router';
import {QuestionService} from '../_service/question/question.service';

@Component({
  selector: 'app-update-question',
  templateUrl: './update-question.component.html',
  styleUrls: ['./update-question.component.scss'],
  animations: [fadeAnimation]
})
export class UpdateQuestionComponent implements OnInit {

  question: UpdateQuestionModel;
  form: FormGroup;
  allProfiles = ['PHYSICS', 'INFORMATICS', 'MATHS'];
  answers: FormArray;
  send: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private questionService: QuestionService,
    private checkAnswerStatusTrue: CheckAnswerStatusTrue,
    private checkAnswerStatusFalse: CheckAnswerStatusFalse,
    private alertService: AlertService,
    private router: Router
  ) {
    if (this.router.getCurrentNavigation().extras.state === undefined) {
      this.router.navigate(['']);
    }
    this.question = this.router.getCurrentNavigation().extras.state.question;
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      content: [this.question.content, Validators.required],
      description: [this.question.description, Validators.required],
      type: [this.question.type, Validators.required],
      answers: new FormArray(this.initializeAnswer(),
        [Validators.required, this.checkAnswerStatusTrue.check(), this.checkAnswerStatusFalse.check()])
    }, {
    });
    this.send = false;
    this.answers = this.form.get('answers') as FormArray;
  }

  private get formFields() { return this.form.controls; }

  addQuestion() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      if (this.form.get('answers').hasError('statusTrue') && this.form.get('answers').hasError('statusFalse')) {
        this.alertService.error('At least one correct and one wrong answer must be entered');
      } else if (this.form.get('answers').hasError('statusTrue')) {
        this.alertService.error('At least one correct answer must be entered');
      } else if (this.form.get('answers').hasError('statusFalse')) {
        this.alertService.error('At least one incorrect answer must be entered');
      }
      return;
    }
    this.buildQuestion();
    this.send = true;
    this.alertService.clear();
    setTimeout(() => {
      this.questionService.update(this.question)
        .pipe(first())
        .subscribe(
          data => {
            this.alertService.success('Question updated');
          },
          error => {
            this.alertService.error('Update question error');
          }
        );
      this.send = false;
    }, 1000);
  }

  addAnswer() {
    const control =  new FormGroup({
      content: new FormControl('', [Validators.required]),
      status: new FormControl(null, Validators.required)
    });
    this.answers.push(control);
  }

  deleteAnswer(index) {
    this.answers.removeAt(index);
  }

  private initializeAnswer() {
    const controlList = [];
    this.question.answers.forEach(answer => {
      const control =  new FormGroup({
        id: new FormControl(answer.id, [Validators.required]),
        content: new FormControl(answer.content, [Validators.required]),
        status: new FormControl(answer.status ? 'true' : 'false', [Validators.required])
      });
      controlList.push(control);
    });
    return controlList;
  }

  private buildQuestion() {
    this.question.content = this.formFields.content.value;
    this.question.description = this.formFields.description.value;
    this.question.type = this.formFields.type.value;
    this.question.answers = [];
    this.formFields.answers.value.forEach( entry => {
      const answer = new UpdateAnswerModel();
      answer.content = entry.content;
      answer.status = entry.status;
      this.question.answers.push(answer);
    });
  }

}
