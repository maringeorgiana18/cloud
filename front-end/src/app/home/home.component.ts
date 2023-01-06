import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../_service/authentication/authentication.service';
import {fadeAnimation} from '../_animation/fadeInOut';
import {UpdateUserModel} from '../_models/updateUserModel';
import {ChartService} from '../_service/chart/chart.service';
import {UserService} from '../_service/user/user.service';
import {Router} from '@angular/router';
import {AlertService} from '../_helpers/alert.service';
import * as CanvasJS from '../../assets/canvasjs.min';
import * as CanvasStockJS from '../../assets/canvasjs.stock.min';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  animations: [fadeAnimation]
})
export class HomeComponent implements OnInit {

  user: UpdateUserModel;

  constructor(
    private chartService: ChartService,
    private userService: UserService,
    private router: Router,
    private alertService: AlertService,
    private authenticationService: AuthenticationService
  ) {
    this.user = undefined;
  }

  ngOnInit(): void {
    if (this.currentUser() !== null) {
      this.userService.getInfo().subscribe(
        response => {
          this.user = response;
        },
        error => {
          this.alertService.error('Get user info failed');
        });
      if (this.currentUser().role === 'ADMIN') {
        this.chartService.logins().subscribe(
          response => {
            const dataPoints = [];
            response.forEach(point => dataPoints.push({x: new Date(point.x), y: point.y}));
            const chart1 = new CanvasStockJS.StockChart('chartContainer1', {
              theme: 'light2',
              animationEnabled: true,
              title: {
                text: 'New users per days'
              },
              charts: [{
                axisX: {
                  crosshair: {
                    enabled: true,
                    snapToDataPoint: true
                  }
                },
                axisY: {
                  crosshair: {
                    enabled: true,
                    snapToDataPoint: true,
                    valueFormatString: '#'
                  }
                },
                toolTip: {
                  shared: true
                },
                data: [{
                  type: 'splineArea',
                  name: 'Users',
                  yValueFormatString: '#',
                  dataPoints
                }]
              }],
              navigator: {
                slider: {
                  minimum: new Date(response[4].x),
                  maximum: new Date(response[0].x)
                }
              }
            });

            chart1.render();
          },
          error => {
            this.alertService.error('Get admin info failed');
          });

        this.chartService.newUsers().subscribe(response => {
            const dataPoints = [];
            response.forEach(point => dataPoints.push({x: new Date(point.x), y: point.y}));
            const chart4 = new CanvasStockJS.StockChart('chartContainer4', {
              theme: 'light2',
              animationEnabled: true,
              title: {
                text: 'Login per days'
              },
              charts: [{
                axisX: {
                  crosshair: {
                    enabled: true,
                    snapToDataPoint: true
                  }
                },
                axisY: {
                  prefix: '$',
                  crosshair: {
                    enabled: true,
                    snapToDataPoint: true,
                    valueFormatString: '$#'
                  }
                },
                toolTip: {
                  shared: true
                },
                data: [{
                  type: 'splineArea',
                  name: 'Price',
                  yValueFormatString: '$#',
                  dataPoints
                }]
              }],
              navigator: {
                slider: {
                  minimum: new Date(response[4].x),
                  maximum: new Date(response[0].x)
                }
              }
            });

            chart4.render();
          },
          error => {
            this.alertService.error('Get users count failed');
          });

        this.chartService.users().subscribe(response => {
            const chart2 = new CanvasJS.Chart('chartContainer2', {
              theme: 'light2',
              animationEnabled: true,
              title: {
                text: 'User Categories',
                horizontalAlign: 'left'
              },
              data: [{
                type: 'doughnut',
                startAngle: 60,
                indexLabelFontSize: 17,
                indexLabel: '{label} - #percent%',
                toolTipContent: '<b>{label}:</b> {y} (#percent%)',
                dataPoints: response
              }]
            });
            chart2.render();
          },
          error => {
            this.alertService.error('Get users count failed');
          });

        this.chartService.questions().subscribe(response => {
            const chart3 = new CanvasJS.Chart('chartContainer3', {
              theme: 'light2',
              animationEnabled: true,
              title: {
                text: 'Question Categories'
              },
              data: [{
                type: 'column',
                dataPoints: response
              }]
            });
            chart3.render();
          },
          error => {
            this.alertService.error('Get questions count failed');
          });
      }

      if (this.currentUser().role === 'TEACHER' || this.currentUser().role === 'STUDENT') {
        this.chartService.questionPerDay().subscribe(
          response => {
            const dataPoints = [];
            response.forEach(point => dataPoints.push({x: new Date(point.x), y: point.y}));
            const chart1 = new CanvasStockJS.StockChart('chartContainer5', {
              theme: 'light2',
              animationEnabled: true,
              title: {
                text: 'Question response per days'
              },
              charts: [{
                axisX: {
                  crosshair: {
                    enabled: true,
                    snapToDataPoint: true
                  }
                },
                axisY: {
                  crosshair: {
                    enabled: true,
                    snapToDataPoint: true,
                    valueFormatString: '#'
                  }
                },
                toolTip: {
                  shared: true
                },
                data: [{
                  type: 'splineArea',
                  name: 'Users',
                  yValueFormatString: '#',
                  dataPoints
                }]
              }],
              navigator: {
                slider: {
                  minimum: new Date(response[4].x),
                  maximum: new Date(response[0].x)
                }
              }
            });

            chart1.render();
          },
          error => {
            this.alertService.error('Get question response info failed');
          });

        this.chartService.questions().subscribe(response => {
            const chart2 = new CanvasJS.Chart('chartContainer6', {
              theme: 'light2',
              animationEnabled: true,
              title: {
                text: 'Question Categories',
                horizontalAlign: 'left'
              },
              data: [{
                type: 'doughnut',
                startAngle: 60,
                indexLabelFontSize: 17,
                indexLabel: '{label} - #percent%',
                toolTipContent: '<b>{label}:</b> {y} (#percent%)',
                dataPoints: response
              }]
            });
            chart2.render();
          },
          error => {
            this.alertService.error('Get question categories failed');
          });

        this.chartService.responses().subscribe(response => {
            const chart3 = new CanvasJS.Chart('chartContainer7', {
              theme: 'light2',
              animationEnabled: true,
              title: {
                text: 'Response count'
              },
              data: [{
                type: 'column',
                dataPoints: response
              }]
            });
            chart3.render();
          },
          error => {
            this.alertService.error('Get response count failed');
          });
      }

    }

  }

  currentUser() {
    return this.authenticationService.currentUserValue;
  }

}
