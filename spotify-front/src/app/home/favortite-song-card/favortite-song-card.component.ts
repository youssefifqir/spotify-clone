import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";

@Component({
  selector: 'app-favortite-song-card',
  standalone: true,
  imports: [
    RouterLink
  ],
  templateUrl: './favortite-song-card.component.html',
  styleUrl: './favortite-song-card.component.scss'
})
export class FavortiteSongCardComponent {

}
