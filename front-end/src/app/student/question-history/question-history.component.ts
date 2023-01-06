import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {fadeAnimation} from '../../_animation/fadeInOut';
import {detailExapand} from '../../_animation/detailExpand';
import {MatTableDataSource} from '@angular/material/table';
import {GetQuestionModel} from '../../_models/getQuestionModel';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {Router} from '@angular/router';
import {AlertService} from '../../_helpers/alert.service';
import {GetAttemptModel} from '../../_models/getAttemptModel';
import {AttemptService} from '../../_service/attempt/attempt.service';

@Component({
  selector: 'app-question-history',
  templateUrl: './question-history.component.html',
  styleUrls: ['./question-history.component.scss'],
  animations: [fadeAnimation, detailExapand]
})
export class QuestionHistoryComponent implements OnInit, AfterViewInit {

  dataSource: MatTableDataSource<GetAttemptModel>;
  columnsToDisplay = ['description', 'type', 'startDate', 'endDate', 'historyStatus', 'status'];
  expandedElement: GetAttemptModel | null;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private router: Router,
    private attemptService: AttemptService,
    private alertService: AlertService
  ) {
    this.dataSource = new MatTableDataSource([]);
  }

  ngOnInit(): void {
    this.attemptService.getAll().subscribe(
      response => {
        this.dataSource.data = response.reverse();
      },
      error => {
        this.alertService.error('Get questions failed');
      });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  convertTimeStamp(timeStamp) {
    if (timeStamp !== null) {
      const date = new Date(timeStamp).getDate();
      const month = new Date(timeStamp).getMonth() + 1;
      const year = new Date(timeStamp).getFullYear();
      const hour = new Date(timeStamp).getHours().toString();
      let min = new Date(timeStamp).getMinutes().toString();
      if (min.length === 1) {
        min = '0' + min;
      }
      let sec = new Date(timeStamp).getSeconds().toString();
      if (sec.length === 1) {
        sec = '0' + sec;
      }
      return year + '/' + month + '/' + date + ' ' + hour + ':' + min + ':' + sec;
    }
    return '-';

  }

  convertDescription(description: string) {
    let substring = description.substring(0, 40);
    substring = description.length > 40 ? substring + '...' : substring;
    return substring;
  }

  updateQuestion(model: GetQuestionModel) {
    this.router.navigate(['/update-question'], {state: {question: model}});
  }

}
