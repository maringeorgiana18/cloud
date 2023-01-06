import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {AddUserModel} from '../../_models/addUserModel';
import {UpdateUserModel} from '../../_models/updateUserModel';
import {Observable} from 'rxjs';
import {GetUserModel} from '../../_models/getUserModel';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private readonly userURL: string;

  constructor(private http: HttpClient) {
    this.userURL = environment.apiUrl + '/users';
  }

  add(type: string, model: AddUserModel) {
    const URL = this.userURL + '/' + type.toUpperCase();
    return this.http.post(URL, model);
  }

  getAll(type: string): Observable<GetUserModel[]>{
    const URL = this.userURL + '/' + type.toUpperCase();
    return this.http.get<GetUserModel[]>(URL);
  }

  getInfo(): Observable<UpdateUserModel>{
    const URL = this.userURL + '/info';
    return this.http.get<UpdateUserModel>(URL);
  }

  checkEmail(email: string, id: number) {
    let URL = this.userURL + '/check-email';
    if (id === null) {
      URL = URL + '/' + email;
    } else {
      URL = URL + '/' + email + '/' + id;
    }
    return this.http.get<any>(URL);
  }

  reactivateTeacher(email: string) {
    const URL = this.userURL + '/reactivate-teacher/' + email;
    return this.http.put(URL, {});
  }

  update(model: UpdateUserModel) {
    const URL = this.userURL;
    return this.http.put(URL, model);
  }

  delete(email: string) {
    const URL = this.userURL + '/' + email;
    return this.http.delete(URL, {});
  }

}
