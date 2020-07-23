import { Moment } from 'moment';

export interface IError {
  id?: string;
  exceptionName?: string;
  message?: string;
  stackTrace?: string;
  createdAt?: string;
  serviceName?: string;
}

export const defaultValue: Readonly<IError> = {};
