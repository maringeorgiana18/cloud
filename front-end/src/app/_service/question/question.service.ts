import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {AddQuestionModel} from '../../_models/addQuestionModel';
import {UpdateQuestionModel} from '../../_models/updateQuestionModel';
import {GetQuestionModel} from '../../_models/getQuestionModel';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  private readonly questionURL: string;

  constructor(private http: HttpClient) {
    this.questionURL = environment.apiUrl + '/questions';
  }

  add(model: AddQuestionModel) {
    const URL = this.questionURL;
    return this.http.post(URL, model);
  }

  getAll() {
    const URL = this.questionURL;
    return this.http.get<GetQuestionModel[]>(URL);
  }

  update(model: UpdateQuestionModel) {
    const URL = this.questionURL;
    return this.http.put(URL, model);
  }

  updateAuthor(questionId: number, authorId: number) {
    const URL = this.questionURL + '/author' + '/' + questionId + '/' + authorId;
    return this.http.put(URL, {});
  }

  delete(id: number) {
    const URL = this.questionURL + '/' + id;
    return this.http.delete(URL);
  }

}
