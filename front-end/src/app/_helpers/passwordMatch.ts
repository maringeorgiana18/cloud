import {FormGroup} from '@angular/forms';

export function PasswordMustMatch(controlPassword: string, matchingControlPassword: string) {
  return (formGroup: FormGroup) => {
    const control = formGroup.controls[controlPassword];
    const matchingControl = formGroup.controls[matchingControlPassword];
    if (matchingControl.errors && !matchingControl.errors.mustMatch) {
      return;
    }
    if (control.value !== matchingControl.value) {
      matchingControl.setErrors({mustMatch: true});
    } else {
      matchingControl.setErrors(null);
    }
  };
}
