import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventoryScreen } from './inventory-screen';

describe('InventoryScreen', () => {
  let component: InventoryScreen;
  let fixture: ComponentFixture<InventoryScreen>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InventoryScreen]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InventoryScreen);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
