import msgDispatcher from "@/ts/MsgDispatcher";

export enum ResMsgType {
  EnterMap = 1
}

class MsgReceiver {
  onReceiveEnterMap(callback: (msg: any) => void) {
    msgDispatcher.addMsgListener(ResMsgType.EnterMap, callback);
  }
}

const msgReceiver = new MsgReceiver();
export default msgReceiver;
