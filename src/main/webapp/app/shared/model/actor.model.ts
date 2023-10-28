import { ICasting } from 'app/shared/model/casting.model';

export interface IActor {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  birthDate?: string | null;
  castings?: ICasting[] | null;
}

export const defaultValue: Readonly<IActor> = {};
