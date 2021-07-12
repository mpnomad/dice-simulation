import { DiceModel } from "src/app/model/dice.model";

export interface RollModel {
  rollCount: number;
  rollTotal: number;

  diceList: DiceModel[];
}
