import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FavortiteSongCardComponent } from './favortite-song-card.component';

describe('FavortiteSongCardComponent', () => {
  let component: FavortiteSongCardComponent;
  let fixture: ComponentFixture<FavortiteSongCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FavortiteSongCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FavortiteSongCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
