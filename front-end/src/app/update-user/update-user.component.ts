import { Component, OnInit } from '@angular/core';
import {UpdateUserModel} from '../_models/updateUserModel';
import {Router} from '@angular/router';
import {fadeAnimation} from '../_animation/fadeInOut';
import { Location } from '@angular/common';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.scss'],
  animations: [fadeAnimation]
})
export class UpdateUserComponent implements OnInit {

  user: UpdateUserModel;

  constructor(
    private router: Router,
    private location: Location,
  ) {
    if (this.router.getCurrentNavigation().extras.state === undefined) {
      this.location.back();
    } else {
      this.user = this.router.getCurrentNavigation().extras.state.user;
    }
  }

  ngOnInit() {
  }

}
