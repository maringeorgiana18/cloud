import {StartAnswerModel} from './startAnswerModel';

export class StartQuestionModel {
  id: number;
  content: string;
  description: string;
  type: string;
  answers: StartAnswerModel[];
}
