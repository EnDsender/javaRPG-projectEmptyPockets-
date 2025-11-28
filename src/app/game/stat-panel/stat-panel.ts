import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { PlayerState } from '../models/player-state';
import { Player } from '../services/player.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-stat-panel',
  imports: [CommonModule],
  templateUrl: './stat-panel.html',
  styleUrl: './stat-panel.css',
  standalone: true,
})
export class StatPanel {
  playerState$!: Observable<PlayerState | null>;

  constructor(private player: Player){}

  ngOnInit(): void{
    this.playerState$ = this.player.playerState$;
  }

}