import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {StartQuestionModel} from '../../_models/startQuestionModel';
import {FinishQuestionModel} from '../../_models/finishQuestionModel';
import {GetQuestionModel} from '../../_models/getQuestionModel';
import {GetAttemptModel} from '../../_models/getAttemptModel';

@Injectable({
  providedIn: 'root'
})
export class AttemptService {

  private readonly attemptURL: string;

  constructor(private http: HttpClient) {
    this.attemptURL = environment.apiUrl + '/attempts';
  }

  start(type: string, option: boolean) {
    const URL = this.attemptURL + '/' + type.toUpperCase() + '/' + option;
    return this.http.post<StartQuestionModel>(URL, {});
  }

  get() {
    const URL = this.attemptURL;
    return this.http.get<StartQuestionModel>(URL);
  }

  getAll() {
    const URL = this.attemptURL + '/all';
    return this.http.get<GetAttemptModel[]>(URL);
  }

  getAllUpdated() {
    const URL = this.attemptURL + '/updated';
    return this.http.get<StartQuestionModel[]>(URL);
  }

  getUpdatedNumber() {
    const URL = this.attemptURL + '/updated-number';
    return this.http.get<number>(URL);
  }

  getNumberOfQuestion(type: string, option: boolean) {
    const URL = this.attemptURL + '/' + type.toUpperCase() + '/' + option;
    return this.http.get<number>(URL);
  }

  finishQuestion(model: FinishQuestionModel) {
    const URL = this.attemptURL;
    return this.http.put(URL, model);
  }

  skip() {
    const URL = this.attemptURL;
    return this.http.delete(URL);
  }

}
