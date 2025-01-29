import { ipcMain } from "electron";
import GameWebSocket from "./GameWebSocket";

export default function (
  app: Electron.App,
  mainWindow: Electron.BrowserWindow
) {
  let gameWebSocket: GameWebSocket;
  ipcMain.handle("wsConnect", async (event, { id, token }) => {
    gameWebSocket = new GameWebSocket(id, token);
    try {
      await gameWebSocket.connect();
      gameWebSocket.onMsgCallback = (msg) => {
        mainWindow.webContents.send("wsMsg", msg);
      };
      return "success";
    } catch (error) {
      console.error(error);
      return "error";
    }
  });

  ipcMain.on("sendMsg", (event, msg) => {
    gameWebSocket && gameWebSocket.send(msg);
  });
}
