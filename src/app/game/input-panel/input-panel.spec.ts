import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InputPanel } from './input-panel';

describe('InputPanel', () => {
  let component: InputPanel;
  let fixture: ComponentFixture<InputPanel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InputPanel]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InputPanel);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
