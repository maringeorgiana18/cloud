import {AddAnswerModel} from './addAnswerModel';

export class AddQuestionModel {
  content: string;
  description: string;
  type: string;
  answers: AddAnswerModel[];
}
