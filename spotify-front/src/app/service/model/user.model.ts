export interface User{
  firstName? : string;
  lastName?: string;
  email?:string;
  subscription? : Subcription;
  imageUrl?: string;
}
export enum Subcription{
  PREMIUM,FREE
}
