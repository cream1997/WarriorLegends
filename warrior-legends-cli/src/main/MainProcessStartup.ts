import { ipcMain } from "electron";
import GameWebSocket from "./GameWebSocket";

export default function (
  app: Electron.App,
  mainWindow: Electron.BrowserWindow
) {
  ipcMain.handle("wsConnect", async (event, { id, token }) => {
    const gameWebSocket: GameWebSocket = new GameWebSocket(id, token);
    try {
      await gameWebSocket.connect();
      gameWebSocket.onMsgCallback = (msg) => {
        mainWindow.webContents.send("wsMsg", msg);
      };
      return "ok";
    } catch (error) {
      return "error";
    }
  });
}
