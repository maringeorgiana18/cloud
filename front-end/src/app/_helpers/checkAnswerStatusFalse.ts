import {AbstractControl, ValidatorFn} from '@angular/forms';
import {Injectable} from '@angular/core';

@Injectable()
export class CheckAnswerStatusFalse {

  check(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: boolean } | null => {
        let check = false;
        control.value.forEach(entry => {
          if (entry.status === 'false') {
            check = true;
          }
        });
        if (!check) {
          return {statusFalse: true};
        }
        return null;
    };
  }
}
