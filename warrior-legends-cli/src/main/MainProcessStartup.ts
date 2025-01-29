import { ipcMain } from "electron";
import GameWebSocket from "./GameWebSocket";

export default function (
  app: Electron.App,
  mainWindow: Electron.BrowserWindow
) {
  ipcMain.handle("wsConnect", async (event, { id, token }) => {
    console.log("wsConnect", id, token);
    const gameWebSocket: GameWebSocket = new GameWebSocket(id, token);
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
}
