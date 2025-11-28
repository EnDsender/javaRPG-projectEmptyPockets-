import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, catchError, throwError } from 'rxjs';
import { PlayerState } from '../models/player-state';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class Player {
  private apiUrl = 'http://localhost:8080/api/player/state';

  private playerStateSubject = new BehaviorSubject<PlayerState | null>(null);
  public playerState$ = this.playerStateSubject.asObservable();

  constructor(private http: HttpClient){}

  fetchPlayerState(): void {
    this.http.get<PlayerState>(this.apiUrl)
    .pipe(
      catchError((error) =>{
        console.error('Error fetching player state from backend', error);
        return throwError(() => new Error('Player data fetch failed'));
      })
    )
    .subscribe((playerData) =>{
      this.playerStateSubject.next(playerData);
    });
  }
}
