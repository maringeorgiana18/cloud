import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UpdateUserModel} from '../_models/updateUserModel';
import {UserService} from '../_service/user/user.service';
import {Router} from '@angular/router';
import {EmailUpdateValidator} from '../_helpers/emailUpdateValidator';
import {AlertService} from '../_helpers/alert.service';
import {PasswordMustMatch} from '../_helpers/passwordMatch';
import {first} from 'rxjs/operators';

@Component({
  selector: 'app-update-user-form',
  templateUrl: './update-user-form.component.html',
  styleUrls: ['./update-user-form.component.scss']
})
export class UpdateUserFormComponent implements OnInit {

  @Input() user: UpdateUserModel;

  form: FormGroup;
  send: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private emailValidator: EmailUpdateValidator,
    private alertService: AlertService,
  ) {
  }

  ngOnInit() {
    this.form = this.formBuilder.group({
      userName: new FormControl(this.user.userName, Validators.required),
      email: [this.user.email, [Validators.required, Validators.email], this.emailValidator.check(this.user.id)],
      password: new FormControl({value: '', disabled: true}, [Validators.required, Validators.pattern('(?=^.{8,}$)(?=.*\\d)(?=.*[!@#$%^&*]+)(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$')]),
      confirmPassword: new FormControl({value: '',  disabled: true}, Validators.required),
    }, {
      validator: PasswordMustMatch('password', 'confirmPassword')
    });
    this.send = false;
  }

  passwordChange() {
    this.formFields.password.enable();
    this.formFields.confirmPassword.enable();
  }

  submit() {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }
    this.send = true;
    this.alertService.clear();
    this.user.userName = this.formFields.userName.value;
    this.user.email = this.formFields.email.value;
    this.user.password = this.formFields.password.enabled ? this.formFields.password.value : this.user.password;
    setTimeout(() => {
      this.userService.update(this.user)
        .pipe(first())
        .subscribe(
          data => {
            this.alertService.success('User updated');
          },
          error => {
            this.alertService.error('Update user failed');
          }
        );
      this.send = false;
    }, 1000);

  }

  private get formFields() { return this.form.controls; }

}
