import { defineStore } from "pinia";

export default defineStore("winMetaStore", {
  state() {
    return {
      rowCount: 0,
      columnCount: 0,
      gridSize: 0,
      winWidth: 0,
      winHeight: 0
    };
  },
  actions: {
    init(
      rowCount: number,
      columnCount: number,
      gridSize: number,
      winWidth: number,
      winHeight: number
    ) {
      this.rowCount = rowCount;
      this.columnCount = columnCount;
      this.gridSize = gridSize;
      this.winWidth = winWidth;
      this.winHeight = winHeight;
    }
  }
});
