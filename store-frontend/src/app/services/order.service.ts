import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private apiUrl = environment.apiUrls.orderService;

  constructor(private http: HttpClient) { }

  createOrder(products: any[]): Observable<any> {
    const orderData = { productosId: products.map(p => p.id) };
    return this.http.post<any>(this.apiUrl, orderData);
  }
}