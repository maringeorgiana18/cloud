import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {AlertService} from '../_helpers/alert.service';
import {Router} from '@angular/router';
import {EmailValidator} from '../_helpers/emailValidator';
import {UserService} from '../_service/user/user.service';
import {PasswordMustMatch} from '../_helpers/passwordMatch';
import {AddUserModel} from '../_models/addUserModel';
import {first} from 'rxjs/operators';
import {fadeAnimation} from '../_animation/fadeInOut';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss'],
  animations: [fadeAnimation]
})
export class AddUserComponent implements OnInit {

  title: string;
  role: string;
  image: string;
  form: FormGroup;
  send: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private alertService: AlertService,
    private router: Router,
    private emailValidator: EmailValidator,
    private userService: UserService
  ) {
  }

  ngOnInit(): void {
    if (this.router.url === '/register') {
      this.title = 'Register';
      this.role = 'student';
      this.image = '../../assets/register.jpg';
    } else {
      this.title = 'Add teacher';
      this.role = 'teacher';
      this.image = '../../assets/add-teacher.jpg';
    }

    this.form = this.formBuilder.group({
      username: new FormControl('', Validators.required),
      email: new FormControl('', [Validators.required, Validators.email], this.emailValidator.check()),
      password: new FormControl('', [Validators.required, Validators.pattern('(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$')]),
      confirmPassword: new FormControl('', Validators.required),
    }, {
      validator: PasswordMustMatch('password', 'confirmPassword')
    });
    this.send = false;
  }

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.alertService.error('Incorrect inputs');
      return;
    }
    const model = new AddUserModel();
    model.userName = this.f.username.value;
    model.email = this.f.email.value;
    model.password = this.f.password.value;
    this.send = true;
    setTimeout(() => {
      this.userService.add(this.role, model)
        .pipe(first())
        .subscribe(
          data => {
            this.alertService.success('Register complete');
          },
          error => {
            this.alertService.error('Register fail');
          }
        );
      this.send = false;
    }, 1000);
  }

  get f() {
    return this.form.controls;
  }
}
