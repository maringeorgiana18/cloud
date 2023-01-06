import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {GetExamModel} from '../../_models/getExamModel';
import {StartExamModel} from '../../_models/startExamModel';
import {FinishExamModel} from '../../_models/finishExamModel';

@Injectable({
  providedIn: 'root'
})
export class ExamService {

  private readonly examURL: string;

  constructor(private http: HttpClient) {
    this.examURL = environment.apiUrl + '/exams';
  }

  start(type: string) {
    const URL = this.examURL + '/' + type.toUpperCase();
    return this.http.post<StartExamModel>(URL, {});
  }

  getStarted() {
    const URL = this.examURL;
    return this.http.get<StartExamModel>(URL);
  }

  getAll() {
    const URL = this.examURL + '/all';
    return this.http.get<GetExamModel[]>(URL);
  }

  finish(model: FinishExamModel) {
    const URL = this.examURL;
    return this.http.put(URL, model);
  }
}
