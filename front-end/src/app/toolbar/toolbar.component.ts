import {Component, OnDestroy, OnInit} from '@angular/core';
import {AuthenticationService} from '../_service/authentication/authentication.service';
import {Router} from '@angular/router';
import {AttemptService} from '../_service/attempt/attempt.service';
import {AlertService} from '../_helpers/alert.service';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  numberOfUpdatedQuestion: number;

  constructor(
    private router: Router,
    private service: AuthenticationService,
    private attemptService: AttemptService,
    private alertService: AlertService
  ) {
    this.numberOfUpdatedQuestion = null;
  }

  ngOnInit(): void {
  }

  currentUser() {
    return this.service.currentUserValue;
  }

  logout() {
    this.service.logout();
    this.router.navigate(['/login']);
  }

  studentMenu() {
    if (this.currentUser().role === 'STUDENT') {
      this.attemptService.getUpdatedNumber()
        .pipe(first())
        .subscribe(
          data => {
            if (data !== 0) {
              this.numberOfUpdatedQuestion = data;
            }
          },
          error => {
            this.alertService.error('Get number of updated question fail');
          }
        );
    }
  }

}
