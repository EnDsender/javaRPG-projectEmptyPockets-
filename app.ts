import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { GameScreen } from './game/game-screen/game-screen';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, GameScreen],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('rpg-app');
}
