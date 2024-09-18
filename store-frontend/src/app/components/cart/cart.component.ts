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
  cartItems: { product: Product, quantity: number }[] = [];
  totalPrice: number = 0;

  constructor(private cartService: CartService, private orderService: OrderService) {}

  ngOnInit(): void {
    this.cartItems = this.cartService.getCartItems();
    this.calculateTotalPrice();
  }

  calculateTotalPrice(): void {
    this.totalPrice = this.cartItems.reduce((acc, item) => acc + (item.product.price * item.quantity), 0);
  }

  increaseQuantity(product: Product): void {
    this.cartService.addToCart(product);
    this.cartItems = this.cartService.getCartItems(); // Actualiza los items del carrito
    this.calculateTotalPrice(); // Recalcula el precio total
  }

  decreaseQuantity(product: Product): void {
    this.cartService.removeFromCart(product);
    this.cartItems = this.cartService.getCartItems(); // Actualiza los items del carrito
    this.calculateTotalPrice(); // Recalcula el precio total
  }

  removeItem(product: Product): void {
    const itemIndex = this.cartItems.findIndex(item => item.product.id === product.id);
    this.cartItems.splice(itemIndex, 1); // Elimina directamente el producto
    this.cartService.removeFromCart(product);
    this.calculateTotalPrice(); // Recalcula el precio total después de eliminar un producto
  }

  purchase(): void {
    const customerId = 1; // ID de cliente, puede ser dinámico en una app real
    
    const orderItems = this.cartItems.map(item => ({
      productId: item.product.id,
      quantity: item.quantity
    }));

    console.log("orderItems: " + orderItems);

    const order = {
      customerId: customerId,
      status: 'CREATED',
      orderItems: orderItems
    };

    console.log("order: " + order);

    this.orderService.createOrder(order).subscribe({
      next: () => {
        console.log("createOrder.subscribe: ");
        this.cartItems.forEach(item => {
          item.product.stock = Math.max(0, item.product.stock - item.quantity);
        });
        this.cartService.clearCart(); // Vacía el carrito después de la compra
        this.calculateTotalPrice(); // Recalcula el precio total tras la compra
        alert('Compra realizada con éxito');
        window.location.reload();
      },
      error: (err) => {
        console.error('Error en el proceso de compra:', err);
        alert('Error al procesar la compra');
      }
    });
  }
}