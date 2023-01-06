import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './home/home.component';
import {AuthGuard} from './_helpers/auth.guard';
import {LoginComponent} from './login/login.component';
import {UpdateUserComponent} from './update-user/update-user.component';
import {AddQuestionComponent} from './add-question/add-question.component';
import {QuestionsComponent} from './questions/questions.component';
import {UpdateQuestionComponent} from './update-question/update-question.component';
import {LearningEnvironmentComponent} from './student/learning-environment/learning-environment.component';
import {ExamSimulationComponent} from './student/exam-simulation/exam-simulation.component';
import {QuestionHistoryComponent} from './student/question-history/question-history.component';
import {QuestionsUpdatedComponent} from './student/questions-updated/questions-updated.component';
import {ResolveQuestionComponent} from './student/resolve-question/resolve-question.component';
import {AddUserComponent} from './add-user/add-user.component';
import {UsersComponent} from './users/users.component';
import {ExamsComponent} from './exams/exams.component';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: AddUserComponent },
  { path: 'add-teacher', component: AddUserComponent, canActivate: [AuthGuard] },
  { path: 'teachers', component: UsersComponent, canActivate: [AuthGuard] },
  { path: 'students', component: UsersComponent, canActivate: [AuthGuard] },
  { path: 'update-user', component: UpdateUserComponent, canActivate: [AuthGuard] },
  { path: 'add-question', component: AddQuestionComponent, canActivate: [AuthGuard] },
  { path: 'update-question', component: UpdateQuestionComponent, canActivate: [AuthGuard] },
  { path: 'questions', component: QuestionsComponent, canActivate: [AuthGuard] },
  { path: 'learning-environment', component: LearningEnvironmentComponent, canActivate: [AuthGuard] },
  { path: 'exam-simulation', component: ExamSimulationComponent, canActivate: [AuthGuard] },
  { path: 'question-history', component: QuestionHistoryComponent, canActivate: [AuthGuard] },
  { path: 'questions-updated', component: QuestionsUpdatedComponent, canActivate: [AuthGuard] },
  { path: 'resolve-question', component: ResolveQuestionComponent, canActivate: [AuthGuard] },
  { path: 'exams', component: ExamsComponent, canActivate: [AuthGuard] },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { relativeLinkResolution: 'legacy' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
