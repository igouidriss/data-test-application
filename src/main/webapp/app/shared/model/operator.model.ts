import { ICinema } from 'app/shared/model/cinema.model';

export interface IOperator {
  id?: number;
  name?: string | null;
  cinema?: ICinema | null;
}

export const defaultValue: Readonly<IOperator> = {};
