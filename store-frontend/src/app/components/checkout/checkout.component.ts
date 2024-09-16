import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  constructor(private orderService: OrderService) { }

  ngOnInit(): void { }

  checkout(): void {
    const cart = localStorage.getItem('cart');
    const products = cart ? JSON.parse(cart) : [];

    if (products.length > 0) {
      this.orderService.createOrder(products).subscribe(response => {
        console.log('Order created successfully!', response);
        localStorage.removeItem('cart');
      }, error => {
        console.error('Error creating order', error);
      });
    }
  }
}