import Role from "@/interface/Role";

export interface LoginMapRes {
  role: Role;
  mapId: number;
  mapName: string;
  width: number;
  height: number;
}

export interface EnterMapRes {
  role: Role;
}
