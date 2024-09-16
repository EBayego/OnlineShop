import { Component, OnInit } from '@angular/core';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: any[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productService.getProducts().subscribe((data: any) => {
      this.products = data;
    }, error => {
      console.error('Error fetching products', error);
    });
  }

  addToCart(product: any): void {
    let cart = localStorage.getItem('cart');
    let cartItems = cart ? JSON.parse(cart) : [];
    cartItems.push(product);
    localStorage.setItem('cart', JSON.stringify(cartItems));
  }
}