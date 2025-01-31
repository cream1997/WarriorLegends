import msgDispatcher from "@/net/ws/MsgDispatcher";
import {
  EnterMapRes,
  LoginMapRes,
  WalkRes
} from "@/interface/res/ResInterface";

type MsgHandler<T> = (msgData: T, msgType: number) => void;

export enum ResMsgType {
  LoginMap = 1,
  EnterMap = 2,
  Walk
}

class MsgReceiver {
  onReceiveLoginMap(handler: MsgHandler<LoginMapRes>) {
    msgDispatcher.addMsgListener(ResMsgType.LoginMap, handler);
  }

  onReceiveEnterMap(handler: MsgHandler<EnterMapRes>) {
    msgDispatcher.addMsgListener(ResMsgType.EnterMap, handler);
  }

  onReceiveWalk(handler: MsgHandler<WalkRes>) {
    msgDispatcher.addMsgListener(ResMsgType.Walk, handler);
  }
}

const msgReceiver = new MsgReceiver();
export default msgReceiver;
