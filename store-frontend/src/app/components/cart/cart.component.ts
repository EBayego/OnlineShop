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
  totalPrice: number = 0;

  constructor(private cartService: CartService, private orderService: OrderService) {}

  ngOnInit(): void {
    this.cartItems = this.cartService.getCartItems();
    this.calculateTotalPrice();
  }

  calculateTotalPrice(): void {
    this.totalPrice = this.cartItems.reduce((acc, item) => acc + item.price, 0);
  }

  purchase(): void {
    const customerId = 1; // ID de cliente, puede ser dinámico en una app real
    const productosId = this.cartItems.map(item => item.id);
  
    // Crea una orden
    this.orderService.createOrder({ customerId, productosId, status: 'CREATED' }).subscribe(() => {
      this.cartItems.forEach(item => {
        // Actualiza el stock de cada producto
        item.stock = Math.max(0, item.stock - 1); // Asegurarse de no tener stock negativo
      });
      this.cartService.clearCart(); // Vacía el carrito después de la compra
      this.calculateTotalPrice(); // Recalcula el precio total tras la compra
      alert('Compra realizada con éxito');
    });
  }  

  removeItem(product: Product): void {
    this.cartService.removeFromCart(product);
    this.cartItems = this.cartService.getCartItems(); // Actualiza los items del carrito
    this.calculateTotalPrice(); // Recalcula el precio total después de eliminar un producto
  }
}