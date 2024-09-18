import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { CartService } from './services/cart.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'store-frontend';

  constructor(public cartService: CartService, private router: Router) { }

  goToCart(): void {
    this.router.navigate(['/cart']);
  }

  goToHome(): void {
    this.router.navigate(['/']).then(() => {
      window.location.reload();
    });
  }
}