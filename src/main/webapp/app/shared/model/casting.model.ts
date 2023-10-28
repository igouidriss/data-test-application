import { IMovie } from 'app/shared/model/movie.model';
import { IActor } from 'app/shared/model/actor.model';

export interface ICasting {
  id?: number;
  movie?: IMovie | null;
  actors?: IActor[] | null;
}

export const defaultValue: Readonly<ICasting> = {};
