import { Injectable } from '@angular/core';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItems: { product: Product, quantity: number }[] = [];

  constructor() {}

  addToCart(product: Product): void {
    const existingItem = this.cartItems.find(item => item.product.id === product.id);
    if (existingItem) {
      existingItem.quantity++; // Incrementa la cantidad si el producto ya está en el carrito
    } else {
      this.cartItems.push({ product, quantity: 1 }); // Si no existe, agrega el producto con cantidad 1
    }
  }

  getCartItems(): { product: Product, quantity: number }[] {
    return this.cartItems;
  }

  getCartItemCount(): number {
    return this.cartItems.reduce((acc, item) => acc + item.quantity, 0); // Suma las cantidades
  }

  removeFromCart(product: Product): void {
    const index = this.cartItems.findIndex(item => item.product.id === product.id);
    if (index !== -1) {
      const item = this.cartItems[index];
      if (item.quantity > 1) {
        item.quantity--; // Si hay más de uno, solo reduce la cantidad
      } else {
        this.cartItems.splice(index, 1); // Si solo hay uno, lo elimina
      }
    }
  }

  clearCart(): void {
    this.cartItems = [];
  }
}