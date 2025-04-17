import { Component, Input } from '@angular/core';
import { Restaurant } from '../../model/Restaurant';
import { animate, state, style, transition, trigger } from '@angular/animations';

@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.scss'],
  animations: [
    trigger('zoomInOut', [
      state('hovered', style({
        transform: 'scale(1.05)'
      })),
      state('notHovered', style({
        transform: 'scale(1)'
      })),
      transition('notHovered <=> hovered', animate('300ms ease-in-out'))
    ]),
    trigger('fadeIn', [
      transition(':enter', [
        style({ opacity: 0 }),
        animate('500ms ease-in', style({ opacity: 1 })),
      ])
    ])
  ]
})
export class RestaurantComponent {
  @Input()
  restaurant?:Restaurant;

  hoverState: string = 'notHovered';

  constructor() {
    this.hoverState = 'notHovered';
  }

  onHover(hovered: boolean) {
    this.hoverState = hovered ? 'hovered' : 'notHovered';
  }
}
