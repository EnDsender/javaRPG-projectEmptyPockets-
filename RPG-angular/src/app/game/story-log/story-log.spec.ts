import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StoryLog } from './story-log';

describe('StoryLog', () => {
  let component: StoryLog;
  let fixture: ComponentFixture<StoryLog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StoryLog]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StoryLog);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
