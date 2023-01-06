import {GetAnswerModel} from './getAnswerModel';

export class GetQuestionModel {
  id: number;
  content: string;
  type: string;
  description: string;
  author: string;
  creationDate: Date;
  answers: GetAnswerModel[];
}
