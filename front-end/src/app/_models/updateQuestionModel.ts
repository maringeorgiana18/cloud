import {UpdateAnswerModel} from './updateAnswerModel';

export class UpdateQuestionModel {
  id: number;
  content: string;
  type: string;
  description: string;
  answers: UpdateAnswerModel[];
}
