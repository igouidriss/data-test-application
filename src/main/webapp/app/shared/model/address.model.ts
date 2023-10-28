import { ICinema } from 'app/shared/model/cinema.model';

export interface IAddress {
  id?: number;
  numberStreet?: string | null;
  postalCode?: string | null;
  city?: string | null;
  cinema?: ICinema | null;
}

export const defaultValue: Readonly<IAddress> = {};
