export interface PlayerState {
  id: string | null;
  name: string;
  archetype: string;
  locationId: string;
  health: number;
  maxHealth: number;
  grit: number;
  streetSmarts: number;
  technicalSkill: number;
  armorClass: number;
  exp: number;
  level: number;
  nextLevelExp: number;
  debtAmount: string; // BigInteger comes over as a String
  ndraBalance: string; // BigInteger comes over as a String
  inventory: string[];
  history: string[];
  isDead: boolean;
  currentEnemy: any;
  acidStacks: number;
  attackDebuff: number;
}
