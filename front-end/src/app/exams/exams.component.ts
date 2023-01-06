import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {fadeAnimation} from '../_animation/fadeInOut';
import {MatTableDataSource} from '@angular/material/table';
import {GetUserModel} from '../_models/getUserModel';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {Router} from '@angular/router';
import {UserService} from '../_service/user/user.service';
import {AlertService} from '../_helpers/alert.service';
import {ExamService} from '../_service/exam/exam.service';
import {GetExamModel} from '../_models/getExamModel';

@Component({
  selector: 'app-exams',
  templateUrl: './exams.component.html',
  styleUrls: ['./exams.component.scss'],
  animations: [fadeAnimation]
})
export class ExamsComponent implements OnInit , AfterViewInit {

  displayedColumns: string[] = ['startDate', 'endDate', 'type', 'result'];
  dataSource: MatTableDataSource<GetExamModel>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private router: Router,
    private examService: ExamService,
    private alertService: AlertService
  ) {
    this.dataSource = new MatTableDataSource([]);
  }

  ngOnInit(): void {
    this.examService.getAll().subscribe(
      response => {
        this.dataSource.data = response.reverse();
      },
      error => {
        this.alertService.error('Get exams history failed');
      });
  }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
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

}
