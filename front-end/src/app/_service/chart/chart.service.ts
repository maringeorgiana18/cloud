import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {GetDataPointsModel} from '../../_models/getDataPointsModel';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ChartService {

  private readonly chartURL: string;

  constructor(private http: HttpClient) {
    this.chartURL = environment.apiUrl + '/charts';
  }

  users() {
    const URL = this.chartURL + '/users';
    return this.http.get(URL);
  }

  questions() {
    const URL = this.chartURL + '/questions';
    return this.http.get(URL);
  }

  logins(): Observable<GetDataPointsModel[]>{
    const URL = this.chartURL + '/logins';
    return this.http.get<GetDataPointsModel[]>(URL);
  }

  newUsers(): Observable<GetDataPointsModel[]>{
    const URL = this.chartURL + '/new-users';
    return this.http.get<GetDataPointsModel[]>(URL);
  }

  responses() {
    const URL = this.chartURL + '/responses';
    return this.http.get(URL);
  }

  questionPerDay(): Observable<GetDataPointsModel[]> {
    const URL = this.chartURL + '/questions-per-day';
    return this.http.get<GetDataPointsModel[]>(URL);
  }
}
