import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-inventory-screen',
  imports: [],
  templateUrl: './inventory-screen.html',
  styleUrl: './inventory-screen.css',
  standalone: true,
})
export class InventoryScreen {
  @Output() close = new EventEmitter<void>();

  closeInventory() {
    this.close.emit();
  }
}
