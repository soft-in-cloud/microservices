import { IAddress } from 'app/shared/model/employeeservice/address.model';
import { Role } from 'app/shared/model/enumerations/role.model';

export interface IEmployee {
  id?: string;
  firstName?: string;
  lastName?: string;
  age?: number;
  pesel?: string;
  role?: Role;
  addresses?: IAddress[];
  managerId?: string;
}

export const defaultValue: Readonly<IEmployee> = {};
