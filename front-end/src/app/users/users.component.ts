import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {MatTableDataSource} from '@angular/material/table';
import {GetUserModel} from '../_models/getUserModel';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {Router} from '@angular/router';
import {UserService} from '../_service/user/user.service';
import {AlertService} from '../_helpers/alert.service';
import {fadeAnimation} from '../_animation/fadeInOut';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss'],
  animations: [fadeAnimation]
})
export class UsersComponent implements OnInit , AfterViewInit {

  title: string;
  role: string;
  image: string;

  displayedColumns: string[] = ['userName', 'email', 'update', 'delete'];
  dataSource: MatTableDataSource<GetUserModel>;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private router: Router,
    private userService: UserService,
    private alertService: AlertService
  ) {
    this.dataSource = new MatTableDataSource([]);
  }

  ngOnInit(): void {
    if (this.router.url === '/teachers') {
      this.title = 'Teachers';
      this.role = 'teacher';
      this.image = '../../../assets/teachers.png';
      this.displayedColumns = ['userName', 'email', 'status', 'update', 'delete'];
    } else {
      this.title = 'Students';
      this.role = 'student';
      this.image = '../../../assets/students.png';
      this.displayedColumns = ['userName', 'email', 'update', 'delete'];
    }

    this.userService.getAll(this.role).subscribe(
      response => {
        this.dataSource.data = response;
      },
      error => {
        this.alertService.error('Get' + this.role + 'failed');
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

  goToUpdateUser(element: GetUserModel) {
    this.router.navigate(['/update-user'], {state: {user: element}});
  }

  deleteUser(index: number, email: string) {
    this.userService.delete(email).subscribe(
      data => {
        if (this.role === 'student') {
          this.dataSource.data.splice(index, 1);
          this.dataSource._updateChangeSubscription();
        } else {
          this.dataSource.data[index].enable = false;
          this.dataSource._updateChangeSubscription();
        }
      },
      error => {
        this.alertService.error('Delete ' + this.role + ' fail');
      }
    );
  }

  reactivateTeacher(index: number, email: string) {
    this.userService.reactivateTeacher(email).subscribe(
      data => {
        this.dataSource.data[index].enable = true;
        this.dataSource._updateChangeSubscription();
      },
      error => {
        this.alertService.error('Reactivating teacher failed');
      }
    );
  }

}
