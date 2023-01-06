import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AlertService} from '../_helpers/alert.service';
import {AddQuestionModel} from '../_models/addQuestionModel';
import {AddAnswerModel} from '../_models/addAnswerModel';
import {CheckAnswerStatusTrue} from '../_helpers/checkAnswerStatusTrue';
import {CheckAnswerStatusFalse} from '../_helpers/checkAnswerStatusFalse';
import {fadeAnimation} from '../_animation/fadeInOut';
import {first} from 'rxjs/operators';
import {QuestionService} from '../_service/question/question.service';

@Component({
  selector: 'app-add-question',
  templateUrl: './add-question.component.html',
  styleUrls: ['./add-question.component.scss'],
  animations: [fadeAnimation]
})
export class AddQuestionComponent implements OnInit {

  question: AddQuestionModel;
  form: FormGroup;
  allProfiles = ['PHYSICS', 'INFORMATICS', 'MATHS'];
  answers: FormArray;
  send: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private questionService: QuestionService,
    private checkAnswerStatusTrue: CheckAnswerStatusTrue,
    private checkAnswerStatusFalse: CheckAnswerStatusFalse,
    private alertService: AlertService
  ) {
    this.question = new AddQuestionModel();
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      content: ['', Validators.required],
      description: ['', Validators.required],
      type: [null, Validators.required],
      answers: new FormArray([], [Validators.required, this.checkAnswerStatusTrue.check(), this.checkAnswerStatusFalse.check()])
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
      this.questionService.add(this.question)
        .pipe(first())
        .subscribe(
          data => {
            this.alertService.success('Question added');
          },
          error => {
            this.alertService.error('Add question error');
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

  private buildQuestion() {
    this.question.content = this.formFields.content.value;
    this.question.description = this.formFields.description.value;
    this.question.type = this.formFields.type.value;
    this.question.answers = [];
    this.formFields.answers.value.forEach( entry => {
      const answer = new AddAnswerModel();
      answer.content = entry.content;
      answer.status = entry.status;
      this.question.answers.push(answer);
    });
  }

}
