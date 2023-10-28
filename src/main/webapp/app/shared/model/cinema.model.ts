import { IOperator } from 'app/shared/model/operator.model';
import { IAddress } from 'app/shared/model/address.model';
import { IRoom } from 'app/shared/model/room.model';

export interface ICinema {
  id?: number;
  name?: string | null;
  operator?: IOperator | null;
  address?: IAddress | null;
  rooms?: IRoom[] | null;
}

export const defaultValue: Readonly<ICinema> = {};
