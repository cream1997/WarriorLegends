import msgDispatcher from "@/ts/MsgDispatcher";
import { LoginMapRes } from "@/interface/res/LoginMapRes";

export enum ResMsgType {
  LoginMap = 1
}

class MsgReceiver {
  onReceiveEnterMap(callback: (msgData: LoginMapRes, msgType: number) => void) {
    msgDispatcher.addMsgListener(ResMsgType.LoginMap, callback);
  }
}

const msgReceiver = new MsgReceiver();
export default msgReceiver;
