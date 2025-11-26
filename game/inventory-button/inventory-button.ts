import { Component,Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-inventory-button',
  imports: [],
  templateUrl: './inventory-button.html',
  styleUrl: './inventory-button.css',
  standalone: true,
})
export class InventoryButton {
  @Output() openInventoryRequest = new EventEmitter<void>();

  openInventory(){
    this.openInventoryRequest.emit();
  }
}
