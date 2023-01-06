import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {AlertService} from '../_helpers/alert.service';
import {AuthenticationService} from '../_service/authentication/authentication.service';
import {first} from 'rxjs/operators';
import {LoginModel} from '../_models/loginModel';
import {fadeAnimation} from '../_animation/fadeInOut';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  animations: [fadeAnimation]
})
export class LoginComponent implements OnInit {

  form: FormGroup;
  send: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private alertService: AlertService,
    private router: Router,
    private service: AuthenticationService
  ) {
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      email: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required),
    });
    this.send = false;
  }

  get f() {
    return this.form.controls;
  }

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.alertService.error('Incorrect inputs');
      return;
    }
    this.alertService.clear();
    const model = new LoginModel();
    model.email = this.f.email.value;
    model.password = this.f.password.value;
    this.send = true;
    setTimeout(() => {
      this.service.login(model)
        .pipe(first())
        .subscribe(
          data => {
            this.router.navigateByUrl('/');
          },
          error => {
            this.alertService.error('Authentication error');
          }
        );
      this.send = false;
    }, 1000);
  }
}
