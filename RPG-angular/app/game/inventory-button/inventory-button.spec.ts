import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryButton } from './inventory-button';

describe('InventoryButton', () => {
  let component: InventoryButton;
  let fixture: ComponentFixture<InventoryButton>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventoryButton]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoryButton);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
