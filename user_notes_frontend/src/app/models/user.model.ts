export interface User {
  id: string;
  name: string;
  email: string;
  note?: string;
}

export interface UserRequest {
  name: string;
  email: string;
  note?: string;
}
