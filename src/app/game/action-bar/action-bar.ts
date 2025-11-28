import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { PlayerState } from '../models/player-state';
import { Player } from '../services/player.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-action-bar',
  imports: [CommonModule],
  templateUrl: './action-bar.html',
  styleUrl: './action-bar.css',
})
export class ActionBar {
  playerState$!: Observable<PlayerState | null>;

  constructor(private player: Player) {}

  ngOnInit(): void {
    this.playerState$ = this.player.playerState$;
  }
}
