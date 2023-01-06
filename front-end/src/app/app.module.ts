import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {AlertComponent} from './alert/alert.component';
import {UpdateUserComponent} from './update-user/update-user.component';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';
import {AddQuestionComponent} from './add-question/add-question.component';
import {QuestionsComponent} from './questions/questions.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {JwtInterceptor} from './_helpers/jwt.interceptor';
import {ErrorInterceptor} from './_helpers/error.interceptor';
import {EmailValidator} from './_helpers/emailValidator';
import {EmailUpdateValidator} from './_helpers/emailUpdateValidator';
import {CheckAnswerStatusTrue} from './_helpers/checkAnswerStatusTrue';
import {CheckAnswerStatusFalse} from './_helpers/checkAnswerStatusFalse';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatIconModule} from '@angular/material/icon';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatTableModule} from '@angular/material/table';
import {MatSortModule} from '@angular/material/sort';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatSelectModule} from '@angular/material/select';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatMenuModule} from '@angular/material/menu';
import { UpdateQuestionComponent } from './update-question/update-question.component';
import {MathJaxModule} from 'ngx-mathjax';
import { LearningEnvironmentComponent } from './student/learning-environment/learning-environment.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatDividerModule} from '@angular/material/divider';
import {MatBadgeModule} from '@angular/material/badge';
import {MatStepperModule} from '@angular/material/stepper';
import { ExamSimulationComponent } from './student/exam-simulation/exam-simulation.component';
import { QuestionHistoryComponent } from './student/question-history/question-history.component';
import { QuestionsUpdatedComponent } from './student/questions-updated/questions-updated.component';
import { ResolveQuestionComponent } from './student/resolve-question/resolve-question.component';
import { UpdateUserFormComponent } from './update-user-form/update-user-form.component';
import { AddUserComponent } from './add-user/add-user.component';
import { UsersComponent } from './users/users.component';
import {ToolbarComponent} from './toolbar/toolbar.component';
import { ExamsComponent } from './exams/exams.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    ToolbarComponent,
    AlertComponent,
    UpdateUserComponent,
    AddQuestionComponent,
    QuestionsComponent,
    UpdateQuestionComponent,
    LearningEnvironmentComponent,
    ExamSimulationComponent,
    QuestionHistoryComponent,
    QuestionsUpdatedComponent,
    ResolveQuestionComponent,
    UpdateUserFormComponent,
    AddUserComponent,
    UsersComponent,
    ExamsComponent
  ],
  imports: [
    BrowserModule,
    MatProgressBarModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatCardModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    ReactiveFormsModule,
    MatButtonModule,
    HttpClientModule,
    MatToolbarModule,
    MatSidenavModule,
    MatTableModule,
    MatSortModule,
    MatMenuModule,
    MatProgressSpinnerModule,
    MatPaginatorModule,
    MatSelectModule,
    MatCheckboxModule,
    MatBadgeModule,
    MatDividerModule,
    MatStepperModule,
    MathJaxModule.forRoot({
      version: '2.7.5',
      config: 'TeX-AMS_HTML',
      hostname: 'cdnjs.cloudflare.com'
    }),
    FormsModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    EmailValidator,
    EmailUpdateValidator,
    CheckAnswerStatusTrue,
    CheckAnswerStatusFalse
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
