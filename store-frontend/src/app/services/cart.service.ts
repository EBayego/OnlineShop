import { Injectable } from '@angular/core';
import { Product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartItems: Product[] = [];

  constructor() {
    this.loadCart();  // Cargar el carrito de localStorage al inicio
  }

  private isLocalStorageAvailable(): boolean {
    return typeof window !== 'undefined' && typeof localStorage !== 'undefined';
  }

  private saveCart(): void {
    if (this.isLocalStorageAvailable()) {
      localStorage.setItem('cart', JSON.stringify(this.cartItems));
    }
  }

  private loadCart(): void {
    if (this.isLocalStorageAvailable()) {
      const savedCart = localStorage.getItem('cart');
      if (savedCart) {
        this.cartItems = JSON.parse(savedCart);
      }
    }
  }

  addToCart(product: Product): void {
    this.cartItems.push(product);
    this.saveCart();  // Guardar en localStorage después de añadir el producto
  }

  getCartItems(): Product[] {
    return this.cartItems;
  }

  getCartItemCount(): number {
    return this.cartItems.length;
  }

  removeFromCart(product: Product): void {
    const index = this.cartItems.findIndex(item => item.id === product.id);
    if (index !== -1) {
      this.cartItems.splice(index, 1);
      this.saveCart();  // Guardar en localStorage después de eliminar el producto
    }
  }

  clearCart(): void {
    this.cartItems = [];
    this.saveCart();  // Guardar en localStorage después de vaciar el carrito
  }
}