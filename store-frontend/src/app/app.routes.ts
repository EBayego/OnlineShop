import { Routes } from '@angular/router';
import { ProductsComponent } from './components/products/products.component';
import { CartComponent } from './components/cart/cart.component';

export const routes: Routes = [
  { path: '', component: ProductsComponent },  // Página principal (listado de productos)
  { path: 'cart', component: CartComponent },   // Carrito de compras
  { path: '**', redirectTo: '', pathMatch: 'full' }  // Redirigir a la página principal si no se encuentra una ruta
];