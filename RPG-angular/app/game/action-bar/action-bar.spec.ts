import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ActionBar } from './action-bar';

describe('ActionBar', () => {
  let component: ActionBar;
  let fixture: ComponentFixture<ActionBar>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ActionBar]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ActionBar);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
