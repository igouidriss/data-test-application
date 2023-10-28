import { IPeriod } from 'app/shared/model/period.model';
import { ICinema } from 'app/shared/model/cinema.model';

export interface IRoom {
  id?: number;
  seatsNumber?: number | null;
  screenHeight?: number | null;
  screenWidth?: number | null;
  distance?: number | null;
  periods?: IPeriod[] | null;
  cinema?: ICinema | null;
}

export const defaultValue: Readonly<IRoom> = {};
