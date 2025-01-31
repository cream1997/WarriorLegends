import msgDispatcher from "@/ts/MsgDispatcher";
import { EnterMapRes, LoginMapRes } from "@/interface/res/ResInterface";

type MsgHandler<T> = (msgData: T, msgType: number) => void;

export enum ResMsgType {
  LoginMap = 1,
  EnterMap = 2
}

class MsgReceiver {
  onReceiveLoginMap(handler: MsgHandler<LoginMapRes>) {
    msgDispatcher.addMsgListener(ResMsgType.LoginMap, handler);
  }

  onReceiveEnterMap(handler: MsgHandler<EnterMapRes>) {
    msgDispatcher.addMsgListener(ResMsgType.EnterMap, handler);
  }
}

const msgReceiver = new MsgReceiver();
export default msgReceiver;
