import {GetAttemptAnswerModel} from './getAttemptAnswerModel';

export class GetAttemptModel {
  id: number;
  content: string;
  description: string;
  status: boolean;
  historyStatus: string;
  type: string;
  startDate: Date;
  endDate: Date;
  answers: GetAttemptAnswerModel[];
}
