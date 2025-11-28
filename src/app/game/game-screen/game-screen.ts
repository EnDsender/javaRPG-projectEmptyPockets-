import { Component, OnInit } from '@angular/core';
import { StoryLog } from '../story-log/story-log';
import { InputPanel } from '../input-panel/input-panel';
import { StatPanel } from '../stat-panel/stat-panel';
import { ActionBar } from '../action-bar/action-bar';
import { InventoryButton } from '../inventory-button/inventory-button';
import { InventoryScreen } from '../inventory-screen/inventory-screen';
import { CommonModule } from '@angular/common';
import { Player } from '../services/player.service';

@Component({
  selector: 'app-game-screen',
  imports: [
    StoryLog,
    InputPanel,
    StatPanel,
    ActionBar,
    InventoryButton,
    InventoryScreen,
    CommonModule
  ],
  templateUrl: './game-screen.html',
  styleUrl: './game-screen.css',
})
export class GameScreen implements OnInit {
  isInventoryOpen: boolean = false;

  constructor(private player: Player) {}

  ngOnInit(): void {
    this.player.fetchPlayerState();
  }

  toggleInventory(){
    this.isInventoryOpen = !this.isInventoryOpen;
  }

}