import {AbstractControl, ValidatorFn} from '@angular/forms';
import {Injectable} from '@angular/core';

@Injectable()
export class CheckAnswerStatusTrue {

  check(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: boolean } | null => {
        let check = false;
        control.value.forEach(entry => {
          if (entry.status === 'true') {
            check = true;
          }
        });
        if (!check) {
          return {statusTrue: true};
        }
        return null;
    };
  }
}
