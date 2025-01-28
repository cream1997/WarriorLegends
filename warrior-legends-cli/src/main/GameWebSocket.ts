// 配置参数
import WebSocket from "ws";
import { clearInterval } from "node:timers";

const WS_CONFIG = {
  URL: "ws://127.0.0.1:8889/ws",
  RECONNECT_INTERVAL: 5000, // 重试间隔（毫秒）
  MAX_RECONNECT_ATTEMPTS: 5, // 最大重试次数
  HEARTBEAT_INTERVAL: 30000 // 心跳间隔
};

export default class GameWebSocket {
  set onMsgCallback(value) {
    this._onMsgCallback = value;
  }

  private ws;
  private reconnectAttempts: number;
  private heartbeatTimer;
  private _onMsgCallback;
  private token: string;
  private id: string;

  constructor(id: string, token: string) {
    this.id = id;
    this.token = token;
    this.ws = null;
    this.reconnectAttempts = 0;
    this.heartbeatTimer = null;
  }

  // 初始化连接
  connect() {
    this.ws = new WebSocket(`WS_CONFIG.URL?id=${this.id}&token=${this.token}`, {
      perMessageDeflate: false,
      handshakeTimeout: 10000
    });
    // 事件绑定
    this.ws
      .on("message", (data) => this.handleMessage(data))
      .on("close", (code, reason) => this.handleClose(code, reason));
    return this.getConnectPromise();
  }

  private getConnectPromise() {
    return new Promise((resolve, reject) => {
      this.ws.on("error", (error) => {
        console.error("[WS] Connection error:", error);
        reject();
      });
      this.ws.on("open", () => {
        console.log("[WS] Connected to game server");
        this.reconnectAttempts = 0;
        // 启动心跳检测
        this.heartbeatTimer = setInterval(() => {
          if (this.ws.readyState === WebSocket.OPEN) {
            this.ws.ping();
          }
        }, WS_CONFIG.HEARTBEAT_INTERVAL);
        resolve(this);
      });
    });
  }

  // 处理消息
  handleMessage(data) {
    try {
      const message = JSON.parse(data);
      this._onMsgCallback(message);
    } catch (error) {
      console.error("[WS] Message parsing error:", error);
    }
  }

  // 关闭连接
  handleClose(code, reason) {
    console.log(`[WS] Connection closed: ${code} ${reason}`);
    clearInterval(this.heartbeatTimer);

    // 自动重连逻辑
    if (this.reconnectAttempts < WS_CONFIG.MAX_RECONNECT_ATTEMPTS) {
      setTimeout(() => {
        this.reconnectAttempts++;
        console.log(`[WS] Reconnecting (attempt ${this.reconnectAttempts})...`);
        this.connect();
      }, WS_CONFIG.RECONNECT_INTERVAL * this.reconnectAttempts);
    }
  }

  // 发送消息到服务器
  send(data) {
    if (this.ws?.readyState === WebSocket.OPEN) {
      this.ws.send(JSON.stringify(data));
    }
  }

  close() {
    clearInterval(this.heartbeatTimer);
    if (this.ws) {
      this.ws.removeAllListeners();
      this.ws.close();
    }
  }
}
