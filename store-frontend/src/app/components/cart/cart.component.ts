import { Component, OnInit } from '@angular/core';
import { CartService } from '../../services/cart.service';
import { Product } from '../../models/product';
import { OrderService } from '../../services/order.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {
  cartItems: Product[] = [];

  constructor(private cartService: CartService, private orderService: OrderService) {}

  ngOnInit(): void {
    this.cartItems = this.cartService.getCartItems();
  }

  purchase(): void {
    const customerId = 1; // Aquí puedes poner un ID de cliente estático o manejarlo de otra forma
    const productosId = this.cartItems.map(item => item.id);

    // Crea una orden
    this.orderService.createOrder({ customerId, productosId, status: 'CREATED' }).subscribe(() => {
      this.cartItems.forEach(item => {
        // Actualiza el stock de cada producto
        item.stock -= 1; // Reduce en 1 el stock, dependiendo de cuántos se compren
        this.cartService.clearCart(); // Vacía el carrito después de la compra
      });
      alert('Compra realizada con éxito');
    });
  }

  removeItem(product: Product): void {
    this.cartService.removeFromCart(product);
  }
}