import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StatPanel } from './stat-panel';

describe('StatPanel', () => {
  let component: StatPanel;
  let fixture: ComponentFixture<StatPanel>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StatPanel]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StatPanel);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
