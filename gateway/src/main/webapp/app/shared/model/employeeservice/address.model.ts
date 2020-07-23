import { AddressType } from 'app/shared/model/enumerations/address-type.model';

export interface IAddress {
  id?: string;
  addressType?: AddressType;
  street?: string;
  postalCode?: string;
  city?: string;
  country?: string;
  stateProvince?: string;
  buildingNumber?: string;
  flatNumber?: string;
  employeeId?: string;
}

export const defaultValue: Readonly<IAddress> = {};
