import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {fadeAnimation} from '../_animation/fadeInOut';
import {detailExapand} from '../_animation/detailExpand';
import {AlertService} from '../_helpers/alert.service';
import {Router} from '@angular/router';
import {GetQuestionModel} from '../_models/getQuestionModel';
import {QuestionService} from '../_service/question/question.service';
import {AuthenticationService} from '../_service/authentication/authentication.service';
import {GetActiveTeacher} from '../_models/getActiveTeacher';
import {GetUserModel} from '../_models/getUserModel';
import {UserService} from '../_service/user/user.service';

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.scss'],
  animations: [fadeAnimation, detailExapand]
})
export class QuestionsComponent implements OnInit, AfterViewInit {
  role: string;

  dataSource: MatTableDataSource<GetQuestionModel>;
  columnsToDisplay = [];
  expandedElement: GetQuestionModel | null;
  teachers: GetUserModel[];
  teacherSelected = null;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private router: Router,
    private questionService: QuestionService,
    private alertService: AlertService,
    private userService: UserService,
    private authenticationService: AuthenticationService
  ) {
    this.dataSource = new MatTableDataSource([]);
    this.role = this.authenticationService.currentUserValue.role;
  }

  ngOnInit(): void {
    if (this.role === 'ADMIN') {
      this.columnsToDisplay = ['description', 'creationDate', 'type', 'author'];
      this.userService.getAll('teacher').subscribe(
        response => {
          this.teachers = response.filter(teacher => teacher.enable);
        },
        error => {
          this.alertService.error('Get active teachers failed');
        });
    } else {
      this.columnsToDisplay = ['description', 'creationDate', 'type'];
    }

    this.questionService.getAll().subscribe(
      response => {
        this.dataSource.data = response;
      },
      error => {
        this.alertService.error('Get questions failed');
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
    const todate = new Date(timeStamp).getDate();
    const tomonth = new Date(timeStamp).getMonth() + 1;
    const toyear = new Date(timeStamp).getFullYear();
    return tomonth + ' / ' + todate + ' / ' + toyear;
  }

  convertDescription(description: string) {
    let substring = description.substring(0, 40);
    substring = description.length > 40 ? substring + '...' : substring;
    return substring;
  }

  updateQuestion(model: GetQuestionModel) {
    this.router.navigate(['/update-question'], {state: {question: model}});
  }

  updateQuestionAuthor(teacher: GetActiveTeacher, questionId: number, index: number) {
    this.questionService.updateAuthor(questionId, teacher.id).subscribe(
      response => {
        this.dataSource.data[index].author = teacher.userName;
        this.dataSource._updateChangeSubscription();
        this.teacherSelected = null;
        this.alertService.success('Update question success');
      },
      error => {
        this.alertService.error('Update question fail');
      }
    );
  }

  deleteQuestion(id: number, index: number) {
    this.questionService.delete(id).subscribe(
      response => {
        this.dataSource.data.splice(index, 1);
        this.dataSource._updateChangeSubscription();
        this.alertService.success('Delete question success');
      },
      error => {
        this.alertService.error('Delete question fail');
      }
    );
  }

  filterList(author: string) {
    return this.teachers.filter(teacher => teacher.userName !== author);
  }

}
