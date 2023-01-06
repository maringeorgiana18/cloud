import {Observable, timer} from 'rxjs';
import {map, switchMap} from 'rxjs/operators';
import {AbstractControl, AsyncValidatorFn} from '@angular/forms';
import {Injectable} from '@angular/core';
import {UserService} from '../_service/user/user.service';

@Injectable()
export class EmailUpdateValidator {

  constructor(private userService: UserService) {
  }

  private searchEmail(email, id) {
    return timer(1000)
      .pipe(
        switchMap(() => {
          return this.userService.checkEmail(email, id);
        })
      );
  }

  check(id): AsyncValidatorFn {
    return (control: AbstractControl): Observable<{ [key: string]: any } | null> => {
      return this.searchEmail(control.value, id)
        .pipe(
          map(res => {
            if (res === false) {
              return { emailExist: true };
            }
          })
        );
    };
  }


}
