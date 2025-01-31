import Xy from "@/interface/Xy";

interface Role {
  id: string;
  nickName: string;
  level: number;
  hp: number;
  mp: number;
  mapId: number;
  xy: Xy;
  loginTime: string;
  logoutTime: string;
  x: number;
}

export default Role;
