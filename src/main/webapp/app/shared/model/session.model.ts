import { IPeriod } from 'app/shared/model/period.model';
import { IMovie } from 'app/shared/model/movie.model';

export interface ISession {
  id?: number;
  startHour?: number | null;
  period?: IPeriod | null;
  movie?: IMovie | null;
}

export const defaultValue: Readonly<ISession> = {};
