import { ISession } from 'app/shared/model/session.model';
import { IRoom } from 'app/shared/model/room.model';

export interface IPeriod {
  id?: number;
  start?: string | null;
  end?: string | null;
  session?: ISession | null;
  room?: IRoom | null;
}

export const defaultValue: Readonly<IPeriod> = {};
