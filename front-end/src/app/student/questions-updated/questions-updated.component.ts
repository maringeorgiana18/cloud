import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {fadeAnimation} from '../../_animation/fadeInOut';
import {detailExapand} from '../../_animation/detailExpand';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {Router} from '@angular/router';
import {AttemptService} from '../../_service/attempt/attempt.service';
import {AlertService} from '../../_helpers/alert.service';
import {StartQuestionModel} from '../../_models/startQuestionModel';

@Component({
  selector: 'app-questions-updated',
  templateUrl: './questions-updated.component.html',
  styleUrls: ['./questions-updated.component.scss'],
  animations: [fadeAnimation, detailExapand]
})
export class QuestionsUpdatedComponent implements OnInit, AfterViewInit {

  dataSource: MatTableDataSource<StartQuestionModel>;
  columnsToDisplay = ['description', 'type', 'action'];
  expandedElement: StartQuestionModel | null;

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
    this.attemptService.getAllUpdated().subscribe(
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

  recheckQuestion(model: StartQuestionModel) {
    this.router.navigate(['/resolve-question'], {state: {question: model}});
  }

}
