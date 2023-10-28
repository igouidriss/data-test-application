import { ISession } from 'app/shared/model/session.model';
import { ICasting } from 'app/shared/model/casting.model';

export interface IMovie {
  id?: number;
  title?: string | null;
  releaseDate?: string | null;
  sessions?: ISession[] | null;
  casting?: ICasting | null;
}

export const defaultValue: Readonly<IMovie> = {};
