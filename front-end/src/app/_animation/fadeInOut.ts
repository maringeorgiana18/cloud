import {trigger, animate, transition, style, state} from '@angular/animations';

export const fadeAnimation =
  trigger('fadeInOut', [
    state('void', style({
      opacity: 0
    })),
    transition('void <=> *', animate(1150)),
  ]);
