import { Moment } from 'moment';

export interface IFeature {
  id?: number;
  displayName?: string;
  technicalName?: string;
  expiresOn?: string;
  description?: string;
  inverted?: boolean;
}

export const defaultValue: Readonly<IFeature> = {
  inverted: false,
};
