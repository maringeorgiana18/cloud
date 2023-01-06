import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {CurrentUser} from '../../_models/currentUser';
import {environment} from '../../../environments/environment';
import {map} from 'rxjs/operators';
import {LoginModel} from '../../_models/loginModel';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private currentUserSubject: BehaviorSubject<CurrentUser>;
  public currentUser: Observable<CurrentUser>;
  private user: CurrentUser;

  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<CurrentUser>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): CurrentUser {
    return this.currentUserSubject.value;
  }

  login(user: LoginModel) {
    return this.http.post<any>(`${environment.apiUrl}/authenticate`, user)
      .pipe(map(res => {
        this.user = new CurrentUser();
        this.user.token = res.token;
        this.user.role = res.role;
        localStorage.setItem('currentUser', JSON.stringify(this.user));
        this.currentUserSubject.next(this.user);
        return res;
      }));
  }

  logout() {
    this.http.post<any>(`${environment.apiUrl}/logout/` + this.currentUserValue.token, {}).subscribe();
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }
}
